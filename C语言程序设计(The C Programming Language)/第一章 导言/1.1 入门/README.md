# 1.1 入门 HelloWorld

> C语言基本结构： 包含一个主程序入库main方法 和 标准输入输出库 stdio.h

``` C
#include <stdio.h>

int main(){
    printf("Hello World!\n");
    return 0;
}
```

`gcc HelloWorld.c`  默认编译生成 一个 `a.exe` 如果是 linux下的话是 `a.out`

`gcc -o HelloWorld.exe HelloWorld.c `  -o可以指定生成的程序的名称

