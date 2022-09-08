import javax.sound.sampled.Line;
import java.io.*;
import java.util.*;

class Pair {
    int now , prev;
    Pair (int now , int prev) {
        this.now = now;
        this.prev = prev;
    }
}

class Load {
    int n1, n2, weight;
    Load (int n1, int n2, int weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }
}

public class Main {
    public static int[] parent,level;
    public static int[][] cost, dp, secMin;
    public static ArrayList<ArrayList<Pair>> line;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int size = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());

        ArrayList<Load> checked = new ArrayList<>();

        Load[] loads = new Load[edges];
        parent = new int[size+1];
        for (int i=0;i<parent.length;i++) parent[i] = i;

        line = new ArrayList<>(size+1);
        for (int i=0;i<=size;i++) line.add(new ArrayList<>());

        for (int i=0;i<loads.length;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            loads[i] = new Load(n1, n2, weight);
        }

        Arrays.sort(loads, new Comparator<Load>() {
            @Override
            public int compare(Load o1, Load o2) {
                return Integer.compare(o1.weight, o2.weight);
            }
        });
        long result = 0;

        for (Load l : loads) {
            if (find(l.n1) == find(l.n2)) {
                checked.add(l);
                continue;
            }

            union(l.n1, l.n2);
            line.get(l.n1).add(new Pair(l.n2, l.weight));
            line.get(l.n2).add(new Pair(l.n1, l.weight));

            result += l.weight;
        }

        for (int i=1;i<=size;i++) {
            if (find(i) != 1) {
                System.out.println(-1);
                return;
            }
        }

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(1, 0));
        dp = new int[30][size+1];
        cost = new int[30][size+1];
        secMin = new int[30][size+1];
        level = new int[size+1];

        Arrays.fill(secMin[0], -1);


        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            for (Pair go : line.get(now.now)) {
                if (go.now == now.prev) continue;

                qu.offer(new Pair(go.now, now.now));
                dp[0][go.now] = now.now;
                cost[0][go.now] = go.prev;
                level[go.now] = level[now.now] + 1;
            }
        }

        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=size;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
                cost[i][j] = Math.max(cost[i-1][j], cost[i-1][dp[i-1][j]]);
            }
        }
        for (int i=1;i<=size;i++) {
            secMin[1][i] = Math.min(cost[0][i], cost[0][dp[0][i]]);
            if (secMin[1][i] == cost[1][i])
                secMin[1][i] = -1;
        }
        for (int i=2;i<dp.length;i++) {
            for (int j=1;j<=size;j++) {
                secMin[i][j] = Math.max(secMin[i-1][j], secMin[i-1][dp[i-1][j]]);
                if (cost[i][j] != cost[i-1][j])
                    secMin[i][j] = Math.max(cost[i-1][j], secMin[i][j]);
                if (cost[i][j] != cost[i-1][dp[i-1][j]])
                    secMin[i][j] = Math.max(cost[i-1][dp[i-1][j]], secMin[i][j]);
            }
        }

        for (Load check : checked) {
            int n1 = check.n1;
            int n2 = check.n2;

            int lca = findLCA(n1, n2);

            max = -1;

            down(n1, level[n1] - level[lca], check.weight);
            down(n2, level[n2] - level[lca], check.weight);

            if (max != -1) {
                min = Math.min(result - max + check.weight, min);
            }
        }

        if (min == Long.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(min);
        }
    }
    public static long min = Long.MAX_VALUE, max;
    public static int find (int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }
    public static void union (int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p2 > p1)
            parent[p2] = p1;
        else
            parent[p1] = p2;
    }
    public static int findLCA (int n1, int n2) {
        if (level[n1] > level[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int distance  = level[n2] - level[n1];
        for (int i=29;i>=0;i--) {
            if ((distance & 1<< i) !=0)
                n2 = dp[i][n2];
        }

        for (int i=29;i>=0;i--) {
            if (dp[i][n1] != dp[i][n2]) {
                n1 = dp[i][n1];
                n2 = dp[i][n2];
            }
        }
        return n1 != n2 ? dp[0][n1] : n1;

    }
    public static void down(int node, int d, int weight) {
        for (int i=29;i>=0;i--) {
            if ((d & 1<<i ) !=0) {
                if (cost[i][node] != weight)
                    max = Math.max(max, cost[i][node]);

                max = Math.max(secMin[i][node], max);
                node = dp[i][node];
            }
        }
    }

}