import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair {
    int left, right, sqrt, answer;
    Pair (int left, int right) {
        this.left = left;
        this.right = right;
        sqrt = (int) Math.sqrt(left);
    }
}

class Segment{
    private int[] tree;
    private int start = 1, leftIdx = 1, rightIdx;
    Segment() {
        rightIdx = 100000;
        while (start < rightIdx) start *= 2;
        tree = new int[start * 2];

    }
    public int answer () {
        return tree[1];
    }

    public void plus (int num) {
        int idx = num -1 + start;
        tree[idx]++;
        idx /= 2;
        while (idx !=0 ){
            tree[idx] = Math.max(tree[idx*2], tree[idx*2+1]);
            idx /= 2;
        }
    }
    public void minus (int num) {
        int idx = num -1 + start;
        tree[idx]--;
        idx /= 2;
        while (idx !=0 ){
            tree[idx] = Math.max(tree[idx*2], tree[idx*2+1]);
            idx /= 2;
        }
    }
    public void clear () {
        Arrays.fill(tree, 0);
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
        Segment tree = new Segment();

        for (int i=0;i<queries.length;i++) {
            if (queries[i].sqrt != nowSqrt) {
                tree.clear();
                nowSqrt = queries[i].sqrt;
                nowL = queries[i].left;
                nowR = queries[i].right;

                for (int j=nowL-1;j<nowR;j++)
                    tree.plus(arr[j]);
            } else {
                for (int j=nowR;j<queries[i].right;j++)
                    tree.plus(arr[j]);
                nowR = queries[i].right;

                if (nowL > queries[i].left) {
                    for (int j = queries[i].left-1;j<nowL-1;j++) {
                        tree.plus(arr[j]);
                    }
                } else if (nowL < queries[i].left) {
                    for (int j=nowL-1;j<queries[i].left-1;j++) {
                        tree.minus(arr[j]);
                    }
                }
                nowL = queries[i].left;
            }
            queries[i].answer = tree.answer();
        }
        for (Pair pa : answer)
            bw.write(pa.answer+"\n");

        bw.flush();
    }
}
