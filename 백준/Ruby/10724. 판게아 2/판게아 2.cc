#include <bits/stdc++.h>
#define MAX 100001

using namespace std;

typedef long long ll;

ll nowTree = 0;

struct node {
    ll mx, value, w;
    bool re = false;

    node* p = nullptr, *l = nullptr, *r = nullptr;

    node (ll value, ll cost) : mx(cost), value(value), w(cost) {}

    void update () {
        mx = w;
        if (l) mx = max(mx, l->mx);
        if (r) mx = max(mx, r->mx);
    }

    void propagate () {
        if (re) {
            swap(l, r);
            if (l) l->re ^= true;
            if (r) r->re ^= true;
        }

        re = false;
    }

};

node* nodes[MAX];

bool isRoot (node* a) {
    return !a->p || (a->p->l != a && a->p->r != a);
}

void rotate (node* a) {
    node* parent = a->p, *temp;

    parent->propagate(); a->propagate();

    if (a->p->l == a) {
        parent->l = temp = a->r;
        a->r = parent;
    } else {
        parent->r = temp = a->l;
        a->l = parent;
    }

    if (temp) temp->p = parent;

    a->p = parent->p;
    parent->p = a;

    if (a->p) {
        if (a->p->l == parent) {
            a->p->l = a;
        } else if (a->p->r == parent) {
            a->p->r = a;
        }
    }

    parent->update();
    a->update();
}

void splay (node* a) {
    a->propagate();
    while(!isRoot(a)) {
        if (!isRoot(a->p)) rotate((a->p->p->l == a->p) == (a->p->l == a) ? a->p : a);
        rotate(a);
    }
}

void access (node* a) {
    splay(a);
    a->r = nullptr;

    a->update();

    while(a->p) {
        splay(a->p);
        a->p->r = a;

        a->p->update();
        splay(a);
    }
}

void makeRoot (node* a) {
    access(a);
    a->re ^= true;
    a->propagate();
}

void link (node* a, node* b) {
    access(a); access(b);
    a->l = b;
    b->p = a;

    a->update();
}

void connect (node* a, node* b) {
    makeRoot(a);
    link(a, b);
}

void cut (node* a) {
    access(a);
    if (a->l) a->l->p = nullptr;
    a->l = nullptr;
    a->value = 0;
    a->update();
}

node* getLca (node* a, node* b) {
    access(a);
    access(b);
    splay(a);

    return a->p ? a->p : a;
}

node* trace (node* a) {
    while(1) {
        if (a->mx == a->w) break;

        if (a->l && a->l->mx == a->mx) a = a->l;
        else a = a->r;
    }

    return a;
}

void query (int n1, int n2, ll cost) {
    node* lca = getLca(nodes[n1], nodes[n2]);

    access(nodes[n1]);
    splay(lca);

    node* target = nullptr;

    if (lca->r) target = trace(lca->r);

    access(nodes[n2]);
    splay(lca);

    if (lca->r) {
        if (target) {
            node* temp = trace(lca->r);

            if (target->w < temp->w) target = temp;
        } else {
            target = trace(lca->r);
        }
    }

    if (!target || target->w < lca->w) target = lca;

    if (target->w <= cost) return;
    nowTree += cost - target->w;

    cut(target);

    node* load = new node(-1, cost);
    connect(load, nodes[n1]);
    connect(load, nodes[n2]);
}




int T, N, Q;

int main () {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> T;

    while(T-->0) {
        cin >> N >> Q;

        int n, cost;
        ll answer = 0;
        nowTree = 0;

        for (int i=0;i<=N;i++) {
            nodes[i] = new node(i, 0);
        }

        for (int i=1;i<N;i++) {
            cin >> n >> cost;

            nowTree += cost;
            node* load = new node(-1, cost);

            link(nodes[i], load);
            link(load, nodes[n]);
        }

        int n1, n2;

        while(Q-->0) {
            cin >> n1 >> n2 >> cost;
            query(n1, n2, cost);

            answer ^= nowTree;
        }
        cout << answer << '\n';
    }
}
