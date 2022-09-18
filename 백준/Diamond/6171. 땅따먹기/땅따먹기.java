import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Line {
    long a, b;
    double s;
    Line (long a, long b) {
        this.a = a;
        this.b = b;
    }

    public long function(int x) {
        return (long) x * a + b;
    }

    public static double cross(Line l1, Line l2) {
        return (double) (l2.b - l1.b) / (l1.a - l2.a);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        ArrayList<Pair> pairs = new ArrayList<>(N+1);
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pairs.add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        pairs.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.x == o2.x) return Integer.compare(o2.y, o1.y);
                return Integer.compare(o2.x, o1.x);
            }
        });

        ArrayList<Pair> lists = new ArrayList<>(N+1);

        int max = -1;
        for (int i=0;i<pairs.size();i++) {
            if (max < pairs.get(i).y) {
                max = pairs.get(i).y;
                lists.add(pairs.get(i));
            }
        }
        pairs.clear();
        System.out.println(answer(lists));
    }

    public static long answer (ArrayList<Pair> p) {
        long[] dp = new long[p.size()];
        if (p.size() == 1) {
            return (long) p.get(0).x * p.get(0).y;
        }

        p.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.x, o2.x);
            }
        });



        for (int i=0;i<dp.length;i++) {
            dp[i] = (long) p.get(i).x * p.get(0).y;
        }

        lines.add(new Line(p.get(1).y, dp[0]));
        for (int i=1;i<dp.length;i++) {
            dp[i] = Math.min(dp[i], calculate(p.get(i).x));

            if (i != dp.length-1)
                addLine(new Line(p.get(i+1).y, dp[i]));
        }
        return dp[dp.length-1];
    }
    public static ArrayList<Line> lines = new ArrayList<>();
    public static void addLine(Line l1) {
        while(!lines.isEmpty()) {
            Line l = lines.get(lines.size()-1);
            double a = Line.cross(l1, l);

            if (a <= l.s) {
                lines.remove(lines.size()-1);
            } else {
                l1.s = a;
                break;
            }
        }

        lines.add(l1);
    }

    public static long calculate (int x) {
        int left = -1, right = lines.size();

        while(true) {
            int mid = left + right >> 1;

            if (lines.get(mid).s > x) {
                right = mid;
            } else {
                left = mid;
            }

            if (right - left == 1) {
                return lines.get(left).function(x);
            }
        }
    }
}