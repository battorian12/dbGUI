package by.lida.pogran.dbui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @autor Petrovskiy
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConnectData {
    String name;
    String serviceName;
    String host;
    private static volatile ConnectData instance;


    public static ConnectData getInstance() {
        ConnectData localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectData.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectData();
                }
            }
        }
        return localInstance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("name='").append(name).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", host='").append(host).append('\'');
        return sb.toString();
    }
}
