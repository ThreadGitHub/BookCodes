#include <stdio.h>

/**
 * 1.5.3 行计数
 * EOF 是 最后Control+C结束 会显示统计的行数
 */
int main(){
    int c = 0;
    int rowSize = 0;

    while((c = getchar()) != EOF){
        if(c == '\n'){
            ++rowSize;
        }
    }

    printf("rowCout: %d", rowSize);
    return 0;
}