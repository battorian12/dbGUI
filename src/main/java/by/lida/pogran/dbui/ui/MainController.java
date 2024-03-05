package by.lida.pogran.dbui.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    // Инъекции Spring
//    @Autowired private ContactService contactService;
    @FXML private AnchorPane rootPane;
    @FXML private Button button;

    // Инъекции JavaFX
//    @FXML private TableView<Contact> table;
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;

    // Variables
//    private ObservableList<Contact> data;

    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     *
     * Обратите внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     *
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct,
     * который вызовется спрингом, после того, как им будут произведены все инъекции.
     * {@link MainController#init()}
     */
    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
//        List<Contact> contacts = contactService.findAll();
//        data = FXCollections.observableArrayList(contacts);
//
//        // Столбцы таблицы
//        TableColumn<Contact, String> idColumn = new TableColumn<>("ID");
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        TableColumn<Contact, String> nameColumn = new TableColumn<>("Имя");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Телефон");
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
//
//        TableColumn<Contact, String> emailColumn = new TableColumn<>("E-mail");
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
//
//        table.getColumns().setAll(idColumn, nameColumn, phoneColumn, emailColumn);
//
//        // Данные таблицы
//        table.setItems(data);
    }

    /**
     * Метод, вызываемый при нажатии на кнопку "Добавить".
     * Привязан к кнопке в FXML файле представления.
     */
    @FXML
    public void addContact() {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(email)) {
            return;
        }

//        Contact contact = new Contact(name, phone, email);
//        contactService.save(contact);
//        data.add(contact);

        // чистим поля
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }

    @FXML
    public void nextPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/contextForm.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Second window");
        stage.setScene(new Scene(parent));
        stage.show();
    }


}
