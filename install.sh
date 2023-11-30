#!/bin/sh

WORK_DIR=`pwd`
FILE_DIR='/home/oms/daps'

#定义输出颜色
SETCOLOR_SUCCESS="echo -en \\033[1;32m"
SETCOLOR_FAILURE="echo -en \\033[1;31m"
SETCOLOR_WARNING="echo -en \\033[1;33m"
SETCOLOR_NORMAL="echo -en \\033[0;39m"

#定义记录日志的函数
logMsg()
{
	time=`date "+%D %T"`
	echo -e "[$time] : INFO : $*"
	$SETCOLOR_NORMAL
}

##警告输出
logWarnMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_WARNING
	echo -e "[$time] : WARN : $*"
	$SETCOLOR_NORMAL
}

##成功输出
logSucMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_SUCCESS
	echo -e "[$time] : SUCCESS : $*"
	$SETCOLOR_NORMAL
}

##错误输出
logErrorMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_FAILURE
	echo -e "[$time] : ERROR : $*"
	$SETCOLOR_NORMAL
}
#安装配置文件路径
GLOBE_COMMON_CONF=$WORK_DIR/globe.common.conf
FDP_HOME=`cat $GLOBE_COMMON_CONF | grep '^FDP_HOME' | cut -d= -f2`
#用户名，默认为root
LOGIN_USERNAME=`getent passwd ${SUDO_UID:-$(id -u)} | cut -d: -f 1`

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
	logErrorMsg "[FDP环境检查]fdp环境不存在对应的fdp移包目录: $FDP_HOME，请联系fdp或者应用市场排查"
	exit 1
fi
FDP_LOCAL_HOME=`cat "${FDP_HOME}/web/webapp/WEB-INF/classes/system.properties"	| grep '^local.home' | cut -d= -f2`
#替换掉\r字符
sed -i "s/\r//" $GLOBE_COMMON_CONF
dos2unix -q $GLOBE_COMMON_CONF

checkConfig(){
	logMsg "OcaDbn_ip=$OcaDbn_ip"
	logMsg "RedisDbn_ip=$RedisDbn_ip"
	logMsg "RedisDbn_dbPort=$RedisDbn_dbPort"
	logMsg "RedisDbn_dbPassword=$RedisDbn_dbPassword"
	logMsg "OCA_DataService_ip=$OCA_DataService_ip"
	logMsg "DataExchange_ip=$DataExchange_ip"
	logMsg "FDP_HOME=$FDP_HOME"
	logMsg "MassSearchAgent_ip=$MassSearchAgent_ip"
	logMsg "LOCAL_CITYCODE=$LOCAL_CITYCODE"
	sed -i "s#\${OcaDbn_ip}#$OcaDbn_ip#g" $GLOBE_COMMON_CONF
	sed -i "s#\${RedisDbn_ip}#$RedisDbn_ip#g" $GLOBE_COMMON_CONF
	sed -i "s#\${RedisDbn_dbPort}#$RedisDbn_dbPort#g" $GLOBE_COMMON_CONF
	sed -i "s#\${RedisDbn_dbPassword}#$RedisDbn_dbPassword#g" $GLOBE_COMMON_CONF
	sed -i "s#\${OCA_DataService_ip}#$OCA_DataService_ip#g" $GLOBE_COMMON_CONF
	sed -i "s#\${DataExchange_ip}#$DataExchange_ip#g" $GLOBE_COMMON_CONF
	sed -i "s#\${FDP_HOME}#$FDP_HOME#g" $GLOBE_COMMON_CONF
	sed -i "s#\${MassSearchAgent_ip}#$MassSearchAgent_ip#g" $GLOBE_COMMON_CONF
	sed -i "s#\${LOCAL_CITYCODE}#$LOCAL_CITYCODE#g" $GLOBE_COMMON_CONF
	#抽取配置文件
	lines=`cat ${GLOBE_COMMON_CONF} | grep -v "##" | sed 's/[\r\t]*//g' | sed 's/ //g' `
	for line in $lines
	do
		if [ -z "$line" ] ; then
			continue
		fi
		if ! test `echo $line | grep "="`;then
			logErrorMsg "[GLOBE_COMMON_CONF] Bad format!! line: $line"
			return 1
		fi
		local name=`echo "$line" | awk -F '=' '{print $1}'`
		local value=`echo "$line" | awk -F '=' '{print $2}'`
		if [ -z "$name" ]; then
			logErrorMsg "[GLOBE_COMMON_CONF] Bad format!! line: $line"
			exit 1
		fi
		if [ -z "$value" ]; then
			logErrorMsg "[GLOBE_COMMON_CONF] Value Empty!! line: $line"
			exit 1
		fi
		export $name=$value
		logMsg "export $name=$value"
	done
}

