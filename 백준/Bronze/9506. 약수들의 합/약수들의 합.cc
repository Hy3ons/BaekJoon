#include<bits/stdc++.h>
#define MAX 1010101

using namespace std;

typedef long long lint;

int N;
vector<int> arr;

int main () {
    while(true) {
        cin >> N;
        if (N == -1) break;
        arr.clear();
        lint sum = 0;

        for (int i=1;i<N;i++) {
            if (N % i == 0) {
                arr.push_back(i);
                sum += i;
            }
        }

        if (sum == N) {
            cout << N << " = " << arr[0] << ' ';

            for (int i=1;i<arr.size();i++) {
                cout << "+ " << arr[i] << ' ';
            }
            cout << '\n';

        } else {
            cout << N << " is NOT perfect.\n";
        }
    }
}