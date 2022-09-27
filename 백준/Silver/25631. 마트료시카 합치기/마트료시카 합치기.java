import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=0;i<n;i++) arr[i] = Integer.parseInt(st.nextToken()  );

        Arrays.sort(arr);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i=n-1;i>=0;i--) {
            if (pq.isEmpty()) {
                pq.add(arr[i]);
                continue;
            }

            if (pq.peek() == arr[i]) {
                pq.offer(arr[i]);
            } else {
                pq.poll();
                pq.add(arr[i]);
            }
        }
        System.out.println(pq.size());
    }

}
