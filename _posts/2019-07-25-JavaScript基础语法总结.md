# JavaScript基础语法总结

JavaScript引入方式

Script标签内写代码

<script>
  // 在这里写你的JS代码
</script>
引入额外的JS文件

<script src="myscript.js"></script>
JavaScript语言规范

注释（注释是代码之母）

// 这是单行注释

/* */这是多行注释

结束符

JavaScript中的语句要以分号（;）为结束符。

------

#### JavaScript语言基础

变量声明

JavaScript的变量名可以使用_，数字，字母，$组成，不能以数字开头。
声明变量使用 var 变量名; 的格式来进行声明
var name = "Alex";
var age = 18;
注意：

变量名是区分大小写的。

推荐使用驼峰式命名规则。

保留字不能用做变量名。

````
补充：
ES6新增了let命令，用于声明变量。其用法类似于var，但是所声明的变量只在let命令所在的代码块内有效。例如：for循环的计数器就很适合使用let命令。

for (let i=0;i<arr.length;i++){...}
ES6新增const用来声明常量。一旦声明，其值就不能改变。

const PI = 3.1415;
PI // 3.1415

PI = 3
// TypeError: "PI" is read-only
再次补充：保留字列表
````

***

#### JavaScript数据类型

JavaScript拥有动态类型

var x;  // 此时x是undefined
var x = 1;  // 此时x是数字
var x = "Alex"  // 此时x是字符串 
数值(Number)

JavaScript不区分整型和浮点型，就只有一种数字类型。

var a = 12.34;
var b = 20;
var c = 123e5;  // 12300000
var d = 123e-5;  // 0.00123
还有一种NaN，表示不是一个数字（Not a Number）。

常用方法：

parseInt("123")  // 返回123
parseInt("ABC")  // 返回NaN,NaN属性是代表非数字值的特殊值。该属性用于指示某个值不是数字。
parseFloat("123.456")  // 返回123.456
字符串(String)

```
var a = "Hello"
var b = "world;
var c = a + b; 
console.log(c);  // 得到Helloworld
```

#### 常用方法：

| 方法                       | 说明               |
| -------------------------- | ------------------ |
| .length                    | 返回长度           |
| .trim()                    | 移除空白           |
| .trimLeft()                | 移除左边的空白     |
| .trimRight()               | 移除右边的空白     |
| .charAt(n)                 | 返回第n个字符      |
| .concat(value, ...)        | 拼接               |
| .indexOf(substring, start) | 子序列位置         |
| .substring(from, to)       | 根据索引获取子序列 |
| .slice(start, end)         | 切片               |
| .toLowerCase()             | 小写               |
| .toUpperCase()             | 大写               |
| .split(delimiter, limit)   | 分割               |

***

#### 拼接字符串一般使用“+”

***

##### 布尔值(Boolean)

区别于Python，true和false都是小写。

var a = true;
var b = false;
""(空字符串)、0、null、undefined、NaN都是false。

------

##### null和undefined

​	null表示值是空，一般在需要指定或清空一个变量时才会使用，如 name=null;undefined表示当声明一个变量但未初始化时，该变量的默认值是undefined。还有就是函数无明确的返回值时，返回的也是undefined。
null表示变量的值是空，undefined则表示只声明了变量，但还没有赋值。

***

##### 对象(Object)

​	JavaScript 中的所有事物都是对象：字符串、数值、数组、函数...此外，JavaScript 允许自定义对象。

JavaScript 提供多个内建对象，比如 String、Date、Array 等等。

对象只是带有属性和方法的特殊数据类型。

------

##### 数组

数组对象的作用是：使用单独的变量名来存储一系列的值。类似于Python中的列表。

var a = [123, "ABC"];
console.log(a[1]);  // 输出"ABC"

###### 常用方法：

