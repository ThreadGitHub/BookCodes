#include <stdio.h>

//定义结构体
struct point{
    int x;
    int y;
};

//定义结构体的同时创建结构体变量 p2
struct point2{
    int x;
    int y;
} p2;

int main(){
    //定义结构体变量
    struct point p = {1,2};
    //输出结构体的 数据
    printf("x: %d\t y: %d\n", p.x, p.y);

    p2.x = 10;
    p2.y = 20;
    //输出结构体的 数据
    printf("x: %d\t y: %d\n", p2.x, p2.y);
    return 0;
}