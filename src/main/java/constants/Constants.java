package constants;

import utils.ConfigReader;

public class Constants {

    public static ConfigReader configReader = new ConfigReader("config.properties");
    public static final String HOST = configReader.getProperty("host");
    public static final String AUTHORIZATION = configReader.getProperty("authorization");
    public static final String METODO_SEARCH = HOST + "/rest/api/3/search?";
    public static final String fecha = "'2023-08-11'";
    public static final String JQL_AUT_1 = "project = QA AND issuetype = Test AND assignee = 6317ac8262fe1e6eac6da1ea AND STATUS Changed AFTER " + fecha;
    public static final String JQL_AUT_2 = "project = QA AND issuetype = Test AND assignee = \"712020:3331b360-3f38-4bb3-bb83-277af123c74c\" AND STATUS Changed AFTER " + fecha;
    public static final String JQL_AUT_3 = "project = QA AND issuetype = Test AND assignee = 622b63824160640069cb7aa4 AND STATUS Changed AFTER " + fecha;
    public static final String JQL_AUT_4 = "project = QA AND issuetype = Test AND assignee = 622b636b6a4c4c0070b2d021 AND STATUS Changed AFTER " + fecha;
    public static final String JQL_AUT_5 = "project = QA AND issuetype = Test AND assignee = 62c34d032c528400c9b5b3f5 AND STATUS Changed AFTER " + fecha;
    public static final String JQL_AUT_6 = "project = QA AND issuetype = Test AND assignee = 61e57e7e7ae0dc006a8bda39 AND STATUS Changed AFTER " + fecha;
    public static final String JQL_AUT_7 = "project = QA AND issuetype = Test AND assignee = 613fb51feaef340069630dda AND STATUS Changed AFTER " + fecha;
}
