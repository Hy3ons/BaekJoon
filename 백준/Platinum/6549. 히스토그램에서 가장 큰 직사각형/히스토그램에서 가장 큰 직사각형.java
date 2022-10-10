import java.io.*;
import java.util.StringTokenizer;

public class Main {
  public static long[] arr;
  public static long result;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    String input;
    
    while(!(input = br.readLine()).equals("0")) {
      StringTokenizer st = new StringTokenizer(input);
      int N = Integer.parseInt(st.nextToken());
      arr = new long[N];
      for (int i=0;i<N;i++) {
        arr[i] = Integer.parseInt(st.nextToken());
      }
      result = 0;
      conquer(0, N-1);
      bw.write(result+"\n");
    }
    bw.flush();
  }
  
  public static void conquer (int start, int end) {
    if (start == end) {
      result = Math.max(result, arr[start]);
      return;
    }
    
    int mid = start + end >> 1;
    long nowMin = arr[mid];
    
    for (int i=mid, j = mid;;) {
      if (i <= start) {
        for (j++;j<=end;j++) {
          nowMin = Math.min(nowMin, arr[j]);
          result = Math.max(result, nowMin * (j - i + 1));
        }
        break;
      }
      if (j >= end) {
        for (i--;i>=start;i--) {
          nowMin = Math.min(nowMin, arr[i]);
          result = Math.max(result, nowMin * (j - i + 1));
        }
        break;
      }
      
      if (arr[i-1] > arr[j+1]) {
        i--;
        nowMin = Math.min(nowMin, arr[i]);
        result = Math.max(result, nowMin * (j - i + 1));
      } else if (arr[i-1] == arr[j+1]) {
        i--;
        j++;
        nowMin = Math.min(nowMin, arr[i]);
        result = Math.max(result, nowMin * (j - i + 1));
      } else {
        j++;
        nowMin = Math.min(nowMin, arr[j]);
        result = Math.max(result, nowMin * (j - i + 1));
      }
      
    }
    
    
    conquer(start, mid);
    conquer(mid+1, end);
  }
  
}
