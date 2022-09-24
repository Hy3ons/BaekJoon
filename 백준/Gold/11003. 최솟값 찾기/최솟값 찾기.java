import java.io.*;
import java.util.*;

public class Main {
    public static Deque<Integer> de = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");

        int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine()," ");
        int[] arr = new int[n];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        for (int i=0;i<k;i++) {
            add(arr[i]);
            bw.write(de.peekFirst()+" ");
        }

        for (int l=0,r=k;r<arr.length;r++,l++) {
            pop(arr[l]);
            add(arr[r]);
            bw.write(de.peekFirst()+" ");
        }
        bw.flush();

    }

    public static void add(int x) {
        if (de.isEmpty()) {
            de.offer(x);
            return;
        }


        while(!de.isEmpty() && x < de.peekLast()) de.pollLast();
        de.offerLast(x);
    }
    public static void pop(int x) {
        if (de.peekFirst() == x) de.pollFirst();
    }
}