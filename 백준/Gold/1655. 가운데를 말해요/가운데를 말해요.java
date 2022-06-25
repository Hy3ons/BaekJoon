import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> min = new PriorityQueue<>()
                , max = new PriorityQueue<>(Comparator.reverseOrder());

        max.offer(Integer.parseInt(br.readLine()));
        bw.write(max.peek()+"\n");

        for (int i=1;i<testcase;i++) {
            if (i%2==1) min.offer(Integer.parseInt(br.readLine()));
            else max.offer(Integer.parseInt(br.readLine()));

            if (max.peek()>min.peek()) {
                int temp = max.poll();
                max.offer(min.poll());
                min.offer(temp);
            }

            bw.write(max.peek()+"\n");
        }
        bw.flush();
    }
}
