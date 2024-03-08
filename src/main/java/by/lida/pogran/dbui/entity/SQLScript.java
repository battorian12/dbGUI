package by.lida.pogran.dbui.entity;

import by.lida.pogran.dbui.constants.SQLConstants;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum SQLScript {
    SCRIPT_1("users_asoosd", Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),"src/main/resources/scripts/users_ASOOSD.sql"),
    SCRIPT_2("z1z4",   Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),"src/main/resources/scripts/#z1-z4.sql"),
    SCRIPT_3("select1",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_4("select1",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_5("select2",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_6("select3",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_7("select4",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_8("select5",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_9("select6",Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_10("select7", Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),""),
    SCRIPT_13("select10", Arrays.asList(SQLConstants.mergeUserQuery,SQLConstants.mergeUserRolesQuery),"");

    String name;
    List<String> script;
    String path;

    SQLScript(String name, List<String> script,String path) {
        this.name = name;
        this.script = script;
        this.path = path;
    }
}
