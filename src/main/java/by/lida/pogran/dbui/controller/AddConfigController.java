package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.entity.ConnectData;
import by.lida.pogran.dbui.entity.ConnectDataLst;
import by.lida.pogran.dbui.exception.SystemException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static by.lida.pogran.dbui.constants.ProgramPath.CONNECT_DATA_FILE_NAME;
import static by.lida.pogran.dbui.constants.ProgramPath.DATA_PATH;

/**
 * Класс для добавления новой конфигруации.
 *
 * @version 1.0
 * @autor Petrovskiy
 */
@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddConfigController {


    public Button saveConfig;
    public TextField name;
    public TextField serviceName;
    public TextField host;

    @FXML
    public void saveConfig(ActionEvent event) {
        String nameText = name.getText();
        String serviceNameText = serviceName.getText();
        String hostText = host.getText();
        try {

            XStream xstream1 = new XStream();
            xstream1.addPermission(AnyTypePermission.ANY);
            xstream1.alias("connectDataLst", ConnectDataLst.class);
            xstream1.addImplicitCollection(ConnectDataLst.class, "connectDataList");
            File file = new File(DATA_PATH + CONNECT_DATA_FILE_NAME);
            ConnectDataLst connectDataLst = new ConnectDataLst();
            if (file.length() != 0) {
                connectDataLst = (ConnectDataLst) xstream1.fromXML(new File(DATA_PATH + "connectData.xml"));
            }
            if (connectDataLst != null) {
                connectDataLst.getConnectDataList().forEach(a -> {
                    if (a.getName().equals(nameText)) {
                        new ContextController().createAlert(null, "Конфигурация с именем: " + nameText + " уже существует");
                        throw new SystemException("Конфигурация уже существует");
                    }
                });
            }
            FileWriter fileWriter = new FileWriter(file);
            ConnectData connectData = ConnectData.builder()
                    .serviceName(serviceNameText)
                    .name(nameText)
                    .host(hostText)
                    .build();
            connectDataLst.getConnectDataList().add(connectData);
            String xml = xstream1.toXML(connectDataLst);
            StringBuilder stringBuilder = new StringBuilder()
                    .append(xml)
                    .insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

        } catch (IOException | SystemException e) {
            log.error("Ошибка добавления файла: " + e.getMessage());
            throw new SystemException("Ошибка добавления конфигурации");
        } finally {
            new ContextController().createAlert(null, "Конфигурация: " + nameText + " сохранена");
        }
    }
}
