#include <stdio.h>

/**
 * string.h 中的strcpy 实现：复制字符串到数组的实现 [数组实现]
 */
void strcpy_func(char *s, char *t);

/**
 * string.h 中的strcpy 实现：复制字符串到数组的实现 [指针实现]
 */
void strcpy_fun2(char *s, char *t);

void strcpy_fun3(char *s, char *t);

int main(){
    int size = 6;
    char s[6];

    strcpy_func(s, "123456");
    printf("strcpy_func: %s\n", s);

    strcpy_fun2(s, "999999");
    printf("strcpy_fun2: %s\n", s);

    strcpy_fun3(s, "777777");
    printf("strcpy_fun3: %s\n", s);

    printf("%d", '\0' == 0);

    return 0;
}

void strcpy_func(char *s, char *t){
    int i = 0;
    while((s[i] = t[i]) != '\0'){
        i++;
    }
}

void strcpy_fun2(char *s, char *t){
    while((*s = *t) != '\0'){
        s++;
        t++;
    }
}

void strcpy_fun3(char *s, char *t){
    while(*s++ = *t++)
        ;
}