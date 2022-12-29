#include<bits/stdc++.h>
#define MAX 202020
#define INF 1e16

typedef long long lint;

using namespace std;

int N, K;

struct node
{
    node *l = nullptr, *r = nullptr;
    lint value = 0;

    node (lint value) : value(value) {}
    node (node *l, node* r) : l(l), r(r) {
        value = l->value + r->value;
    }
};


struct persistance {
    int start, sizes;
    vector<node*> roots;
    vector<int> lastEx;

    persistance(int sizes) : sizes(sizes) {
        start = 1;
        while(start < sizes) start <<= 1;
        vector<node*> tree(start << 1);
        lastEx.resize(sizes+1);

        for (int i=1;i<tree.size();i++) {
            tree[i] = new node(0);
        }

        for (int i=1;i<start;i++) {
            tree[i]->l = tree[i << 1];
            tree[i]->r = tree[i << 1 | 1];
        }

        roots.push_back(tree[1]);
    }

    node* update (node* n, int s, int e, int target, int value) {
        if (target < s || e < target) return n;

        if (s == target && target == e) {
            return new node(value);
        }

        int mid = s + e >> 1;

        node* left = update(n->l, s, mid, target, value);
        node* right = update(n->r, mid+1, e, target, value);

        return new node(left, right);
    }

    void push (int num, int idx) {
        if (!lastEx[num]) {
            roots.push_back(update(roots.back(), 1, start, idx, 1));
        } else {
            node* temp = update(roots.back(), 1, start, lastEx[num], 0);
            roots.push_back(update(temp, 1, start, idx, 1));
        }
        lastEx[num] = idx;
    }

    lint query (int left, int right) {
        return query(roots[right], left, right, 1, start);
    }

    lint query (node *n, int s, int e, int left, int right) {
        if (right < s || e < left) return 0;

        if (s <= left && right <= e) {
            return n->value;
        }

        int mid = left + right >> 1;

        return query(n->l, s, e, left, mid) + query(n->r, s, e, mid+1, right);
    }

};

struct box {
    lint mx;
    int idx;

    box (lint mx= -INF, int idx = -1) : mx(mx), idx(idx) {}

    box operator+ (box o) {
        box res(1, 1);

        res.mx = max(mx, o.mx);

        if (res.mx == mx) {
            res.idx = idx;
        } else {
            res.idx = o.idx;
        }

        return res;
    }
};

struct lazyseg {
    vector<lint> tree, lazy, idx;

    int start = 1, sizes;

    lazyseg (int sizes) : sizes(sizes) {
        while(start < sizes) start <<= 1;

        tree.resize(start << 1);
        lazy.resize(start << 1);
        idx.resize(start << 1);

        for (int i=0;i<sizes;i++) {
            idx[i + start] = i+1;
        }
    }

    void clear () {
        fill(tree.begin(), tree.end(), -INF);
        fill(lazy.begin(), lazy.end(), 0);
    }

    void push (int n, bool propagate) {
        if (!lazy[n]) return;

        tree[n] += lazy[n];

        if (propagate) {
            lazy[n << 1] += lazy[n];
            lazy[n << 1 | 1] += lazy[n];
        }

        lazy[n] = 0;
    }

    void pull (int n) {
        tree[n] = max(tree[n << 1], tree[n << 1 | 1]);
        if (tree[n] == tree[n << 1]) {
            idx[n] = idx[n << 1];
        } else {
            idx[n] = idx[n << 1 | 1];
        }
    }

    void update (int node, int s, int e, int left, int right, lint value, bool init = false) {
        push(node, left != right);

        if (right < s || e < left) return;

        if (s <= left && right <= e) {
            if (init) {
                tree[node] = value;
            } else {
                lazy[node] += value;
                push(node, left != right);
            }
            return;
        }

        int mid = left + right >> 1;

        update(node << 1, s, e, left, mid, value, init);
        update(node << 1 | 1, s, e, mid+1, right, value, init);

        pull(node);
    }

    
    box query (int node, int s, int e, int left, int right) {
        push(node, left != right);

        if (right < s || e < left) return box();

        if (s <= left && right <= e) {
            return box(tree[node], idx[node]);
        }

        int mid = left + right >> 1;

        box res = query(node << 1, s, e, left, mid) + query(node << 1 | 1, s, e, mid+1, right);
        pull(node);

        return res;
    }

};

