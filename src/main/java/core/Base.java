package core;

import constants.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Base {
    private static String fields;
    private static String maxResults;
    private static String expand;
    private static String startAt;
    private static int totalIssues;

    public static String getRequest(String urlString){
        URL url;
        //System.out.println("GET_REQUEST: " + urlString);
        try {
            url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", Constants.AUTHORIZATION);
            con.setDoOutput(true);
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder stringBuilder = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    stringBuilder.append(responseLine.trim());
                }
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getTotalIssues() {
        return totalIssues;
    }

    public static HashMap<String, Float> cantidadDeHHEnEstado(String jql, String estado){
        // Reiniciar estadisticas
        Base.setStartAt("0");
        Base.setExpand("changelog");
        Base.setMaxResults("100");
        Base.setFields("status,statuscategorychangedate");

        // Variables

        HashMap<String, Float> horas = new HashMap<String, Float>();

        List<String> changeList = Base.obtenerEstadosDesdeJQL(jql);

        Base.filtrarListaPorEstado(changeList, estado);
        Base.limpiarFecha(changeList);

        float horasTotales = 0;

        for (int i = 1;i<changeList.size();i++){
            String fechaInicio = changeList.get(i).split(",")[1];
            String fechaFin = changeList.get(i-1).split(",")[1];
            horasTotales += calcularHorasLaborales(fechaInicio,fechaFin);
            i++;
        }

        horas.put("horasTotales",horasTotales);
        horas.put("horasPromedio",horasTotales/Base.getTotalIssues());
        horas.put("totalIssues", (float) getTotalIssues());


        return horas;
    }

    private static void setTotalIssues(int totalIssues) {
        Base.totalIssues = totalIssues;
    }

    public static void setMaxResults(String maxResults) {
        Base.maxResults = maxResults;
    }

    public static void setFields(String fields) {
        Base.fields = fields;
    }

    public static void setExpand(String expand) {
        Base.expand = expand;
    }

    public static void setStartAt(String startAt) {
        Base.startAt = startAt;
    }

    private static JSONObject buscarJQL(String jql){
        String urlGET = Constants.METODO_SEARCH + "jql=" + limpiarJql(jql);
        if (fields != null){
            urlGET += "&fields=" + fields;
        }
        if (maxResults != null){
            urlGET += "&maxResults=" + maxResults;
        }
        if (expand != null){
            urlGET += "&expand=" + expand;
        }
        if (startAt != null){
            urlGET += "&startAt=" + startAt;
        }
        return new JSONObject(Objects.requireNonNull(getRequest(urlGET)));
    }
    private static String limpiarJql(String jql){
        String newJql = jql.replaceAll(" ","%20");
        newJql = newJql.replaceAll("=","%3D");
        return newJql;
    }

    public static List<String> filtrarListaPorEstado(List<String> lista,String estado){
        for (int i = 0;i < lista.size(); i++){
            String aux = lista.get(i);
            if (!aux.contains(estado)){
                lista.remove(i);
                i--;
            }
        }
        return lista;
    }

    public static List<String> limpiarFecha(List<String> lista){
        for (int i = 0;i < lista.size(); i++){
            lista.set(i,lista.get(i).replaceAll("\\.\\d\\d\\d-\\d\\d\\d\\d",""));
        }
        return lista;
    }


    public static List<String> obtenerEstadosDesdeJQL(String jql){
        JSONObject respuesta = Base.buscarJQL(jql);
        JSONArray issues = respuesta.getJSONArray("issues");
        int totalIssues = (int) respuesta.get("total");
        setTotalIssues(totalIssues);
        int iteraciones = totalIssues/Integer.parseInt(maxResults);
        int iteracion = 1;
        List<String> changeList = new ArrayList<>();

        // Recorrer el response obtenido del metodo search de jira y extraer la informacion relevante
        for (int i = 0;i < issues.length();i++){
            JSONObject issue = issues.getJSONObject(i);
            // Key: ej: QA-3232
            String key = issue.getString("key");
            JSONObject changelog = issue.getJSONObject("changelog");
            JSONArray histories = changelog.getJSONArray("histories");
            for (int j = 0;j < histories.length(); j++){
                JSONObject historie = histories.getJSONObject(j);
                // Created: ej: 2023-04-03T17:50:25.393-0400
                String created = historie.getString("created");
                JSONArray items = historie.getJSONArray("items");
                for (int k = 0;k < items.length();k++){
                    JSONObject item = items.getJSONObject(k);
                    // Field: ej: status, FromString: ej: EN PROCESO, ToString: ej: LISTO
                    String field = item.getString("field");
                    String fromString = item.get("fromString").toString();
                    String toString = item.get("toString").toString();
                    if (field.toLowerCase(Locale.ROOT).equals("status")){
                        changeList.add(key + "," + created.replaceAll("\\.\\d\\d\\d-\\d\\d\\d\\d","") + "," + field + "," + fromString + "," + toString);
                    }
                }
            }
        }


        // Se establece un loop para recorrer productos que tengan más de 100 issues
        while (iteracion <= iteraciones){
            int startAt = iteracion*Integer.parseInt(maxResults);
            Base.setStartAt(String.valueOf(startAt));
            //
            respuesta = Base.buscarJQL(jql);
            issues = respuesta.getJSONArray("issues");
            // Recorrer el response obtenido del metodo search de jira y extraer la informacion relevante
            for (int i = 0;i < issues.length();i++){
                JSONObject issue = issues.getJSONObject(i);
                // Key: ej: QA-3232
                String key = issue.getString("key");
                JSONObject changelog = issue.getJSONObject("changelog");
                JSONArray histories = changelog.getJSONArray("histories");
                for (int j = 0;j < histories.length(); j++){
                    JSONObject historie = histories.getJSONObject(j);
                    // Created: ej: 2023-04-03T17:50:25.393-0400
                    String created = historie.getString("created");
                    JSONArray items = historie.getJSONArray("items");
                    for (int k = 0;k < items.length();k++){
                        JSONObject item = items.getJSONObject(k);
                        // Field: ej: status, FromString: ej: EN PROCESO, ToString: ej: LISTO
                        String field = item.getString("field");
                        String fromString = item.get("fromString").toString();
                        String toString = item.get("toString").toString();
                        if (field.toLowerCase(Locale.ROOT).equals("status")){
                            changeList.add(key + "," + created.replaceAll("\\.\\d\\d\\d-\\d\\d\\d\\d","") + "," + field + "," + fromString + "," + toString);
                        }
                    }
                }
            }
            iteracion++;
        }
        return changeList;
    }
    public static int calcularHorasLaborales(String fecha1,String fecha2){
        // Fecha1: fecha inicial
        // Fecha2: fecha final
        LocalDateTime inicio = LocalDateTime.parse(fecha1);
        LocalDateTime fin = LocalDateTime.parse(fecha2);

        // Redondear fecha inicio
        inicio = inicio.plus(inicio.getMinute()*-1, ChronoUnit.MINUTES);
        inicio = inicio.plus(inicio.getSecond()*-1,ChronoUnit.SECONDS);
        // Redondear fecha fin
        fin = fin.plus(60-fin.getMinute(),ChronoUnit.MINUTES);
        fin = fin.plus(fin.getSecond()*-1,ChronoUnit.SECONDS);


        int horasLaborales = 0;

        LocalDateTime fecha = inicio;

        while (fecha.isBefore(fin)) {
            DayOfWeek dia = fecha.getDayOfWeek();
            int hora = fecha.getHour();

            if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY && hora >= 9 && hora < 18) {
                horasLaborales++;
            }

            fecha = fecha.plus(1, ChronoUnit.HOURS);
        }

        //System.out.println("Horas laborales entre " + inicio + " y " + fin + ": " + horasLaborales);
        return horasLaborales;
    }

    public static List<String> obtenerEstadoActualYUltimaActualizacion(String jql){
        JSONObject respuesta = Base.buscarJQL(jql);
        JSONArray issues = respuesta.getJSONArray("issues");
        int totalIssues = (int) respuesta.get("total");
        setTotalIssues(totalIssues);
        int iteraciones = totalIssues/Integer.parseInt(maxResults);
        int iteracion = 1;
        List<String> changeList = new ArrayList<>();

        // Recorrer el response obtenido del metodo search de jira y extraer la informacion relevante
        for (int i = 0;i < issues.length();i++){
            JSONObject issue = issues.getJSONObject(i);
            // Key: ej: QA-3232
            String key = issue.getString("key");
            JSONObject fields = issue.getJSONObject("fields");
            String statusCategoryChangeDate = (String) fields.get("statuscategorychangedate");
            JSONObject status = fields.getJSONObject("status");
            String estadoActual = status.getString("name");

            changeList.add(key+","+estadoActual+","+statusCategoryChangeDate.replaceAll("\\.\\d\\d\\d-\\d\\d\\d\\d",""));
        }


        // Se establece un loop para recorrer productos que tengan más de 100 issues
        while (iteracion <= iteraciones){
            int startAt = iteracion*Integer.parseInt(maxResults);
            Base.setStartAt(String.valueOf(startAt));
            //
            respuesta = Base.buscarJQL(jql);
            issues = respuesta.getJSONArray("issues");
            // Recorrer el response obtenido del metodo search de jira y extraer la informacion relevante
            for (int i = 0;i < issues.length();i++){
                JSONObject issue = issues.getJSONObject(i);
                // Key: ej: QA-3232
                String key = issue.getString("key");
                JSONObject fields = issue.getJSONObject("fields");
                String statusCategoryChangeDate = (String) fields.get("statuscategorychangedate");
                JSONObject status = fields.getJSONObject("status");
                String estadoActual = status.getString("name");

                changeList.add(key+","+estadoActual+","+statusCategoryChangeDate.replaceAll("\\.\\d\\d\\d-\\d\\d\\d\\d",""));
            }
            iteracion++;
        }
        return changeList;
    }

}
