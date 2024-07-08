package utils.database;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.gson.Gson;
import ddo.GoodsRowDDO;
import lombok.SneakyThrows;
import utils.json_utils.JsonUtil;

import java.sql.*;
import java.util.*;

public class SqlExecutor {

    private static ResultSet resultSet;

    public SqlExecutor createQuerySql(String query) {
        try {
            Statement statement = DataBaseConnection.createConnection().getConnection().createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @SneakyThrows
    public SqlExecutor createQuerySql(String query, String... values) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DataBaseConnection.createConnection().getConnection().prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i+1,values[i]);
            }
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
                    String key = metaData.getColumnName(i).replace("FOOD_","").toLowerCase(Locale.ROOT);
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
}
