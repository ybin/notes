### ����Cygwin��ת��ָ��Ŀ¼
�������裺

1. ��Cygwin����bat�����û���������¼ָ����Ŀ¼
2. ��bash��profile�ļ��ж�ȡ�����������л�����Ŀ¼

#### Cygwin�����ű�
��Ϊֻ��gitĿ¼�Ŵ�Cygwin�������������ʵ��жϣ�

	@echo off
	:::::::::::::::::::::::::
	:: �ѵ�����
	set MYBASHHERE=%~1

	:: ��gitĿ¼ֻ��cmd���ڣ�����cygwin
	if not exist "%MYBASHHERE%\.git" (
		start /d %MYBASHHERE%
		goto :finish
	)

	:: ȥ��ð�ţ�������"/cygdrive/"ǰ׺
	set MYBASHHERE=/cygdrive/%MYBASHHERE::=%
	:::::::::::::::::::::::::

	:: gitĿ¼��cygwin
	cd /d D:\cygwin\bin
	bash --login -i

	:finish
	exit

#### ����bash��profile�ű�
��/etc/.profile�ļ�������������´��룬

	# customized code for bash-here
	if [ "$MYBASHHERE" != "" ]; then
	  cd "$(echo $MYBASHHERE | tr "\134" /)"
	  #echo "$(echo $MYBASHHERE | tr "\134" /)"
	fi
	
ע�⣬`tr`��`"\"`ת��Ϊ`"/"`����������˫���Ų���ʡ��������ո���ļ�·���ͻ�����⡣

#### ���ⲽ��
ͨ���������Ϳ�����������Cygwin�ˣ�

	> Cygwin.bat "special folder"

����������Eclipse��StartExplorer�����ͨ����ݼ�����Cygwin��

	cmd.exe /c start D:\cygwin\Cygwin.bat ${resource_path}