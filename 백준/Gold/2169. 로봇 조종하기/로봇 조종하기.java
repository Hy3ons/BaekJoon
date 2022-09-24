import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x=x;this.y=y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        cost = new int[a][b];
        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<Pair> qu = new LinkedList<>();
        int[][] dp = new int[a][b];
        for(int[] a : dp) Arrays.fill(a, Integer.MIN_VALUE);

        dp[0][0] = cost[0][0];
        for (int i=0;i<b-1;i++) {
            cost[0][i+1] += cost[0][i];
            dp[0][i+1] = cost[0][i+1];
        }



        for (int i=1;i<a;i++) {
            for (int j=0;j<b;j++) {
                for (int l=j, temp = dp[i-1][j];l>=0;l--) {
                    temp += cost[i][l];
                    dp[i][l] = Math.max(dp[i][l], temp);
                }
                for (int l=j, temp = dp[i-1][j];l<b;l++) {
                    temp += cost[i][l];
                    dp[i][l] = Math.max(dp[i][l], temp);
                }
            }
        }

        System.out.println(dp[a-1][b-1]);
    }

    public static int[] dx = {1,0,0}, dy = {0,-1,1};
    public static int[][] cost;
    public static int a,b;

    public static int encode(int x, int y) {
        return x * b + y;
    }
}

