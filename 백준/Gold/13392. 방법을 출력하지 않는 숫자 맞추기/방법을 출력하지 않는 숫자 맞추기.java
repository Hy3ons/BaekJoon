import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        char[] now = br.readLine().toCharArray();
        char[] goal = br.readLine().toCharArray();

        int[][] dp = new int[10][n+1];

        for(int[] d : dp) Arrays.fill(d, -1);
        dp[0][0] = 0;


        for (int i=0;i<n;i++) {
            for (int s = 0;s<10;s++) {
                if (dp[s][i] >= 0) {
                    int number = spining(now[i]-'0', s);
                    int goal_number = goal[i] - '0';

                    int right = number - goal_number;
                    if (right < 0) right += 10;

                    if (dp[s][i+1] == -1) {
                        dp[s][i+1] = dp[s][i] + right;
                    } else {
                        dp[s][i+1] = Math.min(dp[s][i+1], dp[s][i] + right);
                    }

                    int left = goal_number - number;
                    if (left < 0) left += 10;

                    int idx = (s + left) % 10;
                    if (dp[idx][i+1] == -1) {
                        dp[idx][i+1] = dp[s][i] + left;
                    } else {
                        dp[idx][i+1] = Math.min(dp[idx][i+1], dp[s][i] + left);
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i=0;i<10;i++) {
            if(dp[i][n] == -1) continue;
            min = Math.min(min, dp[i][n]);
        }
        System.out.println(min);
    }

    public static int spining(int number, int spined) {
        return (number + spined) % 10;
    }
}