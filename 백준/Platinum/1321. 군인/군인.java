import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Tree {
    private int[] tree;
    private int leftIdx = 1, rightIdx, value, start = 1, result;

    Tree (int[] arr) {
        while (start < arr.length) start *= 2;
        tree = new int[start*2];
        rightIdx = arr.length;

        for (int i=0;i<arr.length;i++) {
            tree[start+i] = arr[i];
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                tree[j] = tree[j*2] + tree[j*2+1];
            }
        }
    }
    public int answer (int value) {
        this.value = value;
        result = -1;
        query(1,1,start, 0);
        return result;
    }

    private int query (int node, int left, int right, int value) {
        if (right < leftIdx || rightIdx < left ) return 0;

        int mid = (left + right) / 2;

        if (leftIdx <= left && right <= rightIdx) {
            if (node >= start) {
                int m = value + tree[node];

                if (m >= this.value) {
                    result = right;
                    return this.value;
                } else {
                    return m;
                }
            }

            int temp = value + tree[node];

            if (temp > this.value) {
                //여기서 끝내야함.
                int temp2 = query(node*2, left, mid, value);
                if (temp2!=this.value)
                    query(node*2+1, mid+1, right, temp2);

                return this.value;
            } else if (temp < this.value) {
                return temp;
            } else {
                result = right;
                return this.value;
            }
        }


        int temp = query(node*2, left, mid, value);
        if (temp < this.value)
            query(node*2+1, mid+1, right, temp);

        return this.value;
    }


    public void update(int index, int value) {
        index += start - 1;
        while (index!=0) {
            tree[index] += value;
            index /= 2;
        }
    }
}


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[testcase];

        for (int i=0;i<testcase;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Tree tree = new Tree(arr);

        int queryAmount = Integer.parseInt(br.readLine());

        while (queryAmount-->0) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());

            switch (method) {
                case 1:
                    tree.update(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                    break;
                case 2:
                    bw.write(tree.answer(Integer.parseInt(st.nextToken()))+"\n");
                    break;
            }
        }
        bw.flush();

    }

}