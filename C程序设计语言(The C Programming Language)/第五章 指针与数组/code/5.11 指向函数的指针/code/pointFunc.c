#include <stdio.h>

int sum(int num, int num2);

int main(){
    //定义函数指针
    int (*pointSum)(int, int);

    //赋值函数指针
    pointSum = sum;

    //调用函数指针
    int sumNum = (*pointSum)(1, 2); 
    printf("sumNum: %d\n", sumNum);

    //调用函数指针
    sumNum = pointSum(1, 2); 
    printf("sumNum: %d", sumNum);

    return 0;
}

int sum(int num, int num2){
    return num + num2;
}