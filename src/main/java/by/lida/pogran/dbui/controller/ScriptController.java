package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import by.lida.pogran.dbui.entity.ScriptFile;
import by.lida.pogran.dbui.entity.ScriptFiles;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.lida.pogran.dbui.constants.ProgramPath.DATA_FILE_NAME;
import static by.lida.pogran.dbui.constants.ProgramPath.DATA_PATH;

/**
 * Класс описания работы страницы scriptWindow.fxml.
 *
 * @version 1.0
 * @autor Petrovskiy
 */
public class ScriptController {
    /*Копка для запуска скрипта*/
    public Button startScript;
    public Label description;
    public TextArea descriptionText;
    public CheckBox stopOnError;
    public Label stopOnErrorDescription;
    @FXML
    private ComboBox<String> scripts;
    ScriptFiles scriptFiles;

    /*Инициализация страницы fxml*/
    @FXML
    public void initialize() throws IOException {
        stopOnError.setOnAction(a -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.8), stopOnErrorDescription);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.play();
        });
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        try {
            scriptFiles = (ScriptFiles) xstream.fromXML(new File(DATA_PATH + DATA_FILE_NAME));
        } catch (RuntimeException e) {
            if (scriptFiles == null && !new File(DATA_PATH + DATA_FILE_NAME).exists()) {
                Files.createFile(Paths.get(DATA_PATH + DATA_FILE_NAME));
            }
        }
        if (scripts != null && scriptFiles != null && scriptFiles.getFileList() != null) {
            scripts.getItems().addAll(scriptFiles.getFileList().stream().map(a -> a.getName()).collect(Collectors.toList()));
            scripts.setOnAction((a) -> {
                descriptionText.setText(scriptFiles.getFileList().stream().filter(b -> b.getName().equals(scripts.getValue())).findFirst().get().getDescription());
            });
        }
        ImageView refreshView = new ImageView(new Image("icons8-startup-64.png"));
        refreshView.setFitHeight(20);
        refreshView.setPreserveRatio(true);
        startScript.setGraphic(refreshView);
    }

    /*Запуск выбранного .sql скрипта*/
    @Transactional
    @FXML
    public void startScript() {
        String value = scripts.getValue();
        Optional<ScriptFile> selectedScript = scriptFiles.getFileList().stream().filter(a -> a.getName().equalsIgnoreCase(value)).findFirst();
        if (!selectedScript.isPresent()) {
            new ContextController().createAlert("error", "Выберите или добавьте необходимый скрипт для выполнения");
        } else {

            ScriptFile scriptFile = selectedScript.get();
            try (Connection connection = OracleConfigurationProperties.getInstance().getDataSource().getConnection()) {

                ScriptRunner scriptRunner = new ScriptRunner(connection);
                scriptRunner.setDelimiter(";");
                scriptRunner.setStopOnError(stopOnError.isSelected());
                scriptRunner.runScript(new FileReader(DATA_PATH + scriptFile.getPath()));

                new ContextController().createAlert("Статус запроса:", scriptFile.getName() + " успешно выполнен\n");
            } catch (IOException | SQLException e) {
                new ContextController().createAlert("Ошибка выполнения запроса:", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
