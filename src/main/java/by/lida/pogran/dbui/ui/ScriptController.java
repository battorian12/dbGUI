package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
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
        String value = scripts.getValue();
        Set<String> scripts = Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(value)).flatMap(b -> b.getScript().stream()).collect(Collectors.toSet());
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

        try (Connection connection = OracleConfigurationProperties.getConnection()) {
            connection.setAutoCommit(false);

            scripts.forEach(a -> {
                try (PreparedStatement mergeUserStatement = connection.prepareStatement(a)) {
                    try {
                        mergeUserStatement.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Query Status:");
                        alert.setContentText(value + " успешно выполнен\n");
                        alert.showAndWait();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
