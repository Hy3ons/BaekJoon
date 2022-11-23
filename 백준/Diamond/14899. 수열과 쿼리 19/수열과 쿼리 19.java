import java.io.*;
import java.util.StringTokenizer;

class Node {
  long sum, min = Integer.MAX_VALUE, max;
  
  public void setValue (int value) {
    sum = min = max = value;
  }
  
  Node (){
  
  }
  
  Node (int value) {
    setValue(value);
  }
  
  public static Node makeNode (Node res, Node left, Node right) {
    if (left == null) {
      res.sum = right.sum;
      res.min = right.min;
      res.max = right.max;
    } else if (right == null) {
      res.sum = left.sum;
      res.min = left.min;
      res.max = left.max;
    } else {
      res.sum = left.sum + right.sum;
      res.max = Math.max(left.max, right.max);
      res.min = Math.min(left.min, right.min);
    }
    return res;
  }
}

class Seg {
  private Node[] tree;
  private long[] lazy;
  
  public int start = 1;
  
  Seg (int[] arr) {
    while(start < arr.length) start<<=1;
    
    tree = new Node[start << 1];
    lazy = new long[start << 1];
    
    for (int i=0;i<tree.length;i++) tree[i] = new Node();
    
    for (int i=0;i<arr.length;i++) tree[i + start].setValue(arr[i]);
    
    for (int i= start >> 1; i!=0;i >>= 1) {
      for (int j=i;j< i << 1;j++) {
        Node.makeNode(tree[j], tree[j << 1], tree[j << 1 | 1]);
      }
    }
  }
  
  private boolean tagCondition (Node node, long value) {
    long d = (long) Math.floor(node.max / (double) value);
    long d2 = (long) Math.floor(node.min / (double) value);
    
    return node.max - d == node.min - d2;
  }
  
  public void divUpdate(int node, int s, int e, int left, int right, int value) {
    if (lazy[node] != 0) propagate(node, left, right);
    if (e < left || right < s) return;;
    
    if (s <= left && right <=e && tagCondition(tree[node], value)) {
      lazy[node] += (long) (Math.floor(tree[node].max / (double) value)) - tree[node].max;
      propagate(node, left, right);
      return;
    }
    
    int mid = left + right >> 1;
    
    divUpdate(node << 1, s, e, left, mid, value);
    divUpdate(node << 1 | 1, s, e, mid+1, right, value);
    
    Node.makeNode(tree[node], tree[node << 1], tree[node << 1 | 1]);
  }
  
  private void propagate(int node, int left, int right) {
    tree[node].max += lazy[node];
    tree[node].min += lazy[node];
    tree[node].sum += lazy[node] * (right - left + 1);
    
    if (node < start) {
      lazy[node << 1] += lazy[node];
      lazy[node << 1 | 1] += lazy[node];
    }
    
    lazy[node] = 0;
  }
  
  public Node query(int node, int s, int e, int left, int right) {
    if (e < left || right < s) return null;
    
    if (lazy[node] != 0) propagate(node, left, right);
    
    if (s <= left && right <=e) return tree[node];
    
    int mid = left + right >> 1;
    return Node.makeNode(new Node(), query(node << 1, s, e, left, mid)
        , query(node << 1 | 1, s, e, mid+1, right));
  }
  
  public void addUpdate (int node, int s, int e, int left, int right, int value) {
    if (lazy[node] != 0) propagate(node, left, right);
    if (e < left || right < s) return;;
    
    if (s <= left && right <= e) {
      lazy[node] += value;
      propagate(node, left, right);
      return;
    }
    
    int mid = left + right >> 1;
    
    addUpdate(node << 1, s, e, left, mid, value);
    addUpdate(node << 1 | 1, s, e, mid+1, right, value);
    
    Node.makeNode(tree[node], tree[node << 1], tree[node << 1 | 1]);
  }
  
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N = Integer.parseInt(st.nextToken()), Q = Integer.parseInt(st.nextToken());
    
    int[] arr = new int[N];
    st = new StringTokenizer(br.readLine());
    
    for (int i=0;i<arr.length;i++) {
      arr[i] = Integer.parseInt(st.nextToken());
    }
    
    Seg tree = new Seg(arr);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    while(Q-->0) {
      st = new StringTokenizer(br.readLine());
      int M = Integer.parseInt(st.nextToken());
      int left = Integer.parseInt(st.nextToken()) + 1;
      int right = Integer.parseInt(st.nextToken()) + 1;
      
      if (M == 1) {
        int value = Integer.parseInt(st.nextToken());
        tree.addUpdate(1, left, right, 1, tree.start, value);
      } else if (M == 2) {
        int value = Integer.parseInt(st.nextToken());
        tree.divUpdate(1, left, right, 1, tree.start, value);
      } else if (M == 3){
        bw.write(tree.query(1, left, right, 1, tree.start).min+"\n");
      } else {
        bw.write(tree.query(1, left, right, 1, tree.start).sum+"\n");
      }
    }
    
    bw.flush();
  }
}