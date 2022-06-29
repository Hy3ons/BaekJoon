import java.util.ArrayList;
import java.util.Scanner;

class Trr {
    private Trr[] leaf = new Trr[27];
    private ArrayList<Integer> loc = new ArrayList<>();
    private char s;

    Trr (char s) {
        this.s = s;
    }

    public static void update (Trr node, char[] arr, int depth) {
        if (arr.length == depth) {
            node.setStar();
            return;
        }

        if (node.leaf[arr[depth]-'a']==null) {
            node.leaf[arr[depth]-'a'] = new Trr(arr[depth]);
            node.loc.add(arr[depth]-'a');
        }

        update(node.leaf[arr[depth]-'a'], arr, depth+1);
    }

    public static void print(Trr node, int testcase) {
        int temp = 0;
        for (int idx : node.loc) {
            temp += counting(node.leaf[idx], 1);
        }
        double result = (double) temp / testcase;
        System.out.printf("%.2f\n", result);
    }

    public void setStar () {
        leaf[26] = new Trr('*');
        loc.add(26);
    }

    private static int counting (Trr node, int num) {
        if (node.loc.size()==0) {
            return num;
        }else if (node.loc.size() == 1) {
            return counting(node.leaf[node.loc.get(0)], num);
        } else {
            int sum = 0;
            for (int idx : node.loc) {
                sum += counting(node.leaf[idx], num+1);
            }
            if (node.leaf[26]!=null) sum--;
            return sum;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()) {
            int testcase = Integer.parseInt(sc.nextLine());
            Trr trie = new Trr('*');

            for (int i=0;i<testcase;i++) {
                char[] s = sc.nextLine().toCharArray();
                Trr.update(trie, s, 0);
            }

            Trr.print(trie, testcase);
        }
    }
}
