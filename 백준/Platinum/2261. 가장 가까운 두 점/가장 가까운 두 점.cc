#include<bits/stdc++.h>
#define MAX 101010
#define x first
#define y second
#define INF 1e15


using namespace std;

typedef pair<int, int> pp;
typedef long long lint;

int N;
vector<pp> arr;

lint dist (pp o1, pp o2) {
    lint dx = o1.x - o2.x;
    lint dy = o1.y - o2.y;

    return dx * dx + dy * dy;
}

lint dnc (int left, int right, vector<pp> &arr) {
    if (left + 1 == right) {
        return dist(arr[left], arr[right]);
    } else if (left == right) {
        return INF;
    }

    int mid = left + right >> 1;

    lint res = min(dnc(left, mid, arr), dnc(mid+1, right, arr));
    lint midX = (arr[mid].x + arr[mid+1].x) >> 1;

    vector<pp> poo;

    for (int i=left;i<=right;i++) {
        lint dx = abs(midX - arr[i].x);
        if (dx * dx * 2 < res) poo.push_back(arr[i]);
    }

    for (pp &p : poo) swap(p.x, p.y);
    sort(begin(poo), end(poo));

    for (int i=0;i<poo.size();i++) {
        for (int j=i+1;j<poo.size();j++) {
            if (dist(poo[i], poo[j]) > res) break;
            res = min(res, dist(poo[i], poo[j]));
        }
    }

    return res;
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N;

    int a, b;

    for (int i=0;i<N;i++) {
        cin >> a >> b;
        arr.emplace_back(a, b);
    }

    sort(begin(arr), end(arr));
    cout << dnc(0, N-1, arr);
}
