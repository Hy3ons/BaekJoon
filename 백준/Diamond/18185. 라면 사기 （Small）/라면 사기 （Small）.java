import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine()  );
    int[] arr = new int[N];
  
    StringTokenizer st= new StringTokenizer(br.readLine());
    long sum = 0;
    for (int i=0;i<N;i++) {
      arr[i] = Integer.parseInt(st.nextToken());
    }
    
    for (int i=0;i<N;i++) {
      int first = i, second = i+1, third = i + 2;
      if (i + 2 < N) {
        if (arr[second] > arr[third]) {
          int minus = Math.min(arr[second] - arr[third], arr[first]);
          sum += minus * 5;
          arr[first] -= minus;
          arr[second] -= minus;
        }
        
        int minus = min(arr[first], arr[second], arr[third]);
        arr[first] -= minus;
        arr[second] -=minus;
        arr[third] -= minus;
        sum += minus * 7;
        sum += arr[first] * 3;
      } else if (i + 1 < N) {
        int minus = Math.min(arr[first], arr[second]);
        arr[first] -= minus;
        arr[second] -= minus;
        sum += minus * 5;
        sum += arr[first] * 3;
      } else {
        sum += arr[first] * 3;
      }
    }
    
    System.out.println(sum);
    
  }
  
  public static int min (int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }
}