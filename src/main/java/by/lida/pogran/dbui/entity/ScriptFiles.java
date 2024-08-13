package by.lida.pogran.dbui.entity;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
 * @autor Petrovskiy
 * @version 1.0
 */
@Data
public class ScriptFiles {
    /** Список файлов для xml иерархии(XStream) */
    @XStreamImplicit(itemFieldName="file")
    private List<ScriptFile>fileList;
}
