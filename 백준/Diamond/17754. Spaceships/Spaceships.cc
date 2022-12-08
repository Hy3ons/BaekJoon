#include<bits/stdc++.h>
#define MAX 1010101

using namespace std;

typedef long long ll;

struct node {
    node *p = nullptr, *r = nullptr, *l = nullptr;
    int size = 1, value;


    node(int value) : value(value) {}

    void update () {
        size = 1;
        if (l) size += l->size;
        if (r) size += r->size;
    }
};

node *nodes[MAX];

bool isRoot (node* a) {
    return !a->p || (a->p->l != a && a->p->r != a);
}

void rotate (node* a) {
    node* p = a->p, *temp = nullptr;

    if (a->p->l == a) {
        temp = p->l = a->r;
        a->r = p;
    } else {
        temp = p->r = a->l;
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
    p->update(); a->update();
}

void splay (node* a) {
    while(!isRoot(a)) {
        if (!isRoot(a->p))
            rotate((a->p->p->l == a->p) == (a->p->l == a) ? a->p : a);
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

node* getRoot (node* a) {
    access(a);
    while(a->l) {
        a = a->l;
    }

    return a;
}

node* getLca (node* a, node* b) {
    access(a); access(b);
    splay(a);

    return a->p ? a->p : a;
}

bool isConnect (node* a, node* b) {
    return getRoot(a) == getRoot(b);
}

void link (node* a, node* b) {
    access(a); access(b);
    a->l = b;
    b->p = a;
}

void cut (node * a) {
    access(a);

    if (a->l) a->l->p = nullptr;

    a->l = nullptr;
    a->update();
}

ll dist (node *a, node *b) {
    node* lca = getLca(a, b);

    ll res = 0;

    access(a);
    splay(lca);

    if (lca->r) res += lca->size;

    access(b);
    splay(lca);

    if (lca->r) res += lca->size;

    return res;
}

int N, Q;

int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);

    cin >> N >> Q;

    for (int i=1;i<=N;i++) {
        nodes[i] = new node(i);
    }

    int m, x, y;

    while(Q-->0) {
        cin >> m;

        if (m == 1) {
            cin >> x >> y;

            link(nodes[x], nodes[y]);
        } else if (m == 2){
            cin >> x;
            cut(nodes[x]);
        } else {
            cin >> x >> y;
            if (isConnect(nodes[x], nodes[y])) {
                cout << getLca(nodes[x], nodes[y])->value << '\n';
            } else {
                cout << -1 << '\n';
            }
        }
    }
}