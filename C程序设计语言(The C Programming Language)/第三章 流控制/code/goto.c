#include <stdio.h>

int main(){
// tag: //go可以往前横跳

    for(int i = 0;i < 10;i++){
        for(int j = 0;j < 20;j++){
            printf("for %d\n", j);
            if(j == 2){
                goto tag;
            }
            
        }
    }
tag: //向下横跳
    printf("gogo ....");
    return 0;
}