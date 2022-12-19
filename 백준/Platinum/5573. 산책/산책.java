import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    final int h = Integer.parseInt(st.nextToken()), w = Integer.parseInt(st.nextToken()), N = Integer.parseInt(st.nextToken());
    
    int[][] arr = new int[h][w], dp = new int[h][w];
    dp[0][0] = N-1;
    
    for (int i=0;i<h;i++) {
      st = new StringTokenizer(br.readLine());
      for (int j=0;j<w;j++) {
        arr[i][j] = Integer.parseInt(st.nextToken());
      }
    }
  
    for (int i=0;i<h;i++) {
      for (int j=0;j<w;j++) {
        int mx = (int) Math.floor((dp[i][j]-1) / 2.0) + 1;
        int mn = dp[i][j] >> 1;
        
        if (arr[i][j] == 1) {
          if (j+1 < w)
            dp[i][j+1] += mx;
          if (i+1 < h)
            dp[i+1][j] += mn;
        } else {
          if (j+1 < w)
            dp[i][j+1] += mn;
          if (i+1 < h)
            dp[i+1][j] += mx;
        }
        
        if ((dp[i][j] & 1) == 1) arr[i][j] ^= 1;
      }
    }
    
//    for (int i=0;i<h;i++) {
//      for (int j=0;j<w;j++) {
//        System.out.print(arr[i][j]+" ");
//      }
//      System.out.println();
//    }
    
    int x = 0, y = 0;
    
    while(x != h && y != w) {
      if (arr[x][y] == 1) y++;
      else x++;
    }
    
    System.out.println(x+1 +" "+(y+1));
  }
}