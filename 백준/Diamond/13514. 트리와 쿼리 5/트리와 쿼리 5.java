import java.io.*;
import java.util.*;

class Pair {
    int value, dist;
    Pair (int value, int dist) {
        this.value = value;
        this.dist = dist;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int testcase = Integer.parseInt(br.readLine());
        load = new ArrayList<>(testcase+1);

        for (int i=0;i<=testcase;i++) {
            load.add(new ArrayList<>());
        }

        for (int i=1;i<testcase;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }
        size = new int[testcase+1];
        isC = new boolean[testcase + 1];
        up = new int[testcase+1];
        turn = new boolean[testcase+1];
        dp = new int[31][testcase+1];
        level = new int[testcase+1];

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(1, 0));

        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            for (int go : load.get(now.value)) {
                if (go == now.dist) continue;

                level[go] = level[now.value] + 1;
                dp[0][go] = now.value;
                qu.offer(new Pair(go, now.value));
            }
        }

        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=testcase;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
            }
        }

        sets = new ArrayList<>(testcase+1);

        for (int i=0;i<=testcase;i++) sets.add(new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.dist, o2.dist);
            }
        }));

        function(1, 0);

        int queries = Integer.parseInt(br.readLine());
        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int node = Integer.parseInt(st.nextToken());
            int temp = node;

            if (M == 1) {
                //색깔을 바꾸는 것
                if (!turn[node]) {
                    turn[node] = true;

                    while(temp != 0) {
                        sets.get(temp).offer(new Pair(node, findDist(temp, node)));
                        temp = up[temp];
                    }
                } else {
                    turn[node] = false;
                }

            } else {
                int result = Integer.MAX_VALUE;

                while(temp != 0) {
                    while(!sets.get(temp).isEmpty()) {
                        if (turn[sets.get(temp).peek().value]) {
                           result = Math.min(result, findDist(node, temp) + sets.get(temp).peek().dist);
                           break;
                        } else {
                            sets.get(temp).poll();
                        }
                    }
                    temp = up[temp];
                }

                bw.write((result == Integer.MAX_VALUE ? -1 : result) +"\n");
            }
        }
        bw.flush();
    }
    public static ArrayList<ArrayList<Integer>> load;
    public static ArrayList<PriorityQueue<Pair>> sets;
    public static int[] up, level, size;
    public static int[][] dp;
    public static boolean[] isC, turn;

    public static int findLCA (int n1, int n2) {
        if (level[n1] > level[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int dist = level[n2] - level[n1];
        for (int i=30;i>=0;i--) {
            if ((dist & 1 << i) != 0) {
                n2 = dp[i][n2];
            }
        }

        for (int i=30;i>=0;i--) {
            if (dp[i][n1] != dp[i][n2]) {
                n1 = dp[i][n1];
                n2 = dp[i][n2];
            }
        }

        return n1 != n2 ? dp[0][n1] : n1;
    }

    public static int findDist (int n1, int n2) {
        int lca = findLCA(n1, n2);

        return level[n1] + level[n2] - level[lca] * 2;
    }
    public static void function (int anyNode, int prev) {
        int limit = getSize(anyNode, -1);
        int roid = findRoid(anyNode, -1, limit);

        if (isC[roid]) return;
        isC[roid] = true;

        up[roid] = prev;

        for (int go : load.get(roid)) {
            if (!isC[go]) {
                function(go, roid);
            }
        }
    }
    public static int getSize (int now, int prev) {
        size[now] = 1;
        for (int go : load.get(now)) {
            if (go != prev && !isC[go]) {
                size[now] += getSize(go, now);
            }
        }
        return size[now];
    }
    public static int findRoid (int now, int prev, int limit) {
        for (int go : load.get(now)) {
            if (go != prev && !isC[go] && size[go] * 2 > limit) {
                return findRoid(go, now, limit);
            }
        }
        return now;
    }
}
