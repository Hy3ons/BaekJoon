import java.io.*;
import java.util.*;

class Go {
    int now, prev;
    Go(int now, int prev) {
        this.now = now;
        this.prev = prev;
    }
}

class Segment {
    private int[] tree, lazy1, lazy2;
    private boolean[] isLazy;
    private int start = 1, leftIdx, rightIdx;
    public static final long MOD = 4294967296L;

    Segment(int size) {
        while (start < size) start *= 2;

//        tree = new long[start *2];
//        lazy1 = new long[start * 2];
//        lazy2 = new long[start * 2];

        tree= new int[start * 2];
        lazy1 = new int[start * 2];
        lazy2 = new int[start * 2];
        isLazy = new boolean[start * 2];
        Arrays.fill(lazy1, 1);
    }
    private int query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (isLazy[node]) propagate(node, left, right);

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        return (query(node*2, left, mid)
                + query(node*2+1, mid+1, right));
    }
    public int find (int left, int right) {
        this.leftIdx = left;
        this.rightIdx = right;

        return query(1, 1, start);
    }
    private void propagate (int node, int left, int right) {
        tree[node] *= lazy1[node];
        tree[node] += (lazy2[node]) * (right - left + 1);
//        tree[node] %= MOD;

        if (node >= start) {
            lazy1[node] = 1;
            lazy2[node] = 0;
            isLazy[node] = false;
            return;
        }

        lazy1[node*2] *= lazy1[node];
        lazy1[node*2+1] *= lazy1[node];
        lazy2[node*2] *= lazy1[node];
        lazy2[node*2+1] *= lazy1[node];

        lazy2[node *2] += lazy2[node];
        lazy2[node*2+1] += lazy2[node];

//        lazy1[node*2] %= MOD;
//        lazy1[node*2+1] %= MOD;
//        lazy2[node*2] %= MOD;
//        lazy2[node*2+1] %= MOD;
        isLazy[node*2] = isLazy[node*2+1] = true;

        lazy1[node] = 1;
        lazy2[node] = 0;
        isLazy[node] = false;
    }
    private void update (int node, int left, int right, int mul, int add) {
        if (isLazy[node]) propagate(node, left, right);

        if (right < leftIdx || rightIdx < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            lazy1[node] = mul;
            lazy2[node] = add;
            isLazy[node] = true;
            propagate(node, left, right);
            return;
        }

        int mid = (left + right) / 2;

        update(node*2, left, mid, mul, add);
        update(node*2+1, mid+1, right, mul, add);

        tree[node] = tree[node*2] + tree[node*2+1];
    }
    public void startUpdate (int left, int right, int mul, int add) {
        this.leftIdx = left;
        this.rightIdx = right;
        update(1, 1, start, mul, add);
    }


}

class Chain {
    public static Segment tree;
    int upper, lower;

