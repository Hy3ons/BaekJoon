import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
  public static ArrayList<ArrayList<Integer>> load;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    
    load = new ArrayList<>(N+1);
    for (int i=0;i<=N;i++) load.add(new ArrayList<>());
    
    for (int i=1;i<N;i++) {
      StringTokenizer st= new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
      load.get(n1).add(n2);
      load.get(n2).add(n1);
    }
    dfs(1, 0);
    
    for (int i=1;i<dp.length;i++) {
      for (int j=1;j<=N;j++) {
        dp[i][j] = dp[i-1][dp[i-1][j]];
      }
    }
    
    int K = Integer.parseInt(br.readLine());
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(K-->0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken())
          , n2 = Integer.parseInt(st.nextToken()), n3 = Integer.parseInt(st.nextToken());
      
      if (check(n1, n2, n3, bw) || check(n2, n3, n1, bw) || check(n1, n3, n2, bw)) {
      } else {
        bw.write("-1\n");
      }
    }
    bw.flush();
  }
  public static int[][] dp = new int[28][100001];
  public static int[] level = new int[100001];
  public static boolean check (int n1, int n2, int n3, BufferedWriter bw) throws IOException{
    int lca = getLCA(n1, n2);
    int dist = level[n1] + level[n2] - (level[lca] << 1);
    if ((dist &1) == 1) {
      return false;
    }
    int middle = goUp(level[n1] > level[n2] ? n1 : n2, dist>>1);
  
    int lcas = getLCA(middle, n3);
    int dd = level[middle] + level[n3] - (level[lcas] << 1);
    if (dd == dist >> 1) {
      bw.write(middle+"\n");
      return true;
    } else {
      return false;
    }
  }
  public static void dfs (int node, int prev) {
    for (int go : load.get(node)) if (go != prev) {
      level[go] = level[node] + 1;
      dp[0][go] = node;
      dfs(go, node);
    }
  }
  public static int goUp (int node, int dist ) {
    for (int i=27;i>=0;i--) {
      if ((dist & 1<< i) != 0) node = dp[i][node];
    }
    return node;
  }
  
  public static int getLCA (int n1, int n2) {
    if (level[n1] > level[n2]) {
      int temp = n1;
      n1 = n2;
      n2 = temp;
    }
    
    n2 = goUp(n2, level[n2] - level[n1]);
  
    for (int i=27;i>=0;i--) {
      if (dp[i][n1] != dp[i][n2]) {
        n1 = dp[i][n1];
        n2 = dp[i][n2];
      }
    }
    
    return n1 == n2 ? n1 : dp[0][n1];
  }
  
}