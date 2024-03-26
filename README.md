# GUIforOracleScripts #

### Сборка
```
mvn clean package
```
_____________________
 ***ВАЖНО!!***
## Данные приложения должны храниться в директории: <br/>(C:\oracleGuiProgrData) ##

# Запуск #

java -jar target/gui-0.0.1.jar

## Файл сценария VBScript (.vbs) (запуск .jar hidden) ##

>1) Создать файл .vbs -> указав путь до .jar <br/>
Set objShell = WScript.CreateObject("WScript.Shell")<br/>
objShell.Run "cmd /c start /min /b C:\Users\Administrator.PC03242\Desktop\testGUI\sample-spring-boot-javafx\target\gui-0.0.1.jar", 0, False<br/>
Set objShell = Nothing<br/>
>>2) Создать ярлык для запуска и изменения его значка
