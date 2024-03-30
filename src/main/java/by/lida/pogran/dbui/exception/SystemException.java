package by.lida.pogran.dbui.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemException  extends RuntimeException{
    String message;

    public SystemException(String message){
        this.message = message;
    }
}