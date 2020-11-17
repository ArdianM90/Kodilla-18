call gradlew build
if "%ERRORLEVEL%" == "0" goto rename
echo.
echo GRADLEW BUILD has errors - breaking work
goto fail

:rename
del build\libs\crud.war
ren build\libs\tasks-0.0.1-SNAPSHOT.war crud.war
if "%ERRORLEVEL%" == "0" goto stoptomcat
echo Cannot rename file
goto fail

:stoptomcat
call "C:\Apache Tomcat\apache-tomcat-9.0.39\bin\shutdown.bat"

:copyfile
del "C:\Apache Tomcat\apache-tomcat-9.0.39\webapps\crud.war"
copy "build\libs\crud.war" "C:\Apache Tomcat\apache-tomcat-9.0.39\webapps"
if "%ERRORLEVEL%" == "0" goto runtomcat
echo Cannot copy file
goto fail

:runtomcat
call "C:\Apache Tomcat\apache-tomcat-9.0.39\bin\startup.bat"
if "%ERRORLEVEL%" == "0" goto runmozilla
echo Error while runing up tomcat server.
goto fail

:runmozilla
start "C:\Program Files\Mozilla Firefox\firefox" "http://localhost:8080/crud/v1/task/getTasks"
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.