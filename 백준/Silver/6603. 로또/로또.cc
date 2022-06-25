#include <bits/stdc++.h>

using namespace std;

int limitdepth;
int chose[6];

void recur (int arr[], int depth, int start, int x) {
	if (depth==6) {
		for (int i=0;i<6;i++) {
			cout << arr[chose[i]] << " ";
		}
		cout << endl;
		return;
	}
	
	for (int i=start;i<x;i++) {
		chose[depth] = i;
		recur(arr, depth+1, i+1, x);
	}
}

int main() {
	while (true) {
		int x;
		cin >> x;
		if (x==0) return 0;
		
		limitdepth = x;
		int arr[x];
		for (int i=0;i<x;i++) {
			cin >> arr[i];
		}
		recur(arr, 0, 0, x);
		
		cout << endl;
	}
}