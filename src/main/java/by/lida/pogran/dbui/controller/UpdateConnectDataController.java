package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.entity.ConnectData;
import by.lida.pogran.dbui.entity.ConnectDataLst;
import by.lida.pogran.dbui.exception.SystemException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lida.pogran.dbui.constants.ProgramPath.CONNECT_DATA_FILE_NAME;
import static by.lida.pogran.dbui.constants.ProgramPath.DATA_PATH;

/**
 * Класс обновления данных для подлючения к бд.
 *
 * @version 1.0
 * @autor Petrovskiy
 */
@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateConnectDataController {

    public Button updateConfig;
    public TextField name;
    public TextField serviceName;
    public TextField host;
    private String NAME_REGEX = ".+";

    @FXML
    public void initialize() {
        serviceName.setText(ConnectData.getInstance().getServiceName());
        name.setText(ConnectData.getInstance().getName());
        host.setText(ConnectData.getInstance().getHost());
    }

    @FXML
    public void updateConnectData(ActionEvent actionEvent) {
        try {
            ///validation
            Pattern p = Pattern.compile(NAME_REGEX);
            Matcher m = p.matcher(serviceName.getText().trim());
            Matcher m2 = p.matcher(name.getText().trim());
            if (!m.matches()) {
                try {
                    throw new ValidationException("");
                } catch (ValidationException e) {
                    serviceName.setText("ServiceName обязательно для заполнения *");
                    serviceName.setStyle("-fx-border-color: #ea0808; -fx-min-height: 30");
                    throw new RuntimeException(e);
                }
            }
            if (!m2.matches()) {
                try {
                    throw new ValidationException("");
                } catch (ValidationException e) {
                    name.setText("Name обязательно для заполнения *");
                    name.setStyle("-fx-border-color: #ea0808; -fx-min-height: 30");
                    throw new RuntimeException(e);
                }
            }

            //connectData
            XStream xstream1 = new XStream();
            xstream1.addPermission(AnyTypePermission.ANY);
            xstream1.alias("connectDataLst", ConnectDataLst.class);
            xstream1.addImplicitCollection(ConnectDataLst.class, "connectDataList");
            File file1 = new File(DATA_PATH + CONNECT_DATA_FILE_NAME);
            ConnectDataLst connectDataLst = null;


            if (file1.length() != 0) {
                connectDataLst = (ConnectDataLst) xstream1.fromXML(new File(DATA_PATH + CONNECT_DATA_FILE_NAME));
            }

            List<ConnectData> connectDataList = connectDataLst.getConnectDataList();

            //проверка уникальности по имени
            if (connectDataLst.getConnectDataList() != null) {
                connectDataLst.getConnectDataList().forEach(a -> {
                    if (a.getName().equals(name.getText())) {
                        new ContextController().createAlert(null, "Конфигурация: " + name.getText() + " уже существует");
                        throw new SystemException("Конфигурация уже существует");
                    }
                });
            }

            /*Запись обновленной конфигруации*/
            connectDataList.removeIf(b -> b.getName().equals(ConnectData.getInstance().getName()));
            ConnectData.getInstance().setName(name.getText());
            ConnectData.getInstance().setServiceName(serviceName.getText());
            ConnectData.getInstance().setHost(host.getText());
            connectDataList.add(ConnectData.getInstance());

//            Files.deleteIfExists(Paths.get(DATA_PATH + CONNECT_DATA_FILE_NAME));

            FileWriter fileWriter = new FileWriter(DATA_PATH + "connectData.xml");
            String xml = xstream1.toXML(connectDataList);
            String save = xml.replaceAll("<list>", "<connectDataLst>").replaceAll("</list>", "</connectDataLst>");
            fileWriter.write(save);
            fileWriter.close();
            createAlert(null, "Конфигурация " + ConnectData.getInstance().toString() + " успешно обновлена");
            log.info("конфигурация успешно обновлена");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*Создание уведомления пользователя
     *
     * @param title заголовок уведомления
     * @param context основной текст
     */
    public void createAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        alert.showAndWait();
    }
}
