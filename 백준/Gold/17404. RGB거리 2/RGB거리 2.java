import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        cost = new int[3][N];
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j=0;j<3;j++) {
                cost[j][i] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(Math.min(answer(0), Math.min(answer(1), answer(2))));
    }

    public static int[][] cost;
    public static int N;
    public static int answer (int color) {
        int[][] dp = new int[3][N];

        dp[color][0] = cost[color][0];

        for (int i=0;i<N-1;i++) {
            for (int c = 0;c<3;c++) {
                if (dp[c][i] > 0) {
                    try {
                        for (int j=0;j<3;j++) {
                            if (c == j) continue;
                            if (dp[j][i+1] == 0) {
                                dp[j][i+1] = dp[c][i] + cost[j][i+1];
                            } else {
                                dp[j][i+1] = Math.min(dp[j][i+1], dp[c][i] + cost[j][i+1]);
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                }
            }
        }

        int max = Integer.MAX_VALUE;

        for (int i=0;i<3;i++) {
            if (i == color) continue;
            max = Math.min(max, dp[i][N-1]);
        }
        return max;
    }
}
