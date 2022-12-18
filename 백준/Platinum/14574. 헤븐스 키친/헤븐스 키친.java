import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Tuple {
  final int n1, n2;
  final long cost;
  Tuple (int n1, int n2, long cost) {
    this.n1 = n1;
    this.n2 = n2;
    this.cost = cost;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
  
    int[] p = new int[N] , c = new int[N];
    ArrayList<Tuple> arr = new ArrayList<>();
    
    parent = new int[N+1];
    for (int i=0;i<parent.length;i++) parent[i] = i;
    
    for (int i=0;i<N;i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      
      p[i] = Integer.parseInt(st.nextToken());
      c[i] = Integer.parseInt(st.nextToken());
    }
    
    for (int i=0;i<N;i++) {
      for (int j=i+1;j<N;j++) {
        arr.add(new Tuple(i, j, (c[i] + c[j]) / Math.abs(p[i] - p[j])));
      }
    }
    
    arr.sort(new Comparator<Tuple>() {
      @Override
      public int compare(Tuple o1, Tuple o2) {
        return Long.compare(o2.cost, o1.cost);
      }
    });
    
    load = new ArrayList<>();
    for (int i=0;i<N;i++) {
      load.add(new ArrayList<>());
    }
    
    long res = 0;
    
    for (Tuple t : arr) {
      if (find(t.n1) != find(t.n2)) {
        res += t.cost;
        union(t.n1, t.n2);
        
        load.get(t.n1).add(t.n2);
        load.get(t.n2).add(t.n1);
      }
    }
    
    bw.write(res+"\n");
    dfs(0, -1);
    
    bw.flush();
  }
  
  public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
  
  public static void dfs (int node, int prev) throws IOException{
    for (int go : load.get(node)) if (go != prev){
      dfs(go, node);
      bw.write(node+1+" "+(go+1)+"\n");
    }
  }
  
  public static int[] parent;
  public static ArrayList<ArrayList<Integer>> load;
  
  public static int find (int node) {
    if (parent[node] == node) return node;
    return parent[node] = find(parent[node]);
  }
  
  public static void union (int n1, int n2) {
    int p1 = find(n1), p2 = find(n2);
    parent[p1] = p2;
  }
}