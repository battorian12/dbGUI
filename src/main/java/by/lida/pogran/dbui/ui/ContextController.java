package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.ServiceName;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ContextController {

    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public TextField serviceName;
    @FXML
    public TextField networkProtocol;
    @FXML
    public TextField user;
    @FXML
    public ComboBox<String> serviceNames;
    @FXML
    private PasswordField password;

    @FXML
    private Button connectToDb;
    @FXML
    private ComboBox<String> roles;
    @FXML
    private Label label;
    @FXML
    private Tooltip toolTip;

    @FXML
    public void initialize() {
        serviceNames.setOnAction((e) -> {
            serviceName.setText(Arrays.stream(ServiceName.values()).filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getServiceName());
            host.setText(Arrays.stream(ServiceName.values()).filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getHost());
        });
        serviceNames.getItems().addAll(Arrays.stream(ServiceName.values()).map(ServiceName::getName).collect(Collectors.toList()));
        connectToDb.getStyleClass().add("button");
    }


    @FXML
    public void connectToDb() {
        Connection connection = null;
        String hostText = host.getText();
        String networkProtocolText = networkProtocol.getText();
        String portText = port.getText();
        String passwordText = password.getText();
        String serviceNameText = serviceName.getText();
        String userText = user.getText();

        OracleConfigurationProperties.setHost(hostText);
        OracleConfigurationProperties.setPort(portText);
        OracleConfigurationProperties.setNetworkProtocol(networkProtocolText);
        OracleConfigurationProperties.setServiceName(serviceNameText);
        OracleConfigurationProperties.setPassword(passwordText);
        OracleConfigurationProperties.setUser(userText);

        try {
            connection = OracleConfigurationProperties.getInstance().getDataSourceConnection();
            if (!connection.isClosed()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Registration Status:");
                alert.setContentText("You have successfully connected to db!");
                alert.showAndWait();
                //load the fxml need here!
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scriptWindow.fxml"));
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Scrip Window");
                stage.setScene(new Scene(parent));
                stage.show();
            }
            connection.commit();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
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
