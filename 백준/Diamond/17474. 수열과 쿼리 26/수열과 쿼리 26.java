import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

class Seg {
  public static long MAX = Long.MAX_VALUE;
  
  private Node[] tree;
  private long[] lazy;
  private class Node {
    private long cnt, sum, max, subMax = -1;
    Node () {
    
    }
    
    public void setValue (long value) {
      cnt = 1;
      sum = max = value;
    }
    
  }
  
  public static Node makeNode (Node res, Node left, Node right) {
    if (left.max != right.max) {
      res.max = Math.max(left.max, right.max);
      res.subMax = Math.min(left.max, right.max);
      res.subMax = Math.max(res.subMax, left.subMax);
      res.subMax = Math.max(res.subMax, right.subMax);
      
      res.cnt = res.max == right.max ? right.cnt : left.cnt;
      res.sum = left.sum + right.sum;
    } else {
      res.max = Math.max(left.max, right.max);
      res.subMax = Math.max(left.subMax, right.subMax);
      
      res.cnt = left.cnt + right.cnt;
      res.sum = left.sum + right.sum;
    }
    
    return res;
  }
  
  public int start = 1;
  
  public Seg (int[] arr) {
    while(start < arr.length) start <<= 1;
    tree = new Node[start << 1];
    for (int i=0;i<tree.length;i++) tree[i] = new Node();
    
    for (int i=0;i<arr.length;i++) tree[i + start].setValue(arr[i]);
    
    for (int i=start >> 1;i!=0;i>>=1) {
      for (int j=i;j < i << 1;j++) {
        makeNode(tree[j], tree[j << 1], tree[j << 1 | 1]);
      }
    }
    
    lazy = new long[start << 1];
  
    Arrays.fill(lazy, MAX);
  }
  
  public boolean update (int node, int s, int e, int left, int right, int value) {
    if (lazy[node] != MAX) propagate(node, left, right);
    
    if (e < left || right < s) return false;
    
    if (s <= left && right <= e) {
      lazy[node] = Math.min(lazy[node], value);
      propagate(node, left, right);
      return true;
    }
    
    int mid = left + right >> 1;
    
    update(node << 1, s, e, left, mid, value);
    update(node << 1 | 1, s, e, mid+1, right, value);
    
    makeNode(tree[node], tree[node << 1], tree[node << 1 | 1]);
    return true;
  }
  
  private Node query (int node, int s, int e, int left, int right) {
    if (lazy[node] != MAX) propagate(node, left, right);
    
    if (e < left || right < s) return new Node();
    
    if (s <= left && right <= e) return tree[node];
    
    int mid = left + right >> 1;
    
    return makeNode(new Node(), query(node << 1, s, e, left, mid)
        , query(node << 1 | 1, s, e, mid+1, right));
  }
  
  private boolean propagate (int node, int left, int right) {
    if (tree[node].max <= lazy[node]) {
      lazy[node] = MAX;
      return false;
    }
    
    if (tree[node].max > lazy[node] && tree[node].subMax <= lazy[node]) {
      tree[node].sum += tree[node].cnt * (lazy[node] - tree[node].max);
      tree[node].max = lazy[node];
      
      if (node < start) {
        lazy[node << 1] = Math.min(lazy[node << 1], lazy[node]);
        lazy[node << 1 |1] = Math.min(lazy[node << 1 | 1], lazy[node]);
      }
    } else {
      lazy[node << 1] = Math.min(lazy[node << 1], lazy[node]);
      lazy[node << 1 |1] = Math.min(lazy[node << 1 | 1], lazy[node]);
      
      int mid = left + right >> 1;
      
      propagate(node << 1, left, mid);
      propagate(node << 1 | 1, mid + 1, right);
      
      makeNode(tree[node], tree[node << 1], tree[node << 1 | 1]);
    }
    
    lazy[node] = MAX;
    
    return false;
  }
  
  public long answer (int left, int right, int method) {
    Node res = query(1, left, right, 1, start);
    return method == 2 ? res.max : res.sum;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());
    int[] arr = new int[N];
  
    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i=0;i<N;i++) {
      arr[i] = Integer.parseInt(st.nextToken());
    }
    
    Seg tree = new Seg(arr);
    
    int Q = Integer.parseInt(br.readLine());
  
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(Q-->0) {
      st = new StringTokenizer(br.readLine());
      int M = Integer.parseInt(st.nextToken());
      int left = Integer.parseInt(st.nextToken()), right = Integer.parseInt(st.nextToken());
      
      if (M == 1) {
        int value = Integer.parseInt(st.nextToken());
        tree.update(1, left, right, 1, tree.start, value);
      } else {
        bw.write(tree.answer(left, right, M)+"\n");
      }
      
    }
    
    bw.flush();
  }
}