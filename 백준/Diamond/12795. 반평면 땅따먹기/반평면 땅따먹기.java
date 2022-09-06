import java.io.*;
import java.util.StringTokenizer;

class Node {
    Node left, right;
    long s, e, mid;
    Line line;

    Node() {

    }

    Node(long s, long e) {
        this.s = s;
        this.e = e;
    }

    public void setMid () {
        mid = (long) Math.floor((s + e) / 2.0);
    }

    public void summonNode() {
        left = new Node(s, mid);
        right = new Node(mid+1, e);
        left.setMid();
        right.setMid();

        left.line = right.line = line;
    }

}

class Line {
    long a, b;
    Line (long a, long b) {
        this.a = a;
        this.b = b;
    }

    public long function(long x) {
        return a * x + b;
    }

    public static int compare (Line o1, Line o2, long x) {
        return Long.compare(o1.function(x), o2.function(x));
    }
}

class Lichao {
    Node top;
    Lichao () {
        top = new Node();
        top.s = -Main.MAX;
        top.e = -top.s;
        top.mid = (top.s + top.e) / 2;
    }

    public long query (Node node, long x) {
        if (node.s == node.e) return node.line.function(x);

        if (x <= node.mid) {
            if (node.left == null) {
                return node.line.function(x);
            } else {
                return Math.max(node.line.function(x), query(node.left, x));
            }
        } else {
            if (node.right == null) {
                return node.line.function(x);
            } else {
                return Math.max(node.line.function(x), query(node.right, x));
            }
        }
    }
    public void update(Node node, Line line) {
        if (node.line == null) {
            node.line = line;
            return;
        }

        boolean L = node.line.function(node.s) <= line.function(node.s)
                , R = node.line.function(node.e) <= line.function(node.e);

        if (L && R) {
            node.line = line;
            return;
        }

        if (L == R) return;
        if (node.s == node.e) {
            if (node.line.function(node.mid) <= line.function(node.mid)) {
                node.line = line;
            }
            return;
        }


        if (node.left == null) {
            node.summonNode();
            int c = Line.compare(node.line, line, node.mid);

            if (c == 0) {
                if (node.line.a == line.a) return;
                if (node.line.a < line.a) {
                    node.right.line = line;
                } else {
                    node.left.line = line;
                }
            } else if (c != 1) {
                if (L) {
                    node.left.line = line;
                    update(node.right, line);
                } else {
                    node.right.line = line;
                    update(node.left, line);
                }
            } else {
                if (L) {
                    update(node.left, line);
                } else {
                    update(node.right, line);
                }
            }
        } else {
            update(node.left, line);
            update(node.right, line);
        }

    }
}

public class Main {
    public static long MAX = 1000000000001L;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int queries = Integer.parseInt(br.readLine());

        Lichao tree = new Lichao();

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            if (M == 1) {
                long a = Long.parseLong(st.nextToken())
                        , b = Long.parseLong(st.nextToken());

                tree.update(tree.top, new Line(a, b));
            } else {
                long x = Long.parseLong(st.nextToken());
                bw.write(tree.query(tree.top, x)+"\n");
            }
        }
        bw.flush();
    }
}
