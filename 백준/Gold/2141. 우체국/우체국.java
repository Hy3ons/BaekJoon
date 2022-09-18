import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Pair {
    long x, y;
    Pair (long x, long y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        ArrayList<Pair> al = new ArrayList<>(N);
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            al.add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        al.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.x, o2.x);
            }
        });

        long[] prefixLeft = new long[N], prefixRight = new long[N];

        for (int i=0;i<N;i++) {
            prefixLeft[i] = al.get(i).y + (i == 0 ? 0 : prefixLeft[i-1]);
        }

        for (int i=N-1;i>=0;i--) {
            prefixRight[i] = al.get(i).y + (i == N-1 ? 0 : prefixRight[i+1]);
        }

        long temp = Long.MAX_VALUE;
        long answer = -1;
        for (int i=0;i<N;i++) {
            long left  = (i == 0) ? 0 : prefixLeft[i-1];
            long right =  (i==N-1) ? 0 : prefixRight[i+1];

            long abs = Math.abs(left - right);

            if (abs < temp) {
                temp = abs;
                answer = al.get(i).x;
            }
        }

        System.out.println(answer);
    }
}