#include <bits/stdc++.h>
#define MAX 101010
using namespace std;

int N;
vector<int> arr;

int main () {
    cin >> N;

    long long sum = 0;
    int a;
    for (int i=0;i<N;i++) {
        cin >> a;
        arr.push_back(a);
        sum += a;
    }

    sort(begin(arr), end(arr));

    double all = (double) sum / N;



    cout << fixed << setprecision(9);

    if (N == 1) {
        cout << arr[0];
    } else if (N == 2) {
        cout << all;
    } else {
        cout << max((double)arr[N-2], all);
    }


    

}