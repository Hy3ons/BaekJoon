import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

class Tre {
    private int[] tree, idx, rank;
    private int start ,leftIdx = 1, rightIdx, size, value;
    private BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    Tre (int[] arr, int[] rank, int[] idx) {
        this.rank = rank;
        this.idx = idx;
        start = 1;
        while(start<arr.length) start *=2;
        size = arr.length;
        tree = new int[start*2];
        Arrays.fill(tree, Integer.MAX_VALUE);

        for (int i=0;i<arr.length;i++) {
            tree[start+i] = arr[i];
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<2*i;j++) {
                tree[j] = Math.min(tree[j*2], tree[j*2+1]);
            }
        }
    }

    private int getValue (int node, int leftIdx, int rightIdx) {
        if (rightIdx<this.leftIdx||this.rightIdx<leftIdx)
            return Integer.MAX_VALUE;

        if (this.leftIdx<=leftIdx&&rightIdx<=this.rightIdx) {
            return tree[node];
        }
        int mid = (leftIdx + rightIdx) / 2;

        return Math.min(getValue(node*2, leftIdx, mid)
                , getValue(node*2+1, mid+1, rightIdx));
    }

    private void update (int node) {
        tree[node] = Integer.MAX_VALUE;

        node/= 2;
        while(node!=0) {
            tree[node] = Math.min(tree[node*2], tree[node*2+1]);
            node /= 2;
        }
    }

    private void setPrev (int right, int value) {
        this.rightIdx = right;
        this.value = value;
    }

    public void print() throws IOException {
        int result = 0;
        for (int i=size-1;i>=0;i--) {
            setPrev(idx[i], i);
            int temp = getValue(1, 1, start);
            int nowRank = tree[start+idx[i]];

            if (temp>nowRank)
                result++;
            update(start+idx[i]);
        }
        bw.write(result+"");
        bw.flush();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] Sorting = new int[N]
                , idx = new int[N]
                , rank = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine())
                , st2 = new StringTokenizer(br.readLine())
                , st3 = new StringTokenizer(br.readLine());

        for (int i=0;i<N;i++)
            Sorting[Integer.parseInt(st.nextToken())-1] = i;


        for (int i=0;i<N;i++) {
            rank[i] = Sorting[Integer.parseInt(st2.nextToken())-1];
            idx[rank[i]] = i;
        }
        int[] last = new int[N];

        for (int i=0;i<N;i++)
            last[idx[Sorting[Integer.parseInt(st3.nextToken())-1]]] = i;

        Tre tree = new Tre(last, rank, idx);
        tree.print();

    }
}
