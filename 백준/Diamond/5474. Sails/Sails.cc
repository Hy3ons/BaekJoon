#include<bits/stdc++.h>

using namespace std;
typedef long long lint;

int N;

lint tree[404040], lazy[404040], mn[404040], mx[404040];

void update (int n) {
    tree[n] = tree[n << 1] + tree[n << 1 | 1];
    mn[n] = min(mn[n << 1], mn[n << 1 | 1]);
    mx[n] = max(mx[n << 1], mx[n << 1 | 1]);
}

void push (int n, int l, int r) {
    const lint size = r - l + 1;

    tree[n] += lazy[n] * size;
    mn[n] += lazy[n];
    mx[n] += lazy[n];

    if (l != r){
        lazy[n << 1] += lazy[n];
        lazy[n << 1 | 1] += lazy[n];
    }

    lazy[n] = 0;
}

void lazyQuery (int n, int s, int e, int l, int r) {
    push(n, l, r);
    if (e < l || r < s) return;

    if (s <= l && r <= e) {
        lazy[n]++;
        push(n, l, r);
        return;
    }

    int mid = l + r >> 1;

    lazyQuery(n << 1, s, e, l, mid);
    lazyQuery(n << 1 | 1, s, e, mid+1, r);

    update(n);
}

lint query (int n, int s, int e, int l, int r) {
    push(n, l, r);
    if (e < l || r < s) return 0;

    if (s <= l && r <= e) return tree[n];

    int mid = l + r >> 1;

    return query(n << 1, s, e, l, mid) + query(n << 1 | 1, s, e, mid+1, r);
}

int lower_bound (int n, int s, int e, int l, int r, int k) {
    push(n, l, r);
    if (e < l || r < s || !(mn[n] <= k && k <= mx[n])) return 0;

    if (l == r) return l;

    int mid = l + r  >> 1;

    int temp = lower_bound(n << 1, s, e, l, mid, k);
    if (temp) return temp;
    return lower_bound(n << 1 | 1, s, e, mid+1, r, k);
}

int upper_bound (int n, int s, int e, int l, int r, int k) {
    push(n, l, r);
    if (e < l || r < s || !(mn[n] <= k && k <= mx[n])) return 0;

    if (l == r) return l;

    int mid = l + r >> 1;

    int temp = upper_bound(n << 1 | 1, s, e, mid+1, r, k);
    if (temp) return temp;
    return upper_bound(n << 1, s, e, l, mid, k);
}

int getKth (int n, int l, int r, int idx) {
    push(n, l, r);
    if (l == r) return tree[n];
    int mid = l + r >> 1;

    if (idx <= mid) return getKth(n << 1, l, mid, idx);
    else return getKth(n << 1 | 1, mid+1, r, idx);
}

void init (int n, int s, int e) {
    if (s == e) {
        tree[n] = mn[n] = mx[n] =  -1;
        return;
    }

    int mid = s + e >> 1;

    init(n << 1, s, mid);
    init(n << 1 | 1, mid+1, e);

    update(n);
}

int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N;


    vector<pair<int, int>> vec;

    for (int i=0;i<N;i++) {
        int H, K; cin >> H >> K;

        vec.emplace_back(H, K);
    }

    std::sort(vec.begin(), vec.end());

    const int R = 100000;
    int usingLeft = R+1;

    init(1, 1, R);

    lint answer = 0;

    for (auto [H, K] : vec) {
        const int nowSIZE = R+1 - usingLeft;
        const int L = R - H + 1;

        const int remainMinus = usingLeft - L;

        if (remainMinus < K) {
            usingLeft = L;

            //끝나는지점 어퍼 로어 작업 ㄱ

            int till = usingLeft + K - 1;

            int number = getKth(1, 1, R, till);

            int LL = lower_bound(1, usingLeft, R, 1, R, number);
            int RR = upper_bound(1, usingLeft, R, 1, R, number);

            const int updatedAmount = till - LL + 1;
            const int updatedLeftIdx = RR - updatedAmount + 1;

            lazyQuery(1, usingLeft, LL-1, 1, R);
            lazyQuery(1, updatedLeftIdx, RR, 1, R);

            answer += query(1, usingLeft, LL-1, 1, R);
            answer += query(1, updatedLeftIdx, RR, 1, R);
        } else {
            //일부마이너스만 차감.
            int tempR = usingLeft-1;
            int tempL = tempR - K + 1;

            usingLeft = tempL;

            lazyQuery(1, tempL, tempR, 1, R);
            answer += query(1, tempL, tempR, 1, R);
        }
    }

    cout << answer;
}
