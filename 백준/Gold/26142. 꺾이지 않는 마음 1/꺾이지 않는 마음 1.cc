#include<bits/stdc++.h>
#define MAX 10100

using namespace std;
typedef long long ll;

struct dragon {
    ll a, b, now;
    dragon(ll a, ll b) : a(a), b(b), now(b) {}
};

bool cmp (dragon *a, dragon *b) {
    return a->a > b->a;
}


int N;
vector<dragon*> arr;
bool use[MAX];

int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);

    cin >> N;
    ll a, b;

    for (int i=0;i<N;i++) {
        cin >> a>> b;
        arr.push_back(new dragon(a, b));
    }

    ll ans = 0;

    sort(begin(arr), end(arr), cmp);

    for (int i=0;i<N;i++) {
        ll max = -1, idx = -1, sum = 0, temp = ans;
        ll m = i;

        for (int j=0;j<N;j++) {
            if (!use[j]) {
                if (max < temp + m * arr[j]->a + arr[j]->b) {
                    max = temp + m * arr[j]->a + arr[j]->b;
                    idx = j;
                }
            } else {
                temp += arr[j]->a;
                m--;
            }
        }

        ans = max;
        use[idx] = true;

        cout << ans << '\n';
    }


}