#include<bits/stdc++.h>
#define MAX 4040404
#define SUB 303030
#define INF 1e18
#define cost first
#define loc second

using namespace std;

typedef long long lint;
typedef pair<int, int> pt;

int N, M, K, num, W;
lint dp[MAX];
vector<pt> load[MAX];
 
int getInput (int n) {
    return n + 1000000;
}

int getOutput (int n) {
    return n + 2000000;
}

void init (int node, int s, int e) {
    for (int i=s;i<=e;i++) {
        load[i].push_back({0, getInput(node)});
        load[getOutput(node)].push_back({0, i});
    }

    if (s == e) return;

    int mid = s + e >> 1;

    init(node << 1, s, mid);
    init(node << 1 | 1, mid+1, e);
}

void goQuery (int node, int s, int e, int left, int right) {
    if (right < s || e < left) return;

    if (s <= left && right <= e) {
        load[getInput(node)].push_back({0, num});
        return;
    }

    int mid = left + right >> 1;

    goQuery(node << 1, s, e, left, mid);
    goQuery(node << 1 | 1, s, e, mid+1, right);
}

void fromQuery (int node, int s, int e, int left, int right) {
    if (right < s || e < left) return;

    if (s <= left && right <= e) {
        load[num].push_back({W, getOutput(node)});
        return;
    }

    int mid = left + right >> 1;

    fromQuery(node << 1, s, e, left, mid);
    fromQuery(node << 1 | 1, s, e, mid+1, right);
}
typedef pair<lint, int> ptt;
priority_queue<ptt, vector<ptt>, greater<ptt>> pq;

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N >> M >> K;

    init(1, 1, N);
    fill(dp, dp+MAX, INF);
    dp[K]= 0;

    lint B, C, D, E;

    for (int i=0;i<M;i++) {
        cin >> W >> B >> C >> D >> E;
        num = 3000000+i;

        goQuery(1, B, C, 1, N);
        fromQuery(1, D, E, 1, N);
    }
    
    pq.push({0, K});

    while(!pq.empty()) {
        ptt now = pq.top();pq.pop();
        if (now.cost != dp[now.loc]) continue;

        for (pt go : load[now.loc]) {
            if (dp[go.loc] > now.cost + go.cost) {
                dp[go.loc] = now.cost + go.cost;
                pq.push({dp[go.loc], go.loc});
            }
        }
    }
    
    for (int i=1;i<=N;i++) {
        if (dp[i] == INF) dp[i] = -1;
        cout << dp[i] << ' ';
    }

    return 0;
}