#include <stdio.h>

/**
 * 函数原型 求一个数的几次幂
 */
int power(int num, int n);

int main(){
    printf("%d", power(5, 3));
    return 0;
}

int power(int num, int n){
    int resultNum = 1;
    for(int i = 0;i < n;i++){
        resultNum = resultNum * num;
    }
    return resultNum;
}