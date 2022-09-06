import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x= x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Pair[] pairs = new Pair[N];

        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pairs[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.x, o2.x);
            }
        });

        int[] dp = new int[505];
        int res = 0;

        for (int i=0;i< pairs.length;i++) {
            dp[i]++;
            res = Math.max(dp[i], res);
            for (int j=i+1;j< pairs.length;j++) {
                if (pairs[i].y < pairs[j].y) {
                    dp[j] = Math.max(dp[j], dp[i]);
                    res = Math.max(res, dp[j]);
                }
            }
        }

        System.out.println(N-res);

    }
}