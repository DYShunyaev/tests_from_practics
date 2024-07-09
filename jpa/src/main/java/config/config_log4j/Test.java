package config.config_log4j;


import utils.database.CommonSqlScript;
import utils.database.SqlExecutor;
import utils.file_helper.FileHelper;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        SqlExecutor sqlExecutor = new SqlExecutor();
//        sqlExecutor.createQuerySql(FileHelper.readXml(CommonSqlScript.CREATE_TABLE_USERS));
//        List<String> s = sqlExecutor.createQuerySql(FileHelper.readXml(CommonSqlScript.SHOW_TABLES)).getTableNames();
        int a = 0;

    }
}
