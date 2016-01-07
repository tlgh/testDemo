1.国内npm镜像 淘宝npm 安装方法
	$ npm install -g cnpm --registry=https://registry.npm.taobao.org
2.qunit gulp 等 工具npm依赖初始化
	cnpm install
3.gulp全局安装
	cnpm install grunt-cli -g
4.项目运行(开发环境)(不必依赖上述环境)
	直接访问src/index.html
5.项目测试
	直接访问test/test.html
6.项目编译
	cmd到项目根目录运行 'gulp' 命令
7.项目运行(发布环境)
	直接访问dist/index.html