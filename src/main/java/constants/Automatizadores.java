package constants;

import static constants.Constants.configReader;

public enum Automatizadores {

    aut1(configReader.getProperty("aut.1"),Constants.JQL_AUT_1),
    aut2(configReader.getProperty("aut.2"),Constants.JQL_AUT_2),
    aut3(configReader.getProperty("aut.3"),Constants.JQL_AUT_3),
    aut4(configReader.getProperty("aut.4"),Constants.JQL_AUT_4),
    aut5(configReader.getProperty("aut.5"),Constants.JQL_AUT_5),
    aut6(configReader.getProperty("aut.6"),Constants.JQL_AUT_6),
    aut7(configReader.getProperty("aut.7"),Constants.JQL_AUT_7),
    aut8(configReader.getProperty("aut.8"),Constants.JQL_AUT_8),
    aut9(configReader.getProperty("aut.9"),Constants.JQL_AUT_9),
    aut10(configReader.getProperty("aut.10"),Constants.JQL_AUT_10);


    private final String nombre;
    private final String jql;

    Automatizadores(String nombre, String jql) {
        this.nombre = nombre;
        this.jql = jql;
    }

    public String getNombre() {
        return nombre;
    }

    public String getJql() {
        return jql;
    }

}
