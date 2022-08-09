import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testcase = Integer.parseInt(br.readLine());
        int result = Integer.MAX_VALUE;
        int[][] cost = new int[testcase][testcase];
        StringTokenizer st;

        for (int i=0;i<testcase;i++) {
            st = new StringTokenizer(br.readLine()," ");
            for (int j=0;j<testcase;j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] dp = new int[1<<testcase];
        boolean[] check = new boolean[1<<testcase];
        char[] nowBreak = br.readLine().toCharArray();

        int goal = Integer.parseInt(br.readLine());
        int firstBitMask = 0;
        int stack = 0;

        for (int i=0;i<nowBreak.length;i++) {
            if (nowBreak[i]=='Y') {
                firstBitMask |= 1<<i;
                stack++;
            }
        }
        check[firstBitMask] = true;

        if (stack>=goal) {
            System.out.println(0);
            return;
        }

        if (stack==0) {
            System.out.println(-1);
            return;
        }

        stack++;
        for (int i=0;i<testcase;i++) {
            if ((firstBitMask&(1<<i))==(1<<i)) {
                for (int j=0;j<testcase;j++) {
                    if ((~firstBitMask&(1<<j))==1<<j) {
                        dp[firstBitMask|(1<<j)] = cost[i][j];
                        check[firstBitMask|(1<<j)] = true;
                    }
                }
            }
        }
        int tryTime = goal-stack;
        //check = true 라면 0에다가 math.min 갈겨도 된다는 뜻. false 라면 조심
        for (int z=0;z<tryTime;z++) {
            for (int k=0;k<dp.length;k++) {
                if (check[k]) {
                    for (int i=0;i<testcase;i++) { //출발 i
                        if ((k&(1<<i))==(1<<i)) {
                            for (int j=0;j<testcase;j++) { //j 도착
                                if ((~k&(1<<j))==1<<j) {
                                    int temp = k|(1<<j);
                                    if (check[temp]) {
                                        dp[temp] = Math.min(dp[temp], dp[k]+cost[i][j]);

                                    } else {
                                        check[temp] = true;
                                        dp[temp] = dp[k]+cost[i][j];
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i=0;i<dp.length;i++) {
            if (check[i]) {
                int checkss = 0;

                for (int j=0;j<testcase;j++) {
                    if ((i&(1<<j))==(1<<j)) {
                        checkss++;
                    }
                }

                if (checkss>=goal) {
                    result = Math.min(result, dp[i]);
                }

            }

        }
        System.out.println(result);


    }
}