#include<bits/stdc++.h>
#define x first
#define y second
using namespace std;

int board[1010][1010], N, M, dx[] = {1, -1, 0, 0}, dy[4] = {0, 0, -1, 1};
queue<pair<int, int>> qu;

int main () {
    cin >> N >> M;
    for (int i=0;i<N;i++) for (int j=0;j<M;j++) {
        cin >> board[i][j];
    }

    dx[1] = N-1;
    dy[2] = M-1;

    int ans = 0;

    for (int i=0;i<N;i++) for (int j=0;j<M;j++) if (!board[i][j]) {
        board[i][j] = 1;
        qu.push({i, j});
        ans++;

        while(!qu.empty()) {
            pair<int, int> now = qu.front();qu.pop();

            for (int k=0;k<4;k++) {
                int nx = (now.x + dx[k]) % N;
                int ny = (now.y + dy[k]) % M;

                if (board[nx][ny]) continue;
                board[nx][ny] = 1;
                qu.push({nx, ny});
            }
        }
    }
    cout << ans;
}