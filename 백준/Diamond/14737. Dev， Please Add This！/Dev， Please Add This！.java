import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
  public static int N, M;
  public static char[][] board;
  public static ArrayList<ArrayList<Integer>> load, reverse, sccLoad;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    board = new char[N][];
    
    load = new ArrayList<>(N*M);
    reverse = new ArrayList<>(N*M);
    sccLoad = new ArrayList<>(N*M);
    
    for (int i=0;i<N*M;i++) {
      load.add(new ArrayList<>());
      reverse.add(new ArrayList<>());
      sccLoad.add(new ArrayList<>());
    }
    
    for (int i=0;i<N;i++) board[i] = br.readLine().toCharArray();
    
    for (int i=0;i<N;i++) {
      for (int j=0;j<M;j++) {
        if (board[i][j] == '#') continue;
        
        for (int k=0;k<4;k++) {
          int d = 0;
          
          for (;;d++) {
            int x = i + dx[k] * d;
            int y = j + dy[k] * d;
            
            if (x == -1 || x == N || y == -1 || y == M || board[x][y] == '#') break;
          }
          if (--d == 0) continue;
          
          int from = encode(i, j), to = encode(i + dx[k] * d, j + dy[k] * d);
          load.get(from).add(to);
          reverse.get(to).add(from);
        }
        
      }
    }
  
    
    SSC();
    solve();
  }
  public static int encode (int x, int y) {
    return x * M + y;
  }
  
  
  public static int[] dx = {0,0,-1,1}, dy = {-1,1,0,0};
  public static boolean[] visit = new boolean[3000];
  public static int[] mySCC = new int[3000], inStar = new int[3000];
  public static HashSet<Integer>[] hs = new HashSet[3000];
  public static ArrayList<Integer> starts = new ArrayList<>(), stars = new ArrayList<>();
  public static ArrayList<ArrayList<Integer>> SSC = new ArrayList<>();
  
  public static void SSC () {
    for (int i=0;i<N*M;i++) if (!visit[i] && board[i / M][i % M] != '#'){
      visit[i] = true;
      dfs(i);
    }
    
    for (int i=0;i<hs.length;i++) hs[i] = new HashSet<>();
  
    Arrays.fill(visit, false);
    
    Collections.reverse(starts);
    for (int i : starts) if (!visit[i] && board[i / M][i % M] != '#'){
      visit[i] = true;
      ArrayList<Integer> al = new ArrayList<>();
      
      SSC.add(al);
      dfs2(i, al);
    }
    
    for (int i=0;i< SSC.size();i++) {
      for (int e : SSC.get(i)) {
        mySCC[e] = i;
      }
    }
    
    for (int i=0;i<N*M;i++) {
      for (int go : load.get(i)) {
        if (mySCC[i] == mySCC[go]) continue;
        
        sccLoad.get(mySCC[i]).add(mySCC[go]);
        
        //최적화
        
        if (board[i / M][i % M] == '*') {
          hs[mySCC[go]].add(i);
        }
      }
  
    }
  
    for (int i=0;i< sccLoad.size();i++) {
      HashSet<Integer> hs = new HashSet<>(sccLoad.get(i));
      sccLoad.set(i, new ArrayList<>(hs));
    }
  }
  
  public static void dfs (int node) {
    for (int go : load.get(node)) if (!visit[go]) {
      visit[go] = true;
      dfs(go);
    }
    starts.add(node);
  }
  
  public static void dfs2 (int node, ArrayList<Integer> arr) {
    arr.add(node);
    
    for (int go : reverse.get(node)) if (!visit[go]) {
      visit[go] = true;
      dfs2(go, arr);
    }
  }
  
  public static boolean[] star = new boolean[3000];
  public static int start;
  public static void solve () {
    Arrays.fill(visit, false);
    int goal = 0;
    
    for (int i=0;i<N;i++) {
      for (int j=0;j<M;j++) {
        if (board[i][j] == 'O') {
          start = encode(i, j);
          
        } else if (board[i][j] == '*') {
          hs[mySCC[encode(i, j)]].add(encode(i, j));
          
          inStar[mySCC[encode(i, j)]]++;
          goal++;
        }
      }
    }
    HashSet<Integer> temp = new HashSet<>(hs[mySCC[start]]);
    
    try {
      if (dfs4(mySCC[start], temp) != goal) throw new Exception();
      System.out.println("YES");
    } catch (Exception exception) {
      System.out.println("NO");
    }
  }
  
  public static boolean dfs3 (int node) throws Exception {
    boolean type = star[node];
    
    int count = 0;
    for (int go : sccLoad.get(node)) {
      boolean res = dfs3(go);
      if (res) {
        if (++count == 2) {
          throw new Exception();
        }
      }
      
      type |= res;
    }
    
    return type;
  }
  
  public static int dfs4 (int node, HashSet<Integer> set) throws Exception {
    int m = set.size();
    
    for (int go : sccLoad.get(node)) {
      HashSet<Integer> temp = new HashSet<>(set);
      temp.addAll(hs[go]);
      m = Math.max(m, dfs4(go, temp));
    }
    
    return m;
  }
  
  
}