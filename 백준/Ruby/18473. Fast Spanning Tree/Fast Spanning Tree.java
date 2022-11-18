import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Box {
  int idx, n1, n2;
  long weight, limit;
  Box (int idx, int n1, int n2, long weight, long limit) {
    this.idx = idx;
    this.n1 = n1;
    this.n2 = n2;
    this.weight = weight;
    this.limit = limit;
  }
  
  Box (int idx, int n1, int n2) {
    this(idx, n1, n2, 0, 0);
  }
  
  
}

public class Main {
  public static final int MAX = 300003;
  public static int[] parent = new int[MAX];
  public static long[] weight = new long[MAX];
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    for (int i=0;i<parent.length;i++) parent[i] = i;
  
    ArrayList<Integer> answer = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    final int N = Integer.parseInt(st.nextToken()), Q = Integer.parseInt(st.nextToken());
    st = new StringTokenizer(br.readLine());
    
    for (int i=1;i<=N;i++) weight[i] = Integer.parseInt(st.nextToken());
  
    PriorityQueue<Box> ready = new PriorityQueue<>(new Comparator<Box>() {
      @Override
      public int compare(Box o1, Box o2) {
        return Integer.compare(o1.idx, o2.idx);
      }
    });
    
    for (int i=0;i<pq.length;i++) pq[i] = new PriorityQueue<>(new Comparator<Box>() {
      @Override
      public int compare(Box o1, Box o2) {
        return Long.compare(o1.weight, o2.weight);
      }
    });
    
    for (int i=0;i<Q;i++) {
      st = new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
      int deadLine = Integer.parseInt(st.nextToken());
      
      if (weight[n1] + weight[n2] >= deadLine) {
        ready.add(new Box(i+1, n1, n2));
        continue;
      }
      
      pq[n1].add(new Box(i+1, n1, n2, cal(weight[n1], deadLine, n1, n2) , deadLine));
      pq[n2].add(new Box(i+1, n2, n1, cal(weight[n2], deadLine, n1, n2), deadLine));
    }
    
    boolean[] used = new boolean[MAX];
    
    while(!ready.isEmpty()) {
      Box now = ready.poll();
      if (find(now.n1) == find(now.n2)) continue;
      
      answer.add(now.idx);
      union(now.n1, now.n2);
      
      int node = find(now.n1);
      
      while(!pq[node].isEmpty()) {
        if (weight[node] < pq[node].peek().weight) break;
        
        Box noo = pq[node].poll();
        if (weight[find(noo.n1)] + weight[find(noo.n2)] >= noo.limit) {
          ready.add(noo);
        } else {
          pq[find(noo.n1)].add(new Box(noo.idx, find(noo.n1), find(noo.n2), cal(weight[find(noo.n1)]
              , noo.limit, find(noo.n1), find(noo.n2)), noo.limit));
          pq[find(noo.n2)].add(new Box(noo.idx, find(noo.n2), find(noo.n1), cal(weight[find(noo.n2)]
              , noo.limit, find(noo.n1), find(noo.n2)), noo.limit));
        }
      }
    }
  
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    bw.write(answer.size()+"\n");
    for (int e : answer) bw.write(e+" ");
    bw.flush();
    
    
  }
  
  public static long cal (long base, long limit, int n1, int n2) {
    return base + ((limit - weight[n1] - weight[n2])-1) / 2 + 1;
  }
  
  public static PriorityQueue<Box>[] pq = new PriorityQueue[MAX];
  public static int find(int node) {
    if (parent[node] == node) return node;
    return parent[node] = find(parent[node]);
  }
  
  public static void union (int n1, int n2) {
    int p1 = find(n1);
    int p2 = find(n2);
    
    if (pq[p1].size() > pq[p2].size()) {
      int temp = p1;
      p1 = p2;
      p2 = temp;
    }
    
    weight[p2] += weight[p1];
    parent[p1] = p2;
    pq[p2].addAll(pq[p1]);
    
    //p2 에 집어넣으면 된다. S to L
  }
}