#include<bits/stdc++.h>
#define MAX 10101
#define MAX_T 50505
#define INF 4e18

using namespace std;

typedef long long ll;

int N;

struct line {
    ll a, b;
    int idx;

    double cal (double x) {
        return a * x + b;
    }

    double compare (const line &x, const double t = 0) {
        if (x.a == a) return INF;

        double u = b - x.b;
        double d = x.a - a;


        //Math.ceil
        double cMoment = u / d; 

        if (cMoment > t) return cMoment;
        return INF;
    }

};

struct kineticSeg {
    int size;
    line tree[MAX_T];
    double melt[MAX_T];
    int count[MAX_T];

    vector<line> temp;

    double T = 0;

    void pull (int n) {
        double l = tree[n << 1].cal(T);
        double r = tree[n << 1 | 1].cal(T);

        tree[n] = (l > r || (l == r && tree[n << 1].a > tree[n << 1 | 1].a))
            ? tree[n << 1] : tree[n << 1 | 1];

        melt[n] = min({melt[n << 1], melt[n << 1 | 1]
        , tree[n << 1].compare(tree[n << 1 | 1], T)});
    }

    void init (vector<line> &l) {
        T = 0;
        size = l.size();
        init (1, 0, size-1, l);
    }

    line compare (line  left, line  right) {
        if (left.cal(T) > right.cal(T)) {
            return left;
        } else {
            return right;
        }
    }

    void init (int node, int s, int e, vector<line> &l) {
        if (s == e) {
            tree[node] = l[s];
            melt[node] = INF;
            return;
        }

        int mid = s + e >> 1;

        init(node << 1, s, mid, l);
        init(node << 1 | 1, mid+1, e, l);

        pull(node);
    }

    bool answer (int left, int right) {
        temp.clear();
        double ans = 0;
        for (int i=0;i<8;i++) {
            line target = query(1, left, right, 0, size-1);
            ans += target.cal(T);
            temp.push_back(target);
            update(1, 0, N-1, target.idx, {-10000000,-1000000,-1});
        }

        for (int i=0;i<8;i++) {
            update(1, 0, N-1, temp[i].idx, temp[i]);
        }

        return ans >= 0;
    }

    line query (int node, int s, int e, int left, int right) {
        if (right <s || e < left) return {-100000000,-10000000,-1};

        if (s <= left && right <= e) return tree[node];

        int mid = left + right >> 1;

        return compare(query(node << 1, s, e, left, mid)
             , query(node << 1 | 1, s, e, mid+1, right));
    }

    void update (int node, int s, int e, int target, line l) {
        if (target < s || e < target) return;

        if (s == e) {
            tree[node] = l;
            return;
        }

        int mid = s + e>> 1;
        update(node << 1, s, mid, target, l);
        update(node << 1 | 1, mid+1, e, target, l);

        pull(node);
    }

    void heaten (const double t) {
        if (T >= t) return;
        T = t;

        heaten(1, 0, size-1);
    }

    void heaten (int node, int s, int e) {
        if (melt[node] > T) return;

        int mid = s + e >> 1;
        heaten(node << 1, s, mid);
        heaten(node << 1 | 1, mid+1, e);

        pull(node);
    }
}kst;

typedef pair<int, int> pp;
typedef pair<double, int> ppp;
#define x first
#define y second

vector<line> arr;
vector<pp> q;
vector<ppp> mid;

bool cmp (ppp & o1, ppp & o2) {
    return o1.x < o2.x;
}

double ans[MAX], lefts[MAX], rights[MAX];

int main() {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N;

    ll a, b;

    for (int i=0;i<N;i++) {
        cin >> a >> b;
        arr.push_back({-b, a, i});
    }

    for (int i=0;i<N-7;i++) {
        q.push_back(make_pair(0, 7+i));
    }

    for (int i=0;i<MAX;i++) {
        rights[i] = 1000000;
    }

    for (int t=0;t<60;t++) {
        mid.clear();

        for (int i=0;i<N-7;i++) {
            mid.push_back(make_pair((lefts[i] + rights[i]) / 2.0 , i));
        }

        sort(begin(mid), end(mid), cmp);
        kst.init(arr);

        for (ppp & e : mid) {
            kst.heaten(e.x);
            if (kst.answer(q[e.y].x, q[e.y].y)) {
                lefts[e.y] = e.x;
            } else {
                rights[e.y] = e.x;
            }
        }
    }

    cout << fixed << setprecision(9);

    for (int i=0;i<N-7;i++) {
        cout << lefts[i] << '\n';
    }
}
