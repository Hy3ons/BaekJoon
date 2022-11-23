import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class Trie {
    private ArrayList<Trie> leaf = new ArrayList<>();
    public static ArrayList<Trie> finish = new ArrayList<>();
    public static int count;
    public Trie father;
    boolean finished;
    public int floor;
    public char s;
    public static char[][] board;
    public String word;
    private static int[] dx = {-1, -1, -1, 0, 0, 0, 1, 1, 1}
            , dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

    Trie (int floor, char s, Trie father, String word) {
        this.floor = floor;
        this.s = s;
        this.father = father;
        this.word = word;
    }
    public static void update (Trie node, char[] a, int index) throws Exception {
        if (index==a.length) {
            node.finished = true;
            finish.add(node);
            return;
        }

        for (int i=0;i<node.leaf.size();i++) {
            if (node.leaf.get(i).s==a[index]) {
                update(node.leaf.get(i), a, index+1);
                return;
            }
        }

        Trie temp = new Trie(index+1, a[index], node, node.word+a[index]);
        node.leaf.add(temp);
        update(temp, a, index+1);

    }

    public static void func (Trie node, int x, int y, boolean[][] visited) {
        char st = board[x][y];
        for (int i=0;i<node.leaf.size();i++) {
            if (node.leaf.get(i).s==st) {
                if (node.leaf.get(i).finished) {
                    node.leaf.get(i).finished = false;
                    count++;
                }
                funSub(node.leaf.get(i), x, y, visited);
            }
        }
    }

    private static void funSub (Trie node, int x, int y, boolean[][] visited) {
        for (int i=0;i<9;i++) {
            int ddx = dx[i] + x;
            int ddy = dy[i] + y;

            if (ddx==-1||ddx==4||ddy==-1||ddy==4) continue;
            if (visited[ddx][ddy]) continue;

            visited[ddx][ddy] = true;
            func(node, ddx, ddy, visited);
            visited[ddx][ddy] = false;
        }
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());
        Trie tree = new Trie(0, '-', null, "");

        while (testcase-->0) {
            char[] s = br.readLine().toCharArray();
            Trie.update(tree, s, 0);
        }

        br.readLine();
        testcase = Integer.parseInt(br.readLine());
        Trie.board = new char[4][];

        char[][] board = Trie.board;
        boolean[][] visited = new boolean[4][4];

        for (int i=0;i<testcase;i++) {
            if (i!=0) br.readLine();
            for (int j=0;j<4;j++)
                board[j] = br.readLine().toCharArray();


            for (int j=0;j<4;j++) {
                for (int k=0;k<4;k++) {
                    // j 행 k 열부터 시작한다.

                    visited[j][k] = true;
                    Trie.func(tree, j, k, visited);
                    visited[j][k] = false;
                }
            }

            int result = Integer.MIN_VALUE;
            int score = 0;
            int amountword = 0;
            Trie temp = null;

            ArrayList<Trie> same = new ArrayList<>();

            for (Trie tr : Trie.finish) {
                if (!tr.finished) {
                    tr.finished = true;
                    amountword++;

                    if (result<tr.floor) {
                        same.clear();

                        same.add(tr);
                        result = tr.floor;
                    } else if (result==tr.floor) {
                        same.add(tr);
                    }

                    switch (tr.floor) {
                        case 3 :
                            score++;
                            break;
                        case 4 :
                            score++;
                            break;
                        case 5 :
                            score += 2;
                            break;
                        case 6 :
                            score += 3;
                            break;
                        case 7 :
                            score += 5;
                            break;
                        case 8 :
                            score += 11;
                            break;
                    }
                }
            }
            Collections.sort(same, new Comparator<Trie>() {
                @Override
                public int compare(Trie o1, Trie o2) {
                    return o1.word.compareTo(o2.word);
                }
            });

            StringBuilder sb = new StringBuilder();

            sb.append(score).append(" ").append(same.get(0).word);
            sb.append(" ").append(amountword);
            System.out.println(sb);
        }

    }
}
