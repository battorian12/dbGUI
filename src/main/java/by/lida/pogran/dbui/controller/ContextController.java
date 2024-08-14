package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.ConnectData;
import by.lida.pogran.dbui.entity.ConnectDataLst;
import by.lida.pogran.dbui.entity.ScriptFiles;
import com.jfoenix.controls.JFXDecorator;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static by.lida.pogran.dbui.constants.ProgramPath.*;

/**
 * Класс описания работы страницы contextForm.fxml.
 *
 * @version 1.0
 * @autor Petrovskiy
 */
@Slf4j
public class ContextController {

    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public TextField serviceName;
    @FXML
    public TextField networkProtocol;
    @FXML
    public TextField user;
    @FXML
    public TextField fileName;
    @FXML
    public TextField name;
    @FXML
    public ComboBox<String> serviceNames;
    @FXML
    public Button createMenu;
    @FXML
    public Button refreshPage;
    @FXML
    public Menu deleteMenu;
    public CheckBox savePassword;
    public Button addConfig;
    @FXML
    private PasswordField password;
    @FXML
    private Button connectToDb;
    @FXML
    public Button updateConnectData;
    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();
    private final String NETWORK_PROTOCOL = "TCP";
    private final String PORT = "1521";
    private final String USER = "M_BORDER";

