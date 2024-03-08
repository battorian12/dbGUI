package by.lida.pogran.dbui.entity;

import by.lida.pogran.dbui.constants.SQLConstants;
import lombok.Getter;

@Getter
public enum SQLScript {
    SCRIPT_1("users_asoosd", SQLConstants.users_asoosd,"src/main/resources/scripts/users_ASOOSD.sql"),
    SCRIPT_2("z1z4", SQLConstants.users_asoosd,"src/main/resources/scripts/#z1-z4.sql"),
    SCRIPT_3("select1",SQLConstants.users_asoosd,""),
    SCRIPT_4("select1", SQLConstants.users_asoosd,""),
    SCRIPT_5("select2", SQLConstants.users_asoosd,""),
    SCRIPT_6("select3", SQLConstants.users_asoosd,""),
    SCRIPT_7("select4", SQLConstants.users_asoosd,""),
    SCRIPT_8("select5", SQLConstants.users_asoosd,""),
    SCRIPT_9("select6", SQLConstants.users_asoosd,""),
    SCRIPT_10("select7", SQLConstants.users_asoosd,""),
    SCRIPT_11("select8", SQLConstants.users_asoosd,""),
    SCRIPT_12("select9", SQLConstants.users_asoosd,""),
    SCRIPT_13("select10", SQLConstants.users_asoosd,"");

    String name;
    String script;
    String path;

    SQLScript(String name, String script,String path) {
        this.name = name;
        this.script = script;
        this.path = path;
    }
}
