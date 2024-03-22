package by.lida.pogran.dbui;

import by.lida.pogran.dbui.ui.AddFileController;
import by.lida.pogran.dbui.ui.ContextController;
import by.lida.pogran.dbui.ui.ScriptController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;


@Configuration
public class ControllersConfiguration {

    @Bean(name = "scriptWindow")
    public ViewHolder getScriptPageView() throws IOException {
        return loadView("fxml/scriptWindow.fxml");
    }

    @Bean(name = "mainView")
    public ViewHolder getMainPageView() throws IOException {
        return loadView("fxml/contextForm.fxml");
    }

    @Bean(name = "addFileWindow")
    public ViewHolder getAddFilePageView() throws IOException {
        return loadView("fxml/addFile.fxml");
    }

    /**
     * Именно благодаря этому методу мы добавили контроллер в контекст спринга,
     * и заставили его сделать произвести все необходимые инъекции.
     */
    @Bean
    public ContextController contextController() throws IOException {
        return (ContextController) getMainPageView().getController();
    }

    @Bean
    public ScriptController scriptController() throws IOException {
        return (ScriptController) getScriptPageView().getController();
    }

    @Bean
    public AddFileController addFileController() throws IOException {
        return (AddFileController) getAddFilePageView().getController();
    }



    /**
     * Самый обыкновенный способ использовать FXML загрузчик.
     * Как раз-таки на этом этапе будет создан объект-контроллер,
     * произведены все FXML инъекции и вызван метод инициализации контроллера.
     */
    protected ViewHolder loadView(String url) throws IOException {
        try (InputStream fxmlStream = getClass().getClassLoader().getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        }
    }

    /**
     * Класс - оболочка: контроллер мы обязаны указать в качестве бина,
     * а view - представление, нам предстоит использовать в точке входа {@link Application}.
     */
    public class ViewHolder {
        private Parent view;
        private Object controller;

        public ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }

}
