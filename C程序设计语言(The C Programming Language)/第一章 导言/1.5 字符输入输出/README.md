# 1.5 字符输入/输出

> getchar()  获取输入的单个字符
>
> putchar()  将单个字符输出到控制台
>
> EOF stdio.h 中定义的常量 表示 Control + Z 结束字符 -1
>
> '\n' 如果输入一行最后的换行想结束可以 != '\n' 的条件

```c
int c;
while((c = getchar()) != EOF)
    putchar(c);
```

