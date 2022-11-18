import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Pair {
  int tall, day;
  Pair (int tall, int day) {
    this.tall = tall;
    this.day = day;
  }
}

public class Main {
  public static int MAX = 200002;
  public static int[] limit = new int[MAX];
  
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine()  );
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int Q = Integer.parseInt(st.nextToken());
    
    st = new StringTokenizer(br.readLine());
    for (int i=0;i<M;i++) {
      limit[i+1] = Integer.parseInt(st.nextToken());
    }
  
    ArrayList<ArrayList<Integer>> arr = new ArrayList<>(MAX);
    for (int i=0;i<MAX;i++) arr.add(new ArrayList<>());
    for (int i=0;i<MAX;i++) arr.get(i).add(0);
    
    
    st = new StringTokenizer(br.readLine());
    for (int i=0;i<K;i++)
      arr.get(Integer.parseInt(st.nextToken())).add(i+1);
    
    long[] prefix = new long[K+1];
    
    while(Q-->0) {
      st = new StringTokenizer(br.readLine());
      int k1 = Integer.parseInt(st.nextToken()), k2 = Integer.parseInt(st.nextToken());
      int lim = limit[Integer.parseInt(st.nextToken())];
      
      int left = 0;
      int right = K+1;
      
      while(true) {
        int mid = left + right >> 1;
        int n1 = lowerBound(arr.get(k1), mid);
        int n2 = lowerBound(arr.get(k2), mid);
        
        if (lowerBound(arr.get(k1), mid) + lowerBound(arr.get(k2), mid) >= lim) {
          right = mid;
        } else {
          left = mid;
        }
        
        if (left + 1 == right) {
          if (right == K+1) break;
          prefix[right]++;
          break;
        }
      }
    }
    
    for (int i=1;i<prefix.length;i++) {
      prefix[i] += prefix[i-1];
    }
  
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    for (int i=1; i <prefix.length;i++) {
      bw.write(prefix[i]+"\n");
    }
    bw.flush();
  }
  
  public static int lowerBound (ArrayList<Integer> arr, int goal) {
    int left = -1;
    int right = arr.size();
    
    while(true) {
      int mid = left + right >> 1;
      
      if (goal >= arr.get(mid)) {
        left = mid;
      } else {
        right = mid;
      }
  
      if (left + 1 == right) {
        return left;
      }
    }
  }
}