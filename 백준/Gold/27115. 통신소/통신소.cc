#include<bits/stdc++.h>
#define MAX 9000001

using namespace std;

typedef pair<int, int> pt;

queue<int> qu;
vector<int> arr[3030];

int visited[MAX], N, M, K, dx[] = {1, -1, 0, 0}, dy[] = {0,0,-1,1}, checked[MAX];

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N >> M >> K;

    fill(checked, checked+MAX, -1);

    int a, b, c;
    for (int i=0;i<K;i++) {
        cin >> a >> b >> c;
        a--;b--;
        arr[c].push_back(a*M+b);
    }


    int ans = 0;

    for (int i=3000;i>0;i--) {
        for (int e : arr[i]) {
            if (!visited[e]) {
                visited[e] = 1;
                qu.push(e);
                ans++;
            }
        }

        for (int k=qu.size();k>0;k--) {
            int now = qu.front();qu.pop();
            int nowX = now / M, nowY = now % M;

            for (int j=0;j<4;j++) {
                int x = nowX + dx[j];
                int y = nowY + dy[j];

                if (x == -1 || x == N || y == -1 || y == M || visited[x*M+y]) continue;

                visited[x*M+y] = 1;
                qu.push(x*M+y);
                ans++;
            }
        }
    }

    cout << ans;

}