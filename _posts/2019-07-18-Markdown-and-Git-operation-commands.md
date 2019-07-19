# Day2

------

### Markdown基本语法

1.标题     	

- ‘#‘  一级标题
- ‘##’  二级标题
- ‘###’ 三级标题
- ‘####’ 四级标题
- ‘#####’ 五级标题
- ‘######’ 六级标题

------

2.字体

```
加粗 **文本内容**
斜体 *文本内容*
斜体并加粗 ***文本内容***
删除线 ～～文本内容～～
```

------

3.引用

​	'>'引用的内容，可以以无限叠加 如下：

![效果图](https://upload-images.jianshu.io/upload_images/1293430-aa265ce53d458695.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/393/format/webp)

------

​	4.引用图片

```
![GitHub set up](http://zh.mweb.im/asset/img/set-up-git.gif)
格式: ![Alt Text](url)
![图片alt](图片地址 "图片title"可省略)
```

​	'![](https://upload-images.jianshu.io/upload_images/1293430-4362308e14d20dbb.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/310/format/webp)'

5.超链接引用

```
[GitHub][1]
[1]:http://github.com
自动生成连接  <http://www.github.com/>
```

6.列表(有序，无序)

-  无序列表

  使用 ' - ' ,' + ',' * '  任意一种，与文本内容都要有一个空格.

  ![](https://upload-images.jianshu.io/upload_images/1293430-a214fe776adff15b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/399/format/webp)

-  数字加点式列表

  1.内容 2.内容 

  ![](https://upload-images.jianshu.io/upload_images/1293430-d66a2e0b2e504ec5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/475/format/webp)

- 列表嵌套

  上一级和下一级之间敲三个空格

7.表格

```
表头|表头|表头
---|:--:|---:
内容|内容|内容
内容|内容|内容

第二行分割表头和内容。
- 有一个就行，为了对齐，多加了几个
文字默认居左
-两边加：表示文字居中
-右边加：表示文字居右
注：原生的语法两边都要用 | 包起来。此处省略
```

```
姓名|技能|排行
--|:--:|--:
刘备|哭|大哥
关羽|打|二哥
张飞|骂|三弟
```

| 姓名 | 技能 | 排行 |
| ---- | :--: | ---: |
| 刘备 |  哭  | 大哥 |
| 关羽 |  打  | 二哥 |
| 张飞 |  骂  | 三弟 |

8.代码块显示

单行代码：代码之间分别用一个反引号包起来

```
  `代码内容`
```

```java
`create database hero;`
```

代码块：代码之间分别用三个反引号包起来，且两边的反引号单独占一行

```
(```)
  代码...
  代码...
  代码...
(```)
```

```java
(```)
    function fun(){
         echo "to be or not to be";
    }
    fun();
(```)
```

9.流程图

```
​```flow
st=>start: 开始
op=>operation: My Operation
cond=>condition: Yes or No?
e=>end
st->op->cond
cond(yes)->e
cond(no)->op
&```
```

~~~flow
```flow
st=>start: 开始
op=>operation: My Operation
cond=>condition: Yes or No?
e=>end
st->op->cond
cond(yes)->e
cond(no)->op
&```
~~~



# Git常用篇

- ####  创建版本库

1. 创建版本库：mkdir 目录名
2. pwd 用于显示当前的图表
3. git init 将创建的目录初始化成git可以管理的库
4. 使用git add文件名添加到暂存区内，再 git commit -m"注释" 将其提交到库
5. 可以通过 git status 查看是否还有未提交的文件。修改文件内容后也可以使用 git status 会提示该文件被修改但未被提交
6. git diff 文件名  查看具体的被修改内容，确认无误后再提交至库

- #### 版本回退和删除

  1. 在提交文件至库后 通过命令 git log 查看修改的次数及时间

  2. 通过该git reset --hard HEAD^回退至修改之前的版本 ' ^ '可以叠加，数量越大表示版本越靠前.

  3. 回退至最新修改的版本，可以通过 git reflog ,显示之前所有的增删该操作，头部都会带有版本号，复制想要回退的版本号 使用命令 git reset --hard 输入版本号

  4. git checkout --文件名是将该文件在工作去的从操作全部撤销

  5. git checkout . 将所有的文件操作全部撤销

  6. rm 文件名 删除文件

     

  #### 分支的命令

  ##分支的创建合并与销毁

  1. git checkout -b  --> 分支名 该命令创建分支 并将新建的分支设为主分支
  2. git branch --> 查看分支
  3. git checkout 分支名  --> 切换分支
  4. git merge 分支名  --> 合并某分支到当前分支
  5. git branch -d 分支名   --> 删除分支

  

  

