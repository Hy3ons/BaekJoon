#include<bits/stdc++.h>
#define MAX 505050
#define x first
#define y second
#define INF 1e18

using namespace std;

using lint = long long;
using point = pair<lint, lint>;

int N, M;
lint ans;
vector<point> l, r;

lint cal (point left, point right) {
    lint x = right.x - left.x;
    lint y = right.y - left.y;

    if (x <= 0 || y <= 0) return -abs(x * y);
    return x * y;
}

bool leftCmp (point o1, point o2) {
    if (o1.x == o2.x) return o1.y < o2.y;
    return o1.x < o2.x;
}

bool rightCmp (point o1, point o2) {
    if (o1.x == o2.x) return o1.y > o2.y;
    return o1.x > o2.x;
}

void dnc (int al, int ar, int bl, int br) {
    if (al == ar) {
        for (int i=bl;i<=br;i++) {
            ans = max(ans, cal(l[al], r[i]));
        }
        return;
    }

    lint mx = -INF;
    int idx = -1;
    int mid = al + ar >> 1;
    bool up = false, down = false;

    for (int i=br;i>=bl;i--) {
        lint temp = cal(l[mid], r[i]);

        if (mx < temp) {
            mx = temp;
            idx = i;
        }
    }

    ans = max(ans, mx);

    if (idx == -1) {
        if (up) dnc(al, mid, bl, br);
        else dnc(mid+1, ar, bl, br);

    } else {
        dnc(al, mid, bl, idx);
        dnc(mid+1, ar, idx, br);
    }
}



int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N >> M;
    int x, y;
    vector<point> temp;
    for (int i=0;i<N;i++) {
        cin >> x >> y;
        temp.emplace_back(x, y);
    }

    sort(begin(temp), end(temp), leftCmp);
    lint mx = INF;

    for (point p : temp) {
        if (mx > p.y) {
            mx = p.y;
            l.push_back(p);
        }
    }

    temp.clear();

    for (int i=0;i<M;i++) {
        cin >> x >> y;
        temp.emplace_back(x, y);
    }

    mx = -1;

    sort(begin(temp), end(temp), rightCmp);

    for (point p : temp) {
        if (mx < p.y) {
            mx = p.y;
            r.push_back(p);
        }
    }

    N = l.size();
    M = r.size();
    sort(r.begin(), r.end(), leftCmp);
    
    dnc(0, N-1, 0, M-1);

    cout << ans;
}