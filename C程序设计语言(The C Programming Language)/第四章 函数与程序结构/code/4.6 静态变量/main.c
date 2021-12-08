#include <stdio.h>
#include "math/math.h"

void count();
extern int not_statc_a;
int statc_a = 20;

int main(){
    int result = sum(1, 2);
    printf("sum:%d\n", result);
    printf("static:%d\n", statc_a);

    for(int i = 0;i < 10; i++){
        count();
    }

    printf("not_statc_a: %d\n", not_statc_a);
    printf("statc_a: %d", statc_a);

    return 0;
}

void count(){
    static int num = 0;
    ++num;
    printf("num : %d\n", num);
}