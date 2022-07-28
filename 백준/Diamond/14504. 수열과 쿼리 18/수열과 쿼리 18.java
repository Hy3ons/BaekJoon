import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.function.IntPredicate;

class Bucket {
    int left, right;
    private int[] ary;
    Bucket(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public void update (int value, int to) {
        int idx = findIdx(value);

        ary[idx] = to;
        Arrays.sort(ary);
    }
    public void start () {
        ary = new int[right - left + 1];
        for (int i=left-1;i<right;i++)
            ary[i -left +1] = Main.arr[i];
        Arrays.sort(ary);
    }

    public int low_bound(int value) {
        int left = -1;
        int right = ary.length;

        while (true) {
            int mid = (left + right)/ 2;

            if (ary[mid] <= value) {
                left = mid;
            } else {
                right = mid;
            }

            if (right - left == 1) return ary.length - right;
        }
    }
    private int findIdx (int value) {
        int left = -1;
        int right = ary.length;

        while (true) {
            int mid = (left + right) / 2;

            if (ary[mid] < value) {
                left = mid;
            } else {
                right = mid;
            }

            if (right - left == 1) return right;
        }
    }
}

public class Main {
    public static ArrayList<Bucket> buckets = new ArrayList<>();
    public static int[] arr, myBucket;
    public static int sqrt;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int size = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        arr = new int[size];
        myBucket = new int[size + 1];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        final int P = Integer.parseInt(br.readLine());

        sqrt = (int) Math.sqrt(size) * 2;

        int L = 1;
        while (L <= size) {
            int bucketIdx = buckets.size();
            int end = L + sqrt - 1;

            if (end > size)
                end = size;

            buckets.add(new Bucket(L, end));
            buckets.get(bucketIdx).start();

            for (int i = L;i <= end; i++)
                myBucket[i] = bucketIdx;

            L += sqrt;
        }

        for (int K=0;K<P;K++) {
            st = new StringTokenizer(br.readLine());

            int M = Integer.parseInt(st.nextToken());

            if (M == 1) {
                int left = Integer.parseInt(st.nextToken())
                        , right = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());

                boolean leftSide = true, RightSide = true;
                int result = 0;

                int start = left / sqrt;

                for (int j=start;j<buckets.size();j++) {
                    Bucket b = buckets.get(j);

                    if (left <= b.left && b.right <= right) {
                        if (leftSide) {
                            leftSide = false;

                            for (int i=left-1;i<b.left-1;i++)
                                if (arr[i] > value) result++;
                        }

                        result += b.low_bound(value);
                        if (b.right == right) RightSide = false;
                    } else if (!leftSide) {
                        if (RightSide) {
                            RightSide = false;

                            for (int i = b.left-1;i<right;i++)
                                if (arr[i] > value) result++;
                        } else {
                            break;
                        }
                    }
                }

                if (leftSide) {
                    for (int i = left - 1; i<right;i++) {
                        if (arr[i] > value) result++;
                    }
                }

                bw.write(result+"\n");
            } else {
                int idx = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());

                buckets.get(myBucket[idx]).update(arr[idx-1], value);
                arr[idx-1] = value;
            }
        }
        bw.flush();

    }
}
