import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(br.readLine());

        int[] dp = new int[n];
        int count = 0;

        for (int i=0;i<arr.length;i++) {
            count = Math.max(count, ++dp[i]);
            for (int j=i+1;j<arr.length;j++) {
                if (arr[i] < arr[j]) {
                    dp[j] = Math.max(dp[i], dp[j]);
                }
            }
        }

        System.out.println(n-count);
    }
}