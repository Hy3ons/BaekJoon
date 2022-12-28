import java.io.*;
import java.util.*;

class Pair {
    int go;
    long cost;
    int idx;

    Pair (int go , long cost) {
        this(go, cost, -1);
    }

    Pair (int go, long cost, int idx) {
        this.go= go;
        this.cost = cost;
        this.idx = idx;
    }
}

class Seg {
    int start = 1, size;
    long[] tree;
    Seg (int size) {
        this.size = size;
        while(start < size) start <<= 1;
        tree = new long[start << 1];

        Arrays.fill(tree, Long.MAX_VALUE);
    }

    public void minQuery (int node, int s, int e, int left, int right, long v) {
        if (right <s || e < left) return;

        if (s <= left && right <= e) {
            tree[node] = Math.min(tree[node], v);
            return;
        }

        int mid = left + right >> 1;
        minQuery(node << 1, s, e, left, mid, v);
        minQuery(node << 1 | 1, s, e, mid+1, right, v);
    }

    public void reload () {
        for (int i=1;i<start;i++) {
            tree[i << 1] = Math.min(tree[i << 1], tree[i]);
            tree[i << 1 | 1] = Math.min(tree[i << 1 | 1], tree[i]);
        }
    }

    public long getMin (int idx) {
        return tree[start + idx] == Long.MAX_VALUE ? -1 : tree[start + idx];
    }
}

public class Main {

    public static final int MAX = 2200;
    public static ArrayList<Integer> lucky = new ArrayList<>();

    public static boolean[] lucks = new boolean[MAX], luckyPathIdx;
    public static boolean[][] luckyPath = new boolean[2001][2001];

    public static ArrayList<ArrayList<Pair>> load, tree;
    public static int[] f1, f2, cos;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        final int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken()),
                a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());

        tree = new ArrayList<>();
        load = new ArrayList<>();
        for (int i=0;i<=N;i++) {
            tree.add(new ArrayList<>());
            load.add(new ArrayList<>());
        }

        level[a] = 1;

        re = new int[N+1]; Arrays.fill(re, -1);
        f1 = new int[M]; f2 = new int[M]; cos = new int[M];
        used = new boolean[M];
        luckyPathIdx = new boolean[M];

        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            load.get(n1).add(new Pair(n2, cost, i));
            load.get(n2).add(new Pair(n1, cost, i));

            f1[i] = n1;
            f2[i] = n2;
            cos[i] = cost;
        }

        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        for (int i=0;i<k;i++) {
            int e = Integer.parseInt(st.nextToken());
            lucks[e] = true;
            lucky.add(e);
            order[e] = i+1;
        }

        for (int i=0;i+1< lucky.size();i++) {
             luckyPath[lucky.get(i)][lucky.get(i+1)] = true;
             luckyPath[lucky.get(i+1)][lucky.get(i)] = true;
        }

        long[] prefix = new long[N+1];

        for (int i=0, j =0;i<f1.length;i++) {
            luckyPathIdx[i] = luckyPath[f1[i]][f2[i]];

            if (luckyPathIdx[i]) {
                prefix[j + 1] = prefix[j] + cos[i];
                j++;
            }
        }

        int[] left = getStable(a);
        long[] leftDp = dp;


        int[] right = getStable(b);
        long[] rightDp = dp;

        Seg segtree = new Seg(N);


        for (int i=0;i<f1.length;i++) {
            if (luckyPathIdx[i]) continue;

            int n1 = f1[i], n2 = f2[i];

            //a -> n1 -> n2 -> b

            long cost = leftDp[n1] + rightDp[n2] + cos[i];

            int l = order[left[n1]], r = order[right[n2]];
            if (l+1 <= r) {
                segtree.minQuery(1, l+1, r, 1, segtree.start, cost);
            }

            n2 = f1[i]; n1 = f2[i];

            //a -> n2 -> n1 -> b

            cost = leftDp[n1] + rightDp[n2] + cos[i];

            l = order[left[n1]]; r = order[right[n2]];
            if (l+1 <= r) {
                segtree.minQuery(1, l+1, r, 1, segtree.start, cost);
            }
        }

        segtree.reload();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i=1;i<lucky.size();i++) {
            bw.write(segtree.getMin(i)+"\n");
        }
        bw.flush();
    }

    public static int[] re, level = new int[MAX], order = new int[MAX];
    public static boolean[] used;

    public static ArrayList<ArrayList<Pair>> temp;

    public static int[] getStable (int start) {
        int[] res = new int[3000];

        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.cost, o2.cost);
            }
        });

        dp = new long[MAX];
        Arrays.fill(dp, Long.MAX_VALUE);
        pq.offer(new Pair(start, dp[start] = 0));
        int[] re = new int[MAX];
        Arrays.fill(re, -1);

        while(!pq.isEmpty()) {
            Pair now = pq.poll();
            if (now.cost != dp[now.go]) continue;

            for (Pair go : load.get(now.go)) {
                long cal = now.cost + go.cost;
                if (dp[go.go] > cal) {
                    dp[go.go] = cal;
                    re[go.go] = go.idx;

                    pq.offer(new Pair(go.go, dp[go.go]));
                } else if (dp[go.go] == cal) {
                    if (luckyPathIdx[go.idx]) {
                        re[go.go] = go.idx;
                    }
                }
            }
        }

        temp = new ArrayList<>(MAX);
        for (int i=0;i<MAX;i++) temp.add(new ArrayList<>());

        for (int e : re) {
            if (e == -1) continue;
            temp.get(f1[e]).add(new Pair(f2[e], cos[e]));
            temp.get(f2[e]).add(new Pair(f1[e], cos[e]));
        }

        dfs2(start, -1, start, temp, res);
        return res;
    }

    public static void dfs2 (int node, int prev, int stable, ArrayList<ArrayList<Pair>> load, int[] arr) {
        if (lucks[node]) stable = node;
        arr[node] = stable;

        for (Pair go : load.get(node)) if (go.go != prev) {
            dfs2(go.go, node, stable, load, arr);
        }
    }

    public static long[] dp = new long[MAX];
}