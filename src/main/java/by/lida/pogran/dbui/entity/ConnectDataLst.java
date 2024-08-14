package by.lida.pogran.dbui.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
 * @autor Petrovskiy
 * @version 1.0
 */
@XStreamAlias("connectDataLst")
@Data
public class ConnectDataLst {
    /** Список конфигураций подключений для xml иерархии(XStream) */
    @XStreamImplicit(itemFieldName="connectData")
    private List<ConnectData> connectDataList;
}
