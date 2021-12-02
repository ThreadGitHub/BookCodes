# 第三章 流控制

> 3.3 elseif 中的 对半查找：(要求查找的数组从小到大排列)

```C
int main(){
    int array[10] = {10, 20, 30, 40, 50, 55, 59, 60, 90, 99};
    int result = binsearch(30, array, 10);
    printf("search index: %d", result);
    return 0;
}

int binsearch(int searchNum, int array[], int size){
    int index = 0;
    size = size -1;
    while(index <= size){
        int tempIndex = (index + size) /2;
        printf("%d %d %d\n",index, size, tempIndex);
        //大于说明 要搜索的值在后面
        if(searchNum > array[tempIndex]){
            index = tempIndex + 1;
        }else if(searchNum < array[tempIndex]){
        //小于说明 要搜索的值在前面
            size = tempIndex - 1;
        }else{
            return tempIndex;
        }
    }
    return -1;
}
```

