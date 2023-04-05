#include<bits/stdc++.h>
#define MAX 202020

using namespace std;

long long answer[MAX], arr[MAX], weight[MAX];
int myBucketIdx[MAX];

struct query
{
    int left, right, idx, sq;

    query(int left, int right, int idx) : left(left), right(right), idx(idx) {
        sq = left / 400;
    }
};

struct bucket {
    int left, right, w;
    long long value;

    bucket (int left, int right) : left(left), right(right), value(0), w(0) {}
};

struct query2{
    // mod 0 : 초기화를 해라, 1 : 구간에 대해 해당 수를 더해라, 2 : 구간에 대해 뺴라, 3 value 값에 답을 넣어라
    int mod, left, right;
    long long value, answer;

    query2(int mod, int left, int right, long long value) : mod(mod), left(left), right(right), value(value), answer(0) {}
};

bool cmp (query o1, query o2) {
    if (o1.sq != o2.sq) return o1.sq < o2.sq;
    return o1.right < o2.right;
};

typedef pair<int, int> pp;

vector<query> queries;
vector<query2> subQueries;
vector<bucket> buckets;
vector<pp> pol[MAX];

#define x first
#define y second

int N, Q;

void solve () {
    // 함수가 너무 복잡해서 solve 함수로 subQueries 에 있는 쿼리들을 처리 할 것임.
    //answer[MAX] 에 답을 넣을 것임.

    for (int i=0;i<subQueries.size();i++) {
        if (subQueries[i].mod == 1 || subQueries[i].mod == 2) {
            pol[subQueries[i].right].push_back({subQueries[i].value, i});
            if (subQueries[i].left != 1) pol[subQueries[i].left-1].push_back({-subQueries[i].value, i});
        }
    }

    // N sqrt(N) 전처리

    for (int i=0;i<N;i++) {

        for (bucket& b : buckets) {
            if (b.left <= arr[i] && arr[i] <= b.right) {
                for (int j=b.left;j<=b.right;j++) {
                    weight[j] += abs(j - arr[i]);
                }
            } else {
                b.w += b.left > arr[i] ? 1 : -1;
                b.value += b.left > arr[i] ? -arr[i] : arr[i];
            }
        }


        //x 가 추가하는 값, y가 그 쿼리의 위치
        for (pp s : pol[i+1]) {
            long long d = s.x < 0 ? -1 : 1;
            bucket & b = buckets[myBucketIdx[abs(s.x)]];
            subQueries[s.y].answer += d * (b.value + b.w * abs(s.x) + weight[abs(s.x)]);
        }
    }

    long long temp = 0;

    for (query2 q : subQueries) {
        // mod 1 : 구간에 대해 해당 수를 더해라, 2 : 구간에 대해 뺴라, 0 : 초기화를 날려라 : 3 value 값에 답을 넣어라
        if (q.mod == 0) {
            temp = 0;
        } else if (q.mod == 1) {
            temp += q.answer;
        } else if (q.mod == 2) {
            temp -= q.answer;
        } else {
            answer[q.value] = temp;
        }
    }
}

int main () {
    cin.tie(0);ios_base::sync_with_stdio(0);
    // freopen("C:\\Users\\skyho\\Desktop\\src\\test.in", "r", stdin);

    cin >> N >> Q;

    for (int i=0;i<N;i++) {
        cin >> arr[i];
    }

    int l(1), r, sqq(-1), bucketCnt(0);

    while(true) {
        r = min(N, l+399);
        buckets.push_back({l, r});

        for (int i=l;i<=r;i++) myBucketIdx[i] = bucketCnt;
        bucketCnt++;

        if (r == N) break;
        l = r+1; 
    }

    

    for (int i=0;i<Q;i++) {
        cin >> l >> r;
        queries.push_back(query(l, r, i));
    }

    sort(begin(queries), end(queries), cmp);

    int nowL(1), nowR(1);

    for (query &q : queries) {
        if (sqq != q.sq) {
            sqq = q.sq;
            nowL = nowR = q.left;

            for (int i=q.left-1;i<q.right;i++) {
                if (i == q.left-1) {
                    subQueries.push_back({0, 0, 0, 0});
                } else {
                    subQueries.push_back({1, nowL, nowR, arr[i]});
                }

                nowR = i+1;
            }
        } else {
            for (int i=r;i<q.right;i++) {
                subQueries.push_back({1, nowL, nowR++, arr[i]});
            }

            if (l < q.left) {
                //줄여나가야함
                for (int i=l-1;i<q.left-1;i++) {
                    subQueries.push_back({2, ++nowL, nowR, arr[i]});
                }
            } else {
                for (int i=l-2;i>=q.left-1;i--) {
                    subQueries.push_back({1, nowL--, nowR, arr[i]});
                }

            }

        }

        l = q.left;r = q.right;sqq = q.sq;
        subQueries.push_back({3, -1, -1, q.idx});
    }

    solve();

    // output
    for (int i=0;i<Q;i++) {
        cout << answer[i] << '\n';
    }
    return 0;
}