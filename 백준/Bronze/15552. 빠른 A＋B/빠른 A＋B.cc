#include <stdio.h>


int main() {
	
	int a = 0;
	scanf("%d", &a);

	int p, q;

	for (int i = 0; i < a; i++)
	{
		scanf("%d %d", &p, &q);

		int answer = p + q;
		printf("%d\n", answer);
	}
		
	return 0;
}