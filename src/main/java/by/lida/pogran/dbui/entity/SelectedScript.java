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
public class SelectedScript {

    String script;
    private static volatile SelectedScript instance;

    public static SelectedScript getInstance() {
        SelectedScript localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectData.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SelectedScript();
                }
            }
        }
        return localInstance;
    }
}
