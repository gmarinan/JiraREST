package constants;

import utils.ConfigReader;

public class Constants {

    public static ConfigReader configReader = new ConfigReader("config.properties");
    public static final String HOST = configReader.getProperty("host");
    public static final String AUTHORIZATION = configReader.getProperty("authorization");
    public static final String METODO_SEARCH = HOST + "/rest/api/3/search?";
    public static final String FECHA = "2024-07-01";
    public static final String FECHA_FIN = "2024-07-09";
    public static final String JQL_AUT_1 = "project = QA AND issuetype = Test AND assignee = 6317ac8262fe1e6eac6da1ea AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_2 = "project = QA AND issuetype = Test AND assignee = \"712020:363f9a20-686c-4e59-9477-e0f99e4a7f3e\" AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_3 = "project = QA AND issuetype = Test AND assignee = 622b63824160640069cb7aa4 AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_4 = "project = QA AND issuetype = Test AND assignee = 622b636b6a4c4c0070b2d021 AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_5 = "project = QA AND issuetype = Test AND assignee = 62c34d032c528400c9b5b3f5 AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_6 = "project = QA AND issuetype = Test AND assignee = 61e57e7e7ae0dc006a8bda39 AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_7 = "project = QA AND issuetype = Test AND assignee = 613fb51feaef340069630dda AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_8 = "project = QA AND issuetype = Test AND assignee = 712020:1b83bad1-d901-4033-8291-189c4927d38c AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_9 = "project = QA AND issuetype = Test AND assignee = 712020:7388cf4a-57c8-4ee1-baa4-bb09a1dcdc3a AND STATUS Changed AFTER '" + FECHA + "'";
    public static final String JQL_AUT_10 = "project = QA AND issuetype = Test AND assignee = 712020:c9eb5c62-5f9a-46c4-b9ad-d41476128827 AND STATUS Changed AFTER '" + FECHA + "'";

}
