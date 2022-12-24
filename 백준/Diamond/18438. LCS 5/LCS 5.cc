#include<bits/stdc++.h>
#define MAX 7070

using namespace std;

string a, b;
vector<char> ans;
int dp[MAX], dp2[MAX], bas[MAX], ch[MAX];

void dnc (int al, int ar, int bl, int br) {

    if (al == ar) {
        if (bl > br || bl < 0) return;

        for (int i=bl;i<=br;i++) if (!ch[i] && b[i] == a[al]) {
            ch[i] = 1;
            ans.push_back(b[i]);
            return;
        }

        return;
    }


    for (int i=bl;i<=br+4;i++) {
        dp[i] = dp2[i] = bas[i] = 0;
    }

    int mid = al + ar >> 1, k = -1, mx = -1;

    for (int i=al;i<=mid;i++) {
        for (int j=bl;j<=br;j++) {
            if (a[i] == b[j]) {
                dp[j+1] = max(bas[j]+1, dp[j]);
            } else {
                dp[j+1] = max(dp[j], bas[j+1]);
            }
        }

        if (i != mid) swap(dp, bas);
    }

    for (int i=bl;i<=br+4;i++) bas[i] = 0;

    for (int i=ar;i>mid;i--) {
        for (int j=br;j>=bl;j--) {
            if (a[i] == b[j]) {
                dp2[j+1] = max(bas[j+2] +1 , dp2[j+2]);
            } else {
                dp2[j+1] = max(dp2[j+2], bas[j+1]);
            }
        }

        if (i != mid+1) swap(dp2, bas);
    }

    for (int i=bl;i<=br+1;i++) {
        if (mx < dp[i] + dp2[i+1]) {
            mx = dp[i] + dp2[i+1];
            k = i;
        }
    }

    dnc(al, mid, bl, k);
    dnc(mid+1, ar, k, br);
}

int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> a >> b;

    swap(a, b);

    dnc(0, a.size()-1, 0, b.size()-1);
    cout << ans.size() << '\n';
    for (char s : ans) cout << s;
    cout << '\n';
}