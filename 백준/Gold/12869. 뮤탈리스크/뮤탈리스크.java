import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static int[][][] dp = new int[61][61][61];
    public static int[] memorize = new int[3], loss = new int[3];
    public static int INF = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] init = new int[3];
        for (int i=0;i<N;i++) init[i] = Integer.parseInt(st.nextToken());

        for(int[][] arr : dp) for (int[] ar : arr) Arrays.fill(ar, INF);

        dp[init[0]][init[1]][init[2]] = 0;
        for (int i=0;i<15;i++) cycling(i);
        System.out.println(dp[0][0][0]);
    }

    public static void cycling (int t) {
        for (int i=0;i<61;i++) for (int j=0;j<61;j++) for (int k=0;k<61;k++) if (dp[i][j][k] == t) {
            loss[0] = i;
            loss[1] = j;
            loss[2] = k;
            recursive(1, t + 1);
        }

    }
    public static boolean[] using = new boolean[3];
    public static void recursive(int depth, int t) {
        if (depth == 27) {
            dp[Math.max(0, loss[0]-memorize[0])][Math.max(0, loss[1]-memorize[1])][Math.max(0, loss[2]-memorize[2])] =
                    Math.min(dp[Math.max(0, loss[0]-memorize[0])][Math.max(0, loss[1]-memorize[1])][Math.max(0, loss[2]-memorize[2])],
                            t);
            return;
        }

        for (int i=0;i<3;i++) if (!using[i]) {
            using[i] = true;
            memorize[i] = depth;
            recursive(depth*3, t);
            using[i]= false;
        }
    }
}
