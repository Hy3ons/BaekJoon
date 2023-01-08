#include <bits/stdc++.h>
#define MAX 2020
using namespace std;

typedef long long ll;

struct point {
    ll x, y;
    int myIdx;
    point(ll x, ll y) : x(x), y(y) {}

    void rotate90 () {
        swap(x, y);
        y *= -1;
    }
};

point* origin;

bool cmp (point* p1, point* p2) {
    if (p1->x == p2->x) return p1->y < p2->y;
    return p1->x < p2->x;
}

struct Line {
    point *p1, *p2, *dir;
    int q;
    Line (point* p1, point* p2, int q) : p1(p1), p2(p2), q(q) {
        dir = new point(p1->x-p2->x, p1->y - p2->y);

        if (q == 1) dir->rotate90();

        if (dir->x < 0 || (dir->x == 0 && dir->y < 0)) {
            dir->x *= -1;
            dir->y *= -1;
        }
    }

};
ll cal (point* a, point* b, point* c) {
    return (c->x - a->x) * (b->y - a->y) - (b->x - a->x) * (c->y - a->y);
}

double getDist (point* p1, point* p2) {
    ll x = p1->x - p2->x;
    ll y = p1->y - p2->y;
    return sqrt(x*x + y*y);
}

double getDist (Line& line, point*& p1) {
    ll res = abs(cal(p1, line.p1, line.p2));
    double dist = getDist(line.p1, line.p2);
    return res / dist;
}

bool cmp2 (Line& l1, Line& l2) {
    ll c = cal(origin, l1.dir, l2.dir);
    if (c == 0) {
        if (l1.p1 == l2.p1) {
            return cmp(l1.p2, l2.p2);
        } return cmp(l1.p1, l2.p1);
    } return c < 0;
}


point* pointIdx[5000];
vector<Line> lines, queries;
vector<point*> points;
int N;

double answer;

int main () {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N;
    origin = new point(0, 0);

    ll x, y;
    for (int i=0;i<N;i++) {
        cin >> x >> y;
        points.push_back(new point(x, y));
    }

    sort(begin(points), end(points), cmp);

    for (int i=0;i<N;i++) {
        points[i]->myIdx = i;
        pointIdx[i] = points[i];
    }

    for (int i=0;i<N;i++) {
        for (int j=i+1;j<N;j++) {
            lines.push_back(Line(points[i], points[j], -1));
        }
    }

    sort(begin(lines), end(lines), cmp2);

    bool flag = true;

    const int K = lines.size();

    for (int i=0;i<K;i++) {
        if (lines[i].dir->x > 0 && lines[i].dir->y > 0) {
            int temp = i;
            for (;i<K;i++) {
                queries.push_back(Line(lines[i].p1, lines[i].p2, 1));
            }

            for (i=0;i<temp;i++) {
                queries.push_back(Line(lines[i].p1, lines[i].p2, 1));
            }

            flag = false;
            break;
        }
    }

    if (flag) {
        for (int i=0;i<K;i++) {
            queries.push_back(Line(lines[i].p1, lines[i].p2, 1));
        }
    }


    int idx = 0;

    for (Line& query : queries) {
        for (;idx<lines.size() && cal(origin, lines[idx].dir, query.dir) <= 0; idx++) {
            Line& line = lines[idx];

            swap(line.p1->myIdx, line.p2->myIdx);
            swap(pointIdx[line.p1->myIdx], pointIdx[line.p2->myIdx]);

            int l = min(line.p1->myIdx, line.p2->myIdx);
            int r = max(line.p1->myIdx, line.p2->myIdx);

            if (l != 0) {
                answer = max(answer, getDist(line, pointIdx[l-1]));
            }

            if (r != N-1) {
                answer = max(answer, getDist(line, pointIdx[r+1]));
            }
        }

        if (abs(query.p1->myIdx - query.p2->myIdx) == 1) {
            answer = max(answer, getDist(query.p1, query.p2));
        }
    }

        for (;idx<K; idx++) {
            Line& line = lines[idx];

            swap(line.p1->myIdx, line.p2->myIdx);
            swap(pointIdx[line.p1->myIdx], pointIdx[line.p2->myIdx]);

            int l = min(line.p1->myIdx, line.p2->myIdx);
            int r = max(line.p1->myIdx, line.p2->myIdx);

            if (l != 0) {
                answer = max(answer, getDist(line, pointIdx[l-1]));
            }

            if (r != N-1) {
                answer = max(answer, getDist(line, pointIdx[r+1]));
            }
        }

    cout << fixed << setprecision(11) << answer / 2.0 << '\n';
}