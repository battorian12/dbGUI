package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.CustomDataSourceConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ContextController {

    @FXML
    public TextField dialect;
    @FXML
    public TextField driverClassName;
    @FXML
    public TextField database;
    @FXML
    public TextField url;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button connectToDb;
    @FXML
    private ComboBox<String> roles;
    @FXML
    private Label label;

    @FXML
    public void initialize() {
        connectToDb.getStyleClass().add("button");
    }

    /**
     * Check authorization credentials.
     * <p>
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */


    @FXML
    public void connectToDb() {
        String usernameText = username.getText();
        String urlText = url.getText();
        String passwordText = password.getText();
        String driverClass = driverClassName.getText();
        String databaseText = database.getText();
        String dialectText = dialect.getText();
        String rolesValue = roles.getValue();

        CustomDataSourceConfiguration.setUrl(urlText);
        CustomDataSourceConfiguration.setDriverClassName(driverClass);
        CustomDataSourceConfiguration.setPassword(passwordText);
        CustomDataSourceConfiguration.setUsername(usernameText);

        try {
            Connection connection = CustomDataSourceConfiguration.getInstance().customDataSourceConnection();
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
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