    public int nextAdd (int node) {
        return tree.find(Main.in[upper], Main.in[node]);
    }
    public int inAdd (int n1, int n2) {
        int left = Math.min(Main.in[n1], Main.in[n2]);
        int right = Math.max(Main.in[n1], Main.in[n2]);

        return tree.find(left, right);
    }
    public void nextUpdate(int node, int mul, int value) {
        tree.startUpdate(Main.in[upper], Main.in[node], mul, value);
    }
    public void inUpdate (int n1, int n2, int mul, int value) {
        int left = Math.min(Main.in[n1], Main.in[n2]);
        int right = Math.max(Main.in[n1], Main.in[n2]);
        tree.startUpdate(left, right, mul, value);
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load, real;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        times[0] = 1;
        for (int i=1;i<times.length;i++) {
            times[i] = times[i-1] * 2;
        }

        int size = Integer.parseInt(st.nextToken());
        int queries = Integer.parseInt(st.nextToken());

        load = new ArrayList<>(size+1);
        real = new ArrayList<>(size+1);

        for (int i=0;i<=size;i++) {
            load.add(new ArrayList());
            real.add(new ArrayList());
        }

        for (int i=1;i<size;i++) {
            st = new StringTokenizer(br.readLine());

            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        Queue<Go> qu = new LinkedList<>();
        Stack<Integer> firsts = new Stack<>();

        level = new int[size+1];
        qu.offer(new Go(1, 0));

        while(!qu.isEmpty()) {
            Go now = qu.poll();
            boolean beFirst = true;

            for (int go : load.get(now.now)) {
                if (go == now.prev) continue;

                level[go] = level[now.now] + 1;
                qu.offer(new Go(go, now.now));
                beFirst = false;
            }

            if (beFirst) firsts.push(now.now);
        }

        int nowChain = -1;
        myChain = new int[size+1];
        in = new int[size+1];
        out = new int[size+1];
        nextChain = new int[size+1];
        chainDepth = new int[firsts.size()];

        Chain[] chains = new Chain[firsts.size()];
        Segment tree = Chain.tree = new Segment(size);

        Arrays.fill(myChain, -1);

        while(!firsts.isEmpty()) {
            nowChain++;
            int first = firsts.pop();
            myChain[first] = nowChain;
            chains[nowChain] = new Chain();
            chains[nowChain].lower = first;

            qu.offer(new Go(first, 0));

            while(!qu.isEmpty()) {
                Go now = qu.poll();
                chains[nowChain].upper = now.now;

                for (int go : load.get(now.now)) {
                    if (level[go] == level[now.now] - 1) {
                        real.get(go).add(now.now);

                        if (myChain[go] == -1) {
                            myChain[go] = nowChain;
                            qu.offer(new Go(go, 0));
                        } else {
                            nextChain[nowChain] = go;
                            chainDepth[nowChain] = chainDepth[myChain[go]] + 1;
                        }
                        break;
                    }
                }

            }

        }
        dfs(1);

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int value, y, lca;
            switch (m) {
                case 1:
                    // x의 서브트리에 v원을 더합니다.
                    value = Integer.parseInt(st.nextToken());
                    tree.startUpdate(in[x], out[x], 1, value);
                    break;
                case 2:
                    //x 부터 y v 더하기
                    y = Integer.parseInt(st.nextToken());
                    value = Integer.parseInt(st.nextToken());
                    lca = findLCA(x, y);

                    while (myChain[x] != myChain[lca]) {
                        chains[myChain[x]].nextUpdate(x, 1, value);
                        x = nextChain[myChain[x]];
                    }
                    while (myChain[y] != myChain[lca]) {
                        chains[myChain[y]].nextUpdate(y, 1, value);
                        y = nextChain[myChain[y]];
                    }
                    chains[myChain[lca]].inUpdate(x, y, 1, value);

                    break;
                case 3:
                    //x의 서브트리 v배
                    value = Integer.parseInt(st.nextToken());
                    tree.startUpdate(in[x], out[x], value, 0);
                    break;
                case 4:
                    //x 부터 y v배
                    y = Integer.parseInt(st.nextToken());
                    value = Integer.parseInt(st.nextToken());
                    lca = findLCA(x, y);

                    while (myChain[x] != myChain[lca]) {
                        chains[myChain[x]].nextUpdate(x, value, 0);
                        x = nextChain[myChain[x]];
                    }
                    while (myChain[y] != myChain[lca]) {
                        chains[myChain[y]].nextUpdate(y, value, 0);
                        y = nextChain[myChain[y]];
                    }
                    chains[myChain[lca]].inUpdate(x, y, value, 0);
                    break;
                case 5:
                    //x 서브트리 모든 돈의 값
                    bw.write(moding(tree.find(in[x], out[x]))+"\n");
                    break;
                case 6:
                    //x 부터 y 까지 모든 금고 돈의 값
                    y = Integer.parseInt(st.nextToken());
                    lca = findLCA(x, y);

                    int result = 0;

                    while (myChain[x] != myChain[lca]) {
                        result += chains[myChain[x]].nextAdd(x);
                        x = nextChain[myChain[x]];
                    }
                    while (myChain[y] != myChain[lca]) {
                        result += chains[myChain[y]].nextAdd(y);
                        y = nextChain[myChain[y]];
                    }

                    result += chains[myChain[x]].inAdd(x, y);
                    bw.write(moding(result)+"\n");
                    break;
            }

        }
        bw.flush();

    }
    public static int[] myChain, in, out, nextChain, chainDepth, level;
    public static long[] times = new long[62];
    public static int cnt;
    public static void dfs(int node) {
        in[node] = ++cnt;
        for (int go : real.get(node)) dfs(go);
        out[node] = cnt;
    }
    public static int findLCA (int n1, int n2) {
        if (chainDepth[myChain[n1]] > chainDepth[myChain[n2]]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        while (chainDepth[myChain[n1]] != chainDepth[myChain[n2]]) {
            n2 = nextChain[myChain[n2]];
        }

        while(myChain[n1] != myChain[n2]) {
            n1 = nextChain[myChain[n1]];
            n2 = nextChain[myChain[n2]];
        }

        return level[n1] > level[n2] ? n2 : n1;
    }
    public static long moding (int value) {
        long result = 0;
        for (int i=0;i<32;i++) {
            if ((value & 1<<i) !=0) {
                result += times[i];
            }
        }
        return result;
    }
}