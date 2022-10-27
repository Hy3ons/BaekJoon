import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
  public static long[] tree;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    char[] s = br.readLine().toCharArray();
    
    int[] Z = new int[s.length];
    
    int l = 0, r = 0, start = 1;
    
    while(start < s.length) start<<= 1;
    tree = new long[start << 1];
  
    ArrayList<Integer> ans = new ArrayList<>();
    
    for (int i=1;i<s.length;i++) {
      // r 이 더커야 정보를 이용할 수 있음.
      if (i <= r) Z[i] = Math.min(r-i, Z[i-l]);
      else l = i;
      
      while(Z[i] + i < s.length && s[Z[i]] == s[Z[i] + i]) Z[i]++;
      
      //r은 계속 커져야만 함.
      r = Math.max(r, i + Z[i] - 1);
      
      if (i + Z[i] == s.length) ans.add(Z[i]);
      update(1, 1, Z[i], 1, start);
    }
    tree[1] += 1;
    for (int i=1;i<start;i++) {
      tree[i<<1] += tree[i];
      tree[i*2+1] += tree[i];
    }
    ans.add(s.length);
    Collections.sort(ans);
    
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    bw.write(ans.size()+"\n");
    for (int e : ans) {
      bw.write(e+" "+tree[start + e-1]+"\n");
    }
    bw.flush();
    
  }
  
  public static void update (int node, int s, int e, int left, int right) {
    if (right <s || e < left) return;
    
    if (s <= left && right <= e) {
      tree[node]++;
      return;
    }
    
    int mid = left + right >> 1;
    update(node*2, s, e, left, mid);
    update(node*2+1, s, e, mid+1, right);
  }
}