#include <stdio.h>

//函数原型
int power(int base, int n);

int main(){
    int result = power(7, 2);
    printf("%d", result);
    return 0;
}

//函数传参是 值传递 如果是引用传递需要传递指针类型
int power(int base, int n){
    int result;
    for(result = 1;n > 0;--n){
        result = result * base;
    }
    return result;
}