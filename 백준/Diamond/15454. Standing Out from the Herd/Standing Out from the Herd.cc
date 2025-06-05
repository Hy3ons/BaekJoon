#include<bits/stdc++.h>


namespace suffixDFA {
    using namespace std;

    struct state {
        int len = 0, link = -1;
        map<int, int> next;
        set<int> ids;
    };

    struct suffix_automaton {
        const int MAX_LEN;

        vector<state> st;
        int size, last;
        long long total_substrings = 0;

        suffix_automaton (const int MAX_LEN) : MAX_LEN(MAX_LEN), st(MAX_LEN * 2) {
            sa_init();
        }

        void sa_init () {
            size=1;last=0; //초기 루트 설정.
        }

        void sa_extend (int c, int id) {
            int now = size++;
            st[now].len = st[last].len + 1;
            st[now].ids.insert(id);

            int p = last;

            int isAdded = 0;

            //전이 상태가 없다면, 추가하지 않고 끝냄. 그리고 그 전이상태는, suffix link가 된다.
            while (p != -1 && !st[p].next.count(c)) {
                //전이 상태가 없으면, 추가해 주는 것.

                st[p].next[c] = now;
                p = st[p].link;
                isAdded = 1;
            }

            if (!isAdded) {
                int q = st[p].next[c];
                st[now].ids.insert(-1);
                st[now].link = 0;

                if (st[p].len + 1 != st[q].len) {
                    int clone = size++;
                    st[clone].len = st[p].len + 1;
                    st[clone].next = st[q].next;
                    st[clone].link = st[q].link;

                    st[clone].ids = st[q].ids;
                    st[clone].ids.insert(id);

                    while (p != -1 && st[p].next[c] == q) {
                        st[p].next[c] = clone;
                        p = st[p].link;
                    }

                    st[q].link = clone;
                    last = clone;
                } else {
                    last = q;
                    st[q].ids.insert(id);
                }

                return;
            }

            //이제 새로운 상태, now의 suffix link를 결정해야함.

            if (p == -1) {
                //p 가 -1이라는 것은, 그냥 루트를 suffix로 하면 됨.
                st[now].link = 0;
            } else {
                // p---c--> q

                int q = st[p].next[c];

                if (st[p].len + 1 == st[q].len) {
                    st[now].link = q;
                } else {
                    int temp = size++;

                    st[temp].len = st[p].len + 1;
                    st[temp].next = st[q].next;
                    st[temp].link = st[q].link;
                    st[temp].ids.insert(id);

                    while (p != -1 && st[p].next.count(c) && st[p].next[c] == q) {
                        st[p].next[c] = temp;
                        p = st[p].link;
                    }

                    st[now].link = st[q].link = temp;
                }
            }

            last = now;

            total_substrings += (st[now].len - st[st[now].link].len);
        }
    };
}

using namespace std;
int N;

int main  () {
    ios_base::sync_with_stdio(0);cin.tie(0);

    cin >> N;

    suffixDFA::suffix_automaton dfa(303030);
    vector<string> texts;

    for (int i=0;i<N;i++) {
        string x; cin >> x;
        texts.push_back(x);
    }

    for (int i=0;i<N;i++) {
        string &s = texts[i];
        dfa.last = 0;
        for (char c : s) dfa.sa_extend(c, i);
    }
//
//    for (int i=0;i<N;i++) {
//        string &s = texts[i];
//        dfa.last = 0;
//        for (char c : s) dfa.sa_extend(c, i);
//    }

    vector<long long> identity(101010, 0);
    vector<vector<int>> order_len (202020);

    for (int i=1;i<dfa.size;i++) {
        order_len[dfa.st[i].len].push_back(i);
    }

    for (int len = 150000;len>0;len--) {
        for (int state : order_len[len]) {
            auto &st = dfa.st[state];

            if (st.ids.size() == 1) {
                int id = *(st.ids.begin());

                identity[id] += st.len - dfa.st[st.link].len;

                dfa.st[st.link].ids.insert(id);
            } else {
                dfa.st[st.link].ids.insert(-1);
                dfa.st[st.link].ids.insert(-2);
            }
        }
    }

    for (int i=0;i<N;i++) {
        cout << identity[i] << '\n';
    }


}