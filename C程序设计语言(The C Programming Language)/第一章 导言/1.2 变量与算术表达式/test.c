#include <stdio.h>

int main(){
    //��ϰ1-3 �޸��¶�ת������ ��֮ǰ��ת��������ӡһ������
    printf("%40s", "���������϶������϶��¶ȶ��ձ�\n");

    float fahr, celsius;
    float lower, upper, step;

    lower = 0;
    upper = 300;
    step = 20;

    fahr = lower;
    while(fahr <= upper){
        celsius = (5.0 / 9.0) * (fahr - 32.0);
        //%3.0f ǰ���3��ʾ��ӡ�����ַ���λ��Ȼ�����ݿ��Ҷ��� û��С������
        //%6.1f ǰ���6��ʾ��ӡ6���ַ���λ��Ȼ�� .1 ��ʾ ����һλС�� f��ʾ��ӡ���Ǹ�����������
        printf("%3.0f %6.1f\n", fahr, celsius);
        fahr = fahr + step;
    }

    return 0;
}