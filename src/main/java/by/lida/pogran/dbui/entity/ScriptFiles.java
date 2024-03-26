package by.lida.pogran.dbui.entity;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@Data
public class ScriptFiles {
    @XStreamImplicit(itemFieldName="file")
    private List<ScriptFile>fileList;
}
