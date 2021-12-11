#include <stdio.h>

void qsort(int v[], int left, int right);
void swap(int v[], int i, int j);
void printArray(int array[], int size);

int main(){
    int array[] = {10,32,54,2,4,64,634,6,32};
    int size = sizeof(array) / sizeof(array[0]);

    qsort(array, 0, size-1);
    for(int i = 0;i < size;i++){
        printf("%d\t", array[i]);
    }

    return 0;
}

void qsort(int v[], int left, int right){
    int i, last;
    
    if(left >= right){
        return;
    }

    //最开始的位置 和 中间位置互换
    // swap(v, left, (left + right)/2);

    last = left;
    //遍历当前元素之后的元素
    for(i = left + 1;i <= right;i++){
        //如果后面的元素比当前元素小就互换位置 并且依次找到就挨个换 换的是上一次换的那个的后一个元素
        if(v[i] < v[left])
            swap(v, ++last, i);
    }

    //把当前元素放到比自己小的全部元素之后 比自己大的全部元素之前的中间位置上
    swap(v, left, last);

    printArray(v, 9);

    //当前元素到最后找到比自己小的元素的位置之前
    qsort(v, left, last-1);
    //最后找到比自己小的元素的位置之后 到 数组末尾
    qsort(v, last+1, right);
}

void swap(int v[], int i, int j){
    int temp = v[i];
    v[i] = v[j];
    v[j] = temp;
}

void printArray(int array[], int size){
    printf("----  ");
    for(int i = 0;i < size;i++){
        printf("%d\t", array[i]);
    }
    printf("  ----\n");
}

/*
变化逻辑:
left = 0  right = 8
10,32,54,2,4,64,634,6,32

4,32,54,2,10,64,634,6,32

4,2,54,32,10,64,634,6,32

2,4,54,32,10,64,634,6,32

/
left = 2  right = 8
2,4,54,32,10,64,634,6,32

2,4,64,32,10,54,634,6,32

2,4,64,32,10,54,6,32,634

2,4,32,32,10,54,6,64,634
 */