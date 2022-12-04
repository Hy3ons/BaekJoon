import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

class Node  {
  Node parent, left, right;
  int value, size = 1;
  
  Node (int value) {
    this.value = value;
  }
  
  public void update () {
    size = 1;
    if (left != null) {
      size += left.size;
    }
    
    if (right != null) {
      size += right.size;
    }
    
  }
}

class Splay {
  Node root;
  private int size;
  private Node[] arr;
  
  Splay (int size) {
    arr = new Node[size+1];
    this.size = size;
    
    for (int i=1;i<=size;i++) {
      arr[i] = new Node(i);
    }
    
    for (int i=size;i>1;i--) {
      arr[i-1].right = arr[i];
      arr[i].parent = arr[i-1];
      
      arr[i-1].update();
    }
    
    root = arr[1];
  }
  
  
  public void rotate (Node a) {
    Node parent = a.parent;
    Node temp = null;
    
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
  
  public void delete (int idx) {
    if (idx == 0) {
      findKth(idx+1);
      root = root.right;
      root.parent = null;
    } else if (idx == size-1) {
      findKth(size);
      root = root.left;
      root.parent = null;
    } else {
      findKth(idx+1);
      Node target = root;
      
      root = target.left;
      root.parent = null;
      
      findKth(idx);
      root.right = target.right;
      target.right.parent = root;
      
      root.update();
    }
    
    size--;
  }
  
  public void findKth (int order) {
    Node now = root;
    
    while(true) {
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
  
  public int query (int value, int goal) {
    splay(arr[goal]);
    int goalIdx = root.left == null ? 1 : root.left.size + 1;
    
    splay(arr[value]);
    int nowIdx = root.left == null ? 0 : root.left.size;
    
    int answer = goalIdx - nowIdx;
    
    if (answer == 0) return 0;
    
    if (answer > 0) {
      insert(goalIdx, arr[value] = new Node(value));
      delete(nowIdx);
      answer--;
    } else {
      delete(nowIdx);
      insert(goalIdx, arr[value] = new Node(value));
    }
  
    return answer;
  }
  
  public void inOrder (Node now, BufferedWriter bw) throws IOException {
    if (now.left != null) inOrder(now.left, bw);
    bw.write(now.value+" ");
    if (now.right != null) inOrder(now.right, bw);
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N= Integer.parseInt(st.nextToken()), Q = Integer.parseInt(st.nextToken());
    Splay tree = new Splay(N);
    
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(Q-->0) {
      st = new StringTokenizer(br.readLine());
      bw.write(tree.query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))+"\n");
    }
    tree.inOrder(tree.root, bw);
    bw.write('\n');
    bw.flush();
  }
}