#include <stdio.h>


/**
 * 1.5.1 控制台输入的数据输出出来
 * getchar 获取  putchar输出 
 */

int main(){
    int c;

    //第一种写法
    // c = getchar();
    // while(c != EOF){
    //     putchar(c);
    //     c = getchar();
    // }
        
    //第二种写法
    while((c = getchar()) != EOF)
        putchar(c);

    return 0;
}