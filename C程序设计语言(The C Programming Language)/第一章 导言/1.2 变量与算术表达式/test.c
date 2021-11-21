#include <stdio.h>

int main(){
    //练习1-3 修改温度转换程序 是之前的转换表顶部打印一个标题
    printf("%40s", "《华氏摄氏度与摄氏度温度对照表》\n");

    float fahr, celsius;
    float lower, upper, step;

    lower = 0;
    upper = 300;
    step = 20;

    fahr = lower;
    while(fahr <= upper){
        celsius = (5.0 / 9.0) * (fahr - 32.0);
        //%3.0f 前面的3表示打印三个字符的位置然后内容靠右对齐 没有小数部分
        //%6.1f 前面的6表示打印6个字符的位置然后 .1 表示 保留一位小数 f表示打印的是浮点数据类型
        printf("%3.0f %6.1f\n", fahr, celsius);
        fahr = fahr + step;
    }

    return 0;
}