const fs = require('fs');
const path = require('path');

/**
 * 根据文件路径获取文件内容
 */
function getFileByPath(path, callback, errorCallback){
    fs.readFile(path, 'utf-8', (error,data) =>{
        if(error){
            return errorCallback(error);
        }
        callback(data);
    });
}

getFileByPath(path.join(__dirname, './files/text.txt'), function(data){
    console.log("文件读取内容: " + data);
}, function (error){
    console.log("文件读取失败:" + error.message);
});
