import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
        int dist = Integer.parseInt(st.nextToken());

        long[][] cost = new long[a][b];
        long[][] dp = new long[a][b];

        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++) {
                cost[i][j] = Long.parseLong(st.nextToken());
            }
        }

        for (long[] d : dp) Arrays.fill(d, Long.MIN_VALUE);
        Arrays.fill(dp[0], 0);
        long max = Long.MIN_VALUE;

        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {
                //i, j에서 갑니다.
                for (int k=1;k<=dist;k++) {
                    if (i+k >= a) break;

                    int remain = dist - k;
                    for (int h=0;h<=remain;h++) {
                        //k, h
                        try {
                            dp[i+k][j+h] = Math.max(dp[i+k][j+h], dp[i][j] + cost[i][j] * cost[i+k][j+h]);
                        }catch (Exception ex) {
                        }
                        try {
                            dp[i+k][j-h] = Math.max(dp[i+k][j-h], dp[i][j] +    cost[i][j] * cost[i+k][j-h]);
                        }catch (Exception ex) {
                        }

                    }
                }
            }
        }

        for (int i=0;i<b;i++) {
            max = Math.max(max, dp[a-1][i]);
        }
        System.out.println(max);



    }
}