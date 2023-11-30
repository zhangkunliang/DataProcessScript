#! /bin/sh

#colour level
SETCOLOR_SUCCESS="echo -en \\033[1;32m"
SETCOLOR_FAILURE="echo -en \\033[1;31m"
SETCOLOR_WARNING="echo -en \\033[1;33m"
SETCOLOR_NORMAL="echo -en \\033[0;39m"

LogMsg()
{
	time=`date "+%D %T"`
	echo "[$time] : INFO : $*"
	$SETCOLOR_NORMAL
}

LogWarnMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_WARNING
	echo "[$time] : WARN : $*"
	$SETCOLOR_NORMAL
}

LogSucMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_SUCCESS
	echo "[$time] : SUCCESS : $*"
	$SETCOLOR_NORMAL
}

LogErrorMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_FAILURE
	echo "[$time] : ERROR : $*"
	$SETCOLOR_NORMAL
}

#获取安装运行的用户名
USER_NAME=`getent passwd ${SUDO_UID:-$(id -u)} | cut -d: -f 1`

# 用户名，默认为root
LOGIN_USERNAME=`getent passwd ${SUDO_UID:-$(id -u)} | cut -d: -f 1`

checkFDPVersion(){
	LogMsg "[FDP检测][FDP安装目录检查]-start"
	if [ $LOGIN_USERNAME != "root" ]; then
		FDP_HOME=/home/$LOGIN_USERNAME/fitdataprep
		#同时需要判断是否存在/home/$LOGIN_USERNAME/fitdataprep目录
		if [ ! -d $FDP_HOME ]; then
			FDP_HOME=/home/fitdataprep
		fi
	else
		FDP_HOME=/home/fitdataprep
	fi
	# 进一步判断FDP_HOME目录是否存在
	if [ ! -d $FDP_HOME ]; then
		logErrorMsg "[FDP版本检测][FDP安装目录检查]-end-[ERROR][404][FDP安装目录不存在]"
		exit 1
	else
		LogSucMsg "[FDP版本检测][FDP安装目录检查]-end-[SUCCESS][200][FMDB登录文件目录存在]"
	fi
	LogMsg "[FDP检测][FDP版本检查]-start"
	FDP_VERSION=`cat "${FDP_HOME}/web/webapp/WEB-INF/classes/system.properties"	| grep 'fdp.web.version' | cut -d= -f2`
	if [ "3.3.2" == $FDP_VERSION ]; then
		LogSucMsg "[FDP检测][FDP版本检查]-end-[SUCCESS][200][FDP版本 = $FDP_VERSION ]"
		exit 1
	else
		LogSucMsg "[FDP检测][FDP版本检查]-end-[SUCCESS][500][FDP目标版本 = 3.3.2 ,FDP实际版本 = $FDP_VERSION ]"
	fi
}
checkFmdbAuth(){
	LogMsg "[fmdb文件检测][fmdb登录文件检查]-start"
	if [ -d /home/oms/daps ]; then
		res=`sudo cp /home/oms/daps/* ./conf/fmdb`
		if [ -z "$res" ]; then
			LogSucMsg "[fmdb文件检测][fmdb登录文件检查]-end-[SUCCESS][200][FMDB登录文件目录存在]"
		else
			LogErrorMsg "[fmdb文件检测][fmdb登录文件检查]-end-[ERROR][404][FMDB文件不存在]"
			exit 1
		fi
	else
		LogErrorMsg "[fmdb文件检测][fmdb登录文件检查]-end-[ERROR][404][FMDB文件不存在]"
	exit 1
	fi
}

checkFmdbAuth
checkFDPVersion
exit 0