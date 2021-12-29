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