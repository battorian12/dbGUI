package by.lida.pogran.dbui.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class ScriptFile {
    String name;
    String path;
    String description;
}
