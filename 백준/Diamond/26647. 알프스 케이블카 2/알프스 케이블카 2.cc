#include <bits/stdc++.h>
#define MAX 50505

using namespace std;

typedef long long lint;

lint N, K;
lint arr[MAX] = {}, prefix[MAX] = {}, re[MAX], dx[MAX], dp[MAX];

lint cost (int l, int r) {
    lint x = dx[l] - dx[r];
    lint y = arr[l] - arr[r];

    return dp[l] + (x*x + y*y);
}

int calCross (int j, int i) {
    int left = i, right = N;

    while(true) {
		int mid = left + right >> 1;

        if (cost(j, mid) > cost(i, mid)) {
            right = mid;
        } else {
            left =  mid;
        }

        if (right - left == 1) return right;
    }
}

struct cross {
    int l, r, c;

    cross (int i) : l(i), r(i), c(0) {}

    cross (int l, int r) : l(l), r(r) {
        c = calCross(l, r);
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

void optimized (const lint X) {
	dp[0] = re[0] = 0;

    deck.clear();
    deck.push_back(cross(0));

    for (int i=1;i<N;i++) {
        while(deck.front().c <= i) {
            cross temp = deck.front();
            deck.pop_front();

            if (deck.empty() || deck.front().c > i) {
                deck.push_front(temp);
                break;
            }
        }

        lint temp = cost(deck.front().r, i) + X;
        
		dp[i] = temp;
		re[i] = re[deck.front().r] + 1;

        if (i == N-1) return;
        push(i);
    }
}

int main() {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N >> K;

    for (int i=0;i<N;i++) {
        cin >> arr[i];
        prefix[i+1] = prefix[i] + arr[i];
    }

    for (int i=0;i<N;i++) {
        dx[i] = prefix[i+1] * 2 - arr[i] - arr[0];
    }

	lint left = -1, right = 1e17;

    while(true) {
		lint X = left+ right >> 1;

        optimized(X);

        if (re[N-1] <= K) {
            right = X;
        } else {
            left = X;
        }

        if (right - left == 1) break;
    }

    optimized(right);

	lint rightUsed = re[N-1];
 	lint rightAns = dp[N-1] - (re[N-1] * right);

	if (rightUsed == K) {
		cout << rightAns;
	} else {
		optimized(left);

		lint leftUsed = re[N-1];
 		lint leftAns = dp[N-1] - (re[N-1] * left);

		lint dist = abs(leftUsed - rightUsed);
		lint dAns = abs(rightAns - leftAns);

		lint da = dAns / dist;
		lint realAnswer = rightAns - da * abs(K - rightUsed);

		cout << realAnswer;
	}
}
