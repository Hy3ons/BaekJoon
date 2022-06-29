#include <bits/stdc++.h>

using namespace std;

int main() {
	int testcase;
	cin >> testcase;
	
	long long zero[42] , one[42];
	
	for (int i=0;i<42;i++) {
		zero[i] = one[i] = 0;
	}
	
	zero[0] = one[1] = 1;
	for (int i=2;i<42;i++) {
		zero[i] = zero[i-1] + zero[i-2];
		one[i] = one[i-1] + one[i-2];
	}
	
	while (testcase-->0) {
		int value;
		cin >> value;
		
		cout << zero[value] << " " << one[value] << "\n";
	}
	return 0;
}