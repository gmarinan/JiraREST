import constants.Automatizadores;
import core.Base;

import java.util.HashMap;

public class pruebas {
    public static void main(String[] args) {

        String jql = "project = QA AND issuetype = Test AND assignee = 622b636b6a4c4c0070b2d021 AND STATUS Changed AFTER '2024-01-01'";

        String enProceso = "EN PROCESO DE AUTOMATIZACIÓN";
        String refactor = "REFACTORIZACIÓN";

        System.out.println(jql);
        HashMap<String,Float> result;
        result = Base.cantidadDeHHEnEstado(jql, enProceso);
        System.out.println(result.get("totalIssues") + " - " + result.get("horasTotales") + " - "+ result.get("horasPromedio"));
    }
}
