import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Line {
    long a, b;
    double s;
    Line (long a, long b) {
        this.a = a;
        this.b = b;
    }

}

public class Main {
    public static long[]dp;
    public static int[] power, prefix;
    public static long A, B, C;
    public static ArrayList<Line> lines = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        power = new int[N];
        prefix = new int[N+1];
        dp = new long[N];

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<N;i++) {
            power[i] = Integer.parseInt(st.nextToken());
            prefix[i] = power[i] + (i == 0 ? 0 : prefix[i-1]);
        }

        for (int i=0;i<N;i++) dp[i] = function(prefix[i]);

        lines.add(new Line(-2 * A * prefix[0],
                A * prefix[0] * prefix[0] - B * prefix[0] + dp[0]));

        for (int i=1;i<N;i++) {
            check(i);
            addLine(new Line(-2 * A * prefix[i]
                    , A * prefix[i] * prefix[i] - B * prefix[i] + dp[i]));
        }

        System.out.println(dp[N-1]);
    }

    public static void check(int idx) {
        int left = -1;
        int right = lines.size();

        while(true) {
            int mid = left + right >> 1;

            if (lines.get(mid).s <= prefix[idx]) {
                left = mid;
            } else
                right = mid;

            if (right - left == 1) {
                dp[idx] = Math.max(dp[idx], lines.get(left).a * prefix[idx] + lines.get(left).b + function(prefix[idx]));
                return;
            }
        }
    }

    public static void addLine(Line line) {
        while(!lines.isEmpty()) {
            Line c = lines.get(lines.size()-1);

            if (cross(line, c) <= c.s) {
                lines.remove(lines.size()-1);
            } else {
                line.s = cross(line, c);
                break;
            }
        }
        lines.add(line);
    }

    public static double cross (Line o1, Line o2) {
        return (double) (o2.b - o1.b) / (o1.a - o2.a);
    }

    public static long function(long x) {
        return A * x * x + B * x + C;
    }
}