@echo off
::
:: Tool for RELEASE
:: auther : ybin
:: date	  : 2013-10-9
::

if not exist release mkdir release
:: copy all pdf files to folder: .\release\
for /r %%i in (.) do (
	if exist "%%i\*.pdf" xcopy /Y "%%i\*.pdf" ".\release\" >nul 2>&1
)