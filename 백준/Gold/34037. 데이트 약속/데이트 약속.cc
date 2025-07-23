#include<bits/stdc++.h>

using namespace std;

int N, M;

typedef long long lint;

vector<pair<lint, lint>> vec;

int trap[404040];

int main () {
    cin >> N >> M;

    for (int i=0;i<M;i++) {
        int x, y; cin >> x >> y;
        trap[x] = y;
    }

    vector<pair<lint, lint>> vec;
    vec.push_back({0, 1});

    for (int i=1;i<=N;i++) {
        if (trap[i]) {
            lint mx = 0;
            for (auto &[a, b] : vec) mx = max(mx, a);

            for (auto &[a, b] : vec) {
                a += b - trap[i]; b++;
            }

            vec.push_back({mx, 1});
        } else {
            for (auto &[a, b] : vec) {
                a += b; b++;
            }
        }
    }
    lint mx = 0;
    for (auto &[a, b] : vec) mx = max(mx, a);
    cout << mx;





}

