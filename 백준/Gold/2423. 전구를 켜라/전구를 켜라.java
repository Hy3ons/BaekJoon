import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair {
  int x, y, cost;
  Pair (int x, int y, int cost) {
    this.x=x;this.y=y;this.cost=cost;
  }
}

public class Main{
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
    
    board = new char[a][];
    for (int i=0;i<a;i++) board[i] = br.readLine().toCharArray();
    
    int[][] dp = new int[a+1][b+1];
    for (int[] d : dp) Arrays.fill(d, Integer.MAX_VALUE);
    dp[0][0] = 0;
    a++;
    b++;
  
    PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
      @Override
      public int compare(Pair o1, Pair o2) {
        return Integer.compare(o1.cost, o2.cost);
      }
    });
    
    pq.offer(new Pair(0, 0, dp[0][0]));
    int[] dx = {-1,-1,1,1}, dy = {-1,1,-1,1};
    while(!pq.isEmpty()) {
      Pair now = pq.poll();
      if (now.cost != dp[now.x][now.y]) continue;
      
      for (int i=0;i<4;i++) {
        int x = now.x + dx[i];
        int y = now.y + dy[i];
        
        if (x < 0 || y < 0 || x >= a|| y >=b) continue;
        if (dp[x][y] > dp[now.x][now.y] + cost(i, now.x, now.y)) {
          dp[x][y] = dp[now.x][now.y] + cost(i, now.x, now.y);
          pq.offer(new Pair(x, y, dp[x][y]));
        }
      }
    }
    if (dp[a-1][b-1] == Integer.MAX_VALUE) {
      System.out.println("NO SOLUTION");
      return;
    }
    System.out.println(dp[a-1][b-1]);
    
  }
  public static char[][] board;
  public static int cost (int direction, int x, int y) {
    if (direction == 0) {
      return board[x-1][y-1] == '/' ? 1 : 0;
    } else if (direction == 1) {
      return board[x-1][y] == '/' ? 0 : 1;
    } else if (direction == 2) {
      return board[x][y-1] == '/' ? 0 : 1;
    } else {
      return board[x][y] == '/' ? 1 : 0;
    }
  }
}