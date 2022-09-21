import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int goal  = Integer.parseInt(st.nextToken());

        int[] dp = new int[100005];

        ArrayList<Integer> coins = new ArrayList<>();
        for (int i=0;i<N;i++) coins.add(Integer.parseInt(br.readLine()));

        dp[0] = 1;
        for (int coin : coins) {
            for (int i=0;i<dp.length;i++) {
                try {
                    dp[i + coin] += dp[i];
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    break;
                }
            }
        }
        System.out.println(dp[goal]);


    }

}