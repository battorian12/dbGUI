package by.lida.pogran.dbui.entity;

import lombok.Getter;

@Getter
public enum ServiceName {
    ASOOSD ("ASOOSD", "10.17.3.33", "ASOOSD"),
    POGZBEN ("POGZBEN", "10.35.1.247", "pogzben"),
    POGPSUR ("POGPSUR", "10.35.1.249", "pogpsur"),
    OPKDOT ("OPKDOT", "10.35.1.252", "opkdot"),
    OPKBEN1 ("OPKBEN1", "10.35.1.251", "opkben1"),
    MPOGZVOR ("MPOGZVOR", "10.35.1.248", "mpogzvor"),
    GKPV  ("GKPV", "10.17.17.1", "ORCL"),
    VCH1234  ("VCH1234", "10.35.1.51", "ORCL"),
    POGPZAB  ("POGPZAB", "10.35.1.253", "pogpzab"),
    POGPGERA    ("POGPGERA", "10.35.1.250", "POGPGERA"),
    POGPSUBB    ("POGPSUBB", "10.35.1.246", "POGPSUBB"),
    POGPDROB   ("POGPDROB", "10.35.1.245", "POGPDROB"),
    POGZDOTI   ("POGZDOTI", "10.35.1.243", "POGZDOTI"),
    RPOGZLID   ("RPOGZLID", "10.35.1.6", "RPOGZLID"),
    BEZENCY   ("BEZENCY", "10.17.253.249", "ORCL4"),
    MARM8333   ("MARM8333", "10.35.1.5", "orcl"),
    MARM1    ("MARM1", "10.35.4.56", "orcl"),
    MARM2    ("MARM2", "10.35.4.57", "orcl"),
    REZERV    ("REZERV", "10.35.1.4", "orcl"),
    MARM_3341     ("MARM_3341", "10.35.9.103", "orcl"),
    MARM_3342      ("MARM_3342", "10.35.9.105", "orcl"),
    MARM_3343      ("MARM_3343", "10.35.9.102", "orcl"),
    MARM_3344     ("MARM_3344", "10.35.9.104", "orcl"),
    MARM_3348("MARM_3348", "10.35.6.101", "ORCL"),
    VCH1234_new ("VCH1234_new", "10.35.1.175", "ORCL");

    String name;
    String serviceName;
    String host;

    ServiceName(String name, String host,String serviceName) {
        this.name = name;
        this.host = host;
        this.serviceName = serviceName;
    }
}
