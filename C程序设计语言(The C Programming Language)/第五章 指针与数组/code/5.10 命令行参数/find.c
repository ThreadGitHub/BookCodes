#include <stdio.h>
#include <string.h>

/**
 * 和书中的find不同自己设计的   
 * find -s 字符串 -r 匹配值  返回 -s字符串中匹配到的索引开始位置
 * ./find -s jasdlfjdsaklg -r a     返回：index: 2 index: 10
 */
int main(int argc, char *argv[]){
    char *str;
    char *regex;
    while(--argc > 0){
        char *arg = *++argv;
        // printf("[%d]%s\t",argc, arg);
        //通过参数 取到参数的值
        if(strcmp(arg, "-s") == 0){
            str = *(argv+1);
            // printf("%s", str);
        }
        
        //通过参数取到参数的值
        if(strcmp(arg, "-r") == 0){
            regex = *(argv+1);
            // printf("%s", regex);
        }
    }

    int i = 0;
    while(str[i]){
        char c = str[i++];
        if(c == *regex){
            printf("index: %d\n", i);
        }
    }

    return 0;
}
