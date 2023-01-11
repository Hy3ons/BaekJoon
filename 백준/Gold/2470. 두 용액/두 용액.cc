#include<bits/stdc++.h>
#define MAX 9000001

using namespace std;

vector<int> arr;
int N, k, l, r;
long long ans= 1e19;

void answer (int ls, int rs) {
    if (ans > abs(arr[ls] + arr[rs])) {
        ans = abs(arr[ls] + arr[rs]);
        l = min(ls, rs);
        r = max(ls, rs);
    }
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N;

    for (int i=0;i<N;i++) {
        cin >> k;
        arr.push_back(k);
    }
    
    sort(begin(arr), end(arr));

    for (int i=0;i<N;i++) {

        int left = arr[i] >= 0 ? -1 : i, right = arr[i] >= 0 ? i : N;
        int ol = left, orr = right;

        while(right - left != 1) {
            int mid = left + right >> 1;

            if (-arr[i] <= arr[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (right != orr) answer(i, right);
        if (left != ol) answer(i, left);
    }

    cout << arr[l] << ' ' << arr[r] << '\n';
}