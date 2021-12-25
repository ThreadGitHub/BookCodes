#include <stdio.h>

int main(){
    //二维数组
    int array[2][2] = {
        {1,2},
        {3,4}
    };

    for(int i = 0; i < 2;i++){
        for(int j = 0;j < 2;j++){
            printf("%d\t", array[i][j]);
        }
    }

    printf("\n");

    //三维数组
    int array2[2][2][2] = {
        {
            {1,2},
            {3,4}
        },
        {
            {5,6},
            {7,8}
        }
    };
    for(int i = 0;i < 2;i++){
        for(int j = 0;j < 2;j++){
            for(int k = 0;k < 2;k++){
                printf("%d\t", array2[i][j][k]);
            }
        }
    }

    return 0;
}