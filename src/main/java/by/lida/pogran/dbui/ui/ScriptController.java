package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ScriptController {

    public Button startScript;
    @FXML
    private  ComboBox<String> scripts;


    @FXML
    public void initialize() {
//        scripts.getItems().addAll(Arrays.stream(SQLScript.values()).map(SQLScript::getName).collect(Collectors.toList()));
        //scripts.setOnAction(event -> descritpionInput.setText(Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(scripts.getValue())).findFirst().get().getDescription()));
    }

    @Transactional
    @FXML
    public void startScript() {
        scripts.getItems().addAll(Arrays.stream(SQLScript.values()).map(SQLScript::getName).collect(Collectors.toList()));
        String value = scripts.getValue();
        SQLScript selected = Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(value)).findFirst().get();

        try (Connection connection = OracleConfigurationProperties.getInstance().getDataSource().getConnection()) {

            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setDelimiter(";");
            Reader scriptReader = Resources.getResourceAsReader(selected.getPath());
            scriptRunner.runScript(scriptReader);
            scriptReader.close();
//          connection.close();

            new ContextController().createAlert("Query Status:",selected.getName() + " успешно выполнен\n");
        } catch (IOException| SQLException e) {
            new ContextController().createAlert("Ошибка выполнения запроса:", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
