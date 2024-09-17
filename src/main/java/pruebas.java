import core.Base;

import java.util.HashMap;

public class pruebas {
    public static void main(String[] args) {

        String jql = "project = EM AND issuetype = Automatización AND assignee = 6317ac8262fe1e6eac6da1ea and Sprint = 1217";

        String enProceso = "EN CURSO";

        System.out.println(jql);
        HashMap<String,Float> result;
        result = Base.cantidadDeHHEnEstado(jql, enProceso);
        System.out.println(result.get("totalIssues") + " - " + result.get("horasTotales") + " - "+ result.get("horasPromedio"));
    }
}
