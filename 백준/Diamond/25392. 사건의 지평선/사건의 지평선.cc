#include <bits/stdc++.h>
#define MAX 1500000
#define sub 303030

using namespace std;

int arr[MAX], visited[MAX], my[MAX], indegree[MAX], sccNum[MAX], sizes[MAX], ready[MAX];
int N;
vector<int> load[MAX], re[MAX], sccLoad[MAX];

void query(int node, int s, int e, int left, int right, int my) {
    if (right < s || e < left ) return;

    if (s <= left && right <= e) {
        load[node+sub].push_back(my);
        re[my].push_back(node+sub);
        return;
    }

    int mid = left + right >> 1;
    query(node << 1, s, e, left, mid, my);
    query(node << 1 | 1, s, e, mid+1, right, my);
}

void init (int node, int s, int e) {
    for (int i=s;i<=e;i++) {
        load[i].push_back(node+sub);
        re[node+sub].push_back(i);
    }

    if (s == e) return;

    int mid = s + e >> 1;

    init(node << 1, s, mid);
    init(node << 1 | 1, mid+1, e);
}

struct imf {
    int idx, value;

    imf (int idx, int value) : idx(idx), value(value) {}
};

bool cmp (imf o1, imf o2) {
    return o1.value > o2.value;
}

vector<imf> aa;
queue<int> qu;

struct sccMaker {
    vector<int> st;

    void dfs (int node, int prev = -1) {
        visited[node] = 1;
        for (int go : load[node]) {
            if (visited[go]) continue;
            dfs(go, node);
        }

        st.push_back(node);
    }

    void dfs2(int node, int prev, int myScc) {
        visited[node] = 1;
        for (int go : re[node]) {
            if (visited[go]) continue;
            dfs2(go, node, myScc);
        }

        my[node] = myScc;
        sizes[myScc]++;
    }

    void function () {
        fill(visited, visited+MAX, 0);
        // memset(visited, 0, sizeof(visited));
        for (int i=1;i<=N;i++) {
            if (!visited[i]) dfs(i);
        }

        reverse(begin(st), end(st));
        fill(visited, visited+MAX, 0);

        int cnt = 1;

        for (int i : st) {
            if (!visited[i]) dfs2(i, -1, cnt++);
        }

        for (int i=1;i<=N;i++) {
            ready[my[i]] = max(ready[my[i]], arr[i]);
        }


        for (int i=0;i<MAX;i++) {
            for (int e : load[i]) {
                int from = my[i], go = my[e];
                if (from == go) continue;

                sccLoad[from].push_back(go);
                indegree[go]++;
            }
        }


        for (int i=1;i<MAX;i++) {
            if (!indegree[i]) qu.push(i);
        }

        while(!qu.empty()) {
            int now = qu.front(); qu.pop();
            if (sizes[now] > 1) {
                sccNum[now] = max(sccNum[now], ready[now]);
            }

            for (int e : sccLoad[now]) {
                sccNum[e] = max(sccNum[e], sccNum[now]);
                ready[e] = max(ready[e], ready[now]);
                if (--indegree[e] == 0) {
                    qu.push(e);
                }
            }
        }



    }
} sccMaker;

int main() {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N;

    for (int i=1;i<=N;i++) {
        cin >> arr[i];
        aa.emplace_back(i, arr[i]);
    }

    init(1, 1, N);

    int a, b;
    for (int i=1;i<=N;i++) {
        cin >> a >> b;
        query(1, a, b, 1, N, i);
    }

    sccMaker.function();

    for (int i=1;i<=N;i++) {
        // cout << my[i] << ' ';
        cout << sccNum[my[i]] << ' ';
    }
    return 0;
}