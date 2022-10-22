import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        load = new ArrayList<>(100001);
        for (int i=0;i<=100000;i++) load.add(new ArrayList<>());

        for (int i=1;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        dfs(1, 0);
        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=N;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
            }
        }

        int K = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(K-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int root = Integer.parseInt(st.nextToken()), n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            if (level[n1] > level[n2]) {
                int temp = n1;
                n1 = n2;
                n2 = temp;
            }


            int lca = getLCA(n1, n2);

            if (!isIn(lca, root)) {
                bw.write(lca+"\n");
                continue;
            }

            if (lca == n1) {
                if (beside(n1, n2, root)) {
                    bw.write(root+"\n");
                } else if (isIn(n2, root)) {
                    bw.write(n2+"\n");
                } else {
                    bw.write(getLCA(n2, root)+"\n");
                }
            } else {
                if (beside(lca, n1, root) || beside(lca, n2, root)) {
                    bw.write(root+"\n");
                } else if (isIn(n1, root)){
                    bw.write(n1+"\n");
                } else if (isIn(n2, root)) {
                    bw.write(n2+"\n");
                } else {
                    int lca1 = getLCA(n1, root), lca2 = getLCA(n2, root);
                    int ans= level[lca1] > level[lca2] ? lca1 : lca2;
                    bw.write(ans+"\n");
                }

            }

        }
        bw.flush();
    }
    public static int count;
    public static int[][] dp = new int[28][100001];
    public static int[] level = new int[100001], in = new int[100001], out = new int[100001];
    public static boolean isIn (int node, int target) {
        return in[node] <= in[target] && in[target] <= out[node];
    }
    public static boolean beside (int n1, int n2, int root) {
        if (level[root] >= level[n2]) return false;
        if (level[n1] >= level[root]) return false;

        int dist = level[n2] - level[root];
        int s = goUp(n2, dist);

        return s == root;
    }
    public static void dfs (int node, int prev) {
        in[node] = ++count;
        for (int go : load.get(node)) if (go != prev) {
            level[go] = level[node] + 1;
            dp[0][go] = node;
            dfs(go, node);
        }
        out[node] = count;
    }
    public static int goUp (int node, int dist ) {
        for (int i=27;i>=0;i--) {
            if ((dist & 1<< i) != 0) node = dp[i][node];
        }
        return node;
    }

    public static int getLCA (int n1, int n2) {
        if (level[n1] > level[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        n2 = goUp(n2, level[n2] - level[n1]);

        for (int i=27;i>=0;i--) {
            if (dp[i][n1] != dp[i][n2]) {
                n1 = dp[i][n1];
                n2 = dp[i][n2];
            }
        }

        return n1 == n2 ? n1 : dp[0][n1];
    }
}