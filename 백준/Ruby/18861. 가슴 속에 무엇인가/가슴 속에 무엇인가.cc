#include<bits/stdc++.h>
#define MAX 316666
#define INF 1e10

using namespace std;

typedef long long ll;

struct node {
    ll mx, mn, value;
    int type, sumType;
    node* p = nullptr, *l = nullptr, *r = nullptr;
    bool re = false;

    node (ll value, int type) : mx(value), mn(value), value(value), type(type), sumType(type){
        update();
    }

    void propagate () {
        if (re) {
            swap(l, r);
            if(l) l->re ^= true;
            if(r) r->re ^= true;
        }

        re = false;
    }

    void update () {
        sumType = type;
        if (type) {
            mx = mn = value;
        } else {
            mx = 0;
            mn = INF;
        }

        if (l) {
            mx = max(mx, l->mx);
            mn = min(mn, l->mn);
            sumType |= l->sumType;
        }

        if (r) {
            mx = max(mx, r->mx);
            mn = min(mn, r->mn);
            sumType |= r->sumType;
        }
    }
};

node *nodes[MAX];

struct compare {
    bool operator() (node* a, node* b) {
        return a->value > b->value;
    }
};

priority_queue<node*, vector<node*>, compare> pq;

bool isRoot (node* a) {
    return !a->p || (a->p->l != a && a->p->r != a);
}

void rotate (node* a) {
    node* p = a->p, *temp = nullptr;

    p->propagate(); a->propagate();

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
    a->propagate();
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
        a->propagate();
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

void cut (node * a) {
    access(a);
    if (a->l) a->l->p = nullptr;
    a->l = nullptr;
    a->type = 0;
    a->update();
}

ll query (node* a, node* b) {
    node* lca = getLca(a, b);

    access(a); splay(lca);
    ll ans = 1e9;
    if (lca->r && lca->r->sumType) ans = min(ans, lca->r->mn);

    access(b); splay(lca);
    if (lca->r && lca->r->sumType) ans = min(ans, lca->r->mn);

    if (lca->type) ans = min(ans, lca->value);

    return ans;
}

node* trace (node* a) {
    while(1) {
        if (a->mn == a->value) break;

        if (a->l && a->l->mn == a->mn) a = a->l;
        else a = a->r;
    }

    return a;
}

void query (int n1, int n2, node* c) {
    node* lca = getLca(nodes[n1], nodes[n2]);

    access(nodes[n1]);
    splay(lca);

    node* target = nullptr;

    if (lca->r && lca->r->sumType) target = trace(lca->r);

    access(nodes[n2]);
    splay(lca);

    if (lca->r && lca->r->sumType) {
        if (target) {
            node* temp = trace(lca->r);

            if (target->value > temp->value) target = temp;
        } else {
            target = trace(lca->r);
        }
    }

    if (lca->type && (!target || target->value > lca->value)) target = lca;

    if (target->value >= c->value) return;

    cut(target);

    pq.push(c);
    connect(nodes[n1], c);
    connect(c, nodes[n2]);
}


int N, Q;

int main () {
    cin.tie(nullptr); ios_base::sync_with_stdio(false);

    cin >> N >> Q;

    for (int i=1;i<=N;i++) nodes[i] = new node(0, 0);

    int m, a, b, c;

    while(Q-->0) {
        cin >> m;

        if (m == 1) {
            cin >> a >> b >> c;

            if (isConnect(nodes[a], nodes[b])) {
                //변경 할 려는 쿼리를 날린다.
                node* load = new node(c, 1);

                query(a, b, load);
            } else {
                node* load = new node(c, 1);
                connect(nodes[a], load);
                connect(load, nodes[b]);

                pq.push(load);
            }
        } else if (m == 2) {
            cin >> a;

            while(!pq.empty() && pq.top()->value < a) {
                cut(pq.top()); pq.pop();
            }
        } else {
            cin >> a >> b;
            if (!isConnect(nodes[a], nodes[b])) {
                cout << 0 << '\n';
            } else {
                cout << query(nodes[a], nodes[b]) << '\n';
            }
        }

    }

}