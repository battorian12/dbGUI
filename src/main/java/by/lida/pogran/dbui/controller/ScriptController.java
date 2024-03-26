package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.Application;
import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.SQLScript;
import by.lida.pogran.dbui.entity.ScriptFile;
import by.lida.pogran.dbui.entity.ScriptFiles;
import by.lida.pogran.dbui.entity.ServiceName;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ScriptController {

    public Button startScript;
    @FXML
    private  ComboBox<String> scripts;
    ScriptFiles scriptFiles;
    @Autowired
    ResourceLoader resourceLoader;

    @FXML
    public void initialize(){
        InputStream resourceAsStream = Application.class.getResourceAsStream("/fileData.xml");
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        scriptFiles = (ScriptFiles) xstream.fromXML(resourceAsStream);
        scripts.getItems().addAll(scriptFiles.getFileList().stream().map(a->a.getName()).collect(Collectors.toList()));
        ImageView refreshView = new ImageView(new Image("icons8-startup-64.png"));
        refreshView.setFitHeight(20);
        refreshView.setPreserveRatio(true);
        startScript.setGraphic(refreshView);
//        scripts.getItems().addAll(Arrays.stream(SQLScript.values()).map(SQLScript::getName).collect(Collectors.toList()));
        //scripts.setOnAction(event -> descritpionInput.setText(Arrays.stream(SQLScript.values()).filter(a -> a.getName().equals(scripts.getValue())).findFirst().get().getDescription()));
    }

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

            new ContextController().createAlert("Query Status:",scriptFile.getName() + " успешно выполнен\n");
        } catch (IOException| SQLException e) {
            new ContextController().createAlert("Ошибка выполнения запроса:", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
