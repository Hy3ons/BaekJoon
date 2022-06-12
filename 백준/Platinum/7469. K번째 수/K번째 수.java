import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Tree {
    private ArrayList<ArrayList<Integer>> tree;
    private int leftIdx, rightIdx, start;
    private int value, maxCount;
    private ArrayList<Integer>[] ar;
    private int maxInt, minInt;
    Tree (int[] arr) {
        start = getStart(arr.length);

        ar = new ArrayList[start+10];
        tree = new ArrayList<>(start*2);

        for (int i=0;i<start*2;i++) tree.add(new ArrayList<>());

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
        this.value = value;
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
        this.leftIdx = leftIdx;
        this.rightIdx = rightIdx;
    }

    public int answer () {
        maxCount = 0;
        minInt = Integer.MIN_VALUE;
        maxInt = Integer.MAX_VALUE;

        query(1, 1, start);

        while (true) {
            int mid = (minInt+maxInt) / 2;

            if (reCount(mid)>value) {
                maxInt = mid;
            } else {
                minInt = mid;
            }

            if (maxInt-minInt == 1) return minInt;
        }
    }

    private int reCount (int standard) {
        int result = 0;
        for (int k=0;k<maxCount;k++) {
            result += low_bound(standard, ar[k]);
        }
        return result;
    }

    private int low_bound (int goal, ArrayList<Integer> al) {
        int left = -1;
        int right = al.size();

        while (true) {
            int mid = (left+right) / 2;

            if (al.get(mid)<goal) {
                left = mid;
            } else right = mid;

            if (right - left == 1) return right;
        }
    }

    private void query (int node, int nowLeftIdx, int nowRightIdx) {
        if (nowRightIdx<leftIdx||rightIdx<nowLeftIdx) return;

        if (leftIdx<=nowLeftIdx&&nowRightIdx<=rightIdx) {
            ar[maxCount++] = tree.get(node);
            return;
        }

        int mid = (nowLeftIdx+nowRightIdx) / 2;

        query(node*2, nowLeftIdx, mid);
        query(node*2+1, mid+1, nowRightIdx);
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int queryAmount = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        int[] arr = new int[testcase];

        for (int i=0;i<testcase;i++) arr[i] = Integer.parseInt(st.nextToken());
        Tree tree = new Tree(arr);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i=0;i<queryAmount;i++) {
            st = new StringTokenizer(br.readLine());

            tree.setIdx(Integer.parseInt(st.nextToken())
                    , Integer.parseInt(st.nextToken()));

            tree.setValue(Integer.parseInt(st.nextToken())-1);

            bw.write(tree.answer()+"\n");
        }

        bw.flush();
    }

}
