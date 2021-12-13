#include <stdio.h>

int swap(int *num, int *num2);

int main(){
    int num = 1, num2 = 2;
    swap(&num, &num2);
    printf("num: %d \t num2: %d", num, num2);
    return 0;
}

//指针类型参数
int swap(int *num, int *num2){
    int temp = *num;
    *num = *num2;
    *num2 = temp;
}

