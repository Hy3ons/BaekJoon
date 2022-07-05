import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

        while(testcase-->0) {
            int a = Integer.parseInt(br.readLine());

            if (a==0) {
                if (pq.isEmpty()) {
                    bw.write("0\n");
                } else {
                    bw.write(pq.poll()+"\n");
                }
            } else {
                pq.offer(a);
            }
        }
        bw.flush();
    }
}