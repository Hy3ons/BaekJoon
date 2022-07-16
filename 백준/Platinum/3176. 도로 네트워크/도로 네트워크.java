import java.io.*;
import java.util.*;

class Pair {
    int now, prev;
    Pair (int now, int prev) {
        this.now = now;
        this.prev = prev;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        int[][] dp = new int[32][testcase+1];
        int[][] min = new int[32][testcase+1]
                ,max = new int[32][testcase+1];

        for (int[] arr : min) Arrays.fill(arr, Integer.MAX_VALUE);
        int[] level = new int[testcase+1];
        ArrayList<ArrayList<Pair>> load = new ArrayList<>();
        for (int i=0;i<=testcase;i++) load.add(new ArrayList<>());

        for (int i=1;i<testcase;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int go = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            int costs = Integer.parseInt(st.nextToken());

            load.get(go).add(new Pair(from, costs));
            load.get(from).add(new Pair(go, costs));
        }

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(1, 0));
        level[1] = dp[0][1] = 1;
        while(!qu.isEmpty()) {
            for (int i=qu.size();i>0;i--) {
                Pair now = qu.poll();

                for (Pair go : load.get(now.now)) {
                    if (go.now == now.prev) continue;

                    qu.offer(new Pair(go.now, now.now));
                    dp[0][go.now] = now.now;
                    level[go.now] = level[now.now] + 1;
                    min[0][go.now] = max[0][go.now] = go.prev;
                }
            }
        }

        for (int i=1;i<32;i++) {
            for (int j=1;j<testcase+1;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
                min[i][j] = Math.min(min[i-1][j], min[i-1][dp[i-1][j]]);
                max[i][j] = Math.max(max[i-1][j], max[i-1][dp[i-1][j]]);
            }
        }

        int queries = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(queries-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int result_Max = 0, result_Min = Integer.MAX_VALUE;

            int n1Lev = level[n1];
            int n2Lev = level[n2];

            if (n1Lev > n2Lev) {
                int temp = n1Lev - n2Lev;
                n1Lev = n2Lev;

                for (int i=0;i<32;i++) {
                    if ((temp&(1<<i))!=0) {
                        result_Max = Math.max(result_Max, max[i][n1]);
                        result_Min = Math.min(result_Min, min[i][n1]);
                        n1 = dp[i][n1];
                    }
                }
            }

            if (n2Lev > n1Lev) {
                int temp = n2Lev - n1Lev;
                n2Lev = n1Lev;

                for (int i=0;i<32;i++) {
                    if ((temp&(1<<i))!=0) {
                        result_Max = Math.max(result_Max, max[i][n2]);
                        result_Min = Math.min(result_Min, min[i][n2]);
                        n2 = dp[i][n2];
                    }
                }
            }

            if (n1 == n2) {
                bw.write(result_Min+" "+result_Max+"\n");
                continue;
            }

            C: while (true) {
                for (int i=31;i>=0;i--) {
                    if (dp[i][n1] != dp[i][n2]) {
                        result_Max = Math.max(result_Max, max[i][n1]);
                        result_Max = Math.max(result_Max, max[i][n2]);
                        result_Min = Math.min(result_Min, min[i][n1]);
                        result_Min = Math.min(result_Min, min[i][n2]);
                        n1 = dp[i][n1];
                        n2 = dp[i][n2];
                    }
                }
                break;
            }

            bw.write(Math.min(Math.min(result_Min, min[0][n1]), min[0][n2])
                    +" "+ Math.max(Math.max(result_Max, max[0][n1]), max[0][n2])+"\n");
        }
        bw.flush();
    }
}
