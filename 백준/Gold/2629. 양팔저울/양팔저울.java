import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];
        for (int i=0;i<N;i++) arr[i] = Integer.parseInt(st.nextToken());

        int[] dp = new int[30000];
        dp[0] = 1;

        for (int i=0;i<arr.length;i++) {
            for (int j=dp.length-1;j-arr[i] >=0;j--) {
                dp[j] += dp[j-arr[i]];
            }
        }

        int check = Integer.parseInt(br.readLine()  );
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine());
        C: while(check-->0) {
            int temp = Integer.parseInt(st.nextToken());
            if (temp > 15000) {
                bw.write("N ");
                continue C;
            }

            if (dp[temp] > 0) {
                bw.write("Y ");
            } else {
                for (int i=dp.length-1;i>= temp;i--) {
                    if (dp[i] >0 && dp[i-temp] > 0) {
                        bw.write("Y ");
                        continue C;
                    }
                }
                bw.write("N ");
            }
        }
        bw.flush();
    }
}