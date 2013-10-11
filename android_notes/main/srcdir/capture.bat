@echo off
::
:: capture android screen and copy png file to *current* directory.
::
set blablafname=
for /f "usebackq" %%a in (`echo %date%`) do set "blablafname=%%a"
for /f "usebackq delims=: tokens=1-3" %%a in (`echo %time%`) do (
	set "blablafname=%blablafname%-%%a-%%b-%%c"
)

adb shell screencap /sdcard/screenblablablablabla.png
adb pull /sdcard/screenblablablablabla.png %blablafname%.png > nul 2>&1 
echo Save screen capture to %blablafname%.png
adb shell rm /sdcard/screenblablablablabla.png