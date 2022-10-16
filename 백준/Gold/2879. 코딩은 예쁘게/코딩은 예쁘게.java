import java.io.*;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    final int N = Integer.parseInt(br.readLine());
    
    int[] now = new int[N], goal = new int[N];
  
    StringTokenizer st =new StringTokenizer(br.readLine());
    for (int i=0;i<N;i++) {
      now[i] = Integer.parseInt(st.nextToken());
    }
    st = new StringTokenizer(br.readLine());
    for (int i=0;i<N;i++) {
      goal[i] = Integer.parseInt(st.nextToken());
    }
    
    int[] state = new int[N];
    int ans = 0;
    
    for (int g=0;g<80;g++) {
      for (int i=0;i<N;i++) {
        state[i] = Integer.compare(goal[i], now[i]);
      }
      
      int last = state[0];
      
      for (int i=1;i<N;i++) {
        if (state[i] != last){
          if (last != 0) ans++;
          last = state[i];
        }
      }
      
      if (last != 0) ans++;
      for (int i=0;i<N;i++){
        now[i] += state[i];
      }
    }
    System.out.println(ans);
    
  }
}
