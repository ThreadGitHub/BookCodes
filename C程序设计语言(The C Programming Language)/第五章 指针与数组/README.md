# 第五章 指针与数组

## 5.1 指针与地址

指针存储的是 内存的地址，通过指针的内存地址可以直接获取到指向的对象的值。

![未命名文件](README.assets/未命名文件.png)

```c
int main(){
    //*p 表示指针类型
    int x = 0, y = 2, *p;
    //p 指针指向 x的地址
    p = &x;
    //y 的值现在是 x的值
    y = *p;
    //x 的值现在是3
    *p = 3;
    //p2 和 p 都指向x 并且把值改成了4
    int *p2 = p;
    *p2 = 4;
    return 0;
}
```

## 5.2 指针与函数参数

```c
//指针类型参数
int swap(int *num, int *num2){
    int temp = *num;
    *num = *num2;
    *num2 = temp;
}
```

