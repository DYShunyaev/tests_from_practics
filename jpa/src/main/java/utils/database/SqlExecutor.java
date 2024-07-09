package utils.database;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.gson.Gson;
import config.config_log4j.Test;
import ddo.GoodsRowDDO;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.file_helper.FileHelper;
import utils.json_utils.JsonUtil;

import java.sql.*;
import java.util.*;

public class SqlExecutor {

    private static ResultSet resultSet;
    Logger logger = LoggerFactory.getLogger(Test.class);

    @SneakyThrows
    public SqlExecutor createQuerySql(CommonSqlScript sqlScript) {
        String query = FileHelper.readXml(sqlScript);
        Statement statement = null;
        try {
            statement = DataBaseConnection.createConnection()
                    .getConnection()
                    .createStatement();
            resultSet = statement.executeQuery(query);
            logger.info(query);
        } catch (SQLException e) {
            statement.execute(query);
            logger.info(query);
        }
        return this;
    }

    @SneakyThrows
    public SqlExecutor createQuerySql(CommonSqlScript sqlScript, Object... values) {
        String query = FileHelper.readXml(sqlScript);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DataBaseConnection.createConnection().getConnection().prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            logger.info("Выполнен SQL запрос: {}", query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            assert preparedStatement != null;
            preparedStatement.execute();
        }
        return this;
    }

    public List<GoodsRowDDO> getSelectResult() {
        List<JsonObject> queryResult = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                JsonObject jsonObject = new JsonObject();
                for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                    String key = metaData.getColumnName(i).replace("FOOD_", "").toLowerCase(Locale.ROOT);
                    Object value = resultSet.getObject(metaData.getColumnName(i));
                    if (key.equals("exotic")) {
                        if (resultSet.getInt((metaData.getColumnName(i))) == 1) value = true;
                        else value = false;
                    }
                    jsonObject.put(key, value);
                }
                queryResult.add(jsonObject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        return JsonUtil.deserializeListFromJson(gson.toJson(queryResult), GoodsRowDDO.class);
    }

    public List<String> getTableNames() {
        List<String> tablesName = new ArrayList<>();
        try {
            while (resultSet.next()) {
                tablesName.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tablesName;
    }
}