import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        while (testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            b = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());

            char[][] temp = new char[b][];
            for (int i=0;i<b;i++) temp[i] = br.readLine().toCharArray();

            ary = new char[a][b];
            for (int i=0;i<a;i++) {
                for (int j=0;j<b;j++) {
                    ary[ary.length-1-i][b-1-j] = temp[j][i];
                }
            }

            int p_test = 1<<b;
            check = new boolean[b];
            dp = new int[a][p_test];
            visited = new boolean[a][p_test];
            visited[0][0] = true;

            for (int i=0;i<b;i++) {
                if (ary[0][i]=='x') check[i] = true;
            }

            for (int i=0;i<p_test;i++) {
                if (visited[0][i]) {
                    for (int j=0;j<b;j++) {
                        if ((i&(1<<j))==0&&!check[j]) {
                            if (!visited[0][i|1<<j]) {
                                visited[0][i|1<<j] = true;
                                dp[0][i|1<<j] = Integer.bitCount(i|1<<j);
                            }
                        }
                    }
                }
            }


            memori = new int[b];

            for (int F=1;F<a;F++) {
                for (int i=0;i<p_test;i++) {
                    if (visited[F-1][i]) {
                        Arrays.fill(check, false);

                        for (int j=0;j<b;j++) {
                            if (ary[F][j]=='x') {
                                check[j] = true;
                            }
                        }

                        for (int j=1;j<b-1;j++) {
                            if ((i&(1<<j))!=0) {
                                check[j-1] = check[j] = check[j+1] = true;
                            }
                        }
                        if ((i&(1<<(b-1)))!=0) {
                            check[b-1] = true;
                            try {
                                check[b-2] = true;
                            } catch (ArrayIndexOutOfBoundsException ignored) {
                            }
                        }
                        if ((i&1)!=0) {
                            check[0] = true;
                            try {
                                check[1] = true;
                            } catch (ArrayIndexOutOfBoundsException ignored) {
                            }
                        }

                        for (int j=0;j<=b;j++) {
                            limit = j;
                            func(i, F, 0, 0);
                        }
                    }
                }
            }

            int result = Integer.MIN_VALUE;
            for (int i=0;i<p_test;i++) {
                if (visited[a-1][i]) {
                    result = Math.max(result, dp[a-1][i]);
                }
            }
            bw.write(result+"\n");

        }
        bw.flush();
    }
    public static int[][] dp;
    public static boolean[][] visited;
    public static char[][] ary;
    public static int limit, a, b;
    public static int[] memori;
    public static boolean[] check;

    public static void func (int prevSit, int floor, int depth, int start) {
        if (depth==limit) {
            int count = 0;
            for (int i=0;i<limit;i++) {
                count |= 1<<memori[i];
            }

            if (!visited[floor][count]) {
                dp[floor][count] = dp[floor-1][prevSit] + Integer.bitCount(count);
                visited[floor][count] = true;
            } else {
                dp[floor][count] = Math.max(dp[floor][count], dp[floor-1][prevSit] + Integer.bitCount(count));
            }

            return;
        }

        for (int i=start;i<b;i++) {
            if (!check[i]) {
                memori[depth] = i;
                func(prevSit, floor, depth+1, i+1);
            }
        }
    }
}
