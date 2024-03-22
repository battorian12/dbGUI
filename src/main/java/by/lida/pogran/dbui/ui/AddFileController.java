package by.lida.pogran.dbui.ui;

import by.lida.pogran.dbui.entity.ScriptFile;
import by.lida.pogran.dbui.entity.ScriptFiles;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class AddFileController {

    public Button addFile;
    public TextField fileName;
    public TextArea scriptText;
    public TextArea scriptDescription;
    String DATA_XML_PATH = "src/main/resources/xml/fileData.xml";


    @FXML
    public void initialize() {

    }

    @FXML
    public void addFile() throws IOException {
        File newFile = null;
        String textFileName = fileName.getText() + ".sql";
        String description = scriptDescription.getText();
        scriptText.getText();
        String pathName = "src/main/resources/" + textFileName;
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");

        try {
            File oldFile = new File(DATA_XML_PATH);
            ScriptFiles scriptFiles = (ScriptFiles) xstream.fromXML(oldFile);/////////////TODO получил старый файл с содержимым
            ScriptFile scriptFile = ScriptFile.builder()
                    .path(pathName)
                    .name(textFileName)
                    .description(description)
                    .build();
            scriptFiles.getFileList().forEach(a -> {
                if (a.getName().equals(textFileName)) {
                    new ContextController().createAlert(null, "Скрипт: " + textFileName + "уже существует");
                    throw new RuntimeException("");
                }
            });

            scriptFiles.getFileList().add(scriptFile);

            FileWriter fileWriter = new FileWriter(DATA_XML_PATH); /////TODO записал новый документ
            fileWriter.write(xstream.toXML(scriptFiles));
            fileWriter.close();

            newFile = new File(pathName);

            if (newFile.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(pathName);
                stream.write(scriptText.getText().getBytes());
                stream.flush();
                stream.close();
                new ContextController().createAlert(null, "Файл " + textFileName + " успешно создан");
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
