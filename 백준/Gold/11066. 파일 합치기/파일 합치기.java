import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static int[] prefix;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        while(testcase-->0) {
            int n = Integer.parseInt(br.readLine());
            StringTokenizer st=  new StringTokenizer(br.readLine());

            int[] arr = new int[n];
            for (int i=0;i<n;i++) arr[i] = Integer.parseInt(st.nextToken());

            long[][] dp = new long[n][n];
            prefix = new int[n+1];
            for (int i=0;i<n;i++) {
                prefix[i+1] = prefix[i] + arr[i];
            }


            for (int i=1;i<dp.length;i++){
                for (int j=0;j+i<dp.length;j++) {
                    //write code find Optimized dp[j][j+i]
                    dp[j][j+i] = Long.MAX_VALUE;
                    for (int k=0;k<i;k++) {
                        dp[j][j+i] = Math.min(dp[j][j+i], dp[j][j+k] + dp[j+k+1][j+i]);
                    }
                    dp[j][j+i] += prefix[j+i+1] - prefix[j];
                }
            }

            bw.write(dp[0][n-1]+"\n");
        }
        bw.flush();
    }
}