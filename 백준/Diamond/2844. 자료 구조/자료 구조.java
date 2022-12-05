import java.io.*;
import java.util.StringTokenizer;

class Node  {
  Node parent, left, right;
  long size = 1, sum, value;
  long a, b, lazy;
  boolean lazied;
  
  Node (int value) {
    sum = this.value = value;
    a = 1; b= 0; lazy = 0;
  }
  
  public void update () {
    size = 1;
    a = 1; b= 0; lazy = 0;
    
    sum = value;
    if (left != null) {
      left.propagate();
      size += left.size;
      sum += left.sum;
    }
    
    if (right != null) {
      right.propagate();
      size += right.size;
      sum += right.sum;
    }
  }
  
  public void setRange(int value) {
    a = lazy = 0;
    b = value;
    lazied = true;
  }
  
  public void propagate () {
    if (lazied) {
      long order = left != null ? left.size + 1: 1;
      
      value = value * a + b + lazy * (order);
      sum = sum * a + size * b + lazy * (size * (size + 1) / 2);
  
      if (left != null) {
        left.a *= a;
        left.b *= a;
        left.lazy *= a;
  
        left.b += b;
  
        left.lazy += lazy;
        left.lazied = true;
      }
      
      if (right != null) {
        right.a *= a;
        right.b *= a;
        right.lazy *= a;
  
        right.b += b + order * lazy;
  
        right.lazy += lazy;
        right.lazied = true;
      }
    }
  
    a = 1; b= 0; lazy = 0;
    lazied = false;
  }
  
}

class Splay {
  Node root;
  private int size;
  
  Splay (int[] arr) {
    this.size = arr.length;
    
    Node[] nodes = new Node[size];
    
    for (int i=0;i<nodes.length;i++) {
      nodes[i] = new Node(arr[i]);
    }
    
    for (int i=size-1;i>0;i--) {
      nodes[i-1].right = nodes[i];
      nodes[i].parent = nodes[i-1];
      
      nodes[i-1].update();
    }
    
    root = nodes[0];
  }
  
  
  private void rotate (Node a) {
    Node parent = a.parent;
    Node temp = null;
    
    parent.propagate();
    a.propagate();
    
    if (a == parent.left) {
      temp = parent.left = a.right;
      
      a.right = parent;
    } else if (a == parent.right) {
      temp = parent.right = a.left;
      
      a.left = parent;
    }
    
    a.parent = parent.parent;
    parent.parent = a;
    
    if (temp != null) {
      temp.parent = parent;
    }
    
    if (a.parent != null) {
      if (a.parent.left == parent) {
        a.parent.left = a;
      } else {
        a.parent.right = a;
      }
    } else {
      root = a;
    }
    
    parent.update();
    a.update();
  }
  
  public void splay (Node a) {
    while(a.parent != null) {
      if (a.parent.parent != null) rotate((a.parent.left == a) == (a.parent.parent.left == a.parent) ? a.parent : a);
      rotate(a);
    }
  }
  
  public void insert (int idx, Node temp) {
    if (idx == 0) {
      findKth(1);
      
      root.parent = temp;
      temp.right= root;
      
    } else if (idx == size) {
      findKth(size);
      
      root.parent = temp;
      temp.left = root;
      
    } else {
      //left : idx right : idx+1
      findKth(idx);
      
      temp.right = root.right;
      temp.right.parent = temp;
      
      temp.left = root;
      temp.left.parent = temp;
      
      root.right = null;
      root.update();
    }
    
    temp.update();
    root = temp;
    
    size++;
  }
  
  public void findKth (int order) {
    Node now = root;
    
    while(true) {
      now.propagate();
      if (now.right == null && now.left == null) break;
      
      if (now.left != null && now.right != null ) {
        if (now.left.size + 1 == order) break;
        
        if (now.left.size < order) {
          order -= now.left.size + 1;
          now = now.right;
        } else {
          now = now.left;
        }
      } else if (now.left == null) {
        if (order == 1) break;
        order--;
        now = now.right;
      } else {
        if (now.left.size + 1 == order) break;
        now = now.left;
      }
    }
    
    splay(now);
  }
  
  public Node findNode (int left, int right) {
    if (left == 1 && right == size) {
      root.propagate();
      return root;
    } else if (left == 1) {
      findKth(right+1);
      root.propagate();
      root.left.propagate();
      return root.left;
    } else if (right == size) {
      findKth(left-1);
      
      root.propagate();
      root.right.propagate();
      return root.right;
    } else {
      findKth(left-1);
      Node original = root;
      root.propagate();
      root = root.right;
      root.parent = null;
      
      findKth(right-left +2);
      
      Node ans = root.left;
      
      original.right = root;
      root.parent = original;
      
      original.update();
      root = original;
      
      return ans;
    }
  }
  
  public void inOrder (Node now, BufferedWriter bw) throws IOException {
    now.propagate();
    if (now.left != null) inOrder(now.left, bw);
    bw.write(now.value+" ");
    if (now.right != null) inOrder(now.right, bw);
  }
  
  public long query (int left, int right) {
    return findNode(left, right).sum;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N = Integer.parseInt(st.nextToken()), Q = Integer.parseInt(st.nextToken());
    st = new StringTokenizer(br.readLine());
    
    int[] arr = new int[N];
    for (int i=0;i<N;i++) {
      arr[i] = Integer.parseInt(st.nextToken());
    }
    
    Splay tree = new Splay(arr);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(Q-->0) {
      st = new StringTokenizer(br.readLine());
      int m = Integer.parseInt(st.nextToken());
      
      if (m == 1) {
        int left = Integer.parseInt(st.nextToken()), right = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        Node temp = tree.findNode(left, right);
        temp.setRange(x);
  
        temp.propagate();
  
        while(temp != null) {
          temp.update();
          temp = temp.parent;
        }
        
      } else if (m == 2) {
        int left = Integer.parseInt(st.nextToken()), right = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        Node temp = tree.findNode(left, right);
        
        temp.lazied = true;
        temp.lazy = x;
        
        temp.propagate();
  
        while(temp != null) {
          temp.update();
          temp = temp.parent;
        }
      
      } else if (m == 3) {
        int idx = Integer.parseInt(st.nextToken());
        int value = Integer.parseInt(st.nextToken());
        
        tree.insert(idx-1, new Node(value));
      } else {
        int left = Integer.parseInt(st.nextToken()), right = Integer.parseInt(st.nextToken());
        bw.write(tree.query(left, right)+"\n");
      }
    }
    
    bw.flush();
  }
}