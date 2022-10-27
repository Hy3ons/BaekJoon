import java.io.*;
import java.util.Collections;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    char[] original = br.readLine().toCharArray();
    char[] s = new char[original.length];
    
    for (int i=0;i<s.length;i++) {
      s[i] = original[original.length-1-i];
    }
    
    int[] Z = new int[s.length];
    
    int l = 0, r = 0;
    for (int i=1;i<s.length;i++) {
      // r 이 더커야 정보를 이용할 수 있음.
      if (i <= r) Z[i] = Math.min(r-i, Z[i-l]);
      else l = i;
      
      while(Z[i] + i < s.length && s[Z[i]] == s[Z[i] + i]) Z[i]++;
      
      //r은 계속 커져야만 함.
      r = Math.max(r, i + Z[i] - 1);
    }
    Z[0] = Z.length;
    
    //몰라 이해 안함. 코드를 외움.
    
    int N = Integer.parseInt(br.readLine());
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(N-->0) {
      int K = Integer.parseInt(br.readLine());
      bw.write(Z[s.length-K]+"\n");
    }
    bw.flush();
    
  }
}