#! /bin/sh
#colour level
SETCOLOR_SUCCESS="echo -en \\033[1;32m"
SETCOLOR_FAILURE="echo -en \\033[1;31m"
SETCOLOR_WARNING="echo -en \\033[1;33m"
SETCOLOR_NORMAL="echo -en \\033[0;39m"

LogMsg()
{
	time=`date "+%D %T"`
	echo "[$time] : INFO    : $*"
	$SETCOLOR_NORMAL
}

LogWarnMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_WARNING
	echo "[$time] : WARN    : $*"
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
	echo "[$time] : ERROR   : $*"
	$SETCOLOR_NORMAL
}
# 获取当前登录用户
login_user=`getent passwd ${SUDO_UID:-$(id -u)} | cut -d: -f 1`
exit 0