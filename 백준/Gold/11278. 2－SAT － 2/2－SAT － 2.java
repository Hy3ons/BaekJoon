import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());
        boolean[] casess = new boolean[testcase];
        int[][] query = new int[amount][2];

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());

            query[i][0] = Integer.parseInt(st.nextToken());
            query[i][1] = Integer.parseInt(st.nextToken());
        }

        K: for (int i=0;i<(1<<testcase);i++) {
            Arrays.fill(casess, false);

            for (int k=0;k<testcase;k++) {
                if ((i&(1<<k))!=0) {
                    casess[k] = true;
                }
            }

            for (int k=0;k<query.length;k++) {
                boolean a = false, b = false;

                if (query[k][0]<0) {
                    a = !casess[-query[k][0]-1];
                } else {
                    a = casess[query[k][0]-1];
                }

                if (query[k][1]<0) {
                    b = !casess[-query[k][1]-1];
                } else {
                    b = casess[query[k][1]-1];
                }

                if (!a&&!b) {
                    continue K;
                }

            }

            System.out.println(1);
            StringBuilder sb = new StringBuilder();

            for (boolean b : casess) {
                if (b) sb.append("1 ");
                    else sb.append("0 ");
            }

            System.out.println(sb);
            return;
        }
        System.out.println(0);
    }
}
