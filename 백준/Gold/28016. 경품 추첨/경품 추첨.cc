#include<bits/stdc++.h>

using namespace std;

int N, M;
int arr[202][202];
vector<int> prec[202][202];

vector<int> make_half (vector<int> v) {
    v.insert(v.begin(), 0);
    return v;
}

void adding (vector<int> &v, vector<int> b) {
    int mx = max(v.size(), b.size());

    v.resize(mx);
    b.resize(mx);

    for (int i=mx-1;i>=0;i--) {
        v[i] += b[i];

        if (v[i] == 2) {
            v[i-1]++;
            v[i] = 0;
        } else if (v[i] == 3) {
            v[i] = 1;
            v[i-1]++;
        }
    }
}

bool lq (vector<int> &v, vector<int> &g) {
    int mx = max(v.size(), g.size());
    v.resize(mx);
    g.resize(mx);

    for (int i=0;i<mx;i++) {
        if (v[i] > g[i]) return false;
        else if (v[i] < g[i]) return true;
    }

    return false;
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N >> M;

    for (int i=0;i<N;i++) {
        for (int j=0;j<M;j++) {
            cin >> arr[i][j];
            if (arr[i][j] == 2) prec[i][j].push_back(1);
        }
    }

    for (int i=0;i<N-1;i++) {
        for (int j=0;j<M;j++) {
            if (!arr[i+1][j]) {
                adding(prec[i+1][j], prec[i][j]);
                continue;
            }

            auto half = make_half(prec[i][j]);

            if (!arr[i][j-1] && !arr[i+1][j-1]) {
                adding(prec[i+1][j-1], half);
            }

            if (!arr[i][j+1] && !arr[i+1][j+1]) {
                adding(prec[i+1][j+1], half);
            }
        }
    }

    int ans = -1;
    vector<int> mx;

    for (int i=0;i<M;i++) {
        if (lq(mx, prec[N-1][i])) {
            mx = prec[N-1][i];
            ans = i;
        }
    }

    cout << ans;




}