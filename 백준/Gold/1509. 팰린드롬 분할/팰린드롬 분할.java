import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] s = br.readLine().toCharArray();
        boolean[][] check = new boolean[s.length][s.length];

        for (int i=0;i<s.length;i++) {
            for (int l=i,r=i;l>=0 && r<s.length;l--,r++) {
                if (s[l] == s[r]) {
                    check[l][r] = true;
                } else {
                    break;
                }
            }
        }

        for (int i=0;i<s.length-1;i++) {
            for (int l=i,r=i+1;l>=0 && r<s.length;l--,r++) {
                if (s[l] == s[r]) {
                    check[l][r] = true;
                } else {
                    break;
                }
            }
        }

        long[] dp = new long[s.length];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 1;
        for(int i=1;i<dp.length;i++) {
            if (check[0][i]) {
                dp[i] = 1;
            } else {
                for (int j=1;j<=i;j++) {
                    if (check[j][i]) {
                        dp[i] = Math.min(dp[j-1]+1, dp[i]);
                    }
                }
            }
        }
        System.out.println(dp[dp.length-1]);
    }
}