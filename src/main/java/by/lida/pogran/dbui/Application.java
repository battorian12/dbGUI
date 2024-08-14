package by.lida.pogran.dbui;

import com.jfoenix.controls.JFXDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    @Value("${ui.title:JavaFX приложение}")//
    private String windowTitle;


    @Override
    public void start(Stage stage) throws Exception {
        Image imageIcon = new Image("2730368_animal_character_inkcontober_psyduck_screech_icon.png");
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/contextForm.fxml"));
        Parent parent = loader.load();
        String uri = getClass().getResource("/style.css").toExternalForm();
        JFXDecorator decorator = new JFXDecorator(stage , parent);
        Scene scene = new Scene(decorator);
        stage.setTitle("Scrip Window");
        stage.setScene(scene);
        stage.getIcons().add(imageIcon);
        scene.getStylesheets().add(uri) ;
        stage.setTitle(windowTitle);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.setScene(scene);
        scene.getStylesheets().add(uri) ;
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }

}
