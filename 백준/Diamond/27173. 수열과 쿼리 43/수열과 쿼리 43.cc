#include<bits/stdc++.h>
#define MAX 101010
#define MAX_T 404040

using namespace std;
using lint = long long;

vector<lint> arr;
int N, Q;

struct seg {
    const lint INF = 4e18;
    int cover[MAX_T], re[MAX_T], seTime[MAX_T];
    lint mn[MAX_T], mx[MAX_T], leftSt[MAX_T], rightSt[MAX_T], add[MAX_T], putt[MAX_T];
    lint startL;


    void pull (int n) {
        mn[n] = min(mn[n << 1], mn[n << 1 | 1]);
        mx[n] = max(mx[n << 1], mx[n << 1 | 1]);
    }

    void push (int n, int left, int right) {
        if (!re[n]) return;
        mn[n] *= cover[n]; mx[n] *= cover[n];
        mn[n] += leftSt[n] + add[n] + putt[n];
        mx[n] += rightSt[n] + add[n] + putt[n];

        if (left != right) {
            //should propagate
            int c = cover[n];
            re[n << 1] = re[n << 1 | 1] = 1;

            cover[n << 1] *= c;
            cover[n << 1 | 1] *= c;
            add[n << 1] *= c;
            add[n << 1 | 1] *= c;
            seTime[n << 1] *= c;
            seTime[n << 1 | 1] *= c;
            leftSt[n << 1] *= c;
            leftSt[n << 1 | 1] *= c;
            rightSt[n << 1] *= c;
            rightSt[n << 1 | 1] *= c;
            putt[n << 1] *= c;
            putt[n << 1 | 1] *= c;

            int mid = left + right >> 1;

            leftSt[n << 1] += leftSt[n];
            leftSt[n << 1 | 1] += leftSt[n] + (mid - left + 1) * seTime[n];
            rightSt[n << 1 | 1] += rightSt[n];
            rightSt[n << 1] += rightSt[n] - (right - mid) * seTime[n];

            add[n << 1] += add[n];
            add[n << 1 | 1] += add[n];
            putt[n << 1] += putt[n];
            putt[n << 1 | 1] += putt[n];
            seTime[n << 1] |= seTime[n];
            seTime[n << 1 | 1]  |= seTime[n];
        }

        cover[n] = 1;
        leftSt[n] = rightSt[n] = add[n] = seTime[n]= re[n] = 0;
        putt[n] = 0;
    }

    void init (vector<lint> &l) {
        fill(cover, cover+MAX_T, 1);
        init(1, 0, N-1, l);
    }

    void init (int n, int s, int e, vector<lint> &l) {
        if (s == e) {
            mx[n] = mn[n] = l[s];
            return;
        }

        int mid = s + e >> 1;
        init(n << 1, s, mid, l); init(n << 1 | 1, mid+1, e, l);
        pull(n);
    }

    lint minQuery (int node, int s, int e, int left, int right) {
        push(node, left, right);
        if (right <s || e < left) return INF;
        
        if (s <= left && right <= e) {
            return mn[node];
        }

        int mid = left + right >> 1;
        return min(minQuery(node << 1, s, e, left, mid), minQuery(node << 1 | 1, s, e, mid+1, right));
    }

    bool tagCondition (int node) {
        lint d1 = ((lint) sqrt(mx[node]));
        lint d2 = ((lint) sqrt(mn[node]));

        return d1 == d2;
    }

    void sqrtQuery (int node, int s, int e, int left, int right) {
        push(node, left, right);
        if (right < s || e < left) return;

        if (s <= left && right <= e && tagCondition(node)) {
            re[node] = 1;
            cover[node] = 0;
            putt[node] = (lint) sqrt(mx[node]);
            push(node, left, right);
            return;
        }

        int mid = left + right >> 1;

        sqrtQuery(node << 1 , s, e, left, mid);
        sqrtQuery(node << 1 | 1, s, e, mid+1, right);

        pull(node);
    }

    void addQuery (int node, int s, int e, int left, int right, lint value) {
        push(node, left, right);
        if (right < s || e < left) return;

        if (s <= left && right <= e) {
            re[node] = 1;
            add[node] = value;
            push(node, left, right);
            return;
        }

        int mid = left + right >> 1;

        addQuery(node << 1 , s, e, left, mid, value);
        addQuery(node << 1 | 1, s, e, mid+1, right, value);

        pull(node);
    }

    void sequenceQuery (int node, int s, int e, int left, int right) {
        push(node, left, right);
        if (right < s || e < left) return;

        if (s <= left && right <= e) {
            seTime[node] = re[node] = 1;
            cover[node] = 0;

            leftSt[node] = startL;
            rightSt[node] = startL + (right - left);
            startL += right - left + 1;
            push(node, left, right);
            return;
        }

        int mid = left + right >> 1;

        sequenceQuery(node << 1 , s, e, left, mid);
        sequenceQuery(node << 1 | 1, s, e, mid+1, right);

        pull(node);
    }



}seg;


int main () {
    cin.tie(0);ios::sync_with_stdio(0);
    cin >> N >> Q;
    lint m, x, y, t;

    for (int i=0;i<N;i++) {
        cin >> t;
        arr.push_back(t);
    }

    seg.init(arr);

    for (int i=0;i<Q;i++) {
        cin >> m;
        if (m == 1) {
            cin >> x;
            cout << seg.minQuery(1, x-1, x-1, 0, N-1) << '\n';
        } else if (m == 4) {
            cin >> x >> y;
            seg.sqrtQuery(1, x-1, y-1, 0, N-1);
        } else if (m == 2) {
            cin >> x >> y >> t;
            if (seg.minQuery(1, x-1, y-1, 0, N-1) < t) continue;
            seg.addQuery(1, x-1, y-1, 0, N-1, -t);
        } else {
            cin >> x >> y >> t;

            seg.startL = t-y+x;
            seg.sequenceQuery(1, x-1, y-1, 0, N-1);
        }
    }
}