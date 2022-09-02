import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Line{
    long a, b;
    double s;
    Line(long a, long b) {
        this.a = a;
        this.b = b;
    }
}

public class Main {
    public static long[] a, b, dp;
    public static ArrayList<Line> lines = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());

        a = new long[N];
        b = new long[N];
        dp = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine()), st2 = new StringTokenizer(br.readLine());

        for (int i=0;i<N;i++) {
            a[i] = Long.parseLong(st.nextToken());
            b[i] = Long.parseLong(st2.nextToken());
        }

        lines.add(new Line(b[0], 0));

        for (int i=1;i<N;i++) {
            check(i);
            addLine(new Line(b[i], dp[i]));
        }

        System.out.println(dp[N-1]);
    }

    public static void check(int idx) {
        int left = -1;
        int right = lines.size();

        while(true) {
            int mid = left + right >> 1;

            if (lines.get(mid).s <= a[idx]) {
                left = mid;
            } else {
                right = mid;
            }

            if (right - left == 1) {
                dp[idx] = lines.get(left).a * a[idx] + lines.get(left).b;
                return;
            }
        }
    }

    public static void addLine (Line line) {
        while(!lines.isEmpty()) {
            long a = cross(line, lines.get(lines.size()-1));

            if (a < lines.get(lines.size()-1).s) {
                lines.remove(lines.size()-1);
            } else {
                line.s = a;
                lines.add(line);
                return;
            }
        }
    }

    public static long cross(Line o1, Line o2) {
        return (o2.b - o1.b) / (o1.a - o2.a);
    }
}
