#include <bits/stdc++.h>

using namespace std;

int N;

int main() {
    cin >> N;

    int arr[N];
    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    long long result = 0, sum = 0;
    sort(arr, arr+N );
    for (int i=0;i<N;i++) {
        result += sum;
        result += arr[i];
        sum += arr[i];
    }
    cout << result;
}