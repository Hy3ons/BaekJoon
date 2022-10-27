import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main{
  public static int[][][] dp;
  public static int N, M;
  public static ArrayList<Integer> pos = new ArrayList<>();
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    
    dp = new int[N+1][N+1][2];
    
    boolean excep = false;
    for (int i=0;i<N;i++) {
      int k = Integer.parseInt(br.readLine());
      pos.add(k);
      if (k == 0) excep = true;
    }
    
    if (!excep) pos.add(0);
    Collections.sort(pos);
    
    int idx = -1;
    for (int i=0;i<pos.size();i++) {
      if (pos.get(i) == 0) {
        idx = i;
        break;
      }
    }
    
    clear();
    if (!excep) N++;
    int ans = 0;
    
    for (int i=1;i<N;i++) {
      //이번에 최적화 되지 않은 수에 대해, 그전에 이미 최적화 했다고 보장함.
      clear();
      ans = Math.max(ans, M*i - dP(idx, idx, i, 0));
    }
    if(excep) ans += M;
    System.out.println(ans);
  }
  public static void clear () {
    for(int[][] dd : dp) for(int[] d : dd) Arrays.fill(d, -1);
  }
  
  public static int dP (int l, int r, int c, int left) {
    if (c == 0) return 0;
    
    if (dp[l][r][left] != -1) return dp[l][r][left];
    dp[l][r][left] = Integer.MAX_VALUE;
    
    int now = left == 0 ? l : r;
    if (l != 0)
      dp[l][r][left] = Math.min(dp[l][r][left], dP(l-1, r, c-1, 0) + getDist(now, l-1) * c);
  
    if (r != N-1)
      dp[l][r][left] = Math.min(dp[l][r][left], dP(l, r+1, c-1, 1) + getDist(now, r+1) * c);
  
    return dp[l][r][left];
  }
  public static int getDist (int n1, int n2) {
    return Math.abs(pos.get(n1)- pos.get(n2));
  }
}