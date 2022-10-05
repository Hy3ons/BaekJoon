import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

class Line {
  int x, y;
  Line (int x, int y) {
    this.x=x;this.y=y;
  }
}

public class Main {
  public static ArrayList<ArrayList<Integer>> load;
  public static int[] parent = new int[500001];
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N = Integer.parseInt(st.nextToken());
    int queries = Integer.parseInt(st.nextToken());
    
    for (int i=0;i<parent.length;i++) parent[i] = i;
    int lx = -1, ly = -1;
    
    Line[] lines = new Line[N];
    load = new ArrayList<>(N+1);
    for (int i=0;i<=N;i++) load.add(new ArrayList<>());
    
    for (int i=0;i<N;i++) {
      st = new StringTokenizer(br.readLine());
      int n1= Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
      if (find(n1) != find(n2)) {
        union(n1, n2);
        load.get(n1).add(n2);
        load.get(n2).add(n1);
      } else {
        lx = n1;
        ly = n2;
      }
      
      lines[i] = new Line(n1, n2);
    }
    dfs(1, 0);
    if (level[lx] > level[ly]) {
      int temp = lx;
      lx = ly;
      ly = temp;
    }
    
    int dist = level[ly] - level[lx];
    HashSet<Integer> ans = new HashSet<>();
    
    for (int i=0;i<dist;i++) {
      ans.add(ly);
      ly = up[ly];
    }
    
    while(ly != lx) {
      ans.add(ly);
      ans.add(lx);
      ly = up[ly];
      lx = up[lx];
    }
    ans.add(lx);
    for (int i=0;i<parent.length;i++) parent[i] = i;
    
    ArrayList<Integer> k = new ArrayList<>(ans);
    int any = 300001;
    for (int e : k) {
      ser(e, -1, ans, any++);
    }
    
    int warning = find(k.get(0));
    //warning 집합은 사이클 집합.
    
    
    while(queries-->0) {
      st = new StringTokenizer(br.readLine());
      int n1= Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
      int p1 = find(n1);
      int p2 = find(n2);
      
      if (p1 == p2) {
        bw.write("1\n");
      } else {
        bw.write("2\n");
      }
    }
    bw.flush();
    
    
  }
  
  public static int[] level = new int[200001] , up = new int[200001];
  public static void dfs (int node, int prev) {
    level[node] = level[prev] + 1;
    for (int go : load.get(node)) if (go != prev ) {
      up[go] = node;
      dfs(go, node);
    }
  }
  public static int find (int node) {
    if (parent[node] == node) return node;
    return parent[node] = find(parent[node]);
  }
  
  public static void union (int n1, int n2) {
    int p1 = find(n1);
    int p2 = find(n2);
    
    parent[p2] = p1;
  }
  
  public static void ser (int node, int prev, HashSet<Integer> warning, int root) {
    union(node, root);
    for (int go : load.get(node)) if (go != prev && !warning.contains(go)) {
      ser(go, node, warning, root);
    }
  }
}