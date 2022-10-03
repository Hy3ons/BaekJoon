import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        long[][] dp = new long[N+100][N+100];
        boolean[] eratos = new boolean[1000500];
        eratos[0] = eratos[1] = true;
        for (long i=2;i<eratos.length;i++) {
            if (!eratos[(int)i]) {
                for (long j=i*i;j<eratos.length;j+=i ) eratos[(int)j] = true;
            }
        }

        for (int i=2;i<=N+N+1;i++) {
            for (int j=1;j<=i-1;j++) {
                // j, i-j 칸 이다.
                try {
                    dp[j][i-j+1] = Math.max(dp[j][i-j+1], dp[j][i-j] + (!eratos[c(j, i-j+1)] ? 1 : 0));

                }catch (Exception igo) {
                }

                try {
                    dp[j+1][i-j] = Math.max(dp[j+1][i-j], dp[j][i-j] + (!eratos[c(j+1, i-j)] ? 1 : 0));

                }catch (Exception igo) {
                }

            }
        }
        System.out.println(dp[N][N]);
    }

    public static int c (int a, int b) {
        int leng = String.valueOf(b).length();
        return (int) (a * Math.pow(10, leng) + b);
    }
}