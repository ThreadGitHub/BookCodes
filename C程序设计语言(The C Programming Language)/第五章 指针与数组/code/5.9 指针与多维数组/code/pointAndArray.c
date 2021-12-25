#include <stdio.h>

int main(){
    //数组
    int a[][2] = {
        {1, 2},
        {3, 4}
    };

    //指针数组 指针数组里有两个 int类型的数组
    int *b[2];

    int array[2] = {1, 2};
    int array2[2] = {3, 4};
    b[0] = array;
    b[1] = array2;

    //数组
    for(int i = 0;i < 2;i++){
        for(int j = 0;j < 2;j++){
            printf("%d\t", a[i][j]);
        }
    }

    printf("\n");

    for(int i = 0;i < 2;i++){
        for(int j = 0;j < 2;j++){
            printf("%d\t", b[i][j]);
        }
    }

    printf("\n");

    printf("%d\t%d", a[0][0], b[0][0]);
    return 0;
}