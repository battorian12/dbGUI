# GUIforOracleScripts #

### Сборка
```
mvn clean package
```

### Запуск
```
java -jar target/gui-0.0.1.jar


# Файл сценария VBScript (.vbs) (запуск .jar hidden) 
```
1) Создать файл .vbs -> указав путь до .jar
Set objShell = WScript.CreateObject("WScript.Shell")
objShell.Run "cmd /c start /min /b C:\Users\Administrator.PC03242\Desktop\testGUI\sample-spring-boot-javafx\target\gui-0.0.1.jar", 0, False
Set objShell = Nothing
2) Создать ярлык для запуска и изменения иконки ярлыка
