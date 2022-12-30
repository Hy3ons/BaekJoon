#include <bits/stdc++.h>
#define MAX 50505

using namespace std;

typedef long long lint;
int N, K;
lint arr[MAX], prefix[MAX], dp[MAX], re[MAX];

lint cost (int l, int r) {
    return (prefix[r] - prefix[l]) * (r - l);
}

lint func (int j, int x) {
    return dp[j] + cost(j, x);
}

struct cross {
    int l, r, c;

    cross (int i) : l(i), r(i), c(0) {}

    cross (int l, int r) : l(l), r(r) {
        int left = r, right = N;

        while(true) {
            int mid = left + right >> 1;

            if (func(l, mid) >= func(r, mid)) {
                right = mid;
            } else {
                left = mid;
            }

            if (right - left == 1) break;
        }

        c = right;
    }
};

deque<cross> deck;

void push (int i) {
    while(!deck.empty()) {
        cross temp(deck.back().r, i);

        if (deck.back().c < temp.c) {
            deck.push_back(temp);
            return;
        } else {
            deck.pop_back();
        }
    }

    deck.push_back(cross(i));
}

void optimized (lint X) {
    for (int i=0;i<N;i++) {
        re[i] = 1;
        dp[i] = prefix[i] * (i+1) + X;
    }
    deck.clear();
    deck.push_back(cross(0));

    for (int i=1;i<N;i++) {
        // dp[i] = min j < i (dp[j] + (prefix[i] - prefix[j]) * (i - j)) + X
        // dp[i] = min j < i (dp[j] + prefix[j] * j - j * prefix[i] - i * prefix[j]) prefix[i] * i + X

        while(deck.front().c <= i) {
            cross temp = deck.front();
            deck.pop_front();

            if (deck.empty() || deck.front().c > i) {
                deck.push_front(temp);
                break;
            }
        }

        lint temp = func(deck.front().r, i);

        if (temp + X < dp[i]) {
            dp[i] = temp + X;
            re[i] = re[deck.front().r] + 1;
        }

        if (i == N-1) return;
        push(i);
    }
}

int main() {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N >> K;

    for (int i=0;i<N;i++) {
        cin >> arr[i];
        prefix[i] = (i == 0 ? 0 : prefix[i-1]) + arr[i];
    }

    lint left =-1, right = 1e15;

    while(true) {
        lint X = left + right >> 1;

        optimized(X);

        if (re[N-1] <= K) {
            right = X;
        } else {
            left = X;
        }

        if (right - left == 1) break;
    }

    optimized(right);
    cout << dp[N-1] - K * right;
}