    /*Инициализация страницы fxml*/
    @FXML
    public void initialize() {
        refreshPage.setShape(new Circle(1));
        refreshPage.setMaxSize(2, 2);
        //Добавление иконки png для кнопки
        ImageView refreshView = new ImageView(new Image("icons8-refresh-50.png"));
        refreshView.setFitHeight(20);
        refreshView.setPreserveRatio(true);
        refreshPage.setGraphic(refreshView);

        ImageView connectView = new ImageView(new Image("icons8-connect-80.png"));
        connectView.setFitHeight(20);
        connectView.setPreserveRatio(true);
        connectToDb.setGraphic(connectView);

        //fileData получение данные из xml в java объект
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        File file = new File(DATA_PATH + DATA_FILE_NAME);
        ScriptFiles scriptFiles = null;

        //connectData
        XStream xstream1 = new XStream();
        xstream1.addPermission(AnyTypePermission.ANY);
        xstream1.alias("connectDataLst", ConnectDataLst.class);
        xstream1.addImplicitCollection(ConnectDataLst.class, "connectDataList");
        File file1 = new File(DATA_PATH + CONNECT_DATA_FILE_NAME);
        ConnectDataLst connectDataLst;


        if (file.length() != 0) {
            scriptFiles = (ScriptFiles) xstream.fromXML(new File(DATA_PATH + DATA_FILE_NAME));
        }
        if (file1.length() != 0) {
            connectDataLst = (ConnectDataLst) xstream1.fromXML(new File(DATA_PATH + CONNECT_DATA_FILE_NAME));
        } else {
            connectDataLst = null;
        }
        List<MenuItem> menuItems = new ArrayList<>();
        String decodedString;
        try {
            File password = new File(DATA_PATH + "06c4ba86-c468-49cb-ad5f.txt");
            String passw = FileUtils.readFileToString(password, StandardCharsets.UTF_8);

            byte[] decodedBytes = decoder.decode(passw);
            decodedString = new String(decodedBytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.networkProtocol.setText(NETWORK_PROTOCOL);
        this.port.setText(PORT);
        this.user.setText(USER);
        this.password.setText(decodedString);
        //Заполнение текстовых полей в соответствии с выбранным пунктом меню
        serviceNames.setOnAction((e) -> {
            serviceName.setText(connectDataLst.getConnectDataList().stream().filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getServiceName());
            host.setText(connectDataLst.getConnectDataList().stream().filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getHost());
            name.setText(connectDataLst.getConnectDataList().stream().filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getName());
        });
        //Добавление имен сервисов в dropdown-menu с сортировкой
        serviceNames.getItems().addAll(connectDataLst.getConnectDataList().stream().sorted(Comparator.comparing(ConnectData::getName)).map(ConnectData::getName).collect(Collectors.toList()));
        connectToDb.getStyleClass().add("button");
        if (scriptFiles != null && scriptFiles.getFileList() != null) {
            scriptFiles.getFileList().forEach(a -> {
                MenuItem menuItem = new MenuItem(a.getName());
                menuItems.add(menuItem);
            });
            deleteMenu.getItems().setAll(menuItems);
        }
        ScriptFiles finalScriptFiles = scriptFiles;
        deleteMenu.getItems().forEach(a ->
                a.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            /*Запись нового документа*/
                            finalScriptFiles.getFileList().removeIf(b -> b.getName().equals(a.getText()));
                            FileWriter fileWriter;
                            fileWriter = new FileWriter(DATA_PATH + "fileData.xml");
                            fileWriter.write(xstream.toXML(finalScriptFiles));
                            fileWriter.close();

                            if (Files.deleteIfExists(Paths.get(DATA_PATH + a.getText()))) {
                                createAlert(null, "Скрипт " + a.getText() + " успешно удален");
                                log.info("файл успешно удален");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }));
    }

    /*Создание меню с именами сохраненных скриптов*/
    @FXML
    public void createMenu() throws IOException {
        Stage stage = new Stage();
        String uri = getClass().getResource("/style.css").toExternalForm();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addFile.fxml"));
        Parent parent = fxmlLoader.load();
        fxmlLoader.setLocation(getClass().getResource("/fxml/addFile.fxml"));
        JFXDecorator decorator = new JFXDecorator(stage, parent);
        Scene scene;
        scene = new Scene(decorator);
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void deleteMenu() {

    }

    /*Обновление элементов меню для получения актуальных скриптов*/
    @FXML
    public void refreshPage() {
        ScriptFiles scriptFiles = null;
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        if (new File(DATA_PATH + "fileData.xml").length() != 0) {
            scriptFiles = (ScriptFiles) xstream.fromXML(new File(DATA_PATH + "fileData.xml"));
        }
        //connectData
        XStream xstream1 = new XStream();
        xstream1.addPermission(AnyTypePermission.ANY);
        xstream1.alias("connectDataLst", ConnectDataLst.class);
        xstream1.addImplicitCollection(ConnectDataLst.class, "connectDataList");
        File file1 = new File(DATA_PATH + CONNECT_DATA_FILE_NAME);
        ConnectDataLst connectDataLst;

        if (file1.length() != 0) {
            connectDataLst = (ConnectDataLst) xstream1.fromXML(new File(DATA_PATH + CONNECT_DATA_FILE_NAME));
        } else {
            connectDataLst = null;
        }

        List<MenuItem> menuItems = new ArrayList<>();
        //Заполнение текстовых полей в соответствии с выбранным пунктом меню
        serviceNames.setOnAction((e) -> {
            serviceName.setText(connectDataLst.getConnectDataList().stream().filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getServiceName());
            host.setText(connectDataLst.getConnectDataList().stream().filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getHost());
            name.setText(connectDataLst.getConnectDataList().stream().filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getName());
        });
        serviceNames.getItems().setAll(connectDataLst.getConnectDataList().stream().sorted(Comparator.comparing(ConnectData::getName)).map(ConnectData::getName).collect(Collectors.toList()));
        connectToDb.getStyleClass().add("button");
        if (scriptFiles != null) {
            scriptFiles.getFileList().forEach(a -> {
                MenuItem menuItem = new MenuItem(a.getName());
                menuItems.add(menuItem);
            });
        }

        deleteMenu.getItems().setAll(menuItems);
        ScriptFiles finalScriptFiles = scriptFiles;
        deleteMenu.getItems().forEach(a ->
                a.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            finalScriptFiles.getFileList().removeIf(b -> b.getName().equals(a.getText()));
                            FileWriter fileWriter;
                            fileWriter = new FileWriter(DATA_PATH + "fileData.xml");
                            if (finalScriptFiles.getFileList().isEmpty()) {
                                fileWriter.write(xstream.toXML(null));
                            }
                            fileWriter.write(xstream.toXML(finalScriptFiles));
                            fileWriter.close();

                            if (Files.deleteIfExists(Paths.get(DATA_PATH + a.getText()))) {
                                createAlert(null, "Скрипт " + a.getText() + " успешно удален");
                                log.info("Файл успешно удален " + a.getText());
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }));
        log.info("Данные успешно обновлены");
        createAlert(null, "Данные успешно обновлены");
    }

    /*Подключение к базе данных(создание соединения с бд)
     *
     * @throws IOException если FXMLLoader не находит нужной fxml страницы
     * @SQLException если возникает ошибка при работе с базой данных
     * */
    @Transactional
    @FXML
    public void connectToDb() {
        Connection connection;
        String hostText = host.getText();
        String networkProtocolText = networkProtocol.getText();
        String portText = port.getText();
        String passwordText = password.getText();
        String serviceNameText = serviceName.getText();
        String userText = user.getText();

        OracleConfigurationProperties.setHost(hostText);
        OracleConfigurationProperties.setPort(portText);
        OracleConfigurationProperties.setNetworkProtocol(networkProtocolText);
        OracleConfigurationProperties.setServiceName(serviceNameText);
        OracleConfigurationProperties.setPassword(passwordText);
        OracleConfigurationProperties.setUser(userText);

        try {
            //save password
            if (savePassword.isSelected()) {
                FileWriter fileWriter = new FileWriter(DATA_PATH + "06c4ba86-c468-49cb-ad5f.txt");
                String encodedString = encoder.encodeToString(passwordText.getBytes(StandardCharsets.UTF_8));
                fileWriter.write(encodedString);
                fileWriter.close();
            }
            connection = OracleConfigurationProperties.getInstance().getDataSource().getConnection();
            if (!connection.isClosed()) {
                Stage stage = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scriptWindow.fxml"));
                Parent parent = loader.load();
                String uri = getClass().getResource("/style.css").toExternalForm();
                JFXDecorator decorator = new JFXDecorator(stage, parent);
                Scene scene = new Scene(decorator);
                stage.setTitle("Scrip Window");
                stage.setScene(scene);
                scene.getStylesheets().add(uri);


                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setTitle("Registration Status:");
                alert.setContentText("You have successfully connected to db!");
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.showAndWait();
                stage.show();

            }
        } catch (SQLException | IOException e) {
            createAlert("Ошибка подлючения к бд:", e.getMessage());
            log.error("Ошибка подлючения к бд: " + e.getMessage());
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


    public void updateConnectData() throws IOException {
        String serviceNameText = serviceName.getText();
        String hostName = host.getText();
        String nameText = name.getText();

        if (serviceNameText == null || serviceNameText.trim().isEmpty()) {
            createAlert("", "Выберите конфигурацию для обновления");
            log.warn("Конфигурация для обновления не выбрана");
        } else {
            ConnectData instance = ConnectData.getInstance();
            instance.setName(nameText);
            instance.setHost(hostName);
            instance.setServiceName(serviceNameText);
            Stage stage = new Stage();
            String uri = getClass().getResource("/style.css").toExternalForm();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/updateConnectData.fxml"));
            Parent parent = fxmlLoader.load();
            fxmlLoader.setLocation(getClass().getResource("/fxml/updateConnectData.fxml"));
            JFXDecorator decorator = new JFXDecorator(stage, parent);
            Scene scene;
            scene = new Scene(decorator);
            scene.getStylesheets().add(uri);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void addConfig(ActionEvent event) {
        try {
            Stage stage = new Stage();
            String uri = getClass().getResource("/style.css").toExternalForm();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addNewConfig.fxml"));
            fxmlLoader.setLocation(getClass().getResource("/fxml/addNewConfig.fxml"));
            Parent parent = fxmlLoader.load();
            JFXDecorator decorator = new JFXDecorator(stage, parent);
            Scene scene = new Scene(decorator);
            scene.getStylesheets().add(uri);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
