import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());
    
    int[] prefix = new int[N+1];
    
    st = new StringTokenizer(br.readLine());
    int max = 0;
    for (int i=0;i<N;i++) {
      prefix[i+1] = prefix[i] + Integer.parseInt(st.nextToken());
      max = Math.max(prefix[i+1]-prefix[i], max);
    }
    
    if (prefix[N] < K) {
      System.out.println(0);
    } else if (prefix[N] == K) {
      System.out.println(N);
    } else if (max >= K) {
      System.out.println(1);
    } else {
      int ans = Integer.MAX_VALUE;
      for (int l=0,r=1;r<prefix.length;) {
        int now = prefix[r] - prefix[l];
        if (now >= K) {
          ans = Math.min(ans, r-l);
          l++;
        } else {
          r++;
        }
      }
      System.out.println(ans);
    }
    
    
    
  }
}