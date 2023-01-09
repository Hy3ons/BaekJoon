#include<bits/stdc++.h>
#define MAX 1010101

using namespace std;

int A, B, C, K, da[6] = {1,2,0,0,4,0}, db[6] = {1,0,2,0,0,4}, dc[6] = {0,1,1,2,0,0}, dp[MAX];

int main () {
    cin >> A >> B >> C >> K;
    fill(dp, dp+MAX, 1e9);

    dp[0] = 0;

    for (int i=0;i<MAX;i++) {
        for (int k=0;k<6;k++) {
            int M = da[k] * A + db[k] * B + dc[k] * C;

            if (i + M >= MAX) continue;
            dp[i + M] = min(dp[i + M], dp[i] + da[k] + db[k] + dc[k]);
        }
    }

    A = dp[K] == 1e9 ? -1 : dp[K];
    cout << A;
}