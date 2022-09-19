import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine()  );

        int[] arr = new int[N];
        for (int i=0;i<N;i++) arr[i] = Integer.parseInt(st.nextToken());

        long[][] dp = new long[4][N+1];
        long[] prefix = new long[N+1];

        int sum = 0;
        for (int i=0;i<N;i++) sum += arr[i];

        if (Math.abs(sum) % 4 != 0) {
            System.out.println(0);
            return;
        }

        sum /= 4;

        int now = sum;
        int temp = 0;
        for (int i=0;i<N;i++) {
            if ((temp += arr[i]) == now) {
                dp[0][i] = 1;
            }
        }

        for (int i=1;i<4;i++) {
            Arrays.fill(prefix, 0);
            for (int j=0;j<N;j++) {
                prefix[j+1] = prefix[j] + dp[i-1][j];
            }

            now = sum * (i + 1);
            temp = 0;

            for (int j=0;j<N;j++) {
                if ((temp += arr[j]) == now) {
                    dp[i][j] = prefix[j];
                }
            }
        }

        System.out.println(dp[3][N-1]);
    }
}