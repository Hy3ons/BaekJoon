#include<bits/stdc++.h>
#define MAX 100001
#define MAX_T 404040

using namespace std;
typedef long long ll;

struct Query {
    ll time, root, value;
};

vector<int> load[MAX];

using namespace std;

int N, K;
ll G;

ll tree[MAX_T];
int start = 1;

void update (int node, int s, int e, int left, int right, int value) {
    if (e < left || right < s) return;

    if (s <= left && right <= e) {
        tree[node] += value;
        return;
    }

    int mid = left + right >> 1;
    update(node << 1, s, e, left, mid, value);
    update(node << 1 | 1, s, e, mid+1, right, value);
}

ll getValue (int order) {
    ll res = 0;

    for (int i=start+order-1;i!=0;i>>=1) {
        res += tree[i];
    }

    return res;
}

void treeClear () {
    for (ll &i: tree) i = 0;
}

vector<int> mySong[MAX];
vector<Query> queries;

int sizeT[MAX], in[MAX], out[MAX], left_bs[MAX], right_bs[MAX], ans[MAX];
int nowC = 0;

int dfs (int node) {
    in[node] = ++nowC;
    sizeT[node] = 1;

    for (int & next : load[node]) {
        sizeT[node] += dfs(next);
    }

    out[node] = nowC;
    return sizeT[node];
}

bool cmp (Query & o1, Query & o2) {
    return o1.time < o2.time;
}

vector<int> mid[MAX];
bool sins[MAX];
vector<int> singers;


int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N >> K >> G;
    int parent;

    for (int i=2;i<=N;i++) {
        cin >> parent;
        load[parent].push_back(i);
    }

    while(start < N) start <<= 1;

    dfs(1);

    for (int i=1;i<=N;i++) {
        int singer;
        cin >> singer;
        ans[i] = singer;
        mySong[singer].push_back(i);
        sins[singer] = true;
    }

    for (int i=1;i<=N;i++) {
        if (sins[i]) singers.push_back(i);
    }

    int a, b, c;

    for (int i=0;i<K;i++) {
        cin >> a >> b >> c;
        queries.push_back({a, b, c});
        queries[i].value /= sizeT[queries[i].root];
    }

    sort(begin(queries), end(queries), cmp);

    for (int i=0;i<MAX;i++) {
        left_bs[i] = 0;
        right_bs[i] = K+1;
    }

    while(true) {
        for (int i=0;i<MAX;i++) mid[i].clear();

        bool b = true;

        for (int &i : singers) {
            if (left_bs[i] + 1 == right_bs[i]) continue;

            b = false;
            mid[left_bs[i] + right_bs[i] >> 1].push_back(i);
        }

        if(b) break;

        treeClear();

        for (int i=0;i<K;i++) {
            int root = queries[i].root;
            update(1, in[root], out[root], 1, start, queries[i].value);

            for (int &singer : mid[i+1]) {
                ll value = 0;
                ll ggoal = G * mySong[singer].size();

                bool flag = true;

                for (int &node : mySong[singer]) {
                    value += getValue(in[node]);

                    if (ggoal < value) {
                        right_bs[singer] = i+1;

                        flag = false;
                        break;
                    }
                }

                if (flag) left_bs[singer] = i+1;
            }
        }


    }

    for (int i=1;i<=N;i++) {
        int singer = ans[i];

        if (right_bs[singer] == K+1) {
            cout << -1 << '\n';
        } else {
            cout << queries[right_bs[singer]-1].time << '\n';
        }
    }

}