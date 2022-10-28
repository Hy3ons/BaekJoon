import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Load {
  int go, cost;
  
  Load (int go, int cost) {
    this.go = go;
    this.cost = cost;
  }
}

public class Main {
  public static int mki() {
    return Integer.parseInt(st.nextToken());
  }
  public static StringTokenizer st;
  public static ArrayList<ArrayList<Load>> load, reverse;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    int N = mki(), b = mki(), s = mki(), M = mki();
    
    load = new ArrayList<>(N+1);
    reverse = new ArrayList<>(N+1);
    for (int i=0;i<=N;i++) {
      load.add(new ArrayList<>());
      reverse.add(new ArrayList<>());
    }
    
    for (int i=0;i<M;i++) {
      st = new StringTokenizer(br.readLine());
      int from = mki(), go = mki(), cost = mki();
      load.get(from).add(new Load(go, cost));
      reverse.get(go).add(new Load(from, cost));
    }
    
    int[] go = dkstra(load, b+1, N), receive = dkstra(reverse, b+1, N);
    int[] cost = new int[b];
    for (int i=0;i<b;i++) {
      cost[i] = go[i+1] + receive[i+1];
    }
    Arrays.sort(cost);
    prefix = new long[cost.length];
    for (int i=0;i<prefix.length;i++) {
      prefix[i] = cost[i] + (i == 0 ? 0 : prefix[i-1]);
    }
    
    
    for (int i=0;i<prefix.length;i++) {
      dp[0][i] = prefix[i] * i;
    }
    
    for (int i=1;i<s;i++) {
      dnc(i, i, b-1, 0, b-1);
    }
    
    System.out.println(dp[s-1][b-1]);
  }
  public static long[][] dp = new long[5050][5001];
  public static long[] prefix;
  
  public static void dnc (int step, int left, int right, int s, int e) {
    int mid = left + right >> 1;
    
    dp[step][mid] = Long.MAX_VALUE;
    int idx = -1;
    
    for (int i=s;i<=e && i < mid;i++) {
      long add = prefix[mid] - prefix[i];
      if (dp[step][mid] > dp[step-1][i] + add * (mid - i-1)) {
        dp[step][mid] = dp[step-1][i] + add * (mid - i-1);
        idx = i;
      }
    }
    
    if (left == right) return;
    
    dnc(step, left, mid, s, idx);
    dnc(step, mid+1, right, idx, e);
  }
  public static int[] dkstra (ArrayList<ArrayList<Load>> j, int start, int size) {
    int[] dp = new int[size+1];
    Arrays.fill(dp, 2147483647);
    dp[start] = 0;
  
    PriorityQueue<Load> pq = new PriorityQueue<>(new Comparator<Load>() {
      @Override
      public int compare(Load o1, Load o2) {
        return Integer.compare(o1.cost, o2.cost);
      }
    });
    
    pq.offer(new Load(start, 0));
    while(!pq.isEmpty()) {
      Load now = pq.poll();
      if (now.cost != dp[now.go]) continue;
      
      for (Load go : j.get(now.go)) {
        if (dp[go.go] > dp[now.go] + go.cost) {
          dp[go.go] = dp[now.go] + go.cost;
          pq.offer(new Load(go.go, dp[go.go]));
        }
      }
    }
    
    return dp;
  }
}