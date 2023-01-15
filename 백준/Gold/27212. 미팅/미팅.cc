#include<bits/stdc++.h>
#define MAX 1010

using namespace std;

int N, M, C, A[MAX], B[MAX];
long long ans, dp[MAX][MAX], W[20][20];


int main () {
    cin.tie(0);ios::sync_with_stdio(0);
    cin >> N >> M >> C;

    for (int i=1;i<=C;i++) {
        for (int j=1;j<=C;j++) {
            cin >> W[i][j];
        }
    }

    for (int i=0;i<N;i++) cin >> A[i];
    for (int j=0;j<M;j++) cin >> B[j];
    
    for (int i=0;i<N;i++) {
        for (int j=0;j<M;j++) {
            dp[i][j] = W[A[i]][B[j]] + ((i == 0 || j == 0) ? 0 : dp[i-1][j-1]);
            if (i != 0) dp[i][j] = max(dp[i][j], dp[i-1][j]);
        }

        for (int j=1;j<M;j++) {
            dp[i][j] = max(dp[i][j-1], dp[i][j]);
        }
    }
    cout << dp[N-1][M-1];
}