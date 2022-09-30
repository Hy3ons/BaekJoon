import java.io.*;
import java.util.StringTokenizer;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N], dp = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int sum = 0;
        
        for (int i=0;i<arr.length;i++) {
            if (st.nextToken().equals("1")) {
                sum += arr[i];
                arr[i] *= -1;
            }
        }

        int max = dp[0] = arr[0];
        for (int i=1;i<arr.length;i++) {
            dp[i] = Math.max(dp[i-1] + arr[i], arr[i]);
            max = Math.max(dp[i], max);
        }

        System.out.println(sum+max);
    }

}
