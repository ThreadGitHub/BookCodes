#include <stdio.h>

int main(){
    //*p 表示指针类型
    int x = 0, y = 2, *p;

    //p 指针指向 x的地址
    p = &x;

    //y 的值现在是 x的值
    y = *p;

    //x 的值现在是3
    *p = 3;
    
    //p2 和 p 都指向x 并且把值改成了4
    int *p2 = p;
    *p2 = 4;

    printf("%d \t %d",x ,y);
    return 0;
}