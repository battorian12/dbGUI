package by.lida.pogran.dbui.entity;

import by.lida.pogran.dbui.constants.SQLConstants;
import lombok.Getter;

@Getter
public enum SQLScript {
    SCRIPT_1("users_asoosd", SQLConstants.users_asoosd,"sss.sql","скрипт для ....users_asoosd!."),
    SCRIPT_2("z1z4", SQLConstants.users_asoosd,"src/main/resources/scripts/#z1-z4.sql","скрипт для ....ОПИСАНИЕ2!."),
    SCRIPT_3("select1",SQLConstants.users_asoosd,"","скрипт для ....ОПИСАНИЕ3!."),
    SCRIPT_4("select1", SQLConstants.users_asoosd,"","скрипт для ....ОПИСАНИЕ4!."),
    SCRIPT_5("select2", SQLConstants.users_asoosd,"","скрипт для ....ОПИСАНИЕ5!."),
    SCRIPT_6("select3", SQLConstants.users_asoosd,"","скрипт для ....ОПИСАНИЕ6!."),
    SCRIPT_7("select4", SQLConstants.users_asoosd,"","скрипт для ....ОПИСАНИЕ7!."),
    SCRIPT_8("select5", SQLConstants.users_asoosd,"",""),
    SCRIPT_9("select6", SQLConstants.users_asoosd,"",""),
    SCRIPT_10("select7", SQLConstants.users_asoosd,"",""),
    SCRIPT_11("select8", SQLConstants.users_asoosd,"",""),
    SCRIPT_12("select9", SQLConstants.users_asoosd,"",""),
    SCRIPT_13("select10", SQLConstants.users_asoosd,"","");

    String name;
    String script;
    String path;
    String description;

    SQLScript(String name, String script,String path,String description) {
        this.name = name;
        this.script = script;
        this.path = path;
        this.description = description;
    }
}
