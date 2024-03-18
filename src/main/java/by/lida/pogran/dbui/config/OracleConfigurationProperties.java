package by.lida.pogran.dbui.config;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.datasource.impl.OracleDataSource;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Getter
@Setter
public class OracleConfigurationProperties {

    private static volatile OracleConfigurationProperties instance;
    private static String host;
    private static String port;
    private static String networkProtocol;
    private static String serviceName;
    private static String user;
    private static String password;
    private static String DRIVER_TYPE = "thin";
    private static Connection connection;


    public static OracleConfigurationProperties getInstance() {
        OracleConfigurationProperties localInstance = instance;
        if (localInstance == null) {
            synchronized (OracleConfigurationProperties.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OracleConfigurationProperties();
                }
            }
        }
        return localInstance;
    }

    public DataSource getDataSource() {
        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setNetworkProtocol(networkProtocol);
            dataSource.setPortNumber(Integer.parseInt(port));
            dataSource.setServiceName(serviceName);
            dataSource.setServerName(host);
            dataSource.setDriverType(DRIVER_TYPE);
            dataSource.setPassword(password);
            dataSource.setUser(user);
            return dataSource;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setInstance(OracleConfigurationProperties instance) {
        OracleConfigurationProperties.instance = instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        OracleConfigurationProperties.connection = connection;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        OracleConfigurationProperties.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        OracleConfigurationProperties.port = port;
    }

    public static String getServiceName() {
        return serviceName;
    }

    public static void setServiceName(String serviceName) {
        OracleConfigurationProperties.serviceName = serviceName;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        OracleConfigurationProperties.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        OracleConfigurationProperties.password = password;
    }


    public static String getNetworkProtocol() {
        return networkProtocol;
    }

    public static void setNetworkProtocol(String networkProtocol) {
        OracleConfigurationProperties.networkProtocol = networkProtocol;
    }
}