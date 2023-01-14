#include<bits/stdc++.h>
using namespace std;

int N, arr[101010], ans;

void solve () {
    int temp = 0;

    for (int i=0;i<N;i++) {

        temp = max(arr[i], arr[i] + temp);
        ans = max(temp, ans);
    }
}


int main () {
    cin >> N;
    for (int i=0;i<N;i++) {
        cin >> arr[i];
        if (arr[i] == 2) arr[i] = -1;
    }

    solve();
    for (int &e : arr) e=-e;
    solve();

    cout << ans;


}