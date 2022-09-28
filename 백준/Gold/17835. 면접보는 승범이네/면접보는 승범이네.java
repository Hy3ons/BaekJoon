import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    int go;
    long dist;
    Pair (int go , long idst) {
        this.go = go;
        this.dist = idst;
    }
}

public class Main {
    public static ArrayList<ArrayList<Pair>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        final int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        final int K = Integer.parseInt(st.nextToken());

        load = new ArrayList<>(N+1);
        for (int i=0;i<N+1;i++)load.add(new ArrayList<>());

        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            load.get(n2).add(new Pair(n1, cost));
        }

        st = new StringTokenizer(br.readLine());
        int[] s = new int[K];
        for (int i=0;i<s.length;i++) s[i] = Integer.parseInt(st.nextToken());

        long max = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.dist, o2.dist);
            }
        });

        long[] dists = new long[N+1];
        Arrays.fill(dists, Long.MAX_VALUE);

        for (int i=0;i<s.length;i++) {
            dists[s[i]] = 0;
            pq.offer(new Pair(s[i], 0));
        }

        while(!pq.isEmpty()) {
            Pair now = pq.poll();
            if (now.dist != dists[now.go]) continue;

            for (Pair go : load.get(now.go)) {
                if (dists[go.go] > now.dist + go.dist) {
                    dists[go.go] = now.dist + go.dist;
                    pq.offer(new Pair(go.go, dists[go.go]));
                }
            }
        }

        int idx = 1;
        for (int i=1;i<=N;i++) {
            if (max < dists[i]) {
                max = dists[i];
                idx = i;
            }
        }

        System.out.println(idx);
        System.out.println(max);


    }
}