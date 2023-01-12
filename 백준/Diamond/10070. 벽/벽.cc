#include<bits/stdc++.h>
#define MAX 2020202
#define MAX_T 8080808
#define INF 1e8

using namespace std;
int N, K, A, x, y, m, h;

int mx[MAX_T], mn[MAX_T], re[MAX_T], ans[MAX_T];
void push (int n, bool p) {
    if (!re[n]) return;

    if (p) {
        re[n << 1] = re[n<< 1 | 1] = 1;

        mx[n << 1] = max(mx[n << 1], mx[n]);
        mn[n << 1] = max(mn[n << 1], mx[n]);
        mx[n << 1] = min(mx[n << 1], mn[n]);
        mn[n << 1] = min(mn[n << 1], mn[n]);

        mx[n << 1 | 1] = max(mx[n << 1 | 1], mx[n]);
        mn[n << 1 | 1] = max(mn[n << 1 | 1], mx[n]);
        mx[n << 1 | 1] = min(mx[n << 1 | 1], mn[n]);
        mn[n << 1 | 1] = min(mn[n << 1 | 1], mn[n]);
    } else {
        ans[n] = min(mn[n], ans[n]);
        ans[n] = max(mx[n], ans[n]);
    }

    re[n] = mx[n] = 0;
    mn[n] = INF;
}

void query (int node, int s, int e, int left, int right) {
    push(node, left != right);
    if (right < s || e < left) return;

    if (s <= left && right <= e) {
        if (m == 1) mx[node] = h;
        else mn[node] = h;
        re[node] = 1;
        return;
    }

    int mid = left + right >> 1;
    query(node << 1, s, e, left, mid);
    query(node << 1 | 1, s, e, mid+1, right);
}

void answer (int node, int s, int e) {
    push(node, s != e);
    if (s == e) {
        cout << ans[node] << '\n';
        return;
    }

    int mid = s + e >> 1;
    answer(node << 1, s, mid);
    answer(node << 1 | 1, mid+1, e);
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N >> K;

    fill(mn, mn+MAX_T, INF);

    for (int i=0;i<K;i++) {
        cin >> m >> x >> y >> h;
        query(1, x, y, 0, N-1);
    }

    answer(1, 0, N-1);
}