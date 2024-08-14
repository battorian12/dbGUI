package by.lida.pogran.dbui.controller;

import by.lida.pogran.dbui.entity.SelectedScript;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс проверки содержимого конфигурации.
 *
 * @version 1.0
 * @autor Petrovskiy
 */
@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckConfigController {

    public TextArea descriptionText;

    @FXML
    public void initialize(){
        descriptionText.setText(SelectedScript.getInstance().getScript());
    }
}
