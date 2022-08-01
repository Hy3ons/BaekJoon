#include <bits/stdc++.h>

using namespace std;

int N;
long long board;

struct Line {
    long long left, right;

};

bool cmp (Line o1, Line o2) {
    return o1.left < o2.left;
}

int main() {
    cin >> N >> board;

    Line lines[N];
    for (int i=0;i<N;i++) {
        cin >> lines[i].left >> lines[i].right;
    }

    sort(lines, lines + N, cmp);
    long long left = -1, right = -1;

    long long result = 0;

    for (int i=0;i<N;i++) {
        if (right < lines[i].left) {
            result += (right - left) / board;
            left = lines[i].left;
            right = lines[i].right;

            long long dist = right - left;
            if (dist % board != 0) {
                long long temp = (dist / board) * board + board;
                right = left + temp;
            }
        } else {
            if (right >= lines[i].right) continue;
            right = lines[i].right;

            long long dist = right - left;
            if (dist % board != 0) {
                long long temp = (dist / board) * board + board;
                right = left + temp;
            }
        }
    }

    result += (right - left) / board;
    cout << result;
}