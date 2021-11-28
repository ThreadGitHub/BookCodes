# 1.6 数组

> int[10] array;	//表示声明了一个长度为10的数组 但是没有给每个元素初始化
>
> C语言中没有  new int[10];  这种操作 要循环遍历赋值

```c
int ndigit[10];

for(int i = 0;i < 10; i++){
    ndigit[i] = 0;
}
```

