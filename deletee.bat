@echo off

:: delete tex intermediate files in CURRENT directory.

cd %CD%

del /F /Q *.aux > nul 2>&1
del /F /Q *.log > nul 2>&1
del /F /Q *.nav > nul 2>&1
del /F /Q *.out > nul 2>&1
del /F /Q *.snm > nul 2>&1
del /F /Q *.synctex.gz > nul 2>&1
del /F /Q *.toc > nul 2>&1
del /F /Q *.vrb > nul 2>&1
del /F /Q *.dvi > nul 2>&1
::del /F /Q *.pdf > nul 2>&1
del /F /Q *.ps > nul 2>&1
del /F /Q *.tex.bak > nul 2>&1
del /F /Q *.pyg > nul 2>&1
