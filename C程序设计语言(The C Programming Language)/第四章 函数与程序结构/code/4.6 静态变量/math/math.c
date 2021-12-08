#include "math.h"

//非静态变量
int not_statc_a = 99;

//静态变量 只在当前源文件中有效
//声明static后这个变量即使是全局变量也无法被其他的源文件访问到
//如果没设置static 可以在其他源文件中通过 extern 引用该变量并且不能重复
static int statc_a = 10;

int sum(int num, int num2){
    return num + num2;
}