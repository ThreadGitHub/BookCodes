const fs = require('fs');

function getFileByPath(path) {
    return new Promise(function(resolve,reject){
        fs.readFile(path,'utf-8',function(error, data){
            if(error) reject(error);
            resolve(data);
        });
    });
}

async function getAllFile(){
    await getFileByPath('./files/text.txt').then(function (data) {
        console.log(data);
    });

    await getFileByPath("./files/text2.txt").then(function (data){
        console.log(data);
    });

    await getFileByPath("./files/text3.txt").then(function (data) {
        console.log(data);
    });
}

getAllFile();
