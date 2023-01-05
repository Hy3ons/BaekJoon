#include<bits/stdc++.h>
#define MAX 2020
#define MAX_T 8080

using namespace std;

typedef long long lint;

int N;

struct point {
    lint x, y;
    int idx, cost;

    point (lint x, lint y, int cost = 0) : x(x), y(y), cost(cost) {}
};

struct line {
    point *s, *e, *d;

    line (point *s, point *e) : s(s), e(e) {
        d = new point(s->x - e->x, s->y - e->y);

        if (d->x < 0 || (d->x == 0 && d->y < 0)) {
            d->x *= -1;
            d->y *= -1;
        }
    }
};

point *origin = new point(0, 0);

bool pointcmp (point *o1, point *o2) {
    if (o1->x == o2->x) return o1->y < o2->y;
    return o1->x < o2->x;
}

lint calCCW (point *a, point *b, point *c) {
    return (c->x - a->x) * (b->y - a->y) - (b->x - a->x) * (c->y - a->y);
}

bool linecmp (line l1, line l2) {
    lint res = calCCW(origin, l1.d, l2.d);
    if (res != 0) return res < 0;

    if (l1.s != l2.s) return !pointcmp(l1.s, l2.s);
    return !pointcmp(l1.e, l2.e);
}

struct node {
    lint left, right, sum, ans;

    node (lint v = 0) : left(v), right(v), sum(v), ans(v) {}

    void setValue (lint v) {
        left = right = sum = ans = v;
    }
};


point *pointIdx[MAX];
node tree[MAX_T];

void pull (int n, node &left, node &right) {
    node &res = tree[n];

    res.sum = left.sum + right.sum;
    res.left = max(left.left, left.sum + right.left);
    res.right = max(right.right, left.right + right.sum);
    res.ans = max({left.ans, right.ans, left.right + right.left});
}

void init (int n, int s, int e) {
    if (s == e) {
        tree[n].setValue(pointIdx[s]->cost);
        return;
    }

    int mid = s + e >> 1;
    init(n << 1, s, mid);
    init(n << 1 | 1, mid+1, e);

    pull(n, tree[n << 1], tree[n << 1| 1]);
}

void update (int n, int s, int e, int t) {
    if (s == e) {
        tree[n].setValue(pointIdx[s]->cost);
        return;
    }

    int mid = s + e >> 1;

    if (t <= mid) {
        update(n << 1, s, mid, t);
    } else {
        update(n << 1 | 1, mid+1, e, t);
    }

    pull(n, tree[n << 1], tree[n << 1| 1]);
}

vector<point*> points;
vector<line> lines;

int main () {
    cin.tie(0); ios_base::sync_with_stdio(0);
    cin >> N;

    lint a, b, c;
    for (int i=0;i<N;i++) {
        cin >> a >> b >> c;
        points.push_back(new point(a, b, c));
    }

    sort(begin(points), end(points), pointcmp);

    for (int i=0;i<N;i++) {
        pointIdx[i] = points[i];
        points[i]->idx = i;
    }

    init(1, 0, N-1);

    for (int i=0;i<N;i++) {
        for (int j=i+1;j<N;j++) {
            lines.emplace_back(points[i], points[j]);
        }
    }

    sort(begin(lines), end(lines), linecmp);

    point *last = lines[0].d;

    lint ans = 0;

    for (line &l : lines) {
        if (calCCW(origin, last, l.d) != 0) {
            ans = max(ans, tree[1].ans);
        }

        swap(l.s->idx, l.e->idx);
        swap(pointIdx[l.s->idx], pointIdx[l.e->idx]);

        assert(abs(l.s->idx - l.e->idx) == 1);

        update(1, 0, N-1, l.s->idx);
        update(1, 0, N-1, l.e->idx);

        last = l.d;
    }

    ans = max(ans, tree[1].ans);
    cout << ans;
}