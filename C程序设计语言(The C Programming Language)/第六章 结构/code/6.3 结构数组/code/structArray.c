#include <stdio.h>

//定义结构数组
struct point
{
    int x;
    int y;
} pointArray[] = {
    {1,2},
    {2,3},
    {3,4}
};

int main(){
    int size = sizeof pointArray / sizeof pointArray[0];
    printf("size: %d \n", size);

    for(int i = 0;i < size;i++){
        struct point obj = pointArray[i];
        printf("x: %d \t y: %d \n", obj.x, obj.y);
    }

    printf("\n");

    //第二种初始化
    struct point pointArray2[] = {
        {9, 8},
        {7, 6}
    };
    size = sizeof (pointArray2) / sizeof (pointArray2[0]);
    for(int i = 0;i < size;i++){
        struct point obj = pointArray2[i];
        printf("x: %d \t y: %d \n", obj.x, obj.y);
    }
    return 0;
}