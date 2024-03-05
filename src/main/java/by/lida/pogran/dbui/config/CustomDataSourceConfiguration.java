package by.lida.pogran.dbui.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor
public class CustomDataSourceConfiguration {

    private static volatile CustomDataSourceConfiguration instance;
    private static String driverClassName;
    private static String url;
    private static String username;
    private static String password;

    public static CustomDataSourceConfiguration getInstance() {
        CustomDataSourceConfiguration localInstance = instance;
        if (localInstance == null) {
            synchronized (CustomDataSourceConfiguration.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CustomDataSourceConfiguration();
                }
            }
        }
        return localInstance;
    }

    public Connection customDataSourceConnection() throws SQLException {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build().getConnection();
    }

    public static String getDriverClassName() {
        return driverClassName;
    }

    public static void setDriverClassName(String driverClassName) {
        CustomDataSourceConfiguration.driverClassName = driverClassName;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        CustomDataSourceConfiguration.url = url;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CustomDataSourceConfiguration.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CustomDataSourceConfiguration.password = password;
    }
}
