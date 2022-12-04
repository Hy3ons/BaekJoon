import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Node {
  Node parent, left, right;
  int size = 1, value, min;
  boolean reverse;
  Node (int value) {
    min = this.value = value;
  }
  
  public void update () {
    size = 1;
    min = value;
    
    if (left != null) {
      size += left.size;
      min = Math.min(min, left.min);
    }
    
    if (right != null) {
      size += right.size;
      min = Math.min(min, right.min);
    }
  }
  
  public void propagate () {
    if (reverse) {
      Node temp = left;
      left = right;
      right = temp;
      
      if (left != null) left.reverse ^= true;
      if (right != null) right.reverse ^= true;
    }
    reverse = false;
  }
}

class Splay {
  public Node root;
  public int size;
  Splay (ArrayList<Pair> al) {
    Node[] arr = new Node[al.size()];
    this.size = arr.length;
    
    for (int i=0;i<al.size();i++) {
      arr[i] = new Node(al.get(i).myIdx);
    }
    
    for (int i=al.size()-1;i>0;i--) {
      arr[i-1].right = arr[i];
      arr[i].parent = arr[i-1];
      
      arr[i-1].update();
    }
    
    root = arr[0];
  }
  
  public void propagate(Node a) {
    if (a.reverse) {
      Node temp = a.left;
      a.left = a.right;
      a.right = temp;
      
      if (a.left != null) {
        a.left.reverse ^= true;
      }
      
      if (a.right != null) {
        a.right.reverse ^= true;
      }
    }
    
    a.reverse = false;
  }
  
  public void rotate (Node a) {
    Node p = a.parent, temp = null;
    
    propagate(p);
    propagate(a);
    
    if (p.left == a) {
      temp = p.left = a.right;
      a.right = p;
    } else {
      temp = p.right = a.left;
      a.left = p;
    }
    
    a.parent = p.parent;
    p.parent = a;
    
    if (temp != null) {
      temp.parent = p;
    }
    
    if (a.parent != null) {
      if (a.parent.left == p) {
        a.parent.left = a;
      } else {
        a.parent.right = a;
      }
    } else {
      root = a;
    }
    
    p.update();
    a.update();
  }
  
  public void splay (Node a) {
    while(a.parent != null) {
      if(a.parent.parent != null) rotate(((a.parent.parent.left == a.parent) == (a.parent.left == a)) ? a.parent : a);
      rotate(a);
    }
  }
  
  public void reverseRange (int left, int right) {
    if (left == right) return;
    
    if (left == 1 && right == size) {
      root.reverse ^= true;
      root.propagate();
      
    } else if (left == 1) {
      findKth(right + 1, root);
      
      root.propagate();
      root.left.reverse ^= true;
      root.left.propagate();
      
      root.update();
    } else if (right == size) {
      findKth(left - 1, root);
      
      root.propagate();
      root.right.reverse ^= true;
      root.right.propagate();
      
      root.update();
    } else {
      findKth(left-1, root);
      Node origin = root;
      root.propagate();
      
      root = origin.right;
      root.parent = null;
      
      findKth(right-left + 2, root);
      
      origin.right = root;
      Node answer = root.left;
      answer.reverse ^= true;
      
      propagate(answer);
      root.update();
      origin.update();
      
      root.parent = origin;
      root = origin;
    }
  }
  
  public void findKth (int order, Node now) {
    propagate(now);
    
    if (now.left == null && now.right == null) {
      splay(now);
      return;
    }
    
    if (now.left == null) {
      if (order == 1) {
        splay(now);
        return;
      }
      
      findKth(order-1, now.right);
    } else if (now.right == null) {
      if (now.size == order) {
        splay(now);
      } else {
        findKth(order, now.left);
      }
    } else {
      if (now.left.size + 1 == order) {
        splay(now);
        return;
      }
      
      if (now.left.size < order) {
        findKth(order - now.left.size - 1, now.right);
      } else {
        findKth(order, now.left);
      }
    }
  }
  
  public void findMin () {
    Node now = root;
    
    propagate(now);
  
    while (now.min != now.value) {
      if (now.left != null && now.left.min == now.min) {
        now = now.left;
      } else {
        now = now.right;
      }
    }
    
    splay(now);
  }
}

class Pair {
  int value, idx, myIdx;
  
  Pair (int value, int idx) {
    this.value = value;
    this.idx = idx;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    while(true) {
      int N = Integer.parseInt(br.readLine());
      if (N == 0) break;
  
      ArrayList<Pair> al = new ArrayList<Pair>(), s = new ArrayList<>();
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i=0;i<N;i++) {
        Pair now = new Pair(Integer.parseInt(st.nextToken()), i);
        al.add(now);
        s.add(now);
      }
      
      s.sort(new Comparator<Pair>() {
        @Override
        public int compare(Pair o1, Pair o2) {
          if (o1.value == o2.value) return Integer.compare(o1.idx, o2.idx);
          return Integer.compare(o1.value, o2.value);
        }
      });
      
      for (int i=0;i<s.size();i++)
        s.get(i).myIdx = i;
      
      Splay tree = new Splay(al);
      
      for (int i=0;i<N-1;i++) {
        if (i == 0) {
          tree.findMin();
          int idx = tree.root.left == null ? 1 : tree.root.left.size+1;
          
          bw.write(idx+" ");
          tree.reverseRange(1, idx);
        } else {
          tree.findKth(i, tree.root);
          Node original = tree.root;
          
          tree.root = original.right;
          tree.root.parent = null;
  
          tree.findMin();
          
          int idx = tree.root.left == null ? 1 : tree.root.left.size+1;
          
          original.right = tree.root;
          tree.root.parent = original;
          
          tree.root = original;
          tree.root.update();
          
          tree.reverseRange(i+1, idx+i);
          
          bw.write(idx+i+" ");
        }
        
      }
      
      bw.write(N+"\n");
      
      
    }
    
    bw.flush();
    
  }
}