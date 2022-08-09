import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair {
    int dead, value;
    Pair (int dead, int value) {
        this.dead =dead;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        Pair[] arr = new Pair[testcase];

        for (int i=0;i<arr.length;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(arr, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o2.dead, o1.dead);
            }
        });
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o2.value, o1.value);
            }
        });

        int nowDay = 0;
        int time = 0;
        long result = 0;

        for (int i=0;i<arr.length;) {
            int temp = arr[i].dead;

            while(i < arr.length && temp == arr[i].dead) {
                pq.offer(arr[i]);
                i++;
            }

            time = i < arr.length ? temp - arr[i].dead : temp;

            while(time > 0 && !pq.isEmpty()) {
                result += pq.poll().value;
                time--;
            }
        }

        System.out.println(result);
    }
}