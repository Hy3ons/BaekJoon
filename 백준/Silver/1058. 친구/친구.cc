#include <bits/stdc++.h>

using namespace std;
int N;
string arr[50];
int checked[50];

int main () {
    cin >> N;

    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    int mx = 0;

    for (int i=0;i<N;i++) {
        int cnt = 0;

        fill(checked, checked+50, 0);

        for (int j=0;j<N;j++) {
            if (arr[i][j] == 'Y') {
                if (!checked[j]) {
                    checked[j] = 1;
                    cnt++;
                }

                for (int k=0;k<N;k++) {
                    if (i != k && j != k && arr[j][k] == 'Y' && !checked[k]) {
                        cnt++;
                        checked[k] = 1;
                    }
                }
                
            }
        }

        mx = max(mx, cnt);
    }

    cout << mx << '\n';
}