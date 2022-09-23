import java.io.*;
import java.util.*;

class Line {
    int n1, n2, w, idx;
    boolean chose;
    Line (int n1, int n2, int w, int idx) {
        this.n1 = Math.min(n1, n2);
        this.n2 = Math.max(n1, n2);
        this.w= w;
        this.idx = idx;
    }
}

class Pair {
    int go, weight, idx;
    Pair (int go, int weight, int idx) {
        this.go = go;
        this.weight = weight;
        this.idx = idx;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        parent = new int[N+1];
        clear();

        ArrayList<Line> lines = new ArrayList<>(M);
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            lines.add(new Line(n1, n2, w, i));
        }

        lines.sort(new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return Integer.compare(o1.w, o2.w);
            }
        });

        long min = 0;
        long max = 0;
        lineC = new boolean[lines.size()+1];
        for (int i=0;i<=N;i++) load.add(new ArrayList<>());

        for (int i=0;i<lines.size();i++) {
            if (find(lines.get(i).n1) != find(lines.get(i).n2)) {
                union(lines.get(i).n1, lines.get(i).n2);
                min += lines.get(i).w;
                lines.get(i).chose = true;
                lineC[lines.get(i).idx] = true;
                load.get(lines.get(i).n1).add(new Pair(lines.get(i).n2, lines.get(i).w, lines.get(i).idx));
                load.get(lines.get(i).n2).add(new Pair(lines.get(i).n1, lines.get(i).w, lines.get(i).idx));
            }
        }
        clear();
        for (int i=lines.size()-1;i>=0;i--) {
            if (find(lines.get(i).n1) != find(lines.get(i).n2)) {
                union(lines.get(i).n1, lines.get(i).n2);
                max += lines.get(i).w;
            }
        }

        level = new int[N+1];
        dp = new long[31][N+1];
        cost = new long[31][N+1];

        dfs(1, 0);
        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=N;j++) {
                dp[i][j] = dp[i-1][(int) dp[i-1][j]];
                cost[i][j] = Math.max(cost[i-1][j], cost[i-1][(int) dp[i-1][j]]);
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i=0;i<lines.size();i++) {
            if (lines.get(i).chose) continue;
            long c = getCOST(lines.get(i).n1, lines.get(i).n2);
            if (min - c + lines.get(i).w == max) continue;

            int lca = getLCA(lines.get(i).n1, lines.get(i).n2);
            try {
                finals(lines.get(i).n1, c, lca);
                finals(lines.get(i).n2, c, lca);
            } catch (Exception ex) {
                bw.write("YES\n");

                lineC[lines.get(i).idx] = true;
                for (int j=0;j<lineC.length;j++) {
                    if (lineC[j]) bw.write(j+1+" ");
                }
                bw.flush();
                System.exit(0);
            }
        }

        System.out.println("NO");
    }
    public static boolean[] lineC;

    public static int[] parent, level;
    public static long[][] dp, cost;
    public static ArrayList<ArrayList<Pair>> load = new ArrayList<>();
    public static void finals (int node, long cost,int lca) throws Exception{
        if (node == lca) return;
        for(Pair go : load.get(node)) {
            if (level[go.go] < level[node]) {
                if (go.weight == cost) {
                    lineC[go.idx] = false;
                    throw new Exception();
                }

                finals(go.go, cost, lca);
            }
        }
    }

    public static int getLCA (int n1, int n2) {
        if (level[n1] > level[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int dist = level[n2] - level[n1];
        for (int i=29;i>=0;i--) {
            if ((dist & 1<< i) != 0) n2 = (int) dp[i][n2];
        }

        for (int i=29;i>=0;i--) {
            if (dp[i][n1] != dp[i][n2]) {
                n1 = (int) dp[i][n1];
                n2 = (int) dp[i][n2];
            }
        }

        return n1 == n2 ? n1 : (int) dp[0][n1];
    }

    public static long getCOST (int n1, int n2 ){
        int lca = getLCA(n1, n2);

        int dist = level[n1] - level[lca];

        long costs = Long.MIN_VALUE;
        for (int i=29;i>=0;i--) {
            if ((dist & 1<< i) != 0) {
                costs = Math.max(costs, cost[i][n1]);
                n1 = (int) dp[i][n1];
            }
        }
        dist = level[n2]- level[lca];
        for (int i=29;i>=0;i--) {
            if ((dist & 1<< i) != 0) {
                costs = Math.max(costs, cost[i][n2]);
                n2 = (int) dp[i][n2];
            }
        }
        return costs;
    }
    public static void dfs(int node, int prev) {
        for (Pair go : load.get(node)) {
            if (go.go == prev) continue;
            level[go.go] = level[node] + 1;
            dp[0][go.go] = node;
            cost[0][go.go] = go.weight;
            dfs(go.go, node);
        }
    }
    public static int find(int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }
    public static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        parent[p2] = p1;
    }
    public static void clear() {
        for (int i=0;i<parent.length;i++) parent[i] = i;
    }
}