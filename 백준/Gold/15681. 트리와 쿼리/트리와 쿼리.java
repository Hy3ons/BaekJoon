import java.io.*;
import java.util.*;

class Pair {
    int now , prev;
    Pair (int now , int prev) {
        this.now = now;
        this.prev = prev;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int size = Integer.parseInt(st.nextToken());
        int root = Integer.parseInt(st.nextToken());
        int queryAmount = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Integer>> load = new ArrayList<>();
        ArrayList<ArrayList<Integer>> real = new ArrayList<>();
        for (int i=0;i<=size;i++) {
            load.add(new ArrayList<>());
            real.add(new ArrayList<>());
        }

        for (int i=1;i<size;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        int[] nodes = new int[size+1]
                ,level = new int[size+1]
                ,indegree = new int[size+1];
        Arrays.fill(nodes, 1);
        ArrayList<Integer> starts = new ArrayList<>();

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(root, 0));
        while(!qu.isEmpty()) {
            Pair now = qu.poll();
            boolean beStart = true;

            for (int go : load.get(now.now)) {
                if (go == now.prev) continue;

                level[go] = level[now.now] + 1;
                qu.offer(new Pair(go, now.now));
                beStart = false;
                indegree[now.now]++;
            }
            if (beStart) starts.add(now.now);
        }

        for (int start : starts) {
            qu.offer(new Pair(start,0));

            while(!qu.isEmpty()) {
                Pair now = qu.poll();

                for (int go : load.get(now.now)) {
                    if (level[go] == level[now.now] -1) {
                        nodes[go] += nodes[now.now];
                        if (--indegree[go] == 0) {
                            qu.offer(new Pair(go, 0));
                        }
                        break;
                    }
                }
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(queryAmount-->0) {
            int node = Integer.parseInt(br.readLine());
            bw.write(nodes[node]+"\n");
        }
        bw.flush();


    }
}