configFile(){
	if [[ $AUTO_START != 0 && $AUTO_START != 1 ]]; then
		logErrorMsg "[FDP] FDP AUTO_START VALUE ERROR..please check"
		exit 1
	fi
	#读取文件内容，加密密码，用于替换FDP中的详情
	if [ ! -f $FILE_DIR/$FMDB_CORESITE ]; then
		logErrorMsg "[FMDB_CORESITE] FMDB_CORESITE file not exist..please check"
		exit 1
	else
		export FMDB_CORESITE_BASE64=`base64 -w 100000 $FILE_DIR/$FMDB_CORESITE`
	fi
	if [ ! -f $FILE_DIR/$FMDB_HDFSSITE ]; then
		logErrorMsg "[FMDB_HDFSSITE] FMDB_HDFSSITE file not exist..please check"
		exit 1
	else
		export FMDB_HDFSSITE_BASE64=`base64 -w 100000 $FILE_DIR/$FMDB_HDFSSITE`
	fi
	if [ ! -f $FILE_DIR/$FMDB_KEYTAB ]; then
		logErrorMsg "[FMDB_KEYTAB] FMDB_KEYTAB file not exist..please check"
		exit 1
	else
		export FMDB_KEYTAB_BASE64=`base64 -w 100000 $FILE_DIR/$FMDB_KEYTAB`
	fi
	if [ ! -f $FILE_DIR/$FMDB_KRB5 ]; then
		logErrorMsg "[FMDB_KRB5] FMDB_KRB5 file not exist..please check"
		exit 1
	else
		export FMDB_KRB5_BASE64=`base64 -w 100000 $FILE_DIR/$FMDB_KRB5`
	fi
	if [ ! -f $FILE_DIR/$FMDB_USERNAME ]; then
		logErrorMsg "[FMDB_USERNAME] FMDB_USERNAME file not exist..please check"
		exit 1
	else
		export FMDB_USERNAME_CONTENT=`cat $FILE_DIR/$FMDB_USERNAME`
	fi
	if [ ! -f $FILE_DIR/$FMDB_DRIVER_URL ]; then
		logErrorMsg "[FMDB_DRIVER_URL] FMDB_DRIVER_URL file not exist..please check"
		exit 1
	else
	 export FMDB_MASSDATA_URL=`cat $FILE_DIR/$FMDB_DRIVER_URL | sed "s#/default;#/$FMDB_MASSDATA;#g"`
	 export FMDB_OCA_URL=`cat $FILE_DIR/$FMDB_DRIVER_URL | sed "s#/default;#/$FMDB_OCA;#g"`
	 export FMDB_ODS_URL=`cat $FILE_DIR/$FMDB_DRIVER_URL | sed "s#/default;#/$FMDB_ODS;#g"`
	 logMsg "export FMDB_MASSDATA_URL=$FMDB_MASSDATA_URL"
	 logMsg "export FMDB_OCA_URL=$FMDB_OCA_URL"
	 logMsg "export FMDB_ODS_URL=$FMDB_ODS_URL"
	fi

	export PG_PASS_ENCRY=`sh encryPwd.sh $PG_PASS`
	export REDIS_PASSWORD_ENCRY=`sh encryPwd.sh $REDIS_PASSWORD`
	export SCP_PASS_ENCRY=`sh encryPwd.sh $SCP_PASS`
	export FTP_PASS_ENCRY=`sh encryPwd.sh $FTP_PASS`
}