| 方法               | 说明                                       |
| ------------------ | ------------------------------------------ |
| .length            | 数组的大小                                 |
| .push(ele)         | 尾部追加元素                               |
| .pop()             | 获取尾部的元素                             |
| .unshift(ele)      | 头部插入元素                               |
| .shift()           | 头部移除元素                               |
| .slice(start, end) | 切片                                       |
| .reverse()         | 反转                                       |
| .join(seq)         | 将数组元素连接成字符串                     |
| .concat(val, ...)  | 连接数组                                   |
| .sort()            | 排序                                       |
| .forEach()         | 将数组的每个元素传递给回调函数             |
| .splice()          | 删除元素，并向数组添加新元素。             |
| .map()             | 返回一个数组元素调用函数处理后的值的新数组 |

##### 关于sort()需要注意：

```
如果调用该方法时没有使用参数，将按字母顺序对数组中的元素进行排序，说得更精确点，是按照字符编码的顺序进行排序。要实现这一点，首先应把数组的元素都转换成字符串（如有必要），以便进行比较。

如果想按照其他标准进行排序，就需要提供比较函数，该函数要比较两个值，然后返回一个用于说明这两个值的相对顺序的数字。比较函数应该具有两个参数 a 和 b，其返回值如下：

若 a 小于 b，在排序后的数组中 a 应该出现在 b 之前，则返回一个小于 0 的值。
若 a 等于 b，则返回 0。
若 a 大于 b，则返回一个大于 0 的值。
```

###### 示例：

```
function sortNumber(a,b){
    return a - b
}
var arr1 = [11, 100, 22, 55, 33, 44]
arr1.sort(sortNumber)
关于遍历数组中的元素，可以使用下面的方式：

var a = [10, 20, 30, 40];
for (var i=0;i<a.length;i++) {
  console.log(a[i]);
}
```


forEach()

语法：

forEach(function(currentValue, index, arr), thisValue)

function(currentValue, index, arr)	必需。 数组中每个元素需要调用的函数。
函数参数:
参数	描述
currentValue	必需。当前元素
index	可选。当前元素的索引值。
arr	可选。当前元素所属的数组对象。
thisValue	可选。传递给函数的值一般用 "this" 值。
如果这个参数为空， "undefined" 会传递给 "this" 值

***

splice()

语法：

splice(index,howmany,item1,.....,itemX) 

参数	描述
index	必需。规定从何处添加/删除元素。
该参数是开始插入和（或）删除的数组元素的下标，必须是数字。
howmany	必需。规定应该删除多少元素。必须是数字，但可以是 "0"。
如果未规定此参数，则删除从 index 开始到原数组结尾的所有元素。
item1, ..., itemX	可选。要添加到数组的新元素

------

map()

语法：

map(function(currentValue,index,arr), thisValue)
function(currentValue, index,arr)	必须。函数，数组中的每个元素都会执行这个函数
函数参数:
参数	描述
currentValue	必须。当前元素的值
index	可选。当期元素的索引值
arr	可选。当期元素属于的数组对象
thisValue	可选。对象作为该执行回调时使用，传递给函数，用作 "this" 的值。
如果省略了 thisValue ，"this" 的值为 "undefined"

------

类型查询

typeof "abc"  // "string"
typeof null  // "object"
typeof true  // "boolean"
typeof 123 // "number"
typeof是一个一元运算符（就像++，--，！，- 等一元运算符），不是一个函数，也不是一个语句。

对变量或值调用 typeof 运算符将返回下列值之一：

undefined - 如果变量是 Undefined 类型的
boolean - 如果变量是 Boolean 类型的
number - 如果变量是 Number 类型的
string - 如果变量是 String 类型的
object - 如果变量是一种引用类型或 Null 类型的

------

运算符

算数运算符

+ - * / % ++ --
比较运算符

> >= < <= != == === !==
> >注意：

1 == “1”  // true
1 === "1"  // false
逻辑运算符

&& || !
赋值运算符

= += -= *= /=
流程控制

##### if-else

var a = 10;
if (a > 5){
  console.log("yes");
}else {
  console.log("no");
}

------

##### if-else if-else 

var a = 10;
if (a > 5){
  console.log("a > 5");
}else if (a < 5) {
  console.log("a < 5");
}else {
  console.log("a = 5");
}

***

##### switch

