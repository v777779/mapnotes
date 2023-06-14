rmdir /q/s .gradle
rmdir /q/s .idea
rem rmdir /q/s gradle
rmdir /q/s build

rmdir /q/s app\build
del /q  *.iml *.apks app\*.iml local.properties
rem del /q  gradlew* *.iml  app\*.iml local.properties

copy /y ..\copy_notes\original\build.gradle app\build.gradle