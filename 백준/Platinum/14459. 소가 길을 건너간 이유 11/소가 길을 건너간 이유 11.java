import java.io.*;
import java.util.*;

class Pair {
  int idx, value;
  Pair (int idx, int value) {
    this.idx = idx;
    this.value = value;
  }
}

class Seg {
  private int[] tree;
  public int start = 1;
  
  Seg () {
    while(start < 100001) start <<= 1;
    tree = new int[start << 1];
  }
  
  public void update (int idx, int value) {
    tree[idx + start] = value;
    for (int i= idx + start >> 1;i!=0;i>>=1) {
      tree[i] = Math.max(tree[i << 1], tree[i << 1 | 1]);
    }
  }
  
  public int maxQuery (int node, int s, int e, int left, int right) {
    if (e < left || right < s) return 0;
    
    if (s <= left && right <=e ) return tree[node];
    
    int mid = left + right >> 1;
    return Math.max(maxQuery(node << 1, s, e, left, mid),
        maxQuery(node << 1 | 1, s, e, mid+1, right));
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    
    int[] idx = new int[N+1];
    for (int i=0;i<N;i++) {
      idx[Integer.parseInt(br.readLine())] = i;
    }
    
    ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
    for (int i=0;i<=N;i++) arr.add(new ArrayList<>());
    
    for (int i=0;i<N;i++) {
      int cow = Integer.parseInt(br.readLine());
      
      for (int j=-4;j<=4;j++) {
        if (cow + j <= 0 || cow + j > N) continue;
        int friend = cow + j;
        arr.get(idx[friend]).add(i);
      }
    }
    
    Seg tree = new Seg();
  
    Queue<Pair> qu = new LinkedList<>();
    int ans = 0;
    
    for (int i=0;i<N;i++) {
      for (int e : arr.get(i)) {
        qu.offer(new Pair(e, tree.maxQuery(1, 1, e, 1, tree.start)));
      }
      
      while(!qu.isEmpty()) {
        tree.update(qu.peek().idx, qu.peek().value + 1);
        ans = Math.max(qu.peek().value + 1, ans);
        qu.poll();
      }
    }
    
    System.out.println(ans);
    
  }
}