package by.lida.pogran.dbui.config;


import lombok.*;
import oracle.jdbc.datasource.impl.OracleDataSource;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OracleConfigurationProperties {

    private String host;
    private String username;
    private String password;
    private String url;
    private String role;

    public Connection getDataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setRoleName(role);
//        dataSource.setURL("jdbc:oracle:thin:@//localhost:11521/ORCLPDB1");
        dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        return dataSource.getConnection();
    }

}