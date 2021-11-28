#include <stdio.h>

/**
 * 1.5.2 字符计数
 */
int main(){
    int c;
    long size = 0;

    //这里用的是不等于 \n 因为输入最后一个字符会是回车 然后回车的话下一次也会被 getchar捕获到所以结束不了
    // while((getchar()) != '\n')
    //     ++size;

    //for循环的第二种写法
    for(; (c=getchar()) != '\n'; ++size)
        ;

    printf("str size: %ld", size);
    return 0;
}