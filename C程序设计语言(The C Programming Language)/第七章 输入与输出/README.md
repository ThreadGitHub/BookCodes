# 第七章 输入输出

## 7.1 标准输入输出

```c
int getchar (void) 		//获取一个字符
int tolower (int)  		//大写转小写
int putchar(int __c)	//输出一个字符
```

## 7.2 格式化输出 printf函数

![image-20211229215633486](README.assets/image-20211229215633486.png)

## 7.3 变长参数表

> 通过 #include <stdarg.h> 标准库实现可变参数表

```c
#include <stdio.h>
#include <stdarg.h>

void minprintf(char *str, ...);

int main(){
    minprintf("HelloWorld ! %d %s", 123, "str");
    return 0;
}

void minprintf(char *str, ...){
    va_list ap;
    int ival;
    float fval;
    char *sval;
    va_start(ap, str);
    for(; *str; str++){
        if(*str != '%'){
            putchar(*str);
            continue;
        }
        switch(*++str){
            case 'd':
                ival = va_arg(ap, int);
                printf("%d", ival);
                break;
            case 'f':
                fval = va_arg(ap, double);
                printf("%f", fval);
                break;
            case 's':
                sval = va_arg(ap, char*);
                while(*sval){
                    putchar(*(sval++));
                }
                break;
            default:
                putchar(*str);
        }
    }

}
```

## 7.4 格式化输入

```c
int main(){
    int num;
    printf("input num: ");
    scanf("%d", &num);
    printf("put num : %d", num);

    return 0;
}
```

![image-20211229222037878](README.assets/image-20211229222037878.png)