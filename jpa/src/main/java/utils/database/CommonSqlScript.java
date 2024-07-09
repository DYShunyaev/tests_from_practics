package utils.database;

import lombok.Getter;

@Getter
public enum CommonSqlScript {
    GET_ALL(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    SET_NEW_DATA(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    DELETE_WITH_NAME(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    SET_NAME_WITH_OLD_NAME(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    GET_DATA_WITH_NAME(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    CREATE_TABLE_USERS(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    SHOW_TABLES(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML),
    DROP_TABLE_USERS(CommonSqlScript.PATH_TO_SQL_SCRIPTS_XML);
    private static final String PATH_TO_SQL_SCRIPTS_XML = "D:/Java/tests_from_practics/jpa/src/main/resources/sql_skripts.xml";
    private final  String pathToXml;

    CommonSqlScript(String pathToXml) {
        this.pathToXml = pathToXml;
    }
}
