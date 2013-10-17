
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void sieve(int n) {
	char sieve[n + 1];
	for(int i = 2; i <= n; i++)
		sieve[i] = 1;
	for(int i = 2; i <= sqrt(n) + 1; i++) {
		if(sieve[i])
			for(int j = i; j * i <= n; j++) {
				sieve[j * i] = 0;
		}
	}
	for (int i = 2; i <= n; ++i) {
		if(sieve[i]) {
			printf(" %d\n", i);
		}
	}
}