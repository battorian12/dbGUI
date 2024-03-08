package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.CustomDataSourceConfiguration;
import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ScriptController {

    public Button startScript;
    @FXML
    private ComboBox<String> scripts;


    @FXML
    public void initialize() {
        scripts.getItems().addAll(Arrays.stream(SQLScript.values()).map(SQLScript::getName).collect(Collectors.toList()));
//        scripts.setOnAction(event -> label.setText("Скрипт для выполненения: \n"+Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(scripts.getValue())).findFirst().get().getScript()));
    }

    @FXML
    public void startScript() {
        Connection connection = null;
        String value = scripts.getValue();
        SQLScript selected = Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(value)).findFirst().get();
//        String scriptPath = Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(value)).map(SQLScript::getPath).findFirst().get();
//        try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                sb.append(System.lineSeparator());
//                line = br.readLine();
//            }
//            everything = sb.toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            connection = OracleConfigurationProperties.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement callableStatement = connection.prepareStatement(selected.getScript());
            callableStatement.executeBatch();
            connection.commit();
            boolean execute = callableStatement.execute();
            if (execute) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Query Status:");
                alert.setContentText(selected.getName() + " успешно выполнен\n");
                alert.showAndWait();
            }
            ResultSet res = callableStatement.getResultSet();
            while (res.next()) {

            }
        } catch (SQLException e) {
            // Handle exceptions, log errors, and rollback the transaction on failure
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Close the connection in a finally block
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
        }
    }
}
