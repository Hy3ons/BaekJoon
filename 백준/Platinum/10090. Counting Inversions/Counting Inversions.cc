#include<bits/stdc++.h>
#define MAX 1010101

using namespace std;

typedef long long lint;

int N, idx[MAX], tree[MAX*4];

int query (int node, int s, int e, int left, int right) {
    if (right <s || e < left) return 0;

    if (s <= left && right <= e) return tree[node];

    int mid = left + right >> 1;
    return query(node << 1, s, e, left, mid) + query(node << 1 | 1, s, e, mid+1, right);
}

void update (int node, int s, int e, int t) {
    if (s == e) {
        tree[node] = 1;
        return;
    }

    int mid = s + e >> 1;
    if (t <= mid) update(node << 1, s, mid, t);
    else update(node << 1 | 1, mid+1, e, t);

    tree[node] = tree[node << 1] + tree[node << 1 | 1];
}


int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);

    cin >> N;
    int j;
    for (int i=1;i<=N;i++) {
        cin >> j;
        idx[j] = i;
    }

    lint ans = 0;

    for (int i=1;i<=N;i++) {
        ans += query(1, idx[i], N, 1, N);
        update(1, 1, N, idx[i]);
    }

    cout << ans;
}