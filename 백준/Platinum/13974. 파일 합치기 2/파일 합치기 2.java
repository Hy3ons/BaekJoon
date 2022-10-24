import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int testcase = Integer.parseInt(br.readLine());
        
        while(testcase-->0) {
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st=  new StringTokenizer(br.readLine());
            
            int[] prefix = new int[N+1];
            for (int i=0;i<N;i++) {
                prefix[i+1] = prefix[i] + Integer.parseInt(st.nextToken());
            }
            
            long[][] dp = new long[N][N];
            int[][] re = new int[N][N];
            for (long[] d : dp) Arrays.fill(d, 100000000000000L);
            for (int i=0;i<N;i++) dp[i][i]= 0;
            for(int i=0;i+1<N;i++) {
                dp[i][i+1] = prefix[i+2] - prefix[i];
                re[i][i+1] = i;
            }
            
            for (int i=2;i<N;i++) {
                for (int j=0;j+i<N;j++) {
                    //dp[j][j+i] optimizing DNC
                    
                    for (int k=re[j][j+i-1];k<=re[j+1][j+i] && k+1 < N;k++) {
                        if (dp[j][j+i] > dp[j][k] + dp[k+1][j+i]) {
                            dp[j][j+i] = dp[j][k] + dp[k+1][j+i];
                            re[j][j+i] = k;
                        }
                    }
                    
                    dp[j][j+i] += prefix[j+i+1] - prefix[j];
                    
                    
                }
            }
            bw.write(dp[0][N-1]+"\n");
        
        }
        bw.flush();
    }
}
