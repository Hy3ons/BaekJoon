#include<bits/stdc++.h>
#define MAX 10101
#define x first
#define y second

using namespace std;

typedef long long lint;
typedef pair<lint, lint> point;

int N;
int h[51];
vector<point> arr;

lint calCCW (point a, point b, point c) {
    return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
}

int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N;

    for (int i=0;i<N;i++) {
        cin >> h[i];
        arr.emplace_back(i, h[i]);
    }

    int ans = 0;

    for (int i=0;i<N;i++) {
        int temp = 0;

        for (int j=0;j<N;j++) if (i != j) {
            bool flag = true;

            for (int k=0;k<N;k++) if (min(i, j) < k && k < max(i, j)) {
                if (calCCW(arr[min(i, j)], arr[max(i, j)], arr[k]) <= 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) temp++;
        }

        ans = max(ans, temp);
    }

    cout << ans;
}

