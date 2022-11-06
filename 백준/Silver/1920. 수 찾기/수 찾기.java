import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    HashSet<Integer> hs = new HashSet<>();
    
    int N = Integer.parseInt(br.readLine());
    StringTokenizer st= new StringTokenizer(br.readLine());
    while(N-->0) hs.add(Integer.parseInt(st.nextToken()));
    
    N = Integer.parseInt(br.readLine());
    st = new StringTokenizer(br.readLine());
  
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(N-->0) bw.write(hs.contains(Integer.parseInt(st.nextToken())) ? "1\n" : "0\n");
    bw.flush();
  }
}