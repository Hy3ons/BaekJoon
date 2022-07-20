import java.io.*;
import java.util.*;

class Pair {
    int now, prev;
    Pair (int now, int prev) {
        this.now = now;
        this.prev = prev;
    }
    Pair (int now, long prev) {
        this.now = now;
        this.prev = (int)prev;
    }
}

class Edge {
    int n1, n2;
    long worth;
    boolean used;
    Edge (int n1, int n2, int worth) {
        this.n1 = n1;
        this.n2 = n2;
        this.worth = worth;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertex = Integer.parseInt(st.nextToken());
        int lines = Integer.parseInt(st.nextToken());
        parent = new int[vertex+1];
        for (int i=0;i< parent.length;i++) parent[i] = i;
        load = new ArrayList<>(vertex+1);
        for (int i=0;i<=vertex;i++) load.add(new ArrayList<>());

        Edge[] edges = new Edge[lines];
        for (int i=0;i<edges.length;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken())
                    , n2 = Integer.parseInt(st.nextToken())
                    , worth = Integer.parseInt(st.nextToken());

            edges[i] = new Edge(n1, n2, worth);
        }

        Edge[] ordered = edges.clone();
        Arrays.sort(ordered, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return Long.compare(o1.worth, o2.worth);
            }
        });

        long result = 0;

        for (int i=0;i< ordered.length;i++) {
            if (find(ordered[i].n1) == find(ordered[i].n2)) continue;

            ordered[i].used = true;
            load.get(ordered[i].n1).add(new Pair(ordered[i].n2, ordered[i].worth));
            load.get(ordered[i].n2).add(new Pair(ordered[i].n1, ordered[i].worth));

            union(ordered[i].n1, ordered[i].n2);
            result += ordered[i].worth;
        }
        int[] level = new int[vertex+1];
        int[][] dp = new int[30][vertex+1];
        long[][] maxCost = new long[30][vertex+1];
        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(1, 0));
        level[1] = 1;

        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            for (Pair go : load.get(now.now)) {
                if (go.now == now.prev) continue;

                if (level[go.now] == 0) {
                    dp[0][go.now] = now.now;
                    maxCost[0][go.now] = go.prev;
                    level[go.now] = level[now.now] + 1;
                    qu.offer(new Pair(go.now, now.now));
                }
            }
        }
        dp[0][1] = 1;
        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=vertex;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
                maxCost[i][j] = Math.max(maxCost[i-1][j], maxCost[i-1][dp[i-1][j]]);
            }
        }


        for (Edge edge : edges) {
            if (edge.used) {
                bw.write(result + "\n");
                continue;
            }

            int n1 = edge.n1;
            int n2 = edge.n2;

            if (level[n1] > level[n2]) {
                int temp = n1;
                n1 = n2;
                n2 = temp;
            }
            int distance = level[n2] - level[n1];
            long max = 0;
            for (int j = 0; j < 30; j++) {
                if ((distance & 1 << j) != 0) {
                    max = Math.max(max, maxCost[j][n2]);
                    n2 = dp[j][n2];
                }
            }

            for (int j = 29; j >= 0; j--) {
                if (dp[j][n1] != dp[j][n2]) {
                    max = Math.max(max, Math.max(maxCost[j][n1], maxCost[j][n2]));
                    n1 = dp[j][n1];
                    n2 = dp[j][n2];
                }
            }

            if (n1 != n2) {
                max = Math.max(maxCost[0][n1], max);
                max = Math.max(maxCost[0][n2], max);
            }

            bw.write(result - max + edge.worth + "\n");
        }
        bw.flush();
    }
    public static int[] parent;
    public static ArrayList<ArrayList<Pair>> load;
    public static void union (int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p2 > p1) {
            parent[p2] = p1;
        } else {
            parent[p1] = p2;
        }
    }
    public static int find (int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }
}
