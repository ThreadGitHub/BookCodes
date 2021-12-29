#include <stdio.h>

/*
 * typedef 允许给类型定义别名 可以建立新的数据类型名
 */
int main(){
    typedef int Length;
    Length num = 10;
    printf("%d\n", num);

    //定义 String 类型
    typedef char* String;
    String str = "Hello World !";
    printf("%s", str);

    return 0;
}