import java.io.*;
import java.util.*;

public class Main {
    public static long[] bits = new long[65];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bits[0] = 1;
        for (int i=1;i<bits.length;i++) bits[i] = bits[i-1] * 2;

        N = Integer.parseInt(br.readLine());
        num = new int[N][N];

        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j=0;j<N;j++) {
                num[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        stand = N / 2;
        vertical = new ArrayList<>();
        side = new ArrayList<>();
        for (int i=0;i<N;i++) {
            long a = 0;
            for (int j=i;j<N*N;j+=N) {
                a += bits[j];
            }
            long b = 0;
            for (int j = N*i;j<N*i+N;j++) {
                b += bits[j];
            }
            vertical.add(a);
            side.add(b);
        }

        int f = answer();

        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                num[i][j] ^= 1;
            }
        }
        System.out.println(Math.min(f, answer()));

    }
    public static long ff;

    public static int N, stand;
    public static ArrayList<Long> vertical, side;
    public static int encode(int x, int y) {
        return x * N + y;
    }
    public static int[][] num;
    public static int answer () {
        long base = 0;
        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                if (num[i][j] == 1) {
                    base += bits[encode(i, j)];
                }
            }
        }
        if (base == 0 || base == ff) {
            return 0;
        }
        for (int i=0;i<N*N;i++) ff += bits[i];

        int ans = aa(base, 0, ff);

        for (long e : vertical) {
            ans = Math.min(ans, aa(base ^ e, 1, ff));
        }

        for (long e : side) {
            ans = Math.min(ans, aa(base ^ e, 1, ff));
        }

        for (int i=0;i<N*N;i++) {
            ans = Math.min(ans, aa(base ^ bits[i], 1, ff));
        }

        return ans;
    }

    public static int aa (long base, int count, long ff) {
        Queue<Long> qu = new LinkedList<>();
        qu.offer(base);
        HashSet<Long> hs = new HashSet<>();
        hs.add(base);
        if (base == 0 || base == ff) return count;
        C: while(true) {
            count++;
            long now = qu.poll();

            for (long e : vertical) {
                if (Long.bitCount(now & e) > stand) {
                    long s = now ^ e;
                    if (s == 0 || s == ff) return count;
                    if (!hs.contains(s)) {
                        hs.add(s);
                        qu.offer(s);
                        continue C;
                    }
                }
            }

            for (long e : side) {
                if (Long.bitCount(now & e) > stand) {
                    long s = now ^ e;
                    if (s == 0 || s == ff) return count;
                    if (!hs.contains(s)) {
                        hs.add(s);
                        qu.offer(s);
                        continue C;
                    }
                }
            }

            for (int j = 0; j < N * N; j++) {
                if ((now & bits[j]) != 0) {
                    long ne = now ^ bits[j];
                    if (ne == 0 || ne == ff) return count;
                    if (!hs.contains(ne)) {
                        qu.offer(ne);
                        hs.add(ne);
                        continue C;
                    }
                }
            }
        }
    }
}

