# 第二章 类型、运算符与表达式

## 2.1 变量名

变量名的组成：数字+字母+下划线  大小写敏感 不能以数字开头

变量名使用小写字母，常量全部使用大写字母

## 2.2 数据类型及长度

**基本数据类型**

`char: 字符型 占一个字节,可以存放本地字符集的一个字符`

 `int: 整型,通常反应了所用机器中整数的最自然长度---int可以为16 或 32位`

 `float: 单精度浮点数` 

`double: 双精度浮点数`

**限定符**

`short 和 long 两个限定符 short一般是16位 long 一般是32位 `

`short int num; 可以简写成：short num;`

`long int num; 可以简写成：long num;`

`long double num; 高精度浮点数`

`short` 的长度不能长于 `int` ，`int` 不能长于 `long`

`unsigned 修饰的为无符号整数`

`unsigned int: 无符号整数 没有负数从零开始` 

## 2.3 常量

函数外可以使用 `#define name 值`

函数内可以使用 `const int num = 10;` 

枚举类型：

```C
enum STATUS{
    COMMIT = 0, REJECT = 1
} name;

int main(){
    name = 1;
    if(name == REJECT){
        printf("true\n");
    }
    return 0;
}
```

## 其他

!= 的运算符优先级高于 赋值运算符，这种需要加上括号

```C
int a;
if(a = 9 != 10){ //正确:if((a = 9) != 10)
    
}
```

