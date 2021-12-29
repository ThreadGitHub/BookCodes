#include <stdio.h>

union t_tag{
    int num;
    char *str;
    short age;
};

int main(){
    union t_tag u;
    u.num = 100;
    u.str = "Hello World !";
    u.age = 77;
    printf("u.num: %d\t", u.num);
    printf("u.str: %s\t", u.str);
    printf("u.age: %d\t", u.age);
    return 0;
}