# 第三章 流控制

这一章的话，因为流程控制每种语言差不多，所以这里只写对于自己有成长或者和java语言区别的地方。

首先` if(布尔值)` ，在C语言中其实没有布尔类型，有一个 `<stdbool.h>`的头文件实现了布尔值，但其实是通过define方式实现了，并不是语言本身支持

```c
#define bool char
#define ture 1
#define false 0
```

C语言中 0 表示 false ， 大于 0 的话表示true，这里是和java语言的区别，java语言默认有boolean类型。

参考链接：https://www.runoob.com/note/34742

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

> 3.5 while 循环与 for循环 : 比较有意思的for循环 末尾表达式中的逗号 (测了一下java居然也支持，孤陋寡闻了..)

```c
int c = 0;
for(int i=0;i < 10;i++, c++){
    System.out.println(c);
}
```

> 3.6 goto 语句与标号 (语句跳转，反复横跳...)

goto 语言只要你想可以向上或者向下 反复横跳....

总结：尽量少用...

