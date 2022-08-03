#include <bits/stdc++.h>
#define MAX 100000
using namespace std;

int N, K, conquer;
int arr[MAX];

deque<int> store[MAX+1];

struct Query {
    int left, right, answer, sqrt, idx;
};

struct Segment {
    int start = 131072;
    int tree[272164] = {0};

    void clear () {
        for (int i=0;i<272164;i++) {
            tree[i] = 0;
        }
    }

    void update (int idx, int value) {
        int node = idx + start -1;
        tree[node] += value;
        node >>= 1;
        while(node != 0) {
            tree[node] = tree[node*2] | tree[node*2+1];
            node >>= 1;
        }
    }

    int find () {
        return query(1, 1, start);
    }

    int query (int node, int left, int right) {
        if (node >= start) return right;

        int mid = left + right >> 1;

        if (tree[node*2+1] != 0) return query(node*2+1, mid+1, right);
        else if (tree[node*2] != 0) return query(node*2, left, mid);

        return 0;
    }
};

void deck_clear () {
    for (int i=0;i<=MAX;i++) {
        store[i].clear();
    }
}

bool cmp (Query o1, Query o2) {
    if (o1.sqrt == o2.sqrt) return o1.right < o2.right;
    return o1.sqrt < o2.sqrt;
}

int main() {
    cin.tie(0);
    ios::sync_with_stdio(0);

    cin >> N >> K;

    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    int queries;
    cin >> queries;
    conquer = 400;

    Query quers[queries];
    Segment tree;
    int result[queries];

    for (int i=0;i<queries;i++) {
        cin >> quers[i].left >> quers[i].right;
        quers[i].sqrt = quers[i].left / conquer;
        quers[i].idx = i;
    }

    sort(quers, quers + queries, cmp);
    int left = -1, right = -1, sqrt = -1;
    for (int i=0;i<queries;i++) {
        if (sqrt != quers[i].sqrt) {
            sqrt = quers[i].sqrt;
            tree.clear();
            deck_clear();

            left = quers[i].left;
            right = quers[i].right;

            for (int j=left-1;j<right;j++) {
                if (store[arr[j]].size() > 1) {
                    int temp = store[arr[j]].back() - store[arr[j]].front();
                    tree.update(temp, -1);
                }

                store[arr[j]].push_back(j);
                int temp = store[arr[j]].back() - store[arr[j]].front();
                if (temp != 0) tree.update(temp, 1);
            }
            result[quers[i].idx] = tree.find();
        } else {
            for (int j= right;j<quers[i].right;j++) {
                if (store[arr[j]].size() > 1) {
                    int temp = store[arr[j]].back() - store[arr[j]].front();
                    tree.update(temp, -1);
                }

                store[arr[j]].push_back(j);
                int temp = store[arr[j]].back() - store[arr[j]].front();
                if (temp != 0) tree.update(temp, 1);
            }

            if (left < quers[i].left) {
                for (int j=left-1;j<quers[i].left-1;j++) {
                    if (store[arr[j]].size() > 1) {
                        int temp = store[arr[j]].back() - store[arr[j]].front();
                        tree.update(temp, -1);
                    }

                    store[arr[j]].pop_front();

                    if (store[arr[j]].size() > 1) {
                        int temp = store[arr[j]].back() - store[arr[j]].front();
                        tree.update(temp, 1);
                    }
                }

            } else if (left > quers[i].left) {
                for (int j=left-2;j>=quers[i].left-1;j--) {
                    if (store[arr[j]].size() > 1) {
                        int temp = store[arr[j]].back() - store[arr[j]].front();
                        tree.update(temp, -1);
                    }

                    store[arr[j]].push_front(j);
                    if (store[arr[j]].size() > 1) {
                        int temp = store[arr[j]].back() - store[arr[j]].front();
                        tree.update(temp, 1);
                    }
                }
            }
            result[quers[i].idx] = tree.find();
            left = quers[i].left;
            right = quers[i].right;
        }
    }
    for (int i=0;i<queries;i++) {
        cout << result[i] << '\n';
    }
    return 0;
}