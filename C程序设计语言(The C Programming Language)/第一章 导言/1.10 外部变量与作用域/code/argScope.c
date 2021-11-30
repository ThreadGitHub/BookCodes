#include <stdio.h>

void test(void);

//外部变量
int argScope = 10;

int main(){
    printf("argScope: %d\n", argScope);
    
    //声明外部遍历 如果外部变量的定义在函数之前可以忽略
    extern int argScope2;
    printf("argScope2: %d", argScope2);

    argScope2 = 10;
    
    test();
    return 0;
}

//外部变量
int argScope2 = 99;

void test(void){
    printf("%d", argScope2);
}