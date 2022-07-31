#include <bits/stdc++.h>

using namespace std;

int N, K;
int main() {
    cin >> N >> K;
    int arr[N];
    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    int result = 0;
    for (int i=N-1;i>=0;i--) {
        result += K / arr[i];
        K %= arr[i];
    }

    cout << result;
}