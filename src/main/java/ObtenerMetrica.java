import core.Base;

public class ObtenerMetrica {
    public static void main(String[] args) {
        String fecha = "\"2023-08-01\"";

        String jqlTodos = "project = QA AND issuetype = Test AND (status changed to (\"AUTOMATIZADO\", \"BACKLOG AUTOMATIZACIÓN\", \"EN PROCESO DE AUTOMATIZACIÓN\", \"BLOQUEADO\", \"REFACTORIZACIÓN\") before "+fecha+")";
        String jqlAutomatizados = "project = QA AND issuetype = Test AND status WAS IN (\"AUTOMATIZADO\") before " + fecha;
        String response = Base.buscarJQL(jqlTodos).get("total").toString();
        String responseAutomatizados = Base.buscarJQL(jqlAutomatizados).get("total").toString();
        System.out.println("Total de casos en fecha: " + response + " Automatizados: " + responseAutomatizados);
    }
    
}
