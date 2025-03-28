#include<bits/stdc++.h>

typedef long long lint;
using namespace std;

struct suffix_array {

    int N;
    vector<int> Rank, SA, temp, lcp;
    vector<int> text;

    suffix_array (string s) {
        N = s.size();
        for (char c : s) text.push_back(c);

        Rank.resize(N+1);
        temp.resize(N+1);
        lcp.resize(N+1);
    }

    suffix_array (const vector<int> s) {
        N = s.size();
        text = s;

        Rank.resize(N+100);
        temp.resize(N+100);
        lcp.resize(N+100);
    }

    void solve () {
        if (N == 1) {
            lcp[0] = Rank[0] = 0;
            SA.push_back(0);
            return;
        }
        make_lcp();
    }

    //Rank 배열과, SA배열을 만듭니다.
    //O(N log^2 (N))
    void make_SA () {
        for (int i=0;i<N;i++) SA.push_back(i), Rank[i] = text[i];

        Rank[N] = temp[N] = -1;

        for (int len = 1; len < N; len <<= 1) {
            auto cmp = [&] (int a, int b) {
                if (Rank[a] == Rank[b]) {
                    int l = a + len, r = b + len;
                    l = min(l, N), r = min(r, N);

                    return Rank[l] < Rank[r];
                }

                return Rank[a] < Rank[b];
            };

            std::sort(SA.begin(), SA.end(), cmp);

            temp[SA[0]] = 0;

            for (int i=1;i<N;i++) {
                temp[SA[i]] = temp[SA[i-1]] + cmp(SA[i-1], SA[i]);
            }

            Rank = temp;
        }
    }

    void make_lcp () {
        make_SA();

        for (int i=0, skip = 0;i<N;i++) {
            if (!Rank[i]) continue;

            const int UP_IDX = SA[Rank[i]-1];

            while(i + skip < N && UP_IDX + skip < N && text[i + skip] == text[UP_IDX + skip]) skip++;

            lcp[Rank[i]] = skip;

            skip = max(0, skip-1);
        }
    }

};

struct seg {
    using lint = long long;
    const int size;
    const lint dummy;

    vector<lint> tree;

    seg(const vector<lint> &vc, lint dummy=1e18) : size(vc.size()), dummy(dummy) {
        tree.assign(vc.size() * 4, dummy);
        init(1, 1, size, vc);
    }

    void init (int n, int s, int e, const vector<lint> &vc) {
        if (s == e) {
            tree[n] = vc[s-1];
            return;
        }

        int mid = s + e >> 1;
        init(n*2, s, mid, vc);
        init(n*2+1, mid+1, e, vc);

        tree[n] = min(tree[n << 1] , tree[n << 1 | 1]);
    }

    lint query (int n, int s, int e, int l, int r) {
        if (e < l || r < s) return dummy;

        if (s <= l && r <= e) return tree[n];

        int mid = l + r >> 1;

        return min(query(n*2, s, e, l, mid), query(n*2+1, s, e, mid+1, r));
    }

    vector<tuple<int, int, int, int>> dedi;

    int findd (int n, int s, int e, int l, int r, int MIN) {
        if (r < s || e < l || tree[n] > MIN) return 0;

        if (l == r) return l;

        int mid = l + r >> 1;

        int g = findd(n << 1 | 1, s, e, mid+1, r, MIN);
        if (g) return g;
        return findd(n << 1, s, e, l, mid, MIN);
    }

    int find (int n, int s, int e, int l, int r, int MIN) {
        if (r < s || e < l || tree[n] >= MIN) return 0;

        if (l == r) return l;

        int mid = l + r >> 1;

        int g = find(n << 1, s, e, l, mid, MIN);
        if (g) return g;
        return find(n << 1 | 1, s, e, mid+1, r, MIN);
    }

    void update (int n, int s, int e, int idx, lint delta) {
        if (s == e) {
            tree[n] = delta;
            return;
        }

        int mid = s + e >> 1;

        if (idx <= mid) update(n*2, s, mid, idx, delta);
        else update(n*2+1, mid+1, e, idx, delta);

        tree[n] = min(tree[n << 1] , tree[n << 1 | 1]);
    }

    void update (int idx, lint delta) {
        update(1, 1, size, idx, delta);
    }

    lint query (int L, int R) {
        return query(1, L, R, 1, size);
    }
};

string S, T;

int changer (char c) {
    if (c == '(') return 1;
    if (c == ')') return -1;
    return 0;
}

void solve () {
    cin >> S >> T;

    string legend = S + "*************" + T;
    suffix_array lcp(legend);

    lcp.make_lcp();

    vector<lint> prefix(legend.size()+10);
    for (int i=0;i<legend.size();i++) {
        prefix[i+1] = prefix[i] + changer(legend[i]);
    }

    seg sg(prefix);

    auto adapter = [&] (int i) {
        return i < S.size();
    };

    int mx = 0;

    for (int i=0;i+1<lcp.N;i++) {
        if (legend[lcp.SA[i]] == '*' || legend[lcp.SA[i+1]] == '*') continue;
        if (adapter(lcp.SA[i]) == adapter(lcp.SA[i+1])) continue;

        sg.dedi.clear();

        int left = lcp.SA[i]+1, right = left + lcp.lcp[i+1] - 1;
        if (right < left) continue;

//        cout << legend.substr(lcp.SA[i], lcp.lcp[i+1]) << endl;
        //0-based

        int result = sg.find(1, left+1, right+1, 1, sg.size, prefix[left-1]);
        if (!result) result = sg.findd(1, left+1, right+1, 1, sg.size, prefix[left-1]) ;
        else result--;

        mx = max(mx, result - left);
    }

    cout << mx << '\n';

}

int main () {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    int _; cin >> _;
    while(_-->0) solve();
}
