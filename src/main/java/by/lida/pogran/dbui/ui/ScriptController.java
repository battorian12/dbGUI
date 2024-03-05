package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.CustomDataSourceConfiguration;
import by.lida.pogran.dbui.entity.SQLScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ScriptController {

    public Button startScript;
    @FXML
    private ComboBox<String> scripts;
    @FXML
    private Label label;


    @FXML
    public void initialize() {
        scripts.setOnAction(event -> label.setText("Скрипт для выполненения: \n"+Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(scripts.getValue())).findFirst().get().getScript()));
    }

    @Transactional
    public void startScript(ActionEvent actionEvent) {
        String value = scripts.getValue();
        SQLScript selected = Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(value)).findFirst().get();
        try {
            CallableStatement callableStatement = CustomDataSourceConfiguration.getInstance().customDataSourceConnection().prepareCall(selected.getScript());

            boolean execute = callableStatement.execute();
            if (execute) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Query Status:");
                alert.setContentText(selected.getName() + " успешно выполнен");
                alert.showAndWait();
                label.setText("");
            }
            ResultSet res = callableStatement.getResultSet();
            while (res.next()) {


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
