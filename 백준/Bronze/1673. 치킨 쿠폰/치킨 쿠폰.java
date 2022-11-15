import java.io.*;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String input;
  
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    while((input = br.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(input);
      int N = Integer.parseInt(st.nextToken());
      int K = Integer.parseInt(st.nextToken());
      
      
      long ans = 0;
      ans += N;
      while(true) {
        ans += N / K;
        N = N / K + N % K;
        
        if (N < K) break;
      }
      
      bw.write(ans+"\n");
    }
    
    bw.flush();
  }
}