package by.lida.pogran.dbui;

import com.jfoenix.controls.JFXDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    @Value("${ui.title:JavaFX приложение}")//
    private String windowTitle;

    @Qualifier("mainView")
    @Autowired
    private ControllersConfiguration.ViewHolder view;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/contextForm.fxml"));
        Parent parent = loader.load();
        String uri = getClass().getResource("/style.css").toExternalForm();
        JFXDecorator decorator = new JFXDecorator(stage , parent);
        Scene scene = new Scene(decorator);
        stage.setTitle("Scrip Window");
        stage.setScene(scene);
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
