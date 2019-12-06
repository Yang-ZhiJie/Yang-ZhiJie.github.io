## Docker for Mac 部署JavaSpringBoot项目及映射Mysql

### 	1:创建并且码完一个SpringBoot后台项目,修改之前application.properties配置文件中的JDBC 改为在Docker容器中启动Mysql容器时 设置的"--name"时的名称 .

#### 例如:

<img src="/Users/yangzhijie/Desktop/Docker使用心得/Snipaste_2019-12-04_22-12-50.png" style="zoom:33%;" />

注意:这里的密码指和用户名指的不是宿主机的mysql用户名而是利用从DockerHub官网的下载的镜像文件

<!--docker pull mysql:[数据库的版本号]/不输入默认下载最新版本数据-->

建议下载与宿主机即本机安装的数据库版本相符 避免原始表格导入.sql文件时因数据库版本问题失败，

输入命令:docker images 查看下载的镜像文件是否已经在本地后，启动命令 <!--docker run [镜像文件的名称]-->将镜像文件变成镜像实例化成为容器。

<!-- docker run -it/-d --name [容器名称/可不写] -p/-P 端口映射地址:3306 -e MYSQL_ROOT_PASSWORD=11223344 ["容器的ID或者名称"]  --default-authentication-plugin=mysql_native_password-->

回车执行会有两个现象：

​		*1：-d---返回一大串哈希值,表示启动了守护式容器并且不会有伪终端出现，该容器特性如若没有交互式前台容器启动，那么守护式会自动杀死（Docker自带机制！！）*

​		*2: -it启动了一个终端，i表示交互式  t为终端即为交互式容器，可以输入命令与该容器进行交互*

**docker** **ps**查看STATUS检查容器是否在运行 运行状态显示Up 退出容器则显示Exit.

​		*3:--name 自定义容器启动后的names，不输入的话Docker会随机生成一个*

​		*4:-p 自定义宿主机映射端口号，方便宿主机可视化工具连接容器中的Mysql省去命令行的繁琐SQL操作*

​		5:*-P Docker随机生成的映射端口（建议-p自定义端口，方便操作）*

​		6:*-e 设置环境变量及容器Mysql的密码*

​		7:*由于Mysql8.0之后版本会出现密码转译问题 加上*--default-authentication-plugin=mysql_native_passwor即可解决

#### 开启Mysql容器之后，打开宿主机的可视化工具例如Navicat 测试连接容器mysql，成功则继续下步操作。

​	开启mysql后 打开新的终端界面，使用mvn -v查看是否有maven。这项操作主要是用于使用maven命令的将JAVA项目打包成jar包 部署到Docker上

如果有，直接进入到Java项目根目录，执行 *mvn clean package* 就可以将项目打包成jar包 根据命令行提示找到jar包 将其复制到 用作生成镜像的文件夹内和Dockerfile放在一起

<img src="/Users/yangzhijie/Desktop/Docker使用心得/Snipaste_2019-12-05_09-27-51.png" style="zoom:33%;" />

下一步就是编写Dockerfile文件夹

Form java:8  表示该镜像文件的base镜像来自java:8,该基础镜像文件包含jdk8

WORKDIR 设置该镜像生成容器时的起始目录 一般设置为根目录 jar包也拷贝到该目录下/

ENTRYPOINT ['java','-jar','.jar文件'] 进入后执行java -jar 命令 运行 .jar文件即java后端项目

EXPOSE 暴露访问的端口



#### Dockerfile编写好后，在终端进入该镜像文件夹 执行命令 *docker build "自定义生成的镜像名字"  .*

​	build完 终端会提示build success，下一步就是容器互联：容器互联的意思就是在Docker环境中 将两个隔离的容器做关联。

 *docker run --name ["容器互联的名字"] --link ["自定义实例化mysql容器名"]:mysql8  -p 8080:8080 ["生成的镜像名字"];

#### 完成以上步骤后，打开网页 测试访问8080端看 看看是否成功，如果8080端口能访问 那就试试前端项目启动 访问后端JAVA项目 调取接口拿数据 都可以的话 即为成功。



