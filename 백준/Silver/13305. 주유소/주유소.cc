#include <bits/stdc++.h>

using namespace std;

int N;

int main() {
    cin >> N;
    long long km[N-1], cost[N];

    for (int i=0;i<N-1;i++) cin >> km[i];
    for (int i=0;i<N;i++) cin >> cost[i];

    for (int i=1;i<N;i++) {
        if (cost[i-1] < cost[i]) {
            cost[i] = cost[i-1];
        }
    }
    long long result = 0;

    for (int i=0;i<N-1;i++) {
        result += cost[i] * km[i];
    }
    cout << result;
}