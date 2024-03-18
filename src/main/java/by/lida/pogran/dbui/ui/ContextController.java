package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.ServiceName;
import com.jfoenix.controls.JFXDecorator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    @FXML
    public void connectToDb() {
        Connection connection;
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
//        CustomDataSourceConfiguration.setUsername("postgres");
//        CustomDataSourceConfiguration.setPassword("123");
//        CustomDataSourceConfiguration.setUrl("jdbc:postgresql://localhost:5432/board");
//        CustomDataSourceConfiguration.setDriverClassName("org.postgresql.Driver");

        try {
            connection = OracleConfigurationProperties.getInstance().getDataSource().getConnection();

            if (!connection.isClosed()) {
                Stage stage = new Stage();

                //load the fxml need here!
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scriptWindow.fxml"));
                Parent parent = loader.load();
                String uri = getClass().getResource("/style.css").toExternalForm();
                JFXDecorator decorator = new JFXDecorator(stage, parent);
                Scene scene = new Scene(decorator);
                stage.setTitle("Scrip Window");
                stage.setScene(scene);
                scene.getStylesheets().add(uri);


                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setTitle("Registration Status:");
                alert.setContentText("You have successfully connected to db!");
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.showAndWait();

                stage.show();
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
