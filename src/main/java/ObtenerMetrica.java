import core.Base;

public class ObtenerMetrica {
    public static void main(String[] args) {
        String[] fechas = {"2023-03-01",
                            "2023-03-10",
                            "2023-03-20",
                            "2023-03-30",
                            "2023-04-10",
                            "2023-04-20",
                            "2023-04-30",
                            "2023-05-10",
                            "2023-05-20",
                            "2023-05-30",
                            "2023-06-10",
                            "2023-06-20",
                            "2023-06-30",
                            "2023-07-10",
                            "2023-07-20",
                            "2023-07-30",
                            "2023-08-10",
                            "2023-08-20",
                            "2023-08-30",
                            "2023-09-10",
                            "2023-09-20",
                            "2023-09-30",
                            "2023-10-10"};
        

        for(int i=0;i<fechas.length;i++){   
            String fecha = fechas[i];
            String jqlTodos = "project = QA AND \"Linea de Desarrollo[Dropdown]\" = \"Equipo Medbenefit\" AND issuetype = Test AND status IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") AND status WAS IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\", \"EN PROCESO REFACTORIZACIÓN\") ON "+ fecha;
            String jqlAutomatizados = "project = QA AND \"Linea de Desarrollo[Dropdown]\" = \"Equipo Medbenefit\" AND STATUS = \"AUTOMATIZADO\" AND issuetype = Test AND status IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") AND status WAS IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") ON "+ fecha;
            String jqlBacklog = "project = QA AND \"Linea de Desarrollo[Dropdown]\" = \"Equipo Medbenefit\" AND STATUS = \"BACKLOG AUTOMATIZACIÓN\" AND issuetype = Test AND status IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") AND status WAS IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") ON "+ fecha;
            String jqlEnProceso = "project = QA AND \"Linea de Desarrollo[Dropdown]\" = \"Equipo Medbenefit\" AND STATUS = \"EN PROCESO DE AUTOMATIZACIÓN\" AND issuetype = Test AND status IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") AND status WAS IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") ON "+ fecha;
            String jqlBloqueado = "project = QA AND \"Linea de Desarrollo[Dropdown]\" = \"Equipo Medbenefit\" AND STATUS = \"BLOQUEADO\" AND issuetype = Test AND status IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") AND status WAS IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") ON "+ fecha;
            String jqlRefactorizacion = "project = QA AND \"Linea de Desarrollo[Dropdown]\" = \"Equipo Medbenefit\" AND STATUS = \"REFACTORIZACIÓN\" AND issuetype = Test AND status IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") AND status WAS IN (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") ON "+ fecha;
        
            String response = Base.buscarJQL(jqlTodos).get("total").toString();
            String responseAutomatizados = Base.buscarJQL(jqlAutomatizados).get("total").toString();
            String responseBacklog = Base.buscarJQL(jqlBacklog).get("total").toString();
            String responseEnProceso = Base.buscarJQL(jqlEnProceso).get("total").toString();
            String responseBloqueado = Base.buscarJQL(jqlBloqueado).get("total").toString();
            String responseRefactorizacion = Base.buscarJQL(jqlRefactorizacion).get("total").toString();
            System.out.println(fecha +";" + response + ";" + responseAutomatizados + ";" + responseBacklog + ";" + responseEnProceso + 
            ";" + responseBloqueado + ";" + responseRefactorizacion);
        }

        
    }
    
}
