#include <bits/stdc++.h>

using namespace std;

int main () {
	int testcase;
	cin >> testcase;
	
	stack <int> st;
	int num;
	
	while (testcase-->0) {
		cin >> num;
		if (num==0) {
			st.pop();
		} else {
			st.push(num);
		}
	}
	
	long sum = 0;
	while (!st.empty()) {
		sum += st.top();
		st.pop();
	}
	
	cout << sum;
	return 0;
}