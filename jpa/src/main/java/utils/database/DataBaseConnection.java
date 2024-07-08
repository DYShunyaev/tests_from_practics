package utils.database;

import config_provider.ConfigProvider;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public final class DataBaseConnection {
    private static DataBaseConnection dataBaseConnection;
    private final Connection connection;

    private DataBaseConnection() throws RuntimeException {
        String url = ConfigProvider.JPA_URL;
        String username = ConfigProvider.JPA_USERNAME;
        String password = ConfigProvider.JPA_PASSWORD;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataBaseConnection createConnection() {
        if (dataBaseConnection == null) dataBaseConnection = new DataBaseConnection();
        return dataBaseConnection;
    }
}
