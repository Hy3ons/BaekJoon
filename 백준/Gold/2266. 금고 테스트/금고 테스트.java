import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main{
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());
  
    long[][] dp = new long[600][600];
    for(long[] d : dp) Arrays.fill(d, Long.MAX_VALUE-3);
    for (int i=1;i<600;i++) {
      dp[i][1] = i;
      dp[1][i] = 1;
    }
    for (int i=2;i<550;i++) {
      for (int j=1;j<550;j++) {
        //dp[j][i]
        for (int k=1;k<=j;k++) {
          dp[j][i] = Math.min(dp[j][i], Math.max(dp[k-1][i-1]+1, dp[j-k][i]+1));
        }
      }
    }
    
    for (int i=1;i<550;i++) {
      for (int j=2;j<550;j++) {
        dp[i][j] = Math.min(dp[i][j-1], dp[i][j]);
      }
    }
    
    
    
    System.out.println(dp[N][K]);
  }
}