var day = new Date().getDay();
switch (day) {
  case 0:
  console.log("Sunday");
  break;
  case 1:
  console.log("Monday");
  break;
default:
  console.log("...")
}
switch中的case子句通常都会加break语句，否则程序会继续执行后续case中的语句。

***

##### for

for (var i=0;i<10;i++) {
  console.log(i);
}

##### while

var i = 0;
while (i < 10) {
  console.log(i);
  i++;
}

***

##### 三元运算

var a = 1;
var b = 2;
var c = a > b ? a : b
函数

函数定义

JavaScript中的函数和Python中的非常类似，只是定义方式有点区别。
// 普通函数定义
function f1() {
  console.log("Hello world!");
}

// 带参数的函数
function f2(a, b) {
  console.log(arguments);  // 内置的arguments对象
  console.log(arguments.length);
  console.log(a, b);
}

// 带返回值的函数
function sum(a, b){
  return a + b;
}
sum(1, 2);  // 调用函数

// 匿名函数方式
var sum = function(a, b){
  return a + b;
}
sum(1, 2);

// 立即执行函数
(function(a, b){
  return a + b;
})(1, 2);
补充：

ES6中允许使用“箭头”（=>）定义函数。

var f = v => v;
// 等同于
var f = function(v){
  return v;
}
如果箭头函数不需要参数或需要多个参数，就是用圆括号代表参数部分：
var f = () => 5;
// 等同于
var f = function(){return 5};

var sum = (num1, num2) => num1 + num2;
// 等同于
var sum = function(num1, num2){
  return num1 + num2;
}

***

##### 函数中的arguments参数

function add(a,b){
  console.log(a+b);
  console.log(arguments.length)
}

add(1,2)
输出：

3 2
注意：

函数只能返回一个值，如果要返回多个值，只能将其放在数组或对象中返回。

函数的全局变量和局部变量

***

##### 局部变量：

在JavaScript函数内部声明的变量（使用 var）是局部变量，所以只能在函数内部访问它（该变量的作用域是函数内部）。只要函数运行完毕，本地变量就会被删除。

------

##### 全局变量：

在函数外声明的变量是全局变量，网页上的所有脚本和函数都能访问它。

------

##### 变量生存周期：

JavaScript变量的生命期从它们被声明的时间开始。

局部变量会在函数运行以后被删除。

全局变量会在页面关闭后被删除。

------

##### 作用域

首先在函数内部查找变量，找不到则到外层函数查找，逐步找到最外层。

几个例子：

1.
var city = "BeiJing";
function f() {
  var city = "ShangHai";
  function inner(){
    var city = "ShenZhen";
    console.log(city);
  }
  inner();
}

f();  //输出结果是？ShenZhen
2.
var city = "BeiJing";
function Bar() {
  console.log(city);
}
function f() {
  var city = "ShangHai";
  return Bar;
}
var ret = f();
ret();  // 打印结果是？ ShangHai

3.闭包
var city = "BeiJing";
function f(){
    var city = "ShangHai";
    function inner(){
        console.log(city);
    }
    return inner;
}
var ret = f();
ret();
词法分析（尝试理解）

JavaScript中在调用函数的那一瞬间，会先进行词法分析。

------

##### 词法分析的过程：

当函数调用的前一瞬间，会先形成一个激活对象：Avtive Object（AO），并会分析以下3个方面：

1:函数参数，如果有，则将此参数赋值给AO，且值为undefined。如果没有，则不做任何操作。
2:函数局部变量，如果AO上有同名的值，则不做任何操作。如果没有，则将此变量赋值给AO，并且值为undefined。
3:函数声明，如果AO上有，则会将AO上的对象覆盖。如果没有，则不做任何操作。

函数内部无论是使用参数还是使用局部变量都到AO上找。

看两个例子：

```
var age = 18;
function foo(){
  console.log(age);
  var age = 22;
  console.log(age);
}
foo();  // 问：执行foo()之后的结果是？

第二题：
var age = 18;
function foo(){
  console.log(age);
  var age = 22;
  console.log(age);
  function age(){
    console.log("呵呵");
  }
  console.log(age);
}
foo();  // 执行后的结果是？
```

***

##### 内置对象和方法

