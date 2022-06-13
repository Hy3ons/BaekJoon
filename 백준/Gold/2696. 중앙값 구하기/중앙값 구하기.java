import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());

        for (int T=0;T<testcase;T++) {
            int length = Integer.parseInt(br.readLine());
            PriorityQueue<Integer> qu = new PriorityQueue<>();

            int[] arr = new int[length];
            int arrCount = 0;
            bw.write((length+1)/2+"\n");
            int tryTime = length/10 + 1;
            if (length%10==0) tryTime--;

            for (int i=0;i<tryTime;i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                while(st.hasMoreTokens())
                    arr[arrCount++] = Integer.parseInt(st.nextToken());
            }

            qu.offer(arr[0]);

            bw.write(arr[0]+" ");
            int stack = 1;
            boolean shouldNextLine = false;
            PriorityQueue<Integer> temp = new PriorityQueue<>();

            for (int i=1;i<length;i++) {
                qu.offer(arr[i]);

                if (i%2==0) {
                    int qs = qu.size();
                    int middle = qs/2;

                    for (int k=0;k<qs;k++) {
                        if (k==middle) {
                            int a = qu.poll();
                            bw.write(a+" ");
                            temp.offer(a);
                        } else {
                            temp.offer(qu.poll());
                        }
                    }
                    PriorityQueue<Integer> a = qu;
                    qu = temp;
                    temp = a;

                    shouldNextLine = true;

                    if (++stack==10) {
                        stack = 0;
                        bw.newLine();
                        shouldNextLine = false;
                    }
                }
            }

            if (shouldNextLine) bw.newLine();

        }
        bw.flush();
    }
}
