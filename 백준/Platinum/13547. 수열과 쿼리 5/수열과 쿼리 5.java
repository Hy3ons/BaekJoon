import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Pair {
    int left, right, sqrt, answer;
    Pair (int left, int right) {
        this.left = left;
        this.right = right;
        sqrt = (int) Math.sqrt(left);
    }
}

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int size = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[size];

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        final int P = Integer.parseInt(br.readLine());
        Pair[] queries = new Pair[P];
        for (int i=0;i<P;i++) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            queries[i] = new Pair(left, right);
        }
        Pair[] answer = queries.clone();

        Arrays.sort(queries, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.sqrt == o2.sqrt) return Integer.compare(o1.right, o2.right);
                else return Integer.compare(o1.sqrt, o2.sqrt);
            }
        });

        int nowSqrt = -1;
        int nowL = -1, nowR = -1;
        number = new int[1000001];

        for (int i=0;i<queries.length;i++) {
            if (queries[i].sqrt != nowSqrt) {
                Arrays.fill(number, 0);
                count = 0;
                nowSqrt = queries[i].sqrt;
                nowL = queries[i].left;
                nowR = queries[i].right;

                for (int j=nowL-1;j<nowR;j++)
                    count += addNum(arr[j]);
            } else {
                for (int j=nowR;j<queries[i].right;j++)
                    count += addNum(arr[j]);
                nowR = queries[i].right;

                if (nowL > queries[i].left) {
                    for (int j = queries[i].left-1;j<nowL-1;j++) {
                        count += addNum(arr[j]);
                    }
                } else if (nowL < queries[i].left) {
                    for (int j=nowL-1;j<queries[i].left-1;j++) {
                        count+= popNum(arr[j]);
                    }
                }
                nowL = queries[i].left;
            }
            queries[i].answer = count;
        }
        for (Pair pa : answer)
            bw.write(pa.answer+"\n");

        bw.flush();
    }
    public static int[] number;
    public static int count;

    public static int addNum (int num) {
        if (++number[num] == 1) return 1;
        else return 0;
    }
    public static int popNum (int num) {
        if (--number[num] == 0) return -1;
        else return 0;
    }
}
