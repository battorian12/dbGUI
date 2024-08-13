package by.lida.pogran.dbui.entity;

import lombok.Getter;

/**
 * Enum с информацией о кааждой базе данных.
 * @autor Petrovskiy
 * @version 1.0
 */
@Deprecated
@Getter
public enum ServiceName {
    POGPSUR ("POGPSUR", "10.35.3.11", "pogpsur"),
    POGPSUBB    ("POGPSUBB", "10.35.3.12", "POGPSUBB"),
    POGPGERA    ("POGPGERA", "10.35.3.13", "POGPGERA"),
    POGZBEN ("POGZBEN", "10.35.3.14", "pogzben"),
    POGPDROB   ("POGPDROB", "10.35.3.15", "POGPDROB"),
    POGZDOTI   ("POGZDOTI", "10.35.3.16", "POGZDOTI"),
    POGPZAB  ("POGPZAB", "10.35.3.17", "pogpzab"),
    MPOGZVOR ("MPOGZVOR", "10.35.3.18", "mpogzvor"),
    RPOGZLID   ("RPOGZLID", "10.35.3.19", "RPOGZLID"),
    OPKBEN1 ("OPKBEN1", "10.35.3.20", "opkben1"),
    OPKDOT ("OPKDOT", "10.35.3.21", "opkdot");

    String name;
    String serviceName;
    String host;

    ServiceName(String name, String host,String serviceName) {
        this.name = name;
        this.host = host;
        this.serviceName = serviceName;
    }
}
