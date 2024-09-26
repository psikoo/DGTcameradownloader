echo off
echo.
echo #--------------------------------#
echo ^| Starting maven automated setup ^|
echo #--------------------------------#

cd "%userprofile%\Desktop"
if not exist "%userprofile%\Desktop\DGTCD" ( 
    mkdir "DGTCD"
    cd "%userprofile%\Desktop\DGTCD"
) else (
    cd "%userprofile%\Desktop\DGTCD"
)

echo ^| Copying and renaming files     ^|
echo #--------------------------------#

if exist README.txt ( del README.txt )
if exist DGTCDstart.cmd ( del DGTCDstart.cmd )
if exist dgtcd.jar ( del dgtcd.jar )
copy "%userprofile%\Desktop\projects\java\dgt\README.txt" "%userprofile%\Desktop\DGTCD" >NUL
copy "%userprofile%\Desktop\projects\java\dgt\src\main\resources\DGTCDstart.cmd" "%userprofile%\Desktop\DGTCD" >NUL
if not exist "%userprofile%\Desktop\DGTCD\lib\ffmpeg" ( mkdir "%userprofile%\Desktop\DGTCD\lib\ffmpeg" )
copy "%userprofile%\Desktop\projects\java\dgt\ffmpeg\bin\ffmpeg.exe" "%userprofile%\Desktop\DGTCD\lib\ffmpeg\ffmpeg.exe" >NUL
copy "%userprofile%\Desktop\projects\java\dgt\target\dgtcd-1.0-jar-with-dependencies.jar" "%userprofile%\Desktop\DGTCD" >NUL
ren dgtcd-1.0-jar-with-dependencies.jar dgtcd.jar

echo ^| Packing to .zip archive        ^|
echo #--------------------------------#

if exist DGTCD.gzip ( del DGTCD.gzip )
tar -czf DGTCD.gzip "dgtcd.jar" "DGTCDstart.cmd" "README.txt" 2>NUl
echo ^| Starting dgtcd.jar             ^|
echo #--------------------------------#

cd "%userprofile%\Desktop\DGTCD"
echo.
.\DGTCDstart.cmd