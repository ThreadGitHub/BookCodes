#include <stdio.h>

char *month_name(int n);

int main(){
    char *p = month_name(1);
    printf("%s", p);

    return 0;
}

char *month_name(int n){
    static char *name[] = {
        "Illegal month",
        "January", "May", "June",
        "July", "August", "September",
        "October", "November", "December"
    };
    return name[n];
}