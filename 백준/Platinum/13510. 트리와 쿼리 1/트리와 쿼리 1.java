import java.io.*;
import java.util.*;
import java.util.function.IntPredicate;

class Line {
    public static Line[] lines;
    int n1, n2, value, index, whoUse;
    Line(int n1, int n2, int value, int index) {
        this.n1 = n1;
        this.n2 = n2;
        this.value = value;
        this.index = index;
    }
}

class Breath {
    int loc, prev;
    Breath (int loc , int prev) {
        this.loc = loc;
        this.prev = prev;
    }
}

class Go {
    int go, value, index;
    Go (int go, int value, int index) {
        this.go = go;
        this.value = value;
        this.index = index;
    }
}

class Segment {
    public static int[] myChain, usedLine, level, nodesIdx;
    public static int result;
    private int[] tree;
    private int start = 1, leftIdx, rightIdx, size;

    Segment (ArrayList<Integer> al) {
        while (start < al.size()) start *= 2;
        tree = new int[start*2];
        size = al.size();

        for (int i=0;i<al.size();i++) {
            tree[start + i] = al.get(i) == 1 ? 0
                    : Line.lines[usedLine[al.get(i)]].value;
//            tree[start + i] = Line.lines[usedLine[al.get(i)]].value;
            nodesIdx[al.get(i)] = i;
        }

        for (int i = start / 2; i!=0;i/=2) {
            for (int j=i ; j<i*2 ; j++) {
                tree[j] = Math.max(tree[j*2], tree[j*2+1]);
            }
        }
        leftIdx = 1;
        rightIdx = al.size();
    }

    public void update (int lineIdx, int value) {
        int node = start + nodesIdx[Line.lines[lineIdx].whoUse];
        tree[node] = value;
        node /= 2;

        while(node!=0) {
            tree[node] = Math.max(tree[node*2], tree[node*2+1]);
            node /= 2;
        }
    }
    public void nextChain (int nowNode) {
        leftIdx = nodesIdx[nowNode] + 1;
        rightIdx = size;
        query(1, 1, start);
    }
    public void query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            result = Math.max(result, tree[node]);
            return;
        }

        int mid = (left + right)/2;

        query(node*2, left, mid);
        query(node*2+1, mid+1, right);
    }

    public void answer (int left, int right) {
        if (left == right) return;

        leftIdx = nodesIdx[left] + 1;
        rightIdx = nodesIdx[right];
        query(1, 1, start);
    }

}

public class Main {
    public static int[] myChain, usedLine, level;
    public static ArrayList<ArrayList<Go>> load;
    public static void main(String[] args) throws IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());
        myChain = new int[size+1];
        usedLine = new int[size+1];
        level = new int[size+1];
        Segment.nodesIdx = new int[size+1];
        Segment.myChain = myChain;
        Segment.usedLine = usedLine;
        Segment.level = level;

        Line[] lines = new Line[size-1];
        Line.lines = lines;

        load = new ArrayList<>(size+1);
        for (int i=0;i<=size;i++) load.add(new ArrayList<>());

        for (int i=0;i<size-1;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            lines[i] = new Line(n1, n2, value, i);
            load.get(n1).add(new Go(n2, value, i));
            load.get(n2).add(new Go(n1, value, i));
        }

        Queue<Breath> qu = new LinkedList<>();
        qu.offer(new Breath(1,0));

        Stack<Integer> starts = new Stack<>();

        while(!qu.isEmpty()) {
            Breath now = qu.poll();
            boolean beStart = true;

            for (Go go : load.get(now.loc)) {
                if (go.go == now.prev) continue;

                level[go.go] = level[now.loc] + 1;
                qu.offer(new Breath(go.go, now.loc));
                beStart = false;
            }

            if (beStart) {
                starts.push(now.loc);
            }
        }
        Arrays.fill(myChain, -1);
        int countChain = -1;
        int[] chainDepth = new int[starts.size()];
        int[] nextChain = new int[starts.size()];
        Segment[] segments = new Segment[starts.size()];
        nextChain[0] = 0;

        while(!starts.isEmpty()) {
            countChain++;
            qu.offer(new Breath(starts.pop(), 0));
            myChain[qu.peek().loc] = countChain;
            ArrayList<Integer> al = new ArrayList<>(level[qu.peek().loc]+1);

            while(!qu.isEmpty()) {
                Breath now = qu.poll();
                al.add(now.loc);

                for (Go go : load.get(now.loc)) {
                    if (level[now.loc] - 1 == level[go.go]) {
                        usedLine[now.loc] = go.index;
                        lines[go.index].whoUse = now.loc;

                        if (myChain[go.go] == -1){
                            myChain[go.go] = myChain[now.loc];
                            qu.offer(new Breath(go.go, now.loc));
                        }  else {
                            nextChain[countChain] = go.go;
                            chainDepth[countChain] = chainDepth[myChain[go.go]] + 1;
                        }
                        break;
                    }

                }
            }
            segments[countChain] = new Segment(al);
        }

        int queries = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());
            int k1 = Integer.parseInt(st.nextToken());
            int k2 = Integer.parseInt(st.nextToken());

            Segment.result = 0;

            if (method==1) {
                k1--;
                segments[myChain[lines[k1].whoUse]].update(k1, k2);
            } else {
                // 체인 깊이 우선
                // 깊이가 같다면 체인이 같은지 확인
                //체인이 다르면 양쪽에서 한칸전진
                if (chainDepth[myChain[k1]] > chainDepth[myChain[k2]]) {
                    int temp = k1;
                    k1 = k2;
                    k2 = temp;
                }
                while(chainDepth[myChain[k1]] != chainDepth[myChain[k2]]) {
                    segments[myChain[k2]].nextChain(k2);
                    k2 = nextChain[myChain[k2]];
                }
                while(myChain[k1] != myChain[k2]) {
                    //체인을 전진시킨다.
                    //이때 체인이 0 이라면 전진시키지 않는다.
                    if (myChain[k1] != 0) {
                        segments[myChain[k1]].nextChain(k1);
                        k1 = nextChain[myChain[k1]];
                    }

                    if (myChain[k2] != 0) {
                        segments[myChain[k2]].nextChain(k2);
                        k2 = nextChain[myChain[k2]];
                    }
                }

                if (level[k1] < level[k2]) {
                    int temp = k1;
                    k1 = k2;
                    k2 = temp;
                }
                segments[myChain[k1]].answer(k1, k2);
                bw.write(Segment.result+"\n");
            }
        }
        bw.flush();

    }
}
