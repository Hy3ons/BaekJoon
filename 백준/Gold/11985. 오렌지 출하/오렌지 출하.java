import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    
    int[] orange = new int[N];
    
    for (int i=0;i<orange.length;i++) orange[i] = Integer.parseInt(br.readLine());
    long[] dp = new long[N];
    Arrays.fill(dp, Long.MAX_VALUE);
    
    dp[0] = K;
    for (int i=1;i<dp.length;i++) {
      long max = orange[i], min = orange[i];
      for (int j=0;j<M && i>=j;j++) {
        max = Math.max(max, orange[i-j]);
        min = Math.min(min, orange[i-j]);
        dp[i] = Math.min(dp[i], (i-j-1 >= 0 ? dp[i-j-1] : 0) + (j+1) * (max - min) + K);
      }
    }
    
    System.out.println(dp[N-1]);
    
  }
}
