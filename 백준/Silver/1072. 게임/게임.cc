#include<bits/stdc++.h>
 
using namespace std;

typedef long long lint;

lint X, Y;

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);

    cin >> X >> Y;
    lint Z = (Y * 100) / X;

    lint left = -1, right = 1e16;

    while(right - left != 1) {
        lint mid = left + right >> 1;

        lint temp = ((Y + mid) * 100) / (X + mid);

        if (temp != Z) {
            right = mid;
        }else {
            left = mid;
        }
    }

    lint answer = right == 1e16 ? -1 : right;
    cout << answer;
}