import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.Stream;

class Trees {
    private long[] tree;
    private int leftIdx, rightIdx, start = 1;

    Trees (long[] arr) {
        while (start < arr.length) start *= 2;

        tree = new long[start * 2];

        for (int i = 0; i < arr.length; i++)
            tree[start + i] = arr[i];

        for (int i = start / 2; i != 0; i /= 2)
            for (int j = i; j < i * 2; j++)
                tree[j] = tree[j * 2] + tree[j * 2 + 1];

    }

    public int getStart() {
        return start;
    }

    public void setIdx(int leftIdx, int rightIdx) {
        this.leftIdx = leftIdx;
        this.rightIdx = rightIdx;
    }

    public long query(int node, int nowLeftIdx, int nowRightIdx) {
        if (nowRightIdx < leftIdx || rightIdx < nowLeftIdx) return 0;

        if (leftIdx <= nowLeftIdx && nowRightIdx <= rightIdx) return tree[node];

        int mid = (nowRightIdx + nowLeftIdx) / 2;

        return query(node * 2, nowLeftIdx, mid)
                + query(node * 2 + 1, mid + 1, nowRightIdx);
    }

    public void update(Imf im) {
        int treeIdx = im.index + start - 1;
        tree[treeIdx] = im.value;
        treeIdx /= 2;

        while(treeIdx!=0) {
            tree[treeIdx] = tree[treeIdx*2] + tree[treeIdx*2+1];
            treeIdx /= 2;
        }
    }

    public long getQuery (Imf2 im) {
        setIdx(im.left, im.right);
        return query(1, 1, start);
    }

}

class Imf {
    int index, value;
    Imf (int i, int v){
        index = i;
        value = v;
    }
}

class Imf2 {
    int query, left, right, time;

    Imf2 (int q, int l, int r, int t) {
        query = q;
        left = l;
        right = r;
        time = t;
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        long[] arr = new long[testcase];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < testcase; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Trees tree = new Trees(arr);
        int start = tree.getStart();
        int queryAmount = Integer.parseInt(br.readLine());

        ArrayList<Imf> update = new ArrayList<>();
        ArrayList<Imf2> query = new ArrayList<>();

        for (int i=0;i<queryAmount;i++) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());

            switch (method) {
                case 1 :
                    update.add(new Imf(Integer.parseInt(st.nextToken())
                            , Integer.parseInt(st.nextToken())));
                    break;
                case 2 :
                    int size = query.size();
                    query.add(new Imf2(Integer.parseInt(st.nextToken())
                            , Integer.parseInt(st.nextToken())
                            , Integer.parseInt(st.nextToken()), size));
                    break;
            }
        }

        long[] result = new long[query.size()];
        Collections.sort(query, new Comparator<Imf2>() {
            @Override
            public int compare(Imf2 o1, Imf2 o2) {
                return o1.query-o2.query;
            }
        });
        int nowUpdate = 0;

        for (Imf2 im : query) {
            while (nowUpdate!=im.query) {
                tree.update(update.get(nowUpdate++));
            }

            result[im.time] = tree.getQuery(im);
        }


        StringBuilder sb = new StringBuilder();
        for (long element : result) sb.append(element+"\n");
        System.out.println(sb);

    }
}
