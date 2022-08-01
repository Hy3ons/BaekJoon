#include <bits/stdc++.h>

using namespace std;

struct Line {
    long long left, right;
};
int N;
bool cmp (Line o1, Line o2) {
    if (o1.left == o2.left) {
        return o1.right > o2.right;
    } else {
        return o1.left > o2.left;
    }
}

int main() {
    cin >> N;

    Line arr[N];
    for (int i=0;i<N;i++) {
        cin >> arr[i].left >> arr[i].right;
    }
    sort(arr, arr+N, cmp);

    int cnt = 0;
    long long left = -1, right = -1;
    for (int i=N-1;i>=0;i--) {
        if (right <= arr[i].left) {
            left = arr[i].left;
            right = arr[i].right;
            cnt++;
        } else {
            right = min(right, arr[i].right);
        }
    }
    cout << cnt;
}