configSql(){
	#需要替换的文件
	sqlConf=$WORK_DIR/sql/*.sql
	##替换文件中的变量为真实值(需要全词匹配、批量替换)
	sed -i "s#\${FMDB_CORESITE_BASE64}#$FMDB_CORESITE_BASE64#g" $sqlConf
	sed -i "s#\${FMDB_HDFSSITE_BASE64}#$FMDB_HDFSSITE_BASE64#g" $sqlConf
	sed -i "s#\${FMDB_KEYTAB_BASE64}#$FMDB_KEYTAB_BASE64#g" $sqlConf
	sed -i "s#\${FMDB_KRB5_BASE64}#$FMDB_KRB5_BASE64#g" $sqlConf
	sed -i "s#\${FMDB_USERNAME}#$FMDB_USERNAME_CONTENT#g" $sqlConf
	sed -i "s#\${FMDB_DRIVER_URL}#$FMDB_DRIVER_URL_CONTENT#g" $sqlConf
	sed -i "s#\${FMDB_CORESITE}#$FMDB_CORESITE#g" $sqlConf
	sed -i "s#\${FMDB_HDFSSITE}#$FMDB_HDFSSITE#g" $sqlConf
	sed -i "s#\${FMDB_KEYTAB}#$FMDB_KEYTAB#g" $sqlConf
	sed -i "s#\${FMDB_KRB5}#$FMDB_KRB5#g" $sqlConf
	sed -i "s#\${FMDB_MASSDATA_URL}#$FMDB_MASSDATA_URL#g" $sqlConf
	sed -i "s#\${FMDB_OCA_URL}#$FMDB_OCA_URL#g" $sqlConf
	sed -i "s#\${FMDB_ODS_URL}#$FMDB_ODS_URL#g" $sqlConf
	sed -i "s#\${PG_IP}#$PG_IP#g" $sqlConf
	sed -i "s#\${PG_PORT}#$PG_PORT#g" $sqlConf
	sed -i "s#\${PG_DB}#$PG_DB#g" $sqlConf
	sed -i "s#\${PG_USER}#$PG_USER#g" $sqlConf
	sed -i "s#\${PG_PASS_ENCRY}#$PG_PASS_ENCRY#g" $sqlConf
	sed -i "s#\${REDIS_IP}#$REDIS_IP#g" $sqlConf
	sed -i "s#\${REDIS_PORT}#$REDIS_PORT#g" $sqlConf
	sed -i "s#\${REDIS_PASSWORD_ENCRY}#$REDIS_PASSWORD_ENCRY#g" $sqlConf
	sed -i "s#\${REDIS_AD_ADDRESS_DB}#$REDIS_AD_ADDRESS_DB#g" $sqlConf
	sed -i "s#\${REDIS_AD_TASK_DB}#$REDIS_AD_TASK_DB#g" $sqlConf
	sed -i "s#\${REDIS_PHONE_TASK_DB}#$REDIS_PHONE_TASK_DB#g" $sqlConf
	sed -i "s#\${REDIS_POINT_COUNT_DB}#$REDIS_POINT_COUNT_DB#g" $sqlConf
	sed -i "s#\${REDIS_TASK_CACHE_DB}#$REDIS_TASK_CACHE_DB#g" $sqlConf
	sed -i "s#\${SCP_IP}#$SCP_IP#g" $sqlConf
	sed -i "s#\${SCP_PORT}#$SCP_PORT#g" $sqlConf
	sed -i "s#\${SCP_USER}#$SCP_USER#g" $sqlConf
	sed -i "s#\${SCP_PASS_ENCRY}#$SCP_PASS_ENCRY#g" $sqlConf
	sed -i "s#\${SCP_PATH}#$SCP_PATH#g" $sqlConf
	sed -i "s#\${FTP_IP}#$FTP_IP#g" $sqlConf
	sed -i "s#\${FTP_PORT}#$FTP_PORT#g" $sqlConf
	sed -i "s#\${FTP_USER}#$FTP_USER#g" $sqlConf
	sed -i "s#\${FTP_PASS_ENCRY}#$FTP_PASS_ENCRY#g" $sqlConf
	sed -i "s#\${FTP_IN_TO_OUT_PATH}#$FTP_IN_TO_OUT_PATH#g" $sqlConf
	sed -i "s#\${FTP_OUT_TO_IN_PATH}#$FTP_OUT_TO_IN_PATH#g" $sqlConf
	sed -i "s#\${FDP_USER}#$FDP_USER#g" $sqlConf
}
configRule(){
	ruleConfig=$WORK_DIR/rule/resources/data.properties
	sed -i "s#LOCAL_AREACODE=.*#LOCAL_AREACODE=$LOCAL_AREACODE#g" $ruleConfig
	sed -i "s#AES_PASSKEY=.*#AES_PASSKEY=$AES_PASSKEY#g" $ruleConfig
	sed -i "s#TMP_PATH=.*#TMP_PATH=$TMP_PATH#g" $ruleConfig
	sed -i "s#FHFS_HOST=.*#FHFS_HOST=$FHFS_HOST#g" $ruleConfig
	sed -i "s#REDIS_HOST=.*#REDIS_HOST=$REDIS_IP#g" $ruleConfig
	sed -i "s#REDIS_PORT=.*#REDIS_PORT=$REDIS_PORT#g" $ruleConfig
	sed -i "s#REDIS_PASSWD=.*#REDIS_PASSWD=$REDIS_PASSWORD#g" $ruleConfig
	sed -i "s#REDIS_EXPIRE_DAY=.*#REDIS_EXPIRE_DAY=$REDIS_EXPIRE_DAY#g" $ruleConfig
	sed -i "s#REDIS_AD_ADDRESS_DB=.*#REDIS_AD_ADDRESS_DB=$REDIS_AD_ADDRESS_DB#g" $ruleConfig
	sed -i "s#REDIS_AD_TASK_DB=.*#REDIS_AD_TASK_DB=$REDIS_AD_TASK_DB#g" $ruleConfig
	sed -i "s#REDIS_PHONE_TASK_DB=.*#REDIS_PHONE_TASK_DB=$REDIS_PHONE_TASK_DB#g" $ruleConfig
	sed -i "s#REDIS_POINT_COUNT_DB=.*#REDIS_POINT_COUNT_DB=$REDIS_POINT_COUNT_DB#g" $ruleConfig
	sed -i "s#REDIS_TASK_CACHE_DB=.*#REDIS_TASK_CACHE_DB=$REDIS_TASK_CACHE_DB#g" $ruleConfig
	#sed -i "s#REDIS_EXPAND_DOMESTIC=.*#REDIS_EXPAND_DOMESTIC=$REDIS_EXPAND_DOMESTIC#g" $ruleConfig
	#sed -i "s#REDIS_ATVT_DB=.*#REDIS_ATVT_DB=$REDIS_ATVT_DB#g" $ruleConfig
	#sed -i "s#REDIS_ATVT_RELATION=.*#REDIS_ATVT_RELATION=$REDIS_ATVT_RELATION#g" $ruleConfig
}

importTask(){
	logMsg "开始导入FDP任务..."
	cd $WORK_DIR/sql
	zip -r -q task.zip *
	#通过接口上传sql
	
	fileId=`md5sum task.zip|cut -d' ' -f1`
	mkdir -p ${FDP_HOME}/web/webapp/uploadFile/$fileId
	mv task.zip ${FDP_HOME}/web/webapp/uploadFile/$fileId/task.zip
	result=$(curl -k -X POST -H "Content-type: application/json" -d "{\"fileId\":\"$fileId\",\"fileName\":\"task.zip\",\"mode\":\"$AUTO_START\"}" "${FDP_LOCAL_HOME}api/v1/job/importJobWithIps")
	containsOk=$(echo $result | grep "\"code\":0")
	if [[ "$containsOk" == '' ]]; then
		logErrorMsg "[IMPORT FDP]upload task.zip error::$result"
		rm -rf ${FDP_HOME}/web/webapp/uploadFile/$fileId
		exit 1;
	fi
	rm -rf ${FDP_HOME}/web/webapp/uploadFile/$fileId
	logSucMsg "upload rule.zip success.."
}
importRule(){
	logMsg "开始导入FDP任务..."
	cd $WORK_DIR/rule
	zip -r -q fdp-rule-osint_oca_datadealscript.zip *
	#通过接口上传sql
	fileId=`md5sum fdp-rule-osint_oca_datadealscript.zip|cut -d' ' -f1`
	mkdir -p ${FDP_HOME}/web/webapp/uploadFile/$fileId
	mv fdp-rule-osint_oca_datadealscript.zip ${FDP_HOME}/web/webapp/uploadFile/$fileId/fdp-rule-osint_oca_datadealscript.zip
	result=$(curl -k -X POST -H "Content-type: application/json" -d "{\"fileId\":\"$fileId\",\"fileName\":\"fdp-rule-osint_oca_datadealscript.zip\"}" "${FDP_LOCAL_HOME}api/v1/rule/loadPackage")
	containsOk=$(echo $result | grep "\"code\":0")
	if [[ "$containsOk" == '' ]]; then
		logErrorMsg "[IMPORT FDP]upload fdp-rule-osint_oca_datadealscript.zip error::$result"
		rm -rf ${FDP_HOME}/web/webapp/uploadFile/$fileId
		exit 1;
	fi
	rm -rf ${FDP_HOME}/web/webapp/uploadFile/$fileId
	logSucMsg "upload osint_oca_datadealscript.zip success.."
}
#安装
install(){
	#配置文件检查
	checkConfig
	#文本Base64,密码加密
	configFile
	#FDP任务修改
	configSql
	#派大星配置修改
	configRule
	#提交FDP-派大星脚本
	importRule
	#提交FDP任务
	importTask
	logSucMsg "INSTALL SUCCESS.."
}
install
exit 0