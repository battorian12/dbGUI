package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.config.OracleConfigurationProperties;
import by.lida.pogran.dbui.entity.ScriptFiles;
import by.lida.pogran.dbui.entity.ServiceName;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.lida.pogran.dbui.constants.ProgramPath.DATA_PATH;

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
    public ComboBox<String> serviceNames;
    @FXML
    public Button createMenu;
    @FXML
    public Button refreshPage;
    @FXML
    public Menu deleteMenu;
    @FXML
    private PasswordField password;
    @FXML
    private Button connectToDb;

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

        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("scriptFiles", ScriptFiles.class);
        xstream.addImplicitCollection(ScriptFiles.class, "fileList");
        File file = new File(DATA_PATH + "fileData.xml");
        ScriptFiles scriptFiles = null;
        if (file.length() != 0) {
            scriptFiles = (ScriptFiles) xstream.fromXML(new File(DATA_PATH + "fileData.xml"));
        }
        List<MenuItem> menuItems = new ArrayList<>();
        //Заполнение текстовых полей в соответствии с выбранным пунктом меню
        serviceNames.setOnAction((e) -> {
            serviceName.setText(Arrays.stream(ServiceName.values()).filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getServiceName());
            host.setText(Arrays.stream(ServiceName.values()).filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getHost());
        });
        serviceNames.getItems().addAll(Arrays.stream(ServiceName.values()).map(ServiceName::getName).collect(Collectors.toList()));
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
                                createAlert(null, "Скрипт" + a.getText() + " успешно удален");
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
        List<MenuItem> menuItems = new ArrayList<>();
        //Заполнение текстовых полей в соответствии с выбранным пунктом меню
        serviceNames.setOnAction((e) -> {
            serviceName.setText(Arrays.stream(ServiceName.values()).filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getServiceName());
            host.setText(Arrays.stream(ServiceName.values()).filter(a -> a.getName().equals(serviceNames.getValue())).findFirst().get().getHost());
        });
        serviceNames.getItems().addAll(Arrays.stream(ServiceName.values()).map(ServiceName::getName).collect(Collectors.toList()));
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
                            if(finalScriptFiles.getFileList().isEmpty()){
                                fileWriter.write(xstream.toXML(null));
                            }
                            fileWriter.write(xstream.toXML(finalScriptFiles));
                            fileWriter.close();

                            if (Files.deleteIfExists(Paths.get(DATA_PATH + a.getText()))) {
                                createAlert(null, "Скрипт" + a.getText() + " успешно удален");
                                log.info("файл успешно удален");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }));
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
