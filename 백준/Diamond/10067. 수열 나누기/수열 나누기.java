import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

class Line {
    long a, b;
    int idx;
    double s;
    Line (long a, long b, int idx) {
        this.a = a;
        this.b = b;
        this.idx = idx;
    }

}

public class Main {
    static int N, K, MAX = 100001;
    static long[][] dp = new long[2][MAX];
    static int[][] re = new int[205][MAX];
    static int[] arr = new int[MAX];
    static long[] prefix = new long[MAX];
    static ArrayList<Line> lis1 = new ArrayList<>(MAX+1);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<N;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            prefix[i] = arr[i] + (i == 0 ? 0 : prefix[i-1]);
        }


        for (int g=1;g<=K;g++) {
            long[] temp = dp[1];
            dp[1] = dp[0];
            dp[0] = temp;

            for (int i=g;i<N;i++) {
                addLine(new Line(prefix[i-1], dp[0][i-1] - prefix[i-1] * prefix[i-1], i), lis1);
                check(i, g, lis1);
            }

            lis1.clear();
            System.gc();
        }

        bw.write(dp[1][N-1]+"\n");
        ArrayList<Integer> answer = new ArrayList<>();

        for (int a = re[K][N-1], g=1;g<=K;g++) {
            answer.add(a);
            a = re[K-g][a-1];
        }

        Collections.sort(answer);
        for (int e : answer) bw.write(e+" ");

        bw.flush();
    }

    public static void check(int idx, int g, ArrayList<Line> lines) {
        int left = -1;
        int right = lines.size();

        while(true) {
            int mid = left + right >> 1;

            if (lines.get(mid).s <= prefix[idx]) {
                left = mid;
            } else right = mid;

            if (right - left == 1) {
                Line l = lines.get(left);
                long comp = l.a * prefix[idx] + l.b;
                dp[1][idx] = comp;
                re[g][idx] = l.idx;
                return;
            }

        }
    }

    public static void addLine (Line line, ArrayList<Line> lines) {
        while(!lines.isEmpty()) {
            Line last = lines.get(lines.size()-1);

            if (last.a == line.a) {
                if (line.b > last.b) {
                    lines.remove(lines.size()-1);
                    continue;
                } else {
                    return;
                }
            }

            double a = cross(line, last);

            if (a <= lines.get(lines.size()-1).s) {
                lines.remove(lines.size()-1);
            } else {
                line.s = a;
                break;
            }
        }

        lines.add(line);
    }

    public static double cross(Line o1, Line o2) {
        return (double) (o2.b - o1.b) / (o1.a - o2.a);
    }
}