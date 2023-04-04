#include<bits/stdc++.h>
#define MAX 202020
#define MAX_T 808080

using namespace std;

struct seg
{
    long long tree[MAX_T];

    void update (int node, int s, int e, int left, int right, int value) {
        if (right < s || e < left) return;

        if (s<= left && right <= e) {
            tree[node] += value;
            return;
        }

        int mid = left + right >> 1;

        update(node << 1, s, e, left, mid, value);
        update(node << 1 | 1, s, e, mid+1, right, value);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    long long query (int node, int s, int e, int left, int right) {
        if (right < s || e < left) return 0;

        if (s <= left && right <= e) return tree[node];

        int mid = left + right >> 1;

        return query(node << 1, s, e, left, mid) + query(node << 1 | 1, s, e, mid+1, right);
    }

    void clear () {
        fill(tree, tree+MAX_T, 0);
    }
};

seg cnt, value;

long long answer[MAX], temp;

struct query
{
    int left, right, idx, sq;

    query(int left, int right, int idx) : left(left), right(right), idx(idx) {
        sq = left / 400;
    }
};

bool cmp (query &o1, query &o2) {
    if (o1.sq != o2.sq) return o1.sq < o2.sq;
    return o1.right < o2.right;
}

vector<query> queries;
int N, Q, arr[MAX];

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);

    cin >> N >> Q;

    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    int l, r, sqq(-1);

    for (int i=0;i<Q;i++) {
        cin >> l >> r;
        queries.push_back(query(l, r, i));
    }

    sort(begin(queries), end(queries), cmp);

    for (query q : queries) {
        if (q.sq != sqq) {
            temp = 0;
            cnt.clear(), value.clear();

            for (int i=q.left-1;i<q.right;i++) {
                temp += cnt.query(1, 1, arr[i], 1, N) * arr[i];
                temp -= value.query(1, 1, arr[i], 1, N);
                temp -= cnt.query(1, arr[i], N, 1, N) * arr[i];
                temp += value.query(1, arr[i], N, 1, N);

                cnt.update(1, arr[i], arr[i], 1, N, 1);
                value.update(1, arr[i], arr[i], 1, N, arr[i]);
            }
        } else {
            for (int i = r; i < q.right;i++) {
                temp += cnt.query(1, 1, arr[i], 1, N) * arr[i];
                temp -= value.query(1, 1, arr[i], 1, N);
                temp -= cnt.query(1, arr[i], N, 1, N) * arr[i];
                temp += value.query(1, arr[i], N, 1, N);

                cnt.update(1, arr[i], arr[i], 1, N, 1);
                value.update(1, arr[i], arr[i], 1, N, arr[i]);
            }

            if (l < q.left) {
                for (int i=l-1;i<q.left-1;i++) {
                    cnt.update(1, arr[i], arr[i], 1, N, -1);
                    value.update(1, arr[i], arr[i], 1, N, -arr[i]);

                    temp -= cnt.query(1, 1, arr[i], 1, N) * arr[i];
                    temp += value.query(1, 1, arr[i], 1, N);
                    temp += cnt.query(1, arr[i], N, 1, N) * arr[i];
                    temp -= value.query(1, arr[i], N, 1, N);
                }
            } else {
                for (int i=l-2;i>=q.left-1;i--) {
                    temp += cnt.query(1, 1, arr[i], 1, N) * arr[i];
                    temp -= value.query(1, 1, arr[i], 1, N);
                    temp -= cnt.query(1, arr[i], N, 1, N) * arr[i];
                    temp += value.query(1, arr[i], N, 1, N);

                    cnt.update(1, arr[i], arr[i], 1, N, 1);
                    value.update(1, arr[i], arr[i], 1, N, arr[i]);
                }
            }

        }

        l = q.left;r = q.right;sqq = q.sq;
        answer[q.idx] = temp;
    }


    for (int i=0;i<Q;i++) {
        cout << answer[i] << '\n';
    }
    return 0;
}