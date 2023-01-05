#include<bits/stdc++.h>
#define MAX 202020

using namespace std;
typedef long long lint;

int N, M;

struct node {
    lint value, sum;
    node *p, *l, *r;

    node (lint value) : value(value), sum(value), p(0), l(0), r(0) {}

    void update () {
        sum = value;
        if (l) sum ^= l->sum;
        if (r) sum ^= r->sum;
    }
};

bool isRoot (node *a) {
    return !a->p || (a->p->l != a && a->p->r != a);
}

void rotate (node *a) {
    node *p = a->p, *temp = nullptr;

    if (a->p->l == a) {
        temp = a->p->l = a->r;
        a->r = p;
    } else {
        temp = a->p->r = a->l;
        a->l = p;
    }

    if (temp) temp->p = p;

    a->p = p->p;
    p->p = a;

    if (a->p) {
        if (a->p->l == p) {
            a->p->l = a;
        } else if (a->p->r == p) {
            a->p->r = a;
        }
    }
    p->update();a->update();
}

void splay (node *a) {
    while(!isRoot(a)) {
        if (!isRoot(a->p)) rotate((a->p->l == a) == (a->p->p->l == a->p) ? a->p : a);
        rotate(a);
    }
}

void access (node *a) {
    splay(a);
    a->r = 0;
    a->update();

    while(a->p) {
        splay(a->p);
        a->p->r = a;

        a->p->update();
        splay(a);
    }
}

void link (node *c, node *p) {
    access(c); access(p);
    assert(c->l==nullptr);
    c->l = p;
    p->p = c;

    c->update();
}

node* getLCA (node *a, node *b) {
    access(a); access(b);
    splay(a);

    return a->p ? a->p : a;
}

lint query(node* a, node *b) {
    node* lca = getLCA(a, b);

    lint res = lca->value;

    access(a);
    splay(lca);

    if (lca->r) res ^= lca->r->sum;
    
    access(b);
    splay(lca);

    if (lca->r) res ^= lca->r->sum;
    return res;
}

node *nodes[MAX];
vector<int> load[MAX];

void dfs (int node = 1, int prev = -1) {
    for (int go : load[node]) if (prev != go) {
        dfs(go, node);
        link(nodes[go], nodes[node]);
    }
}


int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(0);
    cin >> N >> M;

    assert(N < 100001);

    int a, b, c;

    for (int i=0;i<N;i++) {
        cin >> a;
        nodes[i+1] = new node(a);
    }

    for (int i=1;i<N;i++) {
        cin >> a >> b;

        load[a].push_back(b);
        load[b].push_back(a);
    }

    dfs();

    for (int i=0;i<M;i++) {
        cin >> a >> b >> c;

        if (a == 1) {
            access(nodes[b]);
            nodes[b]->value = c;
            nodes[b]->update();
        } else {
            lint res = query(nodes[c], nodes[b]);
            cout << res << '\n';
        }
    }
}