package by.lida.pogran.dbui.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Класс файл с информацией основной информацией о скрипте.
 * @autor Petrovskiy
 * @version 1.0
 */
@ToString
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScriptFile {
    /** Поле имя*/
    String name;
    /** Поле пусть*/
    String path;
    /** Поле описание*/
    String description;
}
