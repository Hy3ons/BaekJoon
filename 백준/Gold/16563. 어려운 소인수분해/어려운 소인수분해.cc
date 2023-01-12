#include<bits/stdc++.h>
#define MAX 5050505
using namespace std;

int N, K, eratos[MAX];

vector<int> primes;

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin >> N;

    for (long long i=2;i<MAX;i++) {
        if (!eratos[i]) {
            primes.push_back(i);

            for (long long j=i*i;j<MAX;j+=i) {
                eratos[j] = 1;
            }
        }
    }

    for (int i=0;i<N;i++) {
        cin >> K;

        for (int j : primes) {
            if (j > K) break;
            while(K % j == 0) {
                K /= j;
                cout << j << ' ';
            }

            if (!eratos[K]) break;
        }

        if (K != 1) cout << K;
        cout << '\n';
    }


}