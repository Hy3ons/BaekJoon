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
        long[][] cost = new long[32][testcase+1];
        int[] level = new int[testcase+1];
        ArrayList<ArrayList<Pair>> load = new ArrayList<>();
        for (int i=0;i<=testcase;i++) load.add(new ArrayList<>());

        int[] arr = new int[testcase];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(br.readLine());

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
                    cost[0][go.now] = go.prev;
                }
            }
        }

        for (int i=1;i<32;i++) {
            for (int j=1;j<testcase+1;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
                cost[i][j] = cost[i-1][j] + cost[i-1][dp[i-1][j]];
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int k=0;k<arr.length;k++) {
            int temp = arr[k];

            int nowLoc = k+1;

            for (int i=31;i>=0;i--) {
                if (temp >= cost[i][nowLoc]) {
                    temp -= cost[i][nowLoc];
                    nowLoc = dp[i][nowLoc];
                }
            }

            bw.write(nowLoc+"\n");
        }
        bw.flush();
    }
}
