import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
  public static long[] dp = new long[500050];
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    
    dp[0] = 1;
    int zero = 0;
    int[] candy = new int[10050];
    for (int i=0;i<N;i++) {
      candy[Integer.parseInt(br.readLine())]++;
    }
    
    for (int i=10000;i>=1;i--) {
      if (candy[i] == 0) continue;
      for (int k=dp.length-1;k>=i;k--) {
        for (int j=1;j<=candy[i];j++) {
          if (k-i*j<0) break;
          dp[k] += dp[k-i*j];
        }
      }
    }
    
    boolean[] eratos = new boolean[600000];
    eratos[0] = eratos[1] = true;
    for (long i=2;i<600000;i++) {
      if (!eratos[(int)i]) {
        for (long j=i*i;j<600000;j+=i) {
          eratos[(int)j]= true;
        }
      }
    }
    
    long count = 0;
    for (int i=0;i<dp.length;i++) {
      if (!eratos[i]) {
        count += dp[i];
      }
    }
    System.out.println(count * (candy[zero] + 1));
    
  }
}