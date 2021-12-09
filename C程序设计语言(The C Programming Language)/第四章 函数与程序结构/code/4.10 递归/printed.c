#include <stdio.h>

void printed(int num);

int main(){
    //把整数按照字符打印
    printed(123);
    return 0;
}

/**
 * 把整数按照字符打印
 * @param num 
 */
void printed(int num){
    if(num < 0){
        putchar('-');
        num = -num;
    }

    if(num / 10){
        printed(num / 10);
    }
    putchar(num % 10 + '0');
}