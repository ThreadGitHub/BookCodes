#include <stdio.h>

/**
 * 实现echo程序
 */
int main(int argc, char *argv[]){
    while(--argc > 0){
        printf("%s\t", *++argv);
    }
    return 0;
}