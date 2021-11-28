# 1.7 函数

main方法前面的叫函数原型，在main方法之后的函数需要声明函数原型才可以找到函数

`int power(int num, int n);`



C语言函数体：

```c
int power(int num, int n){
    int resultNum = 1;
    for(int i = 0;i < n;i++){
        resultNum = resultNum * num;
    }
    return resultNum;
}
```

C语言的函数参数称为 形式参数
