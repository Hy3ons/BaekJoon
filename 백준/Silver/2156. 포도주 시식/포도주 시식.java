import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] dp = new int[3][n];
        int[] arr = new int[n];

        for (int i=0;i<arr.length;i++) arr[i]    = Integer.parseInt(br.readLine());

        for (int i=0;i<arr.length;i++) dp[1][i] = arr[i];

        for (int i=0;i<arr.length;i++) {
            for (int h=0;h<3;h++) {
                if (dp[h][i] > 0) {
                    try {
                        if (h == 2) {
                            for (int j=i+2;j<arr.length;j++) {
                                dp[1][j] = Math.max(dp[1][j], dp[h][i] + arr[j]);
                            }
                        } else if (h == 1) {
                            dp[2][i+1] = Math.max(dp[2][i+1], dp[h][i] + arr[i+1]);
                            for (int j=i+2;j<arr.length;j++) {
                                dp[1][j] = Math.max(dp[1][j], dp[h][i] + arr[j]);
                            }
                        }

                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                }
            }
        }
        int max = 0;
        for (int i=0;i<3;i++) {
            for (int j=0;j<arr.length;j++) {
                max = Math.max(max, dp[i][j]);
            }
        }
        System.out.println(max); 
    }
}
