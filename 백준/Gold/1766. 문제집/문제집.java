import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st =new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());

        int[] indegree = new int[N+1];

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(N+1);
        for (int i=0;i<=N;i++) adj.add(new ArrayList<>());

        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            adj.get(from).add(to);
            indegree[to]++;
        }

        PriorityQueue<Integer> qu = new PriorityQueue<>();

        for (int i=1;i<=N;i++) {
            if (indegree[i] == 0) qu.offer(i);
        }

        while(!qu.isEmpty()) {
            int now = qu.poll();
            bw.write(now+" ");

            for (int go : adj.get(now)) {
                if (--indegree[go] == 0) {
                    qu.offer(go);
                }
            }
        }

        bw.flush();
    }
}