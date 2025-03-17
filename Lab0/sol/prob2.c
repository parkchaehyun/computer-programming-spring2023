#include <stdio.h>

int main(void) {

    for (int i = 1; i <= 9; i++) {
        printf("2 x %d = %d\t", i, 2*i);
        printf("3 x %d = %d\n", i, 3*i);
    }


    return 0;
}