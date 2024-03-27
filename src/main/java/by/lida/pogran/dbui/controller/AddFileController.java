package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.entity.ScriptFile;
import by.lida.pogran.dbui.entity.ScriptFiles;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lida.pogran.dbui.constants.ProgramPath.DATA_FILE_NAME;
import static by.lida.pogran.dbui.constants.ProgramPath.DATA_PATH;

/**
 * Класс описания работы страницы addFile.xml.
 *
 * @version 1.0
 * @autor Petrovskiy
 */
@Slf4j
public class AddFileController {

    /*Копка для добавления файла*/
    public Button addFile;
    public TextField fileName;
    public TextArea scriptText;
    public TextArea scriptDescription;
    public Label name;
    private String FILE_NAME_REGEX = ".+";

    /*Инициализация страницы fxml*/
    @FXML
    public void initialize() {
        ImageView addView = new ImageView(new Image("icons8-save-80.png"));
        addView.setFitHeight(20);
        addView.setPreserveRatio(true);
        addFile.setGraphic(addView);
    }

    /*Создание файла .sql и его запись*/
    @FXML
    public void addFile() {
        String textFileName = fileName.getText() + ".sql";
        Pattern p = Pattern.compile(FILE_NAME_REGEX);
        Matcher m = p.matcher(fileName.getText().trim());
        p.matcher(FILE_NAME_REGEX);
        if (!m.matches()) {
            try {
                throw new ValidationException("");
            } catch (ValidationException e) {
                name.setText("Название файла обязательно для заполнения *");
                fileName.setStyle("-fx-border-color: #ea0808; -fx-min-height: 30");
                throw new RuntimeException(e);
            }
        }
        String description = scriptDescription.getText();
        String scriptTextField = scriptText.getText();
        String scriptFiledName = fileName.getText();
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        ScriptFiles scriptFiles = null;
        if (new File(DATA_PATH + DATA_FILE_NAME).length() != 0) {
            scriptFiles = (ScriptFiles) xstream.fromXML(new File(DATA_PATH + DATA_FILE_NAME));
        }
        try {
            // получение содержимого старого файла
            ScriptFile scriptFile = ScriptFile.builder()
                    .path(textFileName)
                    .name(textFileName)
                    .description(description)
                    .build();
            if (scriptFiles != null) {
                scriptFiles.getFileList().forEach(a -> {
                    if (a.getName().equals(textFileName)) {
                        new ContextController().createAlert(null, "Скрипт: " + textFileName + " уже существует");
                        throw new RuntimeException("");
                    }
                });
                scriptFiles.getFileList().add(scriptFile);
            } else {
                scriptFiles = new ScriptFiles();
                scriptFiles.setFileList(Collections.singletonList(scriptFile));
            }

            File file = new File(DATA_PATH + DATA_FILE_NAME);
            FileWriter fileWriter = new FileWriter(file);
            String xstreamXML = xstream.toXML(scriptFiles);
            StringBuilder stringBuilder = new StringBuilder()
                    .append(xstreamXML)
                    .insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

            File newFile = new File(DATA_PATH + textFileName);

            if (new File(DATA_PATH+textFileName).createNewFile()) {
                FileOutputStream newFileWriter = new FileOutputStream(newFile);
                newFileWriter.write(scriptTextField.getBytes());
                newFileWriter.close();

                new ContextController().createAlert(null, "Файл " + textFileName + " успешно создан");
                name.setText("Название файла .SQL");
                fileName.setStyle("-fx-border-color: #1A1A1A; -fx-min-height: 30");
                log.info("File created: " + newFile.getName());
            } else {
                log.info("File not created: " + newFile.getName());
            }
        } catch (IOException e) {
            log.error("Ошибка добавления файла" + scriptFiledName);
            throw new RuntimeException(e);
        }
    }
}
