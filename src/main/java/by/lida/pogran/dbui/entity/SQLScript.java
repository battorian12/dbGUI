package by.lida.pogran.dbui.entity;

import lombok.Getter;

@Getter
public enum SQLScript {
    SCRIPT_1("users_asoosd","users_ASOOSD.sql","Синхронизация пользователей"),
    SCRIPT_2("z1z4","#z1-z4.sql","Общий скрипт"),
    SCRIPT_4("duty+plan+razvedka","duty+plan+razvedka.sql","Все планы");

    String name;
    String path;
    String description;

    SQLScript(String name,String path, String description) {
        this.name = name;
        this.path = path;
        this.description = description;
    }
}
