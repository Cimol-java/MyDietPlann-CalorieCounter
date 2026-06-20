@echo off
echo Compiling...

:: Kumpulkan semua file .java ke satu file list
dir /s /b src\main\java\*.java > .sources.txt

:: Compile
javac -d target\classes @.sources.txt
if %errorlevel% neq 0 (
    echo Compile GAGAL.
    del .sources.txt
    pause
    exit /b 1
)
del .sources.txt

echo Compile berhasil. Menjalankan program...
echo.
java -cp target\classes com.mydietplan.Main
