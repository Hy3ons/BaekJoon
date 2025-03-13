#include<bits/stdc++.h>

typedef long long lint;
using namespace std;

int arr[9][9], mini[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

const int ROW_SIZ = 9*9*9;
const int FEATURE_SIZE = 4*9*9;

vector<int> nums[ROW_SIZ];

struct node {
    node *l = 0, *r = 0, *u = 0, *d = 0;
    int value = 0, x, y;
};

node *nodes[ROW_SIZ][FEATURE_SIZE];
node *root = new node();

vector<node*> did, head(FEATURE_SIZE);
vector<int> selectedRow;

void restoreNode (node *n) {
    if (n->l) n->l->r = n;
    if (n->r) n->r->l = n;
    if (n->u) n->u->d = n;
    if (n->d) n->d->u = n;

    head[n->y]->value++;
}

void restore() {
    assert(did.size());
    restoreNode(did.back());
    did.pop_back();
}

void deleteNode (node *n) {
    if (n->l) n->l->r = n->r;
    if (n->r) n->r->l = n->l;
    if (n->u) n->u->d = n->d;
    if (n->d) n->d->u = n->u;

    head[n->y]->value--;

    did.push_back(n);
}

void rowLeftDelete (node *n) {
    if (!n) return;

    rowLeftDelete(n->l);
    deleteNode(n);
}

void rowRightDelete (node *n) {
    if (!n) return;

    rowRightDelete(n->r);
    deleteNode(n);
}

// n is head

void deleteRow (node *n) {
    if (!n) return;

    deleteRow(n->d);
    rowLeftDelete(n->l);
    rowRightDelete(n->r);
    deleteNode(n);
}

void deleteHead (node *n) {
    if (!n) return;

    deleteRow(n->d);
    deleteNode(n);
}


void selectLeftRow (node *n, vector<int> &vec) {
    if (!n) return;

    selectLeftRow(n->l, vec);
    vec.push_back(n->y);
}

void selectRightRow (node *n, vector<int> &vec) {
    if (!n) return;

    selectRightRow(n->r, vec);
    vec.push_back(n->y);
}

void selectRow (node *n) {
    vector<int> vec = {n->y};

    selectRightRow(n->r, vec);
    selectLeftRow(n->l, vec);

    for (int e : vec) deleteHead(head[e]);
}

bool backTrack (int column) {
    node *now = head[column]->d;

    while(now) {
        const int siz = did.size();
        selectedRow.push_back(now->x);

        selectRow(now);

        int mn = 1e9, idx;

        node *heading = root->r;

        while(heading) {
            if (mn > heading->value) {
                mn = heading->value;
                idx = heading->y;
            }

            heading = heading->r;
        }

        if (mn == 1e9) return true;

        if (mn == 0) {
            // fail
        } else {
            if (backTrack(idx)) return true;
        }

        while(did.size() != siz) restore();
        selectedRow.pop_back();

        now = now->d;
    }


    return false;
}


int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);

    for (int i=0;i<9;i++) {
        for (int j=0;j<9;j++) {
            cin >> arr[i][j];
        }
    }

    for (int i=0;i<FEATURE_SIZE;i++) {
        head[i] = new node();
        head[i]->y = i;
        head[i]->value = ROW_SIZ;
    }

    for (int i=0;i<ROW_SIZ;i++) {
        for (int j=0;j<FEATURE_SIZE;j++) {
            nodes[i][j] = new node();
            nodes[i][j]->x = i;
            nodes[i][j]->y = j;
        }
    }

    for (int i=0;i<81*9;i++) {
        for (int j=0;j<FEATURE_SIZE;j++) {
            if (j-1 >= 0) nodes[i][j]->l = nodes[i][j-1];
            if (j+1 < FEATURE_SIZE) nodes[i][j]->r = nodes[i][j+1];

            if (i-1 >= 0) nodes[i][j]->u = nodes[i-1][j];
            else nodes[i][j]->u = head[j];

            if (i+1 < ROW_SIZ) nodes[i][j]->d = nodes[i+1][j];
        }
    }


    root->r = head[0];

    for (int i=0;i<FEATURE_SIZE;i++) {
        head[i]->d = nodes[0][i];

        if (i-1 >= 0) head[i]->l = head[i-1];
        else head[i]->l = root;

        if (i+1 < FEATURE_SIZE) head[i]->r = head[i+1];
    }

    const int AFTER1 = 81;

    for (int n=1;n<=9;n++) {
        for (int i=0;i<9;i++) {
            for (int j=0;j<9;j++) {
                int idx = (n-1) * 81 + (i * 9) + j;

                vector<int> embed(FEATURE_SIZE);

                embed[i*9 + j] = 1;

                const int C = (n-1) * 9 + i;
                embed[81 + C] = 1;

                const int G = (n-1) * 9 + j;
                embed[81*2 + G] = 1;

                const int K = (n-1) * 9 + (mini[i/3][j/3] - 1);
                embed[81*3 + K] = 1;

                nums[idx] = std::move(embed);
            }
        }
    }

    for (int i=0;i<ROW_SIZ;i++) {
        for (int j=0;j<FEATURE_SIZE;j++) {
            if (nums[i][j] == 0) deleteNode(nodes[i][j]);
        }
    }

    did.clear();

    for (int i=0;i<9;i++) {
        for (int j=0;j<9;j++) {
            if (!arr[i][j]) continue;
            int idx = (arr[i][j] -1) * 81 + i * 9 + j;

            selectRow(nodes[idx][i * 9 + j]);
        }
    }

    int mn = 1e9, idx;

    for (int i=0;i<FEATURE_SIZE;i++) {
        if (head[i]->value < 0) continue;

        if (mn > head[i]->value) {
            mn = head[i]->value;
            idx = i;
        }
    }

    backTrack(idx);

    for (int e : selectedRow) {
        int num = (e / 81) + 1;
        int seat = e % 81;
        int x = seat / 9;
        int y = seat % 9;

        arr[x][y] = num;
    }

    for (int i=0;i<9;i++) {
        for (int j=0;j<9;j++) {
            cout << arr[i][j] << ' ';
        }
        cout << '\n';
    }
}