import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
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

    public void clear () {
        left = null;
        right = null;
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
    Stack<Log> logs = new Stack();
    class Log {
        Node node;
        int type;
        Line line;
        Log (Node node, int type, Line line) {
            this.node = node;
            this.type = type;
            this.line = line;
        }
    }
    Lichao () {
        top = new Node();
        top.s = -Main.MAX;
        top.e = -top.s;
        top.mid = (top.s + top.e) / 2;
    }

    public long query (Node node, long x) throws Exception {
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
    public int update(Node node, Line line) {
        if (node.line == null) {
            node.line = line;
            logs.add(new Log(node, 3, null));
            return 1;
        }

        boolean L = node.line.function(node.s) <= line.function(node.s)
                , R = node.line.function(node.e) <= line.function(node.e);

        if (L && R) {
            logs.add(new Log(node, 1, node.line));
            node.line = line;
            return 1;
        }

        if (L == R) return 0;
        if (node.s == node.e) {
            if (node.line.function(node.mid) < line.function(node.mid)) {
                logs.add(new Log(node, 2, node.line));
                node.line = line;
                return 1;
            }
            return 0;
        }


        if (node.left == null) {
            node.summonNode();
            logs.add(new Log(node, 2, null));
            int c = Line.compare(node.line, line, node.mid);

            if (c == 0) {
                if (node.line.a == line.a) return 0;
                if (node.line.a < line.a) {
                    logs.add(new Log(node.right, 2, node.right.line));
                    node.right.line = line;
                } else {
                    logs.add(new Log(node.left, 2, node.left.line));
                    node.left.line = line;
                }
                return 1;
            } else if (c != 1) {
                if (L) {
                    logs.add(new Log(node.left, 2, node.left.line));
                    node.left.line = line;
                    return 2 + update(node.right, line);
                } else {
                    logs.add(new Log(node.right, 2, node.right.line));
                    node.right.line = line;
                    return 2 + update(node.left, line);
                }
            } else {
                if (L) {
                    return 1 + update(node.left, line);
                } else {
                    return 1 + update(node.right, line);
                }
            }
        } else {
            return update(node.left, line) + update(node.right, line);
        }

    }

    public void redo(int times) {
        while(times-->0){
            Log log = logs.pop();

            if (log.type == 1) {
                log.node.line = log.line;
            } else if (log.type == 2) {
                log.node.clear();
            } else if (log.type == 3) {
                log.node.line = null;
            }
        }
    }
}

class Segment {
    private ArrayList<Line>[] tree;
    private int start = 1;
    private Lichao lichao = new Lichao();
    public BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    Segment () {
        while(start <= 300000) start <<= 1;

        tree = new ArrayList[start<<1];
        for (int i=0;i<tree.length;i++) tree[i] = new ArrayList<Line>();
    }

    public void update (int node, Line line, int s, int e, int left, int right) {
        if (e < left || right < s) return;

        if (s <= left && right <= e) {
            tree[node].add(line);
            return;
        }

        int mid = left + right >> 1;

        update(node*2, line, s, e, left, mid);
        update(node*2+1, line, s, e, mid+1, right);
    }

    public void query (int node, int s, int e, int left, int right) throws IOException {
        if (e < left || right < s) return;

        int times = 0;
        for (Line l : tree[node]) {
            times += lichao.update(lichao.top, l);
        }

        if (left == right) {
            try {
                bw.write(lichao.query(lichao.top, Main.ans.get(left-1))+"\n");
            } catch (Exception ex) {
                bw.write("EMPTY\n");
            }
            lichao.redo(times);
            return;
        }

        int mid = left + right >> 1;
        query(node*2, s, e, left, mid);
        query(node*2+1, s, e, mid+1, right);

        lichao.redo(times);
    }
}

public class Main {
    public static long MAX = 1000000001L;
    public static ArrayList<Long> ans = new ArrayList<>();

    static class Pair {
        long a, b;
        int sTime;

        Pair (long a, long b, int sTime) {
            this. a= a;
            this.b = b;
            this.sTime = sTime;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int queries = Integer.parseInt(br.readLine());

        Segment tree = new Segment();
        int timeline = 1;
        HashMap<Integer, Pair> hm = new HashMap<>();
        int idx = 0;

        while(queries-->0) {
            idx++;
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            if (M == 1) {
                long a = Long.parseLong(st.nextToken())
                        , b = Long.parseLong(st.nextToken());

                Pair p = new Pair(a, b, timeline);
                hm.put(idx, p);
            } else if (M == 2){
                int idxx = Integer.parseInt(st.nextToken());
                int eTime = timeline-1;
                Pair p = hm.get(idxx);
                hm.remove(idxx);

                if (p.sTime > eTime) continue;
                tree.update(1, new Line(p.a, p.b), p.sTime, eTime, 1, 300000);
            } else {
                long x = Long.parseLong(st.nextToken());
                ans.add(x);
                timeline++;
            }
        }

        ArrayList<Integer> keys = new ArrayList<>(hm.keySet());
        for (int i=0;i<keys.size();i++) {
            Pair p = hm.get(keys.get(i));

            if (p.sTime > timeline-1) continue;
            tree.update(1, new Line(p.a, p.b), p.sTime, timeline-1, 1, 300000);
        }

        tree.query(1, 1, timeline-1, 1, 300000);
        tree.bw.flush();
    }
}
