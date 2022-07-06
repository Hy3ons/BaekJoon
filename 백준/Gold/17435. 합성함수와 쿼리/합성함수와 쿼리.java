import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        int[][] dp = new int[32][testcase+1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=1;i<=testcase;i++) dp[0][i] = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(br.readLine());

        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=testcase;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
            }
        }

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());
            int multi = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            multi--;
            int temp = dp[0][value];
            for (int j=0;j<31;j++) {
                if ((multi&(1<<j))!=0) {
                    temp = dp[j][temp];
                }
            }
            bw.write(temp+"\n");
        }
        bw.flush();

    }
}