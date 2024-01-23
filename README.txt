【规则引擎开发工程目录介绍】
├─src
│  ├─com
│  │  └─fh
│  │      └─fdp
│  │          └─rule
│  │              ├─command 存放用户自定义command类，每个command有个同名.md描述文件,用于FDP界面介绍自定义command用法
│  │              │   └─CommandDemo.java 自定义规则样例
│  │              │   └─CommandDemo.md   自定义规则样例描述文件
│  │              └─TestMain.java 开发工程运行主类
│  ├─conf 用于存放规则链xml文件，文件命名规范以.chain.xml结尾
│  │  └─rule1.chain.xml 自定义规则链xml配置文件样例
│  └─log4j2.xml 日志配置文件
├─fdp
│  └─lib FDP提供的公共jar包，供开发自定义command使用
├─lib 用于存放fdp/lib目录之外的jar包，由研发人员审核确认后确定是否使用
├─data 用于存放测试数据
│  └─test.bcp 样例工程测试数据
├─build.xml ant打包脚本
└─build.bat 打包脚本