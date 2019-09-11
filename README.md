# train-route-calculator
A custom graph library project

#### Build application
Just launch `mvn package`

#### Launch application in windows
To start the application in windows, first build the application and extract the generated assembly zip in any folder of your windows system. Then you can execute the file `launcher.bat`. For example:
```
C:\MyFolder> launcher.bat "--railroadFile=railroad_file.csv" "--compute=shortest" "--route=A-D"
```
#### Options
Launch the help option as :
```
C:\MyFolder> launcher.bat "--help"
```