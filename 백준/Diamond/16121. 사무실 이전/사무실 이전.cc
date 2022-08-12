#include <bits/stdc++.h>
#define MAX 300001
#define MOD 998244353

using namespace std;

typedef long long ll;
int treeS[MAX];
vector <int> load[MAX];
bool live[MAX] = {false}, stat[MAX] = {false}, cent[MAX] = {false};
int level[MAX] = {0};
int N, n1, n2;
unsigned long long l_sum, ll_sum, s_sum, ss_sum, s_cnt, l_cnt, result = 0;

void checkLv(int roider, vector<int> &checkList) {
    cout << "roider : " << roider << '\n';
    for (int i=0;i<checkList.size();i++) {
        cout << checkList[i] << " : " << level[checkList[i]] << '\n';
    }
}

void clear () {
    l_sum = ll_sum = s_sum = ss_sum = s_cnt = l_cnt = 0;
}

int getSize(int node, int prev) {
    treeS[node] = 1;
    for (int i=0;i<load[node].size();i++) {
        if (prev == load[node][i] || node == load[node][i] || cent[load[node][i]]) continue;
        treeS[node] += getSize(load[node][i], node);
    }
    return treeS[node];
}

int getRoid(int node, int prev, int limit) {
    for (int i=0;i<load[node].size();i++) {
        int go = load[node][i];

        if (!cent[go] && go != prev && treeS[go] * 2 > limit) {
            return getRoid(go, node, limit);
        }
    }
    return node;
}

void find (int node, int prev = 0, ll depth = 1) {
    if (stat[node]) {
        ss_sum += depth * depth;
        ss_sum %= MOD;
        s_cnt++;
        s_sum += depth;
        s_sum %= MOD;
    }

    if (live[node]) {
        ll_sum += depth * depth;
        ll_sum %= MOD;
        l_cnt++;
        l_sum += depth;
        l_sum %= MOD;
    }

    for (int i=0;i<load[node].size();i++) if(!cent[load[node][i]] && prev != load[node][i]) {
        find(load[node][i], node, depth+1);
    }

}

void getAnswer(int node, int prev, ll depth = 1) {
    if (live[node]) {
        result += ss_sum;
        result %= MOD;
        result += 2 * s_sum * depth;
        result %= MOD;
        result += depth * depth * s_cnt;
        result %= MOD;
    }

    if (stat[node]) {
        result += ll_sum;
        result %= MOD;
        result += 2 * l_sum * depth;
        result %= MOD;
        result += depth * depth * l_cnt;
        result %= MOD;
    }

    for (int i=0;i<load[node].size();i++) if(!cent[load[node][i]] && prev != load[node][i]) {
        getAnswer(load[node][i], node, depth+1);
    }
}

void getAnswer(int roider) {
    clear();

    vector<int> v;
    int temp = result;

    for (int i=0;i<load[roider].size();i++) if (!cent[load[roider][i]]) v.push_back(load[roider][i]);

    for (int i=0;i<v.size();i++) {
        getAnswer(v[i], roider);
        find(v[i]);
    }

//    cout << "roider : " << roider << '\n';
//    for (int i=1;i<=N;i++) {
//        cout << i << " level : " << level[i] << '\n';
//    }

    if (stat[roider]) {
        result += ll_sum;
        result %= MOD;
    }
    if (live[roider]) {
        result += ss_sum;
        result %= MOD;
    }

//    cout << "charge : " << result - temp << "\n\n";
}

void func(int any = 1) {
    int limit = getSize(any, 0);
    int roider = getRoid(any, 0, limit);

    if (cent[roider]) return;
    cent[roider] = true;

    getAnswer(roider);

    for (int i=0;i<load[roider].size();i++) {
        if (!cent[load[roider][i]]) {
            func(load[roider][i]);
        }
    }
}


int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N;
    for (int i=1;i<N;i++) {
        cin >> n1 >> n2;
        load[n1].push_back(n2);
        load[n2].push_back(n1);
    }

    cin >> n1;
    int temp;
    for (int i=0;i<n1;i++) {
        cin >> temp;
        live[temp] = true;
    }
    cin >> n2;
    for (int i=0;i<n2;i++) {
        cin >> temp;
        stat[temp] = true;
    }

    func();
    result %= MOD;
    cout << result;
    return 0;
}