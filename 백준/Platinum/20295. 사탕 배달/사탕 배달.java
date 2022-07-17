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
        int[][] candy = new int[32][testcase+1];
        int[] level = new int[testcase+1];
        ArrayList<ArrayList<Integer>> load = new ArrayList<>();
        for (int i=0;i<=testcase;i++) load.add(new ArrayList<>());

        int[] arr = new int[testcase+1];
        int existCandy = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=1;i<arr.length;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            existCandy |= 1 << arr[i];
        }

        for (int i=1;i<testcase;i++) {
            st = new StringTokenizer(br.readLine());

            int go = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());

            load.get(go).add(from);
            load.get(from).add(go);
        }

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(1, 0));
        level[1] = dp[0][1] = 1;
        while(!qu.isEmpty()) {
            for (int i=qu.size();i>0;i--) {
                Pair now = qu.poll();

                for (int go : load.get(now.now)) {
                    if (go == now.prev) continue;

                    qu.offer(new Pair(go, now.now));
                    dp[0][go] = now.now;
                    level[go] = level[now.now] + 1;
                    candy[0][go] = (1 << arr[go]) | (1<< arr[now.now]);
                }
            }
        }

        for (int i=1;i<32;i++) {
            for (int j=1;j<testcase+1;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
                candy[i][j] = candy[i-1][j] | candy[i-1][dp[i-1][j]];
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int query = Integer.parseInt(br.readLine());
        int now = -1;
        if (query > 0) {
            st = new StringTokenizer(br.readLine());
            now = Integer.parseInt(st.nextToken());
            int want = Integer.parseInt(st.nextToken());

            if ((existCandy&(1<<want)) !=0) {
                bw.write("PLAY\n");
            } else {
                bw.write("CRY\n");
            }
        }

        for (int k=1;k<query;k++) {
            st = new StringTokenizer(br.readLine());

            int goal = Integer.parseInt(st.nextToken());
            int want = Integer.parseInt(st.nextToken());

            int temp = goal;
            int temp2 = now;

            int nowHave = (1<<arr[temp]) | (1<<arr[temp2]);

            if (level[temp] > level[temp2]) {
                int afs = temp;
                temp = temp2;
                temp2 = afs;
            }
            //temp 레벨이 더적음.

            int levelDistance = level[temp2] - level[temp];
            for (int i=31;i>=0;i--) {
                if ((levelDistance&(1<<i))!=0) {
                    nowHave |= candy[i][temp2];
                    temp2 = dp[i][temp2];
                }
            }

            for (int i=31;i>=0;i--) {
                if (dp[i][temp] != dp[i][temp2]) {
                    nowHave |= candy[i][temp2] | candy[i][temp];
                    temp = dp[i][temp];
                    temp2 = dp[i][temp2];

                }
            }

            if (temp != temp2)
                nowHave |= candy[0][temp];

            if ((nowHave &(1<<want))!=0) {
                bw.write("PLAY\n");
            } else {
                bw.write("CRY\n");
            }
            now = goal;
        }
        bw.flush();
    }
}
