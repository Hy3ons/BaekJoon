import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Tree {
    private ArrayList<ArrayList<Integer>> tree;
    private int leftIdx, rightIdx, start;
    private int value, lastAnswer;

    Tree (int[] arr) {
        start = getStart(arr.length);

        tree = new ArrayList<>(start*2);

        for (int i=0;i<start*2;i++) tree.add(new ArrayList<>(1));

        for (int i=0;i<arr.length;i++) {
            tree.get(start+i).add(arr[i]);
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<2*i;j++) {
                tree.set(j, merge(tree.get(j*2), tree.get(j*2+1)));
            }
        }


    }

    public void setValue (int value) {
        this.value = value ^ lastAnswer;
    }

    private ArrayList<Integer> merge (ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        if (arr1.size()==0) {
            return (ArrayList<Integer>) arr2.clone();
        }
        if (arr2.size()==0) {
            return (ArrayList<Integer>) arr1.clone();
        }

        int c1 = 0, c2 = 0;
        ArrayList<Integer> result = new ArrayList<>(arr1.size()+arr2.size());

        while (true) {
            if (arr1.get(c1) < arr2.get(c2)) {
                result.add(arr1.get(c1++));
            } else if (arr1.get(c1) > arr2.get(c2)) {
                result.add(arr2.get(c2++));
            } else {
                result.add(arr2.get(c2++));
                result.add(arr1.get(c1++));
            }

            if (c1==arr1.size()||c2==arr2.size()) {
                if (c1<arr1.size()) {
                    while(c1!=arr1.size()) result.add(arr1.get(c1++));
                } else if (c2<arr2.size()) {
                    while(c2!=arr2.size()) result.add(arr2.get(c2++));
                }
                return result;
            }
        }
    }
    private int getStart (int testcase) {
        int result = 1;
        while (true) {
            if (result>=testcase) return result;
            result *= 2;
        }
    }

    public void setIdx (int leftIdx, int rightIdx) {
        this.leftIdx = leftIdx ^ lastAnswer;
        this.rightIdx = rightIdx ^ lastAnswer;
    }

    public int answer () {
        lastAnswer = query(1, 1, start);
        return lastAnswer;
    }

    private int low_bound (ArrayList<Integer> al) {
        int left = -1;
        int right = al.size();

        while (true) {
            int mid = (left+right) / 2;

            if (al.get(mid)>value) {
                right = mid;
            } else left = mid;

            if (right - left == 1) return al.size() - left - 1;
        }
    }

    private int query (int node, int nowLeftIdx, int nowRightIdx) {
        if (nowRightIdx<leftIdx||rightIdx<nowLeftIdx) return 0;

        if (leftIdx<=nowLeftIdx&&nowRightIdx<=rightIdx) {
            return low_bound(tree.get(node));
        }

        int mid = (nowLeftIdx+nowRightIdx) / 2;

        return query(node*2, nowLeftIdx, mid)
                + query(node*2+1, mid+1, nowRightIdx);
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int queryAmount = Integer.parseInt(br.readLine());
        int[] arr = new int[testcase];

        for (int i=0;i<testcase;i++) arr[i] = Integer.parseInt(st.nextToken());
        Tree tree = new Tree(arr);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i=0;i<queryAmount;i++) {
            st = new StringTokenizer(br.readLine());

            tree.setIdx(Integer.parseInt(st.nextToken())
                    , Integer.parseInt(st.nextToken()));

            tree.setValue(Integer.parseInt(st.nextToken()));

            bw.write(tree.answer()+"\n");
        }

        bw.flush();
    }

}
