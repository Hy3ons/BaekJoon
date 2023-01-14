#include<bits/stdc++.h>
#define MAX 50

using namespace std;

int N, k, arr[50], ans;
string s;

void func() {
    for (int i=0;i<6;i++) {
        if (s[i] == 'Y') {
            ans = max(ans, arr[i+1] * (i+1));
        }
    }

    if (s[6] == 'Y') {
        for (int i=0;i<6;i++) {
            if (arr[i+1] < 4) continue;
            ans = max(ans, 4 * (i+1));
        }
    }

    if (s[7] == 'Y') {
        bool flag1 = false, flag2 = false;
        int temp = 0;

        for (int i=1;i<=6;i++) {
            if (arr[i] == 2) flag1 = true;
            if (arr[i] == 3) flag2 = true;

            temp += arr[i] * i;
        }

        
        if(flag1 && flag2) {
            ans = max(ans, temp);
        }
    }

    if (s[8] == 'Y') {
        bool flag = true;
        for (int i=1;i<=5;i++) {
            if (!arr[i]) flag = false;
        }

        if (flag) {
            ans = max(ans, 30);
        }
    }

    if (s[9] == 'Y') {
        bool flag = true;
        for (int i=2;i<=6;i++) {
            if (!arr[i]) flag = false;
        }

        if (flag) {
            ans = max(ans, 30);
        }
    }

    if (s[10] == 'Y') {
        for (int i=1;i<=6;i++) {
            if (arr[i] == 5) {
                ans = max(ans, 50);
            }
        }
    }

    if (s[11] == 'Y') {
        int temp = 0;
        for (int i=1;i<=6;i++) {
            temp += arr[i] * i;
        }
        ans = max(ans, temp);
    }
}


int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> s;
    for (int i=0;i<3;i++) {
        cin >> k;
        arr[k]++;
    }

    for (int i=1;i<=6;i++) {
        for (int j=1;j<=6;j++) {
            arr[i]++;arr[j]++;

            func();

            arr[i]--;arr[j]--;
        }
    }

    cout << ans;
}

