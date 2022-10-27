import java.io.*;
import java.util.StringTokenizer;

class Seg {
  class Node {
    class Nod {
      Nod left = null, right = null;
      int value;
      public void sub () {
        left = new Nod();
        right = new Nod();
      }
    }
    
    Nod root = new Nod();
    int s = 0, e = 100002;
    
    public void update (Nod node, int s, int e, int left, int right) {
      if (e < left || right < s) return;
      
      if (s <= left && right <= e) {
        node.value++;
        return;
      }
      
      int mid = left + right >> 1;
      if (node.left == null) node.sub();
      update(node.left, s, e, left, mid);
      update(node.right, s, e, mid+1, right);
      node.value = node.left.value + node.right.value;
    }
    public long query (Nod node, int s, int e, int left, int right) {
      if (right < s || e < left) return 0;
      
      if (s <= left && right <= e) return node.value;
      
      if (node.left == null) return 0;
      int mid = left + right >> 1;
      
      return query(node.left, s, e, left, mid)
          + query(node.right, s, e, mid+1, right);
      
    }
  }
  
  Node[] nodes;
  int start = 1;
  Seg () {
    while(start < 100001) start<<=1;
    
    nodes = new Node[start << 1];
    for (int i=0;i<nodes.length;i++) nodes[i] = new Node();
  }
  
  public void update (int x, int y) {
    for(int idx = x + start; idx !=0 ; idx >>= 1) {
      nodes[idx].update(nodes[idx].root, y, y, 0, nodes[idx].e);
    }
  }
  public long query (int node, int s, int e, int left, int right, int y1, int y2) {
    if (e < left || right < s) return 0;
    
    if (s <= left && right <= e) {
      return nodes[node].query(nodes[node].root, y1, y2, 0, nodes[node].e);
    }
    int mid = left + right >> 1;
    
    return query(node*2, s, e, left, mid, y1, y2)
        + query(node*2+1, s, e, mid+1, right, y1, y2);
  }
  
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    int testcase = Integer.parseInt(br.readLine());
    
    while(testcase-->0) {
      System.gc();
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
      
      Seg tree = new Seg();
      for (int i=0;i<N;i++) {
        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
        tree.update(x, y);
      }
      
      long ans = 0;
      while(M-->0) {
        st = new StringTokenizer(br.readLine());
        int x1 = Integer.parseInt(st.nextToken()), x2 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken()), y2 = Integer.parseInt(st.nextToken());
        
        ans += tree.query(1, x1, x2, 0, tree.start-1, y1, y2);
      }
      bw.write(ans+"\n");
    }
    bw.flush();
  }
}