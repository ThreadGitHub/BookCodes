const fs = require('fs');
const path = require('path');

/**
 * Promise提供一种异步执行的实现
 * new Promise(function(resolve, reject)    //resolve成功回调  reject失败的回调
 */
function getFileByPath(path,){
    return new Promise(function(resolve, reject){
        fs.readFile(path, 'utf-8', (error, data) =>{
            if(error) return reject(error);
            resolve(data);
        });
    });
}

getFileByPath('./files/text.txt')
.then(function (data){
    console.log(data);
    return getFileByPath('./files/text2.txt');
})
.then(function (data) {
    console.log(data);
    return getFileByPath("./files/text3.txt");
})
.then(function (data) {
    console.log(data);
})
.catch(function (err) {
    console.log(err);
});
