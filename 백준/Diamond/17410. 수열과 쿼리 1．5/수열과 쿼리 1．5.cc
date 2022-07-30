#include <iostream>
#include <bits/stdc++.h>

#define MAX 100000

using namespace std;

int N, conquer;
int arr[MAX], myBucket[MAX+1];

struct bucket {
    int left, right;
    vector<int> ary;

    void start () {
        for (int i=left-1;i<right;i++) {
            ary.push_back(arr[i]);
        }
        //이제 정렬하면 된다. 배우고 ㄱㄱ'

        sort(begin(ary), end(ary));
    }

    void update (int from, int goal) {
        ary[lower_bound(begin(ary), end(ary), from) - begin(ary)] = goal;
        sort(begin(ary), end(ary));
    }

    int find (int value) {
        return ary.size() - (upper_bound(begin(ary), end(ary), value) - begin(ary));
    }
};

int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N;
    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    int queries;
    cin >> queries;

    conquer = int(sqrt(N)) * 2;
    int L = 1;

    vector<bucket> buckets;

    while (L <= N) {
        int end = L + conquer - 1;

        if (end > N)
            end = N;

        bucket b;
        b.left = L;
        b.right = end;
        b.start();

        for (int i=L;i<=end;i++) myBucket[i] = int(buckets.size());
        buckets.push_back(b);
        L += conquer;
    }

    int m, left, right, value;
    while(queries-->0) {
        cin >> m;

        if (m == 1) {
            cin >> left >> value;

            buckets[myBucket[left]].update(arr[left-1], value);
            arr[left-1] = value;
        } else {
            cin >> left >> right >> value;

            int start = left / conquer;
            int result = 0;
            bool first = true;

            for (int i=start;i<buckets.size();i++) {
                if (left <= buckets[i].left && buckets[i].right <= right) {
                    if (first) {
                        first = false;

                        for (int j=left-1;j<buckets[i].left-1;j++) {
                            if (arr[j] > value) result++;
                        }
                    }

                    result += buckets[i].find(value);
                } else {
                    if (!first) {
                        for (int j=buckets[i].left-1;j<right;j++) {
                            if (arr[j] > value) result++;
                        }
                        break;
                    }
                }
            }

            if (first) {
                for (int i=left-1;i<right;i++) {
                    if (arr[i] > value) result++;
                }
            }
            cout << result << '\n';
        }
    }

    return 0;
}
