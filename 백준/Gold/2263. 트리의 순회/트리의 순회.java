import java.io.*;
import java.util.StringTokenizer;

class Tree {
    Tree left;
    Tree right;
    int value;
    Tree (int value) {
        this.value = value;
    }
}

public class Main {
    public static int mki (StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        ordered = new int[testcase];
        arr = new int[testcase];

        StringTokenizer st = new StringTokenizer(br.readLine()),
                st2 = new StringTokenizer(br.readLine());

        for (int i=0;i< testcase;i++) {
            ordered[mki(st)-1] = i;
            arr[i] = mki(st2);
        }
        //예외 처리
        if (testcase==1) {
            System.out.println(arr[0]);
            return;
        }

        int length = -1;
        Tree tree = new Tree(arr[arr.length-1]);
        length = ordered[arr[arr.length-1]-1];

        //나누면서 들어가기.
        if (length > 0)
            makeTree(tree, 0, length-1, arr.length-1, true);
        if (testcase-1-length > 0)
            makeTree(tree, length, testcase-2, arr.length-1, false);

        recursive(tree, 0);
        bw.flush();
    }
    public static int[] ordered, arr;

    public static void makeTree (Tree node, int start, int end, int prevLast, boolean c) {
        Tree temp = new Tree(arr[end]);
        if (c)
            node.left = temp;
        else
            node.right = temp;

        if (start>=end)
            return;

        int length = c ? end - start - (ordered[arr[prevLast] -1] - ordered[arr[end] -1] - 1)
                : ordered[arr[end]-1] - ordered[arr[prevLast]-1] - 1;

        if (length>0)
            makeTree(temp, start, start + length - 1, end, true);

        if (end - start - length > 0)
            makeTree(temp, start+length, end-1, end, false);

    }

    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void recursive (Tree node,int depth) throws IOException {
        if (node==null) return;
        bw.write(node.value+" ");

        recursive(node.left, depth+1);
        recursive(node.right, depth+1);
    }
}