JavaScript中的所有事物都是对象：字符串、数字、数组、日期，等等。在JavaScript中，对象是拥有属性和方法的数据。

我们在学习基本数据类型的时候已经带大家了解了，JavaScript中的Number对象、String对象、Array对象等。

注意var s1 = "abc"和var s2 = new String("abc")的区别：typeof s1 --> string而 typeof s2 --> Object

------

##### 自定义对象

JavaScript的对象（Object）本质上是键值对的集合（Hash结构），但是只能用字符串作为键。

var a = {"name": "Alex", "age": 18};
console.log(a.name);
console.log(a["age"]);
遍历对象中的内容：

var a = {"name": "Alex", "age": 18};
for (var i in a){
  console.log(i, a[i]);
}
事情并没有那么简单...

##### 创建对象：

var person=new Object();  // 创建一个person对象
person.name="Alex";  // person对象的name属性
person.age=18;  // person对象的age属性
注意：

ES6中提供了Map数据结构。它类似于对象，也是键值对的集合，但是“键”的范围不限于字符串，各种类型的值（包括对象）都可以当做键。

也就是说，Object结构提供了“字符串--值”的对应，Map结构提供了“值--值”的对应，是一种更完善的Hash结构实现。

***

#####  Map

```
var m = new Map();
var o = {p: "Hello World"}

m.set(o, "content"}
m.get(o)  // "content"

m.has(o)  // true
m.delete(o)  // true
m.has(o) // false

```

***

#####  JavaScript面向对象之继承

```
// 父类构造函数
var Car = function (loc) {
  this.loc = loc;
};

// 父类方法
Car.prototype.move = function () {
  this.loc ++;
};

// 子类构造函数
var Van = function (loc) {
  Car.call(this, loc);
};

// 继承父类的方法
Van.prototype = Object.create(Car.prototype);
// 修复 constructor
Van.prototype.constructor = Van;
// 扩展方法
Van.prototype.grab = function () {
  /* ... */
};
```

------

#### Date对象

##### 创建Date对象

//方法1：不指定参数
var d1 = new Date();
console.log(d1.toLocaleString());
//方法2：参数为日期字符串
var d2 = new Date("2004/3/20 11:12");
console.log(d2.toLocaleString());
var d3 = new Date("04/03/20 11:12");
console.log(d3.toLocaleString());
//方法3：参数为毫秒数
var d3 = new Date(5000);
console.log(d3.toLocaleString());
console.log(d3.toUTCString());

//方法4：参数为年月日小时分钟秒毫秒
var d4 = new Date(2004,2,20,11,12,0,300);
console.log(d4.toLocaleString());  //毫秒并不直接显示

------

##### Date对象的方法：

var d = new Date(); 
//getDate()                 获取日
//getDay ()                 获取星期
//getMonth ()               获取月（0-11）
//getFullYear ()            获取完整年份
//getHours ()               获取小时
//getMinutes ()             获取分钟
//getSeconds ()             获取秒
//getMilliseconds ()        获取毫秒
//getTime ()                返回累计毫秒数(从1970/1/1午夜)

***

##### JSON对象

var str1 = '{"name": "Alex", "age": 18}';
var obj1 = {"name": "Alex", "age": 18};
// JSON字符串转换成对象
var obj = JSON.parse(str1); 
// 对象转换成JSON字符串
var str = JSON.stringify(obj1);

***

##### Math对象

 Math.

| abs(x)   | 返回数的绝对值。             |
| -------- | ---------------------------- |
| exp(x)   | 返回 e 的指数。              |
| floor(x) | 对数进行下舍入。             |
| log(x)   | 返回数的自然对数（底为e）。  |
| max(x,y) | 返回 x 和 y 中的最高值。     |
| min(x,y) | 返回 x 和 y 中的最低值。     |
| pow(x,y) | 返回 x 的 y 次幂。           |
| random() | 返回 0 ~ 1 之间的随机数。    |
| round(x) | 把数四舍五入为最接近的整数。 |
| sin(x)   | 返回数的正弦。               |
| sqrt(x)  | 返回数的平方根。             |
| tan(x)   | 返回角的正切。               |


​    
