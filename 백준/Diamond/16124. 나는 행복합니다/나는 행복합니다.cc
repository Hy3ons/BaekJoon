#include<bits/stdc++.h>

using namespace std;
using lint = long long;

const lint mod = 998244353, rev = 299473306;

lint ans;
string str;
int N, Q;

struct node {
    int values[10] = {0}, lazy, lvalue[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
};

vector<node> nodes(4040404);
vector<lint> base(1010101, 1), rebase(1010101, 1);

void update (int n) {
    int L = n << 1;
    int R = n << 1 | 1;

    for (int i=0;i<10;i++) {
        nodes[n].values[i] = (nodes[L].values[i] + nodes[R].values[i]) % mod;
    }
}

void push (int n, int l, int r) {
    if (!nodes[n].lazy) return;
    nodes[n].lazy = 0;

    int values[10] = {};

    for (int i=0;i<10;i++) {
        values[nodes[n].lvalue[i]] += nodes[n].values[i];
        values[nodes[n].lvalue[i]] %= mod;
    }

    memcpy(nodes[n].values, values, 40);

    if (l != r) {
        int L = n << 1;
        int R = n << 1 | 1;

        for (int &i : nodes[L].lvalue) {
            i = nodes[n].lvalue[i];
        }

        for (int &i : nodes[R].lvalue) {
            i = nodes[n].lvalue[i];
        }

        nodes[L].lazy = nodes[R].lazy = 1;
    }

    iota(nodes[n].lvalue, nodes[n].lvalue+10, 0);
}

void init (int n, int s, int e) {
    if (s == e) {
        nodes[n].values[str[e-1] - '0'] = base[e];
        return;
    }

    int mid = s + e >> 1;

    init(n << 1, s, mid);
    init(n << 1 | 1, mid+1, e);

    update(n);
}

void update (int n, int s, int e, int l, int r, int from, int to) {
    push(n, l, r);
    if (e < l || r < s) return;

    if (s <= l && r <= e) {
        nodes[n].lvalue[from] = to;
        nodes[n].lazy = 1;
        push(n, l, r);
        return;
    }

    int mid = l + r >> 1;
    update(n << 1, s, e, l, mid, from, to);
    update(n << 1 | 1, s, e, mid+1, r, from, to);

    update(n);
}

void query (int n, int s, int e, int l, int r) {
    push(n, l, r);
    if (e < l || r < s) return;

    if (s <= l && r <= e) {
        for (lint i=0;i<10;i++) {
            ans += nodes[n].values[i] * i;
            ans %= mod;
        }
        return;
    }

    int mid = l + r >> 1;
    query(n << 1, s, e, l, mid);
    query(n << 1 | 1, s, e, mid+1, r);

    update(n);
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(false);

    cin >> str >> Q;
    N = str.size();

    for (int i=N-1;i>=1;i--) {
        base[i] = base[i+1] * 10;
        base[i] %= mod;
    }

    for (int i=1;i<=N;i++) {
        rebase[i] = (rebase[i-1] * rev) % mod;
    }

    init(1, 1, N);

    while (Q--) {
        int m, l, r, from, to;
        cin >> m;

        if (m == 1) {
            cin >> l >> r >> from >> to;
            update(1, l, r, 1, N, from, to);
        } else {
            cin >> l >> r;

            ans = 0;
            query(1, l, r, 1, N);

            int step = N - r;
            ans *= rebase[step];
            ans %= mod;

            cout << ans << '\n';
        }
    }
}