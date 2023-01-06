#include<bits/stdc++.h>
#define MAX 1010
#define x first
#define y second
using namespace std;

int N, a, b;

typedef pair<int, int> pp;
int checked[MAX];

bool cmp (pp o1, pp o2) {
    if (o1.y == o2.y) return o1.x < o2.x;
    return o1.y < o2.y;
}

void solve (int t) {
    memset(checked, 0, sizeof(checked));
    vector<pp> arr;
    int a, b, ans = 0;

    for (int i=0;i<N;i++) {
        cin >> a >> b;
        arr.emplace_back(a << 1, b << 1);
    }

    for (int i=16;i<48;i++) {
        int temp = 123123;
        int idx = -1;

        for (int j=0;j<N;j++) {
            if (checked[j]) continue;

            if (arr[j].x <= i && i < arr[j].y) {
                if (temp > arr[j].y) {
                    temp = arr[j].y;
                    idx = j;
                }

            }

        }

        if (idx != -1) {
            checked[idx] = 1;
            ans++;
        }
    }

    cout << "On day "<< t <<" Emma can attend as many as "<< ans <<" parties." << '\n';
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    for(int i=1;;i++) {
        cin >> N;
        if (N == 0) break;
        solve(i);
    }
}