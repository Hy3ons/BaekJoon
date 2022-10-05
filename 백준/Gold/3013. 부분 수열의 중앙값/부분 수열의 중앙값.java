import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());
    int[] arr= new int[N];
    
    st = new StringTokenizer(br.readLine());
    int IDX = -1;
    for (int i=0;i<N;i++) {
      arr[i] = Integer.compare(Integer.parseInt(st.nextToken()), K);
      if (arr[i] == 0) IDX = i+1;
    }
    
    int[] prefix = new int[N+1];
    for (int i=0;i<arr.length;i++) {
      prefix[i+1] = prefix[i] + arr[i];
    }
    for (int i=0;i<prefix.length;i++) prefix[i] += 100000;
    ArrayList<ArrayList<Integer>> idx = new ArrayList<>(200200);
    for (int i=0;i<200200;i++) idx.add(new ArrayList<>());
    for (int i=0;i<prefix.length;i++) {
      idx.get(prefix[i]).add(i);
    }
    
    long c = 0;
    for (int i=0;i<idx.size();i++) {
      int l = 0, r =0;
      for (int e : idx.get(i)) {
        if (e < IDX) l++;
        else r++;
      }
      
      c += ((long) l * r);
    }
    
    System.out.println(c);
    
    
    
    
  }
}