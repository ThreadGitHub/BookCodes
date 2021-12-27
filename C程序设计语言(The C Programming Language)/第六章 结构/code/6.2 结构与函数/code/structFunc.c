#include <stdio.h>

//定义结构体的参数的函数
struct point sum(struct point p1, struct point p2);

struct point
{
    int x;
    int y;
};

int main(){
    struct point p1 = {1,2};
    struct point p2 = {3,4};
    struct point sumPoint = sum(p1, p2);
    printf("sumPoint x: %d \t sumPoint y: %d\n", sumPoint.x, sumPoint.y);

    //定义结构体指针
    struct point *p = &sumPoint;
    //结构体指针取值
    int x = (*p).x; //这里必须要加括号因为 .的优先级高于 ()
    printf("(*p).x: %d\n",  x);

    //C语言中提供一种简写
    x = p -> x;
    int y = p -> y;
    printf("p -> x: %d \t p -> y: %d", x, y);
    return 0;
}

//定义结构体的参数的函数
struct point sum(struct point p1, struct point p2){
    struct point sum = {
        p1.x + p2.x,
        p1.y + p2.y
    };
    return sum;
}