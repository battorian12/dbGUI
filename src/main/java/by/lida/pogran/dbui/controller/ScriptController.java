package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import by.lida.pogran.dbui.entity.ScriptFile;
import by.lida.pogran.dbui.entity.ScriptFiles;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
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
    @FXML
    private ComboBox<String> scripts;
    ScriptFiles scriptFiles;

    /*Инициализация страницы fxml*/
    @FXML
    public void initialize() throws IOException {
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        try {
            scriptFiles = (ScriptFiles) xstream.fromXML(new File(DATA_PATH + DATA_FILE_NAME));
        } catch (RuntimeException e) {
            if (scriptFiles == null && !new File(DATA_PATH + DATA_FILE_NAME).exists()) {
                new File(DATA_PATH).mkdir();
                Files.createFile(Paths.get(DATA_PATH + DATA_FILE_NAME));
            }
        }
        if (scripts != null && scriptFiles != null && scriptFiles.getFileList() != null) {
            scripts.getItems().addAll(scriptFiles.getFileList().stream().map(a -> a.getName()).collect(Collectors.toList()));
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
        scripts.getItems().addAll(Arrays.stream(SQLScript.values()).map(SQLScript::getName).collect(Collectors.toList()));
        String value = scripts.getValue();
        ScriptFile scriptFile = scriptFiles.getFileList().stream().filter(a -> a.getName().equals(value)).findFirst().get();

        try (Connection connection = OracleConfigurationProperties.getInstance().getDataSource().getConnection()) {

            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setDelimiter(";");
            Reader scriptReader = Resources.getResourceAsReader(scriptFile.getPath());
            scriptRunner.runScript(scriptReader);
            scriptReader.close();

            new ContextController().createAlert("Query Status:", scriptFile.getName() + " успешно выполнен\n");
        } catch (IOException | SQLException e) {
            new ContextController().createAlert("Ошибка выполнения запроса:", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
