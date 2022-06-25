import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken())
                , b = Integer.parseInt(st.nextToken());

        int[][] cost = new int[a][a];
        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<a;j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int tryTime = a-b;
        if (tryTime == 0) {
            System.out.println(0);
            return;
        }
        int p_test = 1<<a;
        int[] dp = new int[p_test];
        boolean[] visited = new boolean[p_test];
        visited[0] = true;

        for (int T=0;T<tryTime;T++) {
            for (int i=0;i<p_test;i++) {
                if (Integer.bitCount(i)!=T) continue;
                if (!visited[i]) continue;

                for (int j=0;j<a;j++) {
                    if ((i&(1<<j))!=0) continue;
                    for (int k=0;k<a;k++) {
                        //j 를 k로! 이때 k의 비트는 꺼져 있어야 한다!
                        //옮긴 필드의 비트는 j번째 비트는 켜버려야 한다!
                        if (k==j) continue;
                        if ((i&(1<<k))!=0) continue;

                        if (visited[i|(1<<j)]) {
                            dp[i|(1<<j)] = Math.min(dp[i|(1<<j)], dp[i] + cost[j][k]);
                        } else {
                            visited[i|(1<<j)] = true;
                            dp[i|(1<<j)] = dp[i] + cost[j][k];
                        }

                    }
                }
            }
        }

        int result = Integer.MAX_VALUE;

        for (int i=0;i<p_test;i++) {
            if ((Integer.bitCount(i) == tryTime) && visited[i]) {
                result = Math.min(result, dp[i]);
            }
        }
        System.out.println(result);

    }
}
