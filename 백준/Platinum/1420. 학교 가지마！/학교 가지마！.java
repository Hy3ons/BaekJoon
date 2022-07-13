import javax.print.attribute.standard.JobKOctets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    char type;
    int index;
    Node[] near = new Node[5];
    Node output;
    int[] flow = new int[5], limit = new int[5];

    Node (char type, int index, boolean make, int size) {
        this.type = type;
        this.index = index;
        if (make) {
            output = new Node(type, index + size, false, size);
            near[4] = output;
            limit[4] = 1;
        }
    }
}

public class Main {
    public static int[] dx = {-1,0,1,0} , dy = {0,1,0,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        int size = a*b;

        char[][] graph = new char[a][];
        for (int i=0;i<a;i++) graph[i] = br.readLine().toCharArray();

        Node[][] board = new Node[a][b];
        Node[] linear = new Node[size*2];

        int start = -1, goal = -1;

        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {
                int index = i*b + j;
                linear[index] = new Node(graph[i][j], index, true, size);
                linear[index + size] = linear[index].output;
                board[i][j] = linear[index];

                if (graph[i][j]=='K') start = index;
                if (graph[i][j]=='H') goal = index;
            }
        }

        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {

                for (int k=0;k<4;k++) {
                    int x = i + dx[k];
                    int y = j + dy[k];

                    if (x==-1||x==a || y==-1 || y==b) continue;
                    board[i][j].output.near[k] = board[x][y];
                    board[i][j].near[k] = board[x][y].output;
                    if (graph[x][y]=='#') continue;

                    board[i][j].output.limit[k] = 1;
                }
            }
        }

        if (start == -1 || goal == -1 ) {
            System.out.println(-1);
            return;
        }

        for (int k=0;k<4;k++) {
            if (linear[start].output.near[k]==null) continue;

            if (linear[start].output.near[k].type == 'H') {
                System.out.println(-1);
                return;
            }
        }

//        int[] level = new int[size*2];
        int[] re = new int[size*2];
        Queue<Node> qu = new LinkedList<>();
        int result = 0;

//        while (true) {
//            Arrays.fill(level, -1);
//            qu.offer(linear[start]);
//            level[start] = 0;
//
//            while(!qu.isEmpty()) {
//                Node now = qu.poll();
//
//                for (int i=0;i<4;i++) {
//                    if (now.near[i]==null || now.near[i].type =='#') continue;
//
//                    if (level[now.near[i].index]== -1
//                            && now.limit[i] > now.flow[i]) {
//                        level[now.near[i].index] = level[now.index] + 1;
//                        qu.offer(now.near[i]);
//                    }
//                }
//
//            }
//
//            if (level[goal]==-1) break;

            while (true) {
                Arrays.fill(re, -1);
                qu.offer(linear[start].output);

                while(!qu.isEmpty()) {
                    Node now = qu.poll();

                    for (int i=0;i<5;i++) {
                        if (now.near[i]==null || now.near[i].type =='#') continue;

                        if (re[now.near[i].index]== -1 && now.limit[i] > now.flow[i]) {
                            re[now.near[i].index] = now.index;
                            qu.offer(now.near[i]);
                        }
                    }

                }

                if (re[goal]==-1) break;

                for (int i=goal;i!=linear[start].output.index;i = re[i]) {
                    for (int k=0;k<5;k++) {
                        if (linear[re[i]].near[k] == linear[i]) {
                            linear[re[i]].flow[k]++;
                            break;
                        }
                    }
                    for (int k=0;k<5;k++) {
                        if (linear[i].near[k]==linear[re[i]]) {
                            linear[i].flow[k]--;
                            break;
                        }
                    }
                }
                result++;
            }
//        }
        System.out.println(result);

    }
}