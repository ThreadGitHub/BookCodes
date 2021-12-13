#include <stdio.h>

int strlen(char *s);

/**
 * 数组变量 表示数组第一个元素的内存地址，下一个元素等于数组元素地址 +1
 */
int main(){
    int array[] = {1, 20, 30, 4, 50};
    int *p = array;

    for(int i = 0; i < 5; *p++, i++){
        printf("%d\t", *p);
    }

    int lengh = strlen("Hello World!");
    printf("\nlengh = %d\n", lengh);

    char charArray[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
    lengh = strlen(charArray);
    printf("lengh = %d\n", lengh);

    printf("array[1]:%d", *(array+1));
    return 0;
}

int strlen(char *s){
    int n;
    for (n = 0;*s != '\0';s++){
        n++;
    }
    return n;
}