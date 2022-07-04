import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class Pair {
    int prev, go;
    Pair (int go, int prev) {
        this.go = go;
        this.prev = prev;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input = "";
        int caseCount = 0;

        while(true) {
            input = br.readLine();
            if (input.equals("0 0")) break;

            caseCount++;
            StringTokenizer st = new StringTokenizer(input);

            int testcase = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());

            ArrayList<ArrayList<Integer>> load = new ArrayList<>(testcase+1);
            for (int i=0;i<testcase+1;i++) load.add(new ArrayList<>());

            int[] parent = new int[testcase+1];
            for (int i=1;i<=testcase;i++) parent[i] = i;

            for (int i=0;i<amount;i++) {
                st = new StringTokenizer(br.readLine());
                int n1 = Integer.parseInt(st.nextToken());
                int n2 = Integer.parseInt(st.nextToken());

                union(parent, n1, n2);

                if (n1==n2) {
                    parent[getParent(parent, n1)] = 0;
                }

                load.get(n1).add(n2);
                load.get(n2).add(n1);
            }

            boolean[] visited = new boolean[testcase+1];
            Queue<Pair> qu = new LinkedList<>();

            for (int i=1;i<=testcase;i++) {
                if (!visited[i] && getParent(parent, i)!=0) {
                    visited[i] = true;

                    qu.offer(new Pair(i, 0));
                    K: while(!qu.isEmpty()) {
                        Pair now = qu.poll();

                        for (int go : load.get(now.go)) {
                            if (now.prev ==go || now.go==go) continue;

                            if (visited[go]) {
                                union(parent, 0, go);

                                qu.clear();
                                break K;
                            } else {
                                visited[go] = true;
                                qu.offer(new Pair(go, now.go));
                            }

                        }
                    }
                }
            }

            Arrays.fill(visited, false);
            int count = 0;

            for (int i=1;i<=testcase;i++) getParent(parent, i);
            for (int i=1;i<=testcase;i++) {
                if (parent[i]==0) continue;
                if (!visited[parent[i]]) {
                    visited[parent[i]] = true;
                    count++;
                }
            }

            bw.write("Case "+caseCount+": ");
            if (count==1) {
                bw.write("There is one tree.\n");
            } else if (count==0) {
                bw.write("No trees.\n");
            } else {
                bw.write("A forest of "+count+" trees.\n");
            }
        }
        bw.flush();

    }

    public static void union(int[] parent, int node1, int node2) {
        int n1 = getParent(parent, node1);
        int n2 = getParent(parent, node2);

        if (n1>n2) {
            parent[n1] = n2;
        } else {
            parent[n2] = n1;
        }
    }

    public static int getParent(int[] parent, int node) {
        if (parent[node]==node) return node;
        return parent[node] = getParent(parent, parent[node]);
    }
}