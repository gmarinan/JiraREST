import constants.Automatizadores;
import core.Base;
import utils.CSVManager;

import java.util.*;

public class Main {
    public static int MAX_RESULTS = 100;
    public static void main(String[] args) {

        HashMap<String, Float> horasEnProceso = new HashMap<String, Float>();
        HashMap<String, Float> horasRefactorizando = new HashMap<String, Float>();

        CSVManager csvManager = new CSVManager();
        String csvDir = System.getProperty("user.home") + "\\Documents\\MetricasAutomatizacion\\metricasPRUEBA.xls";
        csvManager.deleteFile(csvDir);
        csvManager.createCSV(csvDir);
        csvManager.addLine(csvDir,"automatizador;casosTotales;hhTotalesAutomatizando;hhPromedioAutomatizando;hhTotalesRefactorizando;hhPromedioRefactorizando");

        for (Automatizadores automatizador: Automatizadores.values()){
            System.out.println(automatizador.getNombre());
            horasEnProceso = Base.cantidadDeHHEnEstado(automatizador.getJql(),"EN PROCESO DE AUTOMATIZACIÓN");
            horasRefactorizando = Base.cantidadDeHHEnEstado(automatizador.getJql(),"REFACTORIZACIÓN");
            csvManager.addLine(csvDir,automatizador.getNombre() + ";" + horasEnProceso.get("totalIssues").intValue() + ";"
                    + horasEnProceso.get("horasTotales").intValue() + ";" + horasEnProceso.get("horasPromedio").toString().replaceAll("\\.",",") + ";"
                    + horasRefactorizando.get("horasTotales").intValue() + ";" + horasRefactorizando.get("horasPromedio").toString().replaceAll("\\.",","));

        }

    }


}