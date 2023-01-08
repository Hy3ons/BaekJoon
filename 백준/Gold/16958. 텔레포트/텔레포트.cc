#include<bits/stdc++.h>
#define MAX 1010
#define x first
#define y second

#define fastio() cin.tie(0);ios_base::sync_with_stdio(0)

using namespace std;

typedef pair<int, int> pt;

const int INF = 100000000;
int N, M, T, a, b, s[MAX], cost[MAX][MAX];
vector<pt> pts;

int dist (int a, int b) {
    int dx = pts[a].x - pts[b].x;
    int dy = pts[a].y - pts[b].y;

    return abs(dx) + abs(dy);
}

void solve () {
    
    for (int i=0;i<MAX;i++) for (int j=0;j<MAX;j++) cost[i][j] = INF;

    for (int i=0;i<N;i++) {
        for (int j=i+1;j<N;j++) {
            int temp = s[i] && s[j] ? T : INF;
            cost[i][j] = min({cost[i][j], dist(i, j), temp});
            cost[j][i] = cost[i][j];
        }
    }


    for (int k=0;k<N;k++) {
        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                if (i != k && i != j && k != j) {
                    cost[i][j] = min(cost[i][j], cost[i][k] + cost[k][j]);
                }
            }
        }
    }

}

int main () {
    fastio();
    cin >> N >> T;
    int special;

    for (int i=0;i<N;i++) {
        cin >> special >> a >> b;
        pts.emplace_back(a, b);
        s[i] = special;
    }
    solve();

    cin >> M;

    for (int i=0;i<M;i++) {
        cin >> a >> b;
        a--;b--;
        cout << cost[a][b] << '\n';
    }
}