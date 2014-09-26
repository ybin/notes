### 启动Cygwin并转到指定目录
两个步骤：

1. 在Cygwin启动bat中设置环境变量记录指定的目录
2. 在bash的profile文件中读取环境变量并切换到该目录

#### Cygwin启动脚本
因为只有git目录才打开Cygwin，所以这里做适当判断，

	@echo off
	:::::::::::::::::::::::::
	:: 脱掉引号
	set MYBASHHERE=%~1

	:: 非git目录只打开cmd窗口，不打开cygwin
	if not exist "%MYBASHHERE%\.git" (
		start /d %MYBASHHERE%
		goto :finish
	)

	:: 去掉冒号，并增加"/cygdrive/"前缀
	set MYBASHHERE=/cygdrive/%MYBASHHERE::=%
	:::::::::::::::::::::::::

	:: git目录打开cygwin
	cd /d D:\cygwin\bin
	bash --login -i

	:finish
	exit

#### 设置bash的profile脚本
在/etc/.profile文件的最后增加如下代码，

	# customized code for bash-here
	if [ "$MYBASHHERE" != "" ]; then
	  cd "$(echo $MYBASHHERE | tr "\134" /)"
	  #echo "$(echo $MYBASHHERE | tr "\134" /)"
	fi
	
注意，`tr`将`"\"`转换为`"/"`，并且外层的双引号不能省，否则带空格的文件路径就会出问题。

#### 额外步骤
通过这两步就可以这样启动Cygwin了，

	> Cygwin.bat "special folder"

还可以设置Eclipse的StartExplorer插件，通过快捷键启动Cygwin，

	cmd.exe /c start D:\cygwin\Cygwin.bat ${resource_path}