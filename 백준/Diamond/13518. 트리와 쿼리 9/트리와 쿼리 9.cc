#include <bits/stdc++.h>
#define MAX 100001
#define INF 1000001

using namespace std;

int in[MAX], out[MAX];
int line[MAX * 2];
vector<int> load[MAX];
int N, answer;
int arr[MAX], level[MAX], dp[30][MAX] = {0};
int cnt = 0, conquer = 400;

struct Query {
    int n1, n2, idx, sqrt;
    int left, right, lca;
    bool check = false;
};

void dfs (int now, int prev) {
    in[now] = ++cnt;
    level[now] = level[prev] + 1;
    dp[0][now] = prev;

    for (int i=0;i<load[now].size();i++) {
        if (load[now][i] == prev) continue;
        dfs(load[now][i], now);
    }

    out[now] = ++cnt;
}

void makeDP () {
    for (int i=1;i<30;i++) {
        for (int j=1;j<=N;j++) {
            dp[i][j] = dp[i-1][dp[i-1][j]];
        }
    }
}

int findLCA (int n1, int n2) {
    if (level[n1] > level[n2]) {
        int temp = n1;
        n1 = n2;
        n2 = temp;
    }

    int dist = level[n2] - level[n1];

    for (int i=29;i>=0;i--) {
        if ((dist & 1<<i) != 0) {
            n2 = dp[i][n2];
        }
    }

    for (int i=29;i>=0;i--) {
        if (dp[i][n1] != dp[i][n2]) {
            n1 = dp[i][n1];
            n2 = dp[i][n2];
        }
    }

    return n1 == n2 ? n1 : dp[0][n1];
}

bool cmp (Query o1, Query o2) {
    if (o1.sqrt == o2.sqrt) return o1.right < o2.right;
    return o1.sqrt < o2.sqrt;
}

int exist[MAX], exNum[INF];

void clear () {
    answer = 0;
    for (int & i : exist) {
        i = 0;
    }

    for (int & i : exNum) i = 0;
}

void add (int node) {
    exist[node]++;

    if (exist[node] == 1) {

        if (++exNum[arr[node]] == 1) {
            answer++;
        }
    } else if (exist[node] == 2) {
        if (--exNum[arr[node]] == 0) {
            answer--;
        }
    }
}

void pop (int node) {
    exist[node]--;

    if (exist[node] == 1) {

        if (++exNum[arr[node]] == 1) {
            answer++;
        }
    } else if (exist[node] == 0) {
        if (--exNum[arr[node]]==0) {
            answer--;
        }
    }
}


int main (void) {
    ios_base::sync_with_stdio(0);
    cin.tie(0);
    
    cin >> N;
    for (int i=1;i<=N;i++) cin >> arr[i];

    int n1, n2;

    for (int i=1;i<N;i++) {
        cin >> n1 >> n2;
        load[n1].push_back(n2);
        load[n2].push_back(n1);
    }

    dfs(1, 0);
    makeDP();

    for (int i=1;i<=N;i++) {
        line[in[i]] = i;
        line[out[i]] = i;
    }

    int queries;
    cin >> queries;
    Query query[queries];
    int result[queries];

    for (int i=0;i<queries;i++) {
        cin >> query[i].n1 >> query[i].n2;
        query[i].idx = i;

        n1 = query[i].n1;
        n2 = query[i].n2;

        if (in[n1] > in[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int lca = findLCA(n1, n2);
        query[i].lca = lca;

        if (n1 == lca) {
            query[i].left = in[n1];
            query[i].right = in[n2];
            query[i].check = true;
        } else {
            query[i].left = out[n1];
            query[i].right = in[n2];
        }

        query[i].sqrt = query[i].left / conquer;
    }

    sort(query, query + queries, cmp);

    int nowSqrt = -1;
    int nowL, nowR;

    for (int i=0;i<queries;i++) {
        if (nowSqrt != query[i].sqrt) {
            nowSqrt = query[i].sqrt;

            nowL = query[i].left;
            nowR = query[i].right;
            clear();

            for (int j=nowL;j<=nowR;j++) {
                add(line[j]);
            }

            if (!query[i].check) {
                add(query[i].lca);
            }

            result[query[i].idx] = answer;

            if (!query[i].check) {
                pop(query[i].lca);
            }
        } else {

            for (int j = nowR + 1;j<=query[i].right;j++) {
                add(line[j]);
            }

            if (nowL < query[i].left) {
                for (int j=nowL;j<query[i].left;j++) {
                    pop(line[j]);
                }
            } else if (nowL > query[i].left) {
                for (int j=query[i].left;j<nowL;j++) {
                    add(line[j]);
                }
            }

            nowL = query[i].left;
            nowR = query[i].right;

            if (!query[i].check) {
                add(query[i].lca);
            }

            result[query[i].idx] = answer;

            if (!query[i].check) {
                pop(query[i].lca);
            }
        }
    }
    for (int i=0;i<queries;i++) {
        cout << result[i] << '\n';
    }
}



