import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Seg {
  private int[] tree;
  public int start = 1;
  
  Seg (int[] arr) {
    while(start < arr.length) start <<= 1;
    tree = new int[start << 1];
    
    for (int i=0;i<arr.length;i++) {
      tree[i + start] = arr[i]  ;
    }
    
    for (int i=start>>1;i!=0;i>>=1) {
      for (int j=i;j < i << 1; j++) {
        tree[j] = Math.min(tree[j << 1], tree[j << 1 | 1]);
      }
    }
  }
  
  public int query (int node, int s, int e, int left, int right) {
    if (e < left || right < s) return Integer.MAX_VALUE;
    
    if (s<= left && right <=e) return tree[node];
    
    int mid = left + right >> 1;
    return Math.min(query(node << 1, s, e, left, mid)
        , query(node << 1 | 1, s, e, mid+1, right));
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    char[] s = br.readLine().toCharArray();
    int N = Integer.parseInt(br.readLine());
    int[] prefix = new int[s.length+1];
    
    for (int i=0;i<s.length;i++) {
      prefix[i+1] = prefix[i] + (s[i] == '(' ? 1 : -1);
    }
    
    Seg tree = new Seg(prefix);
    
    int ans = 0;
    while(N-->0) {
      StringTokenizer st= new StringTokenizer(br.readLine());
      int left = Integer.parseInt(st.nextToken()), right= Integer.parseInt(st.nextToken());
      
      if (prefix[right] != prefix[left-1]) continue;
      
      int min = tree.query(1, left+1, right+1, 1, tree.start);
      
      if (prefix[left-1] <= min) ans++;
    }
    System.out.println(ans);
    
    
  }
}