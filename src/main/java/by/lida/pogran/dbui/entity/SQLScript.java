package by.lida.pogran.dbui.entity;

import lombok.Getter;

@Getter
public enum SQLScript {
    SCRIPT_1("update", "(((((((((((((((((SELECT * from users)))))))))))))))))"),
    SCRIPT_2("create", "SELECT * from users"),
    SCRIPT_3("select", "SELECT * from users"),
    SCRIPT_4("select1", "***********************************"),
    SCRIPT_5("select2", "SELECT * from users"),
    SCRIPT_6("select3", "SELECT * from users");

    String name;
    String script;

    SQLScript(String name, String script) {
        this.name = name;
        this.script = script;
    }
}