vector<int> arr;
int lastNum[MAX];

int main () {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N >> K;
    int temp;

    persistance seg(N);
    lazyseg tree(N);

    for (int i=1;i<=N;i++) {
        cin >> temp;
        arr.push_back(temp);
        seg.push(temp, i);
    }

    lint left = -1, right = 100000;
    vector<lint> dp(N+1), re(N+1);
    lint ans = 0;

    int c = 0;

    while(true) {
        assert(++c <= 150);
        lint X = left + right >> 1;

        fill(re.begin(), re.end(), 1);
        tree.clear();
        fill(lastNum, lastNum+MAX, 0);

        for (int i=0;i<N;i++) {
            dp[i] = seg.query(1, i+1) - X;
        }

        for (int i=0;i<N;i++) {
            //dp[i] = min j < i (dp[j] + cost[j][i]) + X         O( N^2 log ^ 2 N )
            // dp[i] 를 찾을 때, segment Tree 를 또 응용하기 위해선, 기존에 업데이트 되어있는 구간 [0, i-1], 에
            // 대하여 각각의 cost[j][i] 의 변화에 알맞게 업데이트 해주어야 함.

            // 어느 한 부분 Y   [Y, i-1] 구간 +1 해야함.    
            // Y 는 최근에 나온 arr[i] 의 위치+1
            if (i == 0) {
                tree.update(1, i+1, i+1, 1, tree.start, dp[i], true);
                lastNum[arr[i]] = i+1;
                continue;
            }

            if (!lastNum[arr[i]]) {
                tree.update(1, 1, i, 1, tree.start, 1);
            } else {
                tree.update(1, lastNum[arr[i]], i, 1, tree.start, 1);
            }

            lastNum[arr[i]] = i+1;

            box mn = tree.query(1, 1, i, 1, tree.start);

            if (mn.mx - X > dp[i]) {
                //역추적도 해야함.
                dp[i] = mn.mx - X;
                re[i] = re[mn.idx-1] + 1;
            }

            tree.update(1, i+1, i+1, 1, tree.start, dp[i], true);
        }

        if (re[N-1] <= K) {
            right = X;
        } else {
            left = X;
        }

        if (right - left == 1)  {
            // cout << re[N-1] << '\n';
            // cout << X << '\n';
            // cout << ((X == re[N-1]) ? dp[N-1] - K * X : ans) << '\n';
            
            break;
        }

    }

        lint X = right;

        fill(re.begin(), re.end(), 1);
        tree.clear();
        fill(lastNum, lastNum+MAX, 0);

        for (int i=0;i<N;i++) {
            dp[i] = seg.query(1, i+1) - X;
        }

        for (int i=0;i<N;i++) {
            //dp[i] = min j < i (dp[j] + cost[j][i]) + X         O( N (log N)^ 2 )
            // dp[i] 를 찾을 때, segment Tree 를 또 응용하기 위해선, 기존에 업데이트 되어있는 구간 [0, i-1], 에
            // 대하여 각각의 cost[j][i] 의 변화에 알맞게 업데이트 해주어야 함.

            // 어느 한 부분 Y   [Y, i-1] 구간 +1 해야함.    
            // Y 는 최근에 나온 arr[i] 의 위치+1
            if (i == 0) {
                tree.update(1, i+1, i+1, 1, tree.start, dp[i], true);
                lastNum[arr[i]] = i+1;
                continue;
            }

            if (!lastNum[arr[i]]) {
                tree.update(1, 1, i, 1, tree.start, 1);
            } else {
                tree.update(1, lastNum[arr[i]], i, 1, tree.start, 1);
            }

            lastNum[arr[i]] = i+1;

            box mn = tree.query(1, 1, i, 1, tree.start);

            if (mn.mx - X > dp[i]) {
                //역추적도 해야함.
                dp[i] = mn.mx - X;
                re[i] = re[mn.idx] + 1;
            }

            tree.update(1, i+1, i+1, 1, tree.start, dp[i], true);
        }

        cout << dp[N-1] + K * X << '\n';
}