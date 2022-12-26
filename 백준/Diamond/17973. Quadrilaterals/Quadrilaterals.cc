#include <bits/stdc++.h>
#define MAX 1010
using namespace std;

typedef long long ll;

struct point {
    ll x, y;
    int myIdx;
    point(ll x, ll y) : x(x), y(y) {}
};

point* origin;

bool cmp (point* p1, point* p2) {
    if (p1->x == p2->x) return p1->y > p2->y;
    return p1->x > p2->x;
}

struct Line {
    point *p1, *p2, *dir;
    Line (point* p1, point* p2) : p1(p1), p2(p2) {
        dir = new point(p1->x-p2->x, p1->y - p2->y);

        if (dir->x < 0 || (dir->x == 0 && dir->y < 0)) {
            dir->x *= -1;
            dir->y *= -1;
        }
    }
};
ll cal (point* a, point* b, point* c) {
    return (c->x - a->x) * (b->y - a->y) - (b->x - a->x) * (c->y - a->y);
}

bool cmp2 (Line*& l1, Line*& l2) {
    ll c = cal(origin, l1->dir, l2->dir);
    if (c == 0) {
        if (l1->p1 == l2->p1) {
            return cmp(l1->p2, l2->p2);
        } return cmp(l1->p1, l2->p1);
    } return c < 0;
}


point* pointIdx[MAX];
vector<Line*> lines;
vector<point*> points;
int N;
ll ans;

int dl[4] = {-1,-1,-2,-2}, dr[4] = {1,2,1,2};

typedef unsigned long long ull;

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
            lines.push_back(new Line(points[i], points[j]));
        }
    }

    sort(begin(lines), end(lines), cmp2);
    ull cnt = 0, mx = 1e19;

    for (Line *line : lines) {
        swap(line->p1->myIdx, line->p2->myIdx);
        swap(pointIdx[line->p1->myIdx], pointIdx[line->p2->myIdx]);

        int left = min(line->p1->myIdx, line->p2->myIdx);
        int right = max(line->p1->myIdx, line->p2->myIdx);

        ans += (N-right-1) * left;
        //볼록다각형의 경우 카운팅이 2번 되는 문제가 존재한다.

        for (int i=0;i<4;i++) {
            int l = left + dl[i];
            int r = right + dr[i];

            if (0 <= l && r < N) {
                ull a = abs(cal(pointIdx[r], line->p1, line->p2));
                ull b = abs(cal(pointIdx[l], line->p1, line->p2));
                ull c = abs(cal(line->p1, pointIdx[l], pointIdx[r]));
                ull d = abs(cal(line->p2, pointIdx[l], pointIdx[r]));


                if (mx > a + b) {
                    mx = a + b;
                    cnt = (a+b == c+d) ? 1 : 2;
                } else if (mx == a + b) {
                    cnt += (a+b == c+d) ? 1 : 2;
                }
            }    
        }
    }
    ans += cnt;
    cout << ans << '\n';
}