import constants.Automatizadores;
import core.Base;

import java.util.HashMap;

public class pruebas {
    public static void main(String[] args) {

        HashMap<String,Float> result;
        result = Base.cantidadDeHHEnEstado(Automatizadores.aut2.getJql(), "EN PROCESO DE AUTOMATIZACIÓN" );
        System.out.println(result.get("totalIssues") + " - " + result.get("horasTotales") + " - "+ result.get("horasPromedio"));
    }
}
