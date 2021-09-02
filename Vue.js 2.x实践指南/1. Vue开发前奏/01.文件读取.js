const fs = require('fs');
const path = require('path');

fs.readFile(path.join(__dirname, './files/text.txt'), 'utf-8', (err, data) => {
    if(err) throw err
    console.log('读取的数据: ' + data);
});
