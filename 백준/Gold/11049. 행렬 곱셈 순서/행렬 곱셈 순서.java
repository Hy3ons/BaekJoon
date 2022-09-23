import java.io.*;
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
        final int N = Integer.parseInt(br.readLine());

        Pair[] p = new Pair[N];
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            p[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        int[][] dp = new int[N][N];
        for (int i=1;i<N;i++) {
            for (int j=0;j+i<N;j++) {
                //write dp[j][j+i] Optimizing Code
                dp[j][j+i] = Integer.MAX_VALUE;
                for (int k=0;k<i;k++) {
                    dp[j][j+i] = Math.min(dp[j][j+i]
                            , p[j].x * p[j+k].y * p[j+i].y + dp[j][j+k] + dp[j+k+1][j+i]);
                }
            }
        }

        System.out.println(dp[0][N-1]);
    }
}