# 1.8 参数-传值调用

C语言中的函数参数是值传递，如果想传递引用需要定义指针类型。

```C
//函数传参是 值传递 如果是引用传递需要传递指针类型
int power(int base, int n){
    int result;
    for(result = 1;n > 0;--n){
        result = result * base;
    }
    return result;
}
```

