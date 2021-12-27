#include <stdio.h>

struct student{
    char *name;
    int age;
} stu={"xiaoming", 18}, *p = &stu;

int main(){
    printf("%s \t %d\n", p->name, p->age);

    //定义结构指针
    struct student stu2 = {
        "xiaozhang", 20
    };
    struct student *p2 = &stu2;
    printf("%s \t %d\n", p2->name, p2->age);
    return 0;
}