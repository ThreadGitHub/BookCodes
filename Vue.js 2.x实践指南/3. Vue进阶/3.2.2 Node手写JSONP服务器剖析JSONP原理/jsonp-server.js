const http = require('http');
const url = require('url');

const server = http.createServer();
server.on('request', function (req,res) {
    const query = url.parse(req.url, true);

    console.log("pathname:" + query.pathname);
    console.log("callback:" + query.query.callback);

    if(query.pathname  === '/actionScript'){
        var data = {
            name : '无痕公子',
            title : '春梦了无痕',
            age : 40
        };
        // 这里拼接成  callback(data) 执行js函数
        var scriptStr = `${query.query.callback}(${JSON.stringify(data)})`;
        res.end(scriptStr);
    }else{
        res.end('404');
    }
});

server.listen(8000, function(){
    console.log('server listen at http://127.0.0.1:8000');
});