#include<bits/stdc++.h>
#define MAX 10101
#define x first
#define y second

using namespace std;

typedef long long lint;
typedef pair<lint, lint> point;

int N;
vector<point> arr;

point origin(0, 0);

lint calCCW (point a, point b, point c) {
    return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
}

int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N;

    int a, b;
    for (int i=0;i<N;i++) {
        cin >> a >> b;
        arr.emplace_back(a, b);
    }


    lint ans = 0;

    for (int i=1;i+1<N;i++) {
        ans += calCCW(arr[0], arr[i], arr[i+1]);
    }

    ans = abs(ans);
    int last = ans & 1 ? 5 : 0;
    cout << (ans >> 1) << "." << last;
}

