import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class Pair {
        int dist, idx;
        Pair(int d, int i) {
            dist = d;
            idx = i;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dist = new int[k];

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<k;i++) {
            dist[i] = Integer.parseInt(st.nextToken());
        }

        int left = -1, right = 1000001;
        while(true) {
            int mid = left + right >> 1;

            if (simulate(mid) == m) {
                left = mid;
            } else {
                right = mid;
            }

            if (right - left == 1) break;
        }

        if (simulate(right) != m) {
            right = left;
        }

        int now = 1, last = 0;
        int[] result = new int[k];
        result[0] = 1;

        for (int i=1;i<k;i++) {
            if (right <= dist[i] - dist[last]) {
                last = i;
                result[i] = 1;
                if(++now == m) break;
            }
        }

        for(int e : result) System.out.print(e);



    }

    public static int[] dist;
    public static int N, m, k;

    public static int simulate (int goal) {
        int now = 1, last = 0;
        for (int i=1;i<k;i++) {
            if (goal <= dist[i] - dist[last]) {
                last = i;
                if(++now == m) break;
            }
        }

        return now;
    }
}
