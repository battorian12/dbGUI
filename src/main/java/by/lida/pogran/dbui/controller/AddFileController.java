package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.Application;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AddFileController {

    public Button addFile;
    public TextField fileName;
    public TextArea scriptText;
    public TextArea scriptDescription;
    public Label name;
    private String FILE_NAME_REGEX=".+";
    ClassLoader classLoader = getClass().getClassLoader();

    @FXML
    public void initialize() {
        ImageView addView = new ImageView(new Image("icons8-save-80.png"));
        addView.setFitHeight(20);
        addView.setPreserveRatio(true);
        addFile.setGraphic(addView);
    }

    @FXML
    public void addFile(){
        File newFile = null;
        String textFileName = fileName.getText() + ".sql";
        Pattern p = Pattern.compile(FILE_NAME_REGEX);
        Matcher m = p.matcher(fileName.getText().trim());
        p.matcher(FILE_NAME_REGEX);
        if(!m.matches()){
            try {
                throw new ValidationException("");
            } catch (ValidationException e) {
                name.setText("Название файла обязательно для заполнения *");
                fileName.setStyle("-fx-border-color: #ea0808; -fx-min-height: 30");
                throw new RuntimeException(e);
            }
        }
        String description = scriptDescription.getText();
        scriptText.getText();
        String pathName = "src/main/resources/" + textFileName;
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");

        try {
            ScriptFiles scriptFiles = (ScriptFiles) xstream.fromXML(Application.class.getResourceAsStream("/fileData.xml"));//TODO получил старый файл с содержимым
            ScriptFile scriptFile = ScriptFile.builder()
                    .path(pathName)
                    .name(textFileName)
                    .description(description)
                    .build();
            scriptFiles.getFileList().forEach(a -> {
                if (a.getName().equals(textFileName)) {
//                    new ContextController().createAlert(null, "Скрипт: " + textFileName + "уже существует");
                    throw new RuntimeException("");
                }
            });

            scriptFiles.getFileList().add(scriptFile);
            File file = new File(classLoader.getResource("fileData.xml").getFile());
            FileWriter fileWriter = new FileWriter(file);//TODO error
            fileWriter.write(xstream.toXML(scriptFiles));
            fileWriter.close();
            newFile = new File(pathName);


            if (newFile.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(pathName);
                stream.write(scriptText.getText().getBytes());
                stream.flush();
                stream.close();
                new ContextController().createAlert(null, "Файл " + textFileName + " успешно создан");
                name.setText("Название файла .SQL");
                fileName.setStyle("-fx-border-color: #1A1A1A; -fx-min-height: 30");
                log.info("File created: " + newFile.getName());
            } else {
                log.info("File not created: " + newFile.getName());
            }
        } catch (IOException e) {
            log.error("Ошибка добавления файла" + newFile.getName());
            throw new RuntimeException(e);

        }
    }
}
