import java.io.*;
import java.util.*;

public class Main {
    public static final int MAX = 400004;
    public static String[] arr = new String[MAX];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, Integer> hm = new HashMap<>();
        ArrayList<ArrayList<Integer>> load = new ArrayList<>(MAX);
        for (int i=0;i<MAX;i++) load.add(new ArrayList<>());

        int n = Integer.parseInt(br.readLine());

        int now = 1;
        int[] indegree = new int[MAX];
        for (int i=0;i<n;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            String a = st.nextToken(), b = st.nextToken();

            if (!hm.containsKey(a)) {
                hm.put(a, now++);
            }
            if (!hm.containsKey(b)) {
                hm.put(b, now++);
            }

            arr[hm.get(a)] = a;
            arr[hm.get(b)] = b;

            indegree[hm.get(b)] += 1;
            load.get(hm.get(a)).add(hm.get(b));
        }

        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }) , temp = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        now -= 1;
        int count = 0;
        for (int i=1;i<=now;i++) {
            if(indegree[i] == 0) {
                count++;

                temp.offer(arr[i]);
            }
        }

        StringBuilder sb = new StringBuilder();

        while(!temp.isEmpty()) {
            Object af = pq;
            pq = temp;
            temp = (PriorityQueue<String>) af;

            while(!pq.isEmpty()) {
                String nows = pq.poll();
                sb.append(nows).append('\n');
                int idx = hm.get(nows);

                for (int go : load.get(idx)) {
                    if (--indegree[go] == 0) {
                        temp.offer(arr[go]);
                        count++;
                    }
                }
            }
        }

        System.out.println(count == now ? sb : -1);


    }
}