package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Transactional
    @FXML
    public void startScript() {
        String value = scripts.getValue();
        SQLScript selected = Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(value)).findFirst().get();

        try (Connection connection = OracleConfigurationProperties.getInstance().getDataSource().getConnection()) {

            String s = connection.nativeSQL(selected.getScript());
            try (PreparedStatement mergeStatements = connection.prepareStatement(s)) {
                mergeStatements.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Query Status:");
            alert.setContentText(selected.getName() + " успешно выполнен\n");
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
