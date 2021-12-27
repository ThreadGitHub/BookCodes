#include <stdio.h>

struct tnode{
    char *title;
    struct tnode *left;
    struct tnode *right;
} root;

void printTree(struct tnode *tree);

int main(){
    root.title = "roo_node";
    struct tnode node2 = {
        "node2"
    };
    struct tnode node4 = {
        "node4"
    };
    struct tnode node5 = {
        "node5"
    };
    struct tnode node3 = {
        "node3",
        &node4,
        &node5
    };
     struct tnode node1 = {
        "node1",
        &node2,
        &node3
    };
    struct tnode nodeLeft = {
        "node",
        &node1
    };
    root.left = &nodeLeft;

    struct tnode nodeRight = {
        "node5",
        &node4,
        &node5
    };
    root.right = &nodeRight;

    struct tnode *p = &root;
    printTree(p);
    return 0;
}

void printTree(struct tnode *tree){
    if(tree != NULL){
        printf("%s\n", tree->title);
        printTree(tree->left);
        printTree(tree->right);
    }
}