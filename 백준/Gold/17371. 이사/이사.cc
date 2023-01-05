#include <bits/stdc++.h>
#define MAX 1010
using namespace std;

typedef long long lint;

int N;
lint A[MAX], B[MAX];

lint dist (int i, int j) {
    lint x = A[i] - A[j];
    lint y= B[i] - B[j];

    return x*x + y*y;
}

int main () {
    cin.tie(0); ios_base::sync_with_stdio(0);

    cin >> N;

    for (int i=0;i<N;i++) {
        cin >> A[i] >> B[i];
    }

    if (N <= 2) {
        cout << A[0] << ' ' << B[0] << '\n';
        return 0;
    }

    int idx;
    lint mx = 1e18;

    for (int i=0;i<N;i++) {
        lint temp = -1;
        for (int j=0;j<N;j++) {
            if (i == j) continue;


            if (temp < dist(i, j)) {
                temp = dist(i, j);
            }
        }

        if (mx > temp) {
            mx = temp;
            idx = i;
        }
    }

    cout << A[idx] << ' ' << B[idx] << '\n';
}

