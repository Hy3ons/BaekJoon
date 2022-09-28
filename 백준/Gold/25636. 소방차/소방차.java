import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    int go;
    long dist, water;
    Pair (int go , long idst) {
        this.go = go;
        this.dist = idst;
    }

    Pair (int go, long dist, long water) {
        this.go = go;
        this.dist = dist;
        this.water = water;
    }
}

public class Main {
    public static ArrayList<ArrayList<Pair>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] water = new long[N+1];
        for (int i=1;i<=N;i++) water[i] = Integer.parseInt(st.nextToken());

        final int M = Integer.parseInt(br.readLine());
        load = new ArrayList<>(N+1);
        for (int i=0;i<=N;i++)load.add(new ArrayList<>());
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            load.get(n1).add(new Pair(n2, cost));
            load.get(n2).add(new Pair(n1, cost));
        }

        st = new StringTokenizer(br.readLine());
        final int S = Integer.parseInt(st.nextToken()), T = Integer.parseInt(st.nextToken());
        long[] dist = new long[N+1], waters = new long[N+1];
        Arrays.fill(dist, Long.MAX_VALUE);

        dist[S] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.dist == o2.dist) return Long.compare(o1.water, o2.water);
                return Long.compare(o1.dist, o2.dist);
            }
        });

        pq.offer(new Pair(S, 0, water[S]));
        waters[S] = water[S];
        while(!pq.isEmpty()) {
            Pair now = pq.poll();
            if (now.dist > dist[now.go]) continue;
            else if (now.dist == dist[now.go] && waters[now.go] != now.water) continue;

            for (Pair go : load.get(now.go)) {
                if (dist[go.go] > dist[now.go] + go.dist) {
                    dist[go.go] = dist[now.go] + go.dist;
                    waters[go.go] = water[go.go] + now.water;
                    pq.offer(new Pair(go.go, dist[go.go], waters[go.go]));
                } else if (dist[go.go] == dist[now.go] + go.dist){
                    if (waters[go.go] < water[go.go] + now.water) {
                        waters[go.go] = water[go.go] + now.water;
                        pq.offer(new Pair(go.go, dist[go.go], waters[go.go]));
                    }
                }
            }
        }

        if (dist[T] == Long.MAX_VALUE) {
            System.out.println(-1);
        } else {

            System.out.println(dist[T]+" "+waters[T]);
        }


    }
}