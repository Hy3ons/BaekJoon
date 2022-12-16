#include<bits/stdc++.h>
#define MAX 101010
#define x first
#define y second

using namespace std;
typedef long long ll;
typedef pair<ll, ll> pp;


const ll INF = 1e18;
int N, M;

int visits[MAX];
priority_queue<pp, vector<pp>, greater<pp>> pq; 

vector<pp> load[MAX];
vector<ll> dp(MAX, INF);

int main() {
    cin >> N >> M;

    for (int i=0;i<N;i++) {
        cin >> visits[i];
    }
    visits[N-1] = 0;

    int n1, n2, t;

    for (int i=0;i<M;i++) {
        cin >> n1 >> n2 >> t;

        load[n1].emplace_back(n2, t);
        load[n2].emplace_back(n1, t);
    }

    pq.push({0, 0});
    dp[0] = 0;

    while(!pq.empty()){
        auto [cost, here] = pq.top(); pq.pop();

        if (dp[here] != cost) continue;

        for (pp next : load[here]) {
            if (visits[next.x]) continue;

            if (dp[next.x] > cost + next.y) {

                dp[next.x] = cost + next.y;
                pq.emplace(dp[next.x], next.x);
            }
        }
    }

    if (dp[N-1] == INF) {
        cout << -1 << '\n';
    } else {
        cout << dp[N-1] << '\n';
    }
}