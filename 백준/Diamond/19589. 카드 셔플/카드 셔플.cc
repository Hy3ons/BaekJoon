#include<bits/stdc++.h>
#define MAX 1000001

using namespace std;

struct Node {
    Node *p = nullptr, *left = nullptr, * right = nullptr;

    int value;
    long long size = 1;
    bool reversed = false;

    Node (int value) : value(value) {}

    void update () {
        size = 1;
        if (left) size += left->size;
        if (right) size += right->size;
    }

    void propagate() {
        if (reversed) {
            Node* temp = right;
            right= left;
            left = temp;

            if (left) left->reversed ^= true;
            if (right) right-> reversed ^= true;
        }
        reversed = false;
    }
};

Node* nodes[MAX];
int store[2020], res[2020];
Node* root;
int N, Q, iddx;

void rotate (Node* a) {
    Node* parent = a->p, *leaf;

    parent->propagate();
    a->propagate();

    if (parent->left == a) {
        leaf = parent->left = a->right;
        a->right = parent;
    } else {
        leaf = parent->right = a->left;
        a->left = parent;
    }

    a->p = parent->p;
    parent->p = a;

    if (leaf) {
        leaf->p = parent;
    }

    if(a->p) {
        if (a->p->left == parent) {
            a->p->left = a;
        } else {
            a->p->right = a;
        }
    } else {
        root = a;
    }

    parent->update();
    a->update();
}

void splay (Node* a) {
    if (a == root) return;

    while(a->p) {
        if (a->p->p)
            rotate((a->p->p->left == a->p) == (a->p->left == a) ? a->p : a);
        rotate(a);
    }
}

void findKth (int order) {
    Node* now = root;

    while(1) {
        now->propagate();
        if (now->left == nullptr && now->right == nullptr) break;

        if (now->left && now->right) {
            if (now->left->size + 1 == order) break;

            if (now->left->size < order) {
                order -= now->left->size + 1;
                now = now->right;
            } else {
                now = now->left;
            }
        } else if (now->left) {
            if (now->left->size + 1 == order) break;

            now = now->left;
        } else {
            if (order == 1) break;

            now = now->right;
            order--;
        }
    }

    splay(now);
}

void inOrder (Node* now) {
    now->propagate();

    if (now->left) inOrder(now->left);
    cout << now->value << ' ';
    if (now->right) inOrder(now->right);
}

Node* findNode (int left, int right) {
    if (left == 1 && right == N) {
        return root;
    } else if (left == 1) {
        findKth(right+1);
        return root->left;
    } else if (right == N) {
        findKth(left-1);
        return root->right;
    } else {
        findKth(left-1);

        Node* original = root;

        root = original->right;
        root->p = nullptr;

        findKth(right - left + 2);

        Node* ans = root->left;

        original->right = root;
        root->p = original;

        root = original;
        return ans;
    }

}

void getOrder (Node* now) {
    now->propagate();

    if (now->left) getOrder(now->left);
    store[iddx++] = now->value;
    if (now->right) getOrder(now->right);
}

void putOrder (Node* now) {
    if (now->left) putOrder(now->left);
    now->value = res[iddx++];
    if (now->right) putOrder(now->right);
}

void reverseRange(int left, int right) {
    if(left == right) return;
    findNode(left, right)->reversed ^= true;
}

void refleShuffle (int left, int right) {
    if(left == right) return;

    iddx = 0;
    Node* target = findNode(left, right);
    getOrder(target);

    int l = 0 , r = (iddx >> 1) + (iddx & 1);
    int k = 0;

    while(r < iddx) {
        res[k++] = store[l++];
        res[k++] = store[r++];
    }

    if (iddx & 1) res[k] = store[l];
    iddx = 0;

    putOrder(target);
}


int main () {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    cin >> N >> Q;

    for (int i=0;i<N;i++) {
        nodes[i] = new Node(i+1);
    }

    for (int i=N-1;i>0;i--) {
        nodes[i-1]->right = nodes[i];
        nodes[i]->p = nodes[i - 1];

        nodes[i-1]->update();
    }

    root = nodes[0];

    int m, x, y;

    for (int i=0;i<Q;i++) {
        cin >> m >> x >> y;

        if (m == 1) {
            if (x == 1) continue;
            reverseRange(x, y);
            reverseRange(1, x-1);
            reverseRange(1, y);
        } else if (m== 2) {
            if (y == N) continue;
            reverseRange(x, y);
            reverseRange(y+1, N);
            reverseRange(x, N);
        } else {
            refleShuffle(x, y);
        }

    }

    inOrder(root);
    cout << '\n';
    return 0;
}