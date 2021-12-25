#include <stdio.h>

/**
 * 实现echo程序
 */
int main(int argc, char *argv[]){
    for(int i = 0; i < argc;i++){
        printf("%s\t", argv[i]);
    }
    return 0;
}