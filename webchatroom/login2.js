// var app = require('express')();
var express = require('express');
var app = express();
var session = require('express-session');
// 引入mysql模块  
var mysql = require('mysql');
var path = require('path');
var http = require('http').createServer(app);
var io = require('socket.io')(http);
//文件上传模块
var formidable = require('formidable');
var datetime = require('silly-datetime');
var fs = require('fs');
//生成验证码
const vCode = require('svg-captcha');
// var svgCaptcha = require('svg-captcha');
var bodyParser = require('body-parser');
app.set('view engine', 'ejs');

const cookieParase = require('cookie-parser');
app.use(cookieParase());



// 优化连接mysql
// 所有的连接 都适用 下面这段  pool  连接池
var pool = mysql.createPool({
    connectionLimit: 10,
    host: 'localhost',
    user: 'root',
    password: '11223344',
    database: 'webchat'
});
// pool.connection();
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: false }));

app.use(session({
    secret: 'this is a string key', //加密的字符串，里面内容可以随便写
    resave: false, //强制保存session,即使它没变化
    saveUninitialized: true //强制将未初始化的session存储，默认为true
}));

//登陆
// 全局用户名数组
var cus = [];
var tcode = '';
var lname = '';
app.get('/capt', function(req, res) {
    // res.writeHead(200,{"Content-type":"image/jpeg;charset=utf-8"});

    var captcha = vCode.create({ fontSize: 50, width: 100 });
    // console.log('a'+captcha.text)
    req.session.checkcode = captcha.text;
    tcode = captcha.text;
    res.type('svg');
    res.send(captcha.data);
});

app.get('/test', function(req, res) {
    var code = req.query.code;
    var truecode = req.session.checkcode;
    // console.log(code);
    // console.log(tcode);
    if (code == tcode) {
        res.send(JSON.stringify({
            'type': true
        }));
    } else {
        res.send(JSON.stringify({
            'type': false
        }));
    }
});
app.get('/', function(req, res) {
    // var truecode=req.session.checkcode;
    // console.log(tcode);
    res.render('login');
});
//注册
app.get('/register.html', function(req, res) {
    res.render('register');
});
//请求聊天路由
app.get('/chat.html', function(req, res) {
    res.render('chat');
});
//登陆请求发起时
app.post('/login', function(req, res) {
    pool.getConnection(function(err, connection) {
        // 返回一个 connection  对象  你可以 使用 query 
        var username = req.body.username;
        var password = req.body.password;
        // var code=req.body.codecheck.toLowerCase();
        var sql = "select password,image from `user` where `username`='" + username + "';";
        connection.query(sql, function(error, result) {
            // console.log(result);
            req.session.userpath = username;

            // console.log(captcha);
            // console.log(req.session.userpath);
            var result = JSON.stringify(result);
            var resu = JSON.parse(result);

            if (resu[0].password == password) {
                // cus.push(username);
                // console.log(cus);
                //console.log('yes!');
                lname = username;
                res.render('demo', {
                    'name': username,
                    'newpath': resu[0].image
                });
                //res.end('yes');
                // res.end(resu[0].image)
            } else {
                res.end('no');
            }
        });
    });
    //connection.end();
});
//注册请求发起时
//用户注册 数据插入数据库
app.post('/regist', function(req, res) {
    var username = req.body.username;
    var password = req.body.password;
    // console.log(username);
    pool.getConnection(function(err, connection) {
        var sql = "INSERT INTO `user`(`username`,`password`) VALUES('" + username + "','" + password + "')";
        connection.query(sql, function(err, result) {
            // console.log(result);
            if (err) {
                console.log(err);
                console.log('用户存在');
            } else {
                res.render('login');
            }
        });
    });
    //connection.end();
});
//用户发起上传或者更换头像请求
app.post('/dopost', function(req, res) {
    var form = new formidable.IncomingForm();
    // 设置上传,目录
    form.uploadDir = "./public/Images";
    form.parse(req, function(err, fields, files) {
        // 处理 接受到的参数  再处理图片的上传 // 处理上传文件的名字 // 改名字的步骤  // 起名字 时间 +  随机数  +  后缀
        var t = datetime.format(new Date(), 'YYYYMMDDHHmmss');
        var ran = parseInt(Math.random() * 100000);
        var extname = path.extname(files.sexy_photo.name);
        // console.log(extname);
        var oldpath = __dirname + '/' + files.sexy_photo.path;
        // // 新名字
        var newpath = './public/Images/' + t + ran + extname;
        var newname = '/Images/' + t + ran + extname;
        // console.log(oldpath);
        // // 改名字
        // // rename
        fs.rename(oldpath, newpath, function(err) {
            //连接数据库插入头像图片地址
            pool.getConnection(function(err, connection) {
                var username = req.session.userpath;
                // console.log(req.session.userpath);
                var sql = "UPDATE  `user`  SET `image` = '" + newname + "'  WHERE `username` ='" + username + "'";
                connection.query(sql, function(err, result) {
                    // console.log(newpath);
                    if (err) {
                        console.log(err);
                        console.log('头像上传失败');
                    } else {
                        res.render('demo', {
                            'name': username,
                            'newpath': newname
                        });
                    }
                });
            });
        });
    });

});


var xname = '';
var sli = [];
var usname = '';
//聊天室io传输
io.on('connection', function(socket) {
    console.log(socket.id);
    socket.on('chat message', function(msg) {
        xname = '';
        io.emit('chat message', msg)
    });
    io.emit('some event', { for: 'everyone' });
    io.on('connection', function(socket) {
        socket.broadcast.emit('hi');
    });
    socket.on('client', function(data) {
        socket.id = socket.id + data;
        cus.push(socket.id);
        sli.push(data);
        // console.log(socket.id);
        io.emit('list', sli);
        // console.log(cus);
    });
    socket.on('chater', function(chats) {
        // console.log()
        // console.log(cus);
        // sli.push(socket.id);
        xname = chats.uname;
        usname = chats.lname;
    });
    socket.on('chat', function(msg) {
        var name = msg.name;
        var msg = msg.msg;
        var uname = siliao(name);
        console.log(cus, uname);
        io.to(uname).emit('sli', msg);
        // console.log(uname);
    });
    socket.on('setid', function(data) {
        socket.id = socket.id + chats.lname;
        setId(socket.id, chats.uname);
        // console.log(data);
        // console.log(cus);
    });
    socket.on('qlist', function(data) {
        xname = '';
    })
    if (xname != '') {
        socket.id = socket.id + usname;
        setId(socket.id, usname);
        var tname = siliao(xname);
        // console.log(xname, tname);
        // console.log(cus);
        io.to(tname).emit('chater', {
            uname: xname,
            lname: usname
        });
    }
});



function setId(id, name) {
    for (let i = 0; i < cus.length; i++) {
        if (cus[i].substr(20) == name) {
            cus[i] = id;
        }
    }
}

function siliao(name) {
    for (let i = 0; i < sli.length; i++) {
        if (cus[i].substr(20) == name) {
            return cus[i].substr(0, 20);
        }
    }
}


http.listen('3001', '192.168.28.13');