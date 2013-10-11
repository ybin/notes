@echo off
::
:: Tool for RELEASE
:: auther : ybin
:: date	  : 2013-10-9
::

if not exist release mkdir release
:: clear release folder
del /Q release\*
:: copy all pdf files to folder: .\release\
for /r %%i in (.) do (
	if exist "%%i\*.pdf" xcopy /Y "%%i\*.pdf" ".\release\" >nul 2>&1
)
:: create tarball, use gzip to compress,
:: if 'tar' is not available, do not show the error message.
tar zcf all.tar.gz release/ 2>nul