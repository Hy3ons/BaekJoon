#include<bits/stdc++.h>
#define MAX 303030
#define x first
#define y second

using namespace std;

int N, M;

typedef pair<int, int> pp;
typedef long long ll;

struct sccMaker{
    vector<int> seed, load[MAX], re[MAX], stack;
    int mySCC[MAX], visit[MAX];

    void addEdge (int from, int go) {
        load[from].push_back(go);
        re[go].push_back(from);

        seed.push_back(from);
        seed.push_back(go);
    }

    void clear () {
        for (int & i : seed) {
            mySCC[i] = visit[i] = 0;
            load[i].clear(); re[i].clear();
        }

        stack.clear();
        seed.clear();
    }

    void function () {
        for (int & i : seed) {
            if (!visit[i]) dfs(i);
        }

        reverse(begin(stack), end(stack));
        int cnt = 1;

        for (int & i : stack) {
            if (!mySCC[i]) dfs2(i, -1, cnt++);
        }
    }

    void dfs (int node, int prev = -1) {
        visit[node] = 1;

        for (int & i : load[node]) if (i != prev) {
            if (!visit[i]) dfs(i, node);

        }

        stack.push_back(node);
    }

    void dfs2 (int node, int prev, int mySccNum) {
        mySCC[node] = mySccNum;

        for (int & i : re[node]) if (i != prev){
            if (!mySCC[i]) dfs2(i, node, mySccNum);
        }
    }

    bool isSame (int n1, int n2) {
        if (!mySCC[n1] || !mySCC[n2]) return false;
        return mySCC[n1] == mySCC[n2];
    }
};

ll nowAnswer = 0;
ll parent[MAX], sizes[MAX];

int find (int node) {
    if (parent[node] == node) return node;
    return parent[node] = find(parent[node]);
}

ll cal (ll a) {
    return (a * (a-1)) >> 1;
}

void unions (int n1, int n2) {
    int p1 = find(n1), p2 = find(n2);

    if (p1 == p2) return;

    nowAnswer -= cal(sizes[p1]);
    nowAnswer -= cal(sizes[p2]);

    sizes[p1] += sizes[p2];

    nowAnswer += cal(sizes[p1]);
    parent[p2] = p1;
}

int from[MAX], go[MAX];
ll answer[MAX];

sccMaker scc;

void dnc (int l, int r, vector<int> & edges) {
    if (l == r) {
        for (int & i : edges) unions(from[i], go[i]);
        answer[l] = nowAnswer;
        return;
    }

    int mid = l + r >> 1;
    scc.clear();

    int cnt = 0;

    for (int & i : edges) {
        if (i <= mid) scc.addEdge(find(from[i]), find(go[i]));
        // i는 시간순서 일 뿐, 합치는건 유파로 최적화 되어있는 집함들임.
        // if (i > mid) break;
    }

    scc.function();

    vector<int> left, right;
    for (int & i : edges) {
        if (scc.isSame(find(from[i]), find(go[i]))) {
            left.push_back(i);
        } else {
            right.push_back(i);
        }
    }

    dnc(l, mid, left);
    dnc(mid+1, r, right);
}


vector<pp> pairs;
vector<int> edges;


int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);
    cin >> N >> M;

    for (int i=0;i<MAX;i++) {
        parent[i] = i;
        sizes[i] = 1;
    }

    //pair 로 관리하면 나중에 엣지들의 시간을따로 관리해야함 개귀찮음

    for (int i=0;i<M;i++) {
        cin >> from[i] >> go[i];
    }
    vector<int> edges;
    for (int i=0;i<M;i++) edges.push_back(i);


    dnc(0, M, edges);

    for (int i=0;i<M;i++) {
        cout << answer[i] << '\n';
    }
}