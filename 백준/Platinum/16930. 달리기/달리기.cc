#include <bits/stdc++.h>

using namespace std;

string arr[1010];

int N, M, K;
int sx, sy, ex, ey;

int visited[1010][1010], dp[1010][1010];

int dx[4] = {0, 0, 1, -1}, dy[4] = {1, -1, 0, 0};

int main() {
    cin.tie(0);
    ios_base::sync_with_stdio(false);

    cin >> N >> M >> K;

    for (int i = 0; i < N; i++) {
        cin >> arr[i];

        for (int j=0;j<M;j++) {
            if (arr[i][j] == '#') visited[i][j] = 1;
        }
    }

    cin >> sx >> sy >> ex >> ey;

    sx--,sy--,ex--,ey--;

    queue<pair<int, int>> qu;
    visited[sx][sy] = 1;
    qu.push(make_pair(sx, sy));

    while (qu.size()) {
        auto [nx, ny] = qu.front(); qu.pop();

        for (int d : {0, 1, 2, 3}) {
            for (int i = 1;i<=K;i++) {
                int x = nx + dx[d] * i;
                int y = ny + dy[d] * i;

                if (x < 0 || x >= N || y < 0 || y >= M || arr[x][y] == '#') break;

                if (visited[x][y]) {
                    if (dp[x][y] != dp[nx][ny] + 1) break;
                } else {
                    visited[x][y] = 1;
                    dp[x][y] = dp[nx][ny] + 1;

                    qu.push({x, y});
                }
            }
        }
    }

    if (dp[ex][ey] == 0) dp[ex][ey] = -1;

    cout << dp[ex][ey] ;
}