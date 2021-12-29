#include <stdio.h>

int main(int argc, char *argv[]){
    char *fileName = argv[1];
    FILE *file = fopen(fileName, "r");

    if(file == NULL){
        printf("文件不存在!");
        return 0;
    }

    char c;
    while((c = fgetc(file)) != EOF){
        printf("%c", c);
    }
    return 0;
}