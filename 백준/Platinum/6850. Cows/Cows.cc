#include<bits/stdc++.h>
#define MAX 10101
#define x first
#define y second

using namespace std;

typedef long long lint;
typedef pair<lint, lint> pt;

pt pts[MAX];
pt origin;

stack<pt> st;

int N, a, b;

lint calCCW (pt a, pt b, pt c) {
    return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
}

bool cmp (pt a, pt b) {
    lint res = calCCW(origin, a, b);
    return res < 0;
}


int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N;

    if (N <= 2) {
        cout << 0;
        return 0;
    }

    for (int i=0;i<N;i++) {
        cin >> a >> b;
        pts[i] = {a, b};
    }

    sort(pts, pts+N);

    origin = pts[0];

    sort(pts+1, pts+N, cmp);

    st.push(origin);
    st.push(pts[0]);

    for (int i=1;i<N;i++) {
        while(st.size() >= 2) {
            pt b = st.top(); st.pop();
            pt a = st.top();

            if (calCCW(a, b, pts[i]) < 0) {
                st.push(b);
                break;
            }
        }

        st.push(pts[i]);
    }

    vector<pt> result;

    while(!st.empty()) {
        result.push_back(st.top());st.pop();
    }

    lint res = 0;

    for (int i=1;i+1<result.size();i++) {
        res += calCCW(result[0], result[i], result[i+1]);
    }

    res = abs(res);

    cout << res / 100;
}
