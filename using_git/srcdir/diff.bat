:: setting external diff tool for Git
:: git config --global diff.external diff_4_git.bat
@echo off
"C:\Program Files\WinMerge\WinMergeU.exe" "%2" "%5"