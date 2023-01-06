import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class location {
    int x, y;
    location (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class locationF {
    int x, y;
    char door;
    boolean opened;

    locationF(int x, int y, char door, boolean opened) {
        this.x = x;
        this.y = y;
        this.door = door;
        this.opened = opened;
    }
}

public class Main {
    public static int[] dx = {1,-1,0,0}, dy = {0,0,-1,1};
    public static int a,b,result;
    public static Queue<location> qu;
    public static char[][] graph;
    public static HashSet<Character> hs;
    public static ArrayList<locationF> door;
    public static boolean[][] visited;

    public static void main (String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        for (int T=0;T<testcase;T++) {
            result = 0;
            StringTokenizer st = new StringTokenizer(br.readLine()," ");

            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            graph = new char[a][];

            for (int i=0;i<a;i++) {
                graph[i] = br.readLine().toCharArray();

            }


            hs = new HashSet<>();
            qu = new LinkedList<>();
            visited = new boolean[a][b];
            door = new ArrayList<>();

            char[] keys = br.readLine().toCharArray();
            if (keys[0]!='0') {
                for (int i=0;i<keys.length;i++) {
                    hs.add(keys[i]);
                }
            }

            entrance();
            function();

            while(true) {

                for (int j=0;j<door.size();j++) {

                    if (door.get(j).opened) continue;

                    if (hs.contains((char)(door.get(j).door+32))) {
                        qu.offer(new location(door.get(j).x, door.get(j).y));
                        door.get(j).opened = true;
                    }
                }

                if (!function()) break;
            }

            bw.write(result+"\n");


        }
        bw.flush();
    }

    public static void entrance () {
        for (int i=0;i<a;i++) {

            if (!visited[i][0]&&graph[i][0]!='*') {

                if (graph[i][0]=='.'){
                    qu.offer(new location(i,0));
                } else if (graph[i][0]=='$') {
                    qu.offer(new location(i,0));
                    result++;
                } else if (graph[i][0]>='a'&&graph[i][0]<='z') {
                    qu.offer(new location(i,0));
                    hs.add(graph[i][0]);
                } else if (graph[i][0]>='A'&&graph[i][0]<='Z') {
                    door.add(new locationF(i,0,graph[i][0],false));
                }
                visited[i][0] = true;
            }

            if (!visited[i][b-1]&&graph[i][b-1]!='*') {

                if (graph[i][b-1]=='.'){
                    qu.offer(new location(i,b-1));
                } else if (graph[i][b-1]=='$') {
                    qu.offer(new location(i,b-1));
                    result++;
                } else if (graph[i][b-1]>='a'&&graph[i][b-1]<='z') {
                    qu.offer(new location(i,b-1));
                    hs.add(graph[i][b-1]);
                } else if (graph[i][b-1]>='A'&&graph[i][b-1]<='Z') {
                    door.add(new locationF(i,b-1,graph[i][b-1],false));
                }

                visited[i][b-1] = true;
            }



        }


        for (int i=0;i<b;i++) {

            if (!visited[a-1][i]&&graph[a-1][i]!='*') {

                if (graph[a-1][i]=='.'){
                    qu.offer(new location(a-1,i));
                } else if (graph[a-1][i]=='$') {
                    qu.offer(new location(a-1,i));
                    result++;
                } else if (graph[a-1][i]>='a'&&graph[a-1][i]<='z') {
                    qu.offer(new location(a-1,i));
                    hs.add(graph[a-1][i]);
                } else if (graph[a-1][i]>='A'&&graph[a-1][i]<='Z') {
                    door.add(new locationF(a-1,i,graph[a-1][i],false));
                }

                visited[a-1][i] = true;
            }


            if (!visited[0][i]&&graph[0][i]!='*') {

                if (graph[0][i]=='.'){
                    qu.offer(new location(0,i));
                } else if (graph[0][i]=='$') {
                    qu.offer(new location(0,i));
                    result++;
                } else if (graph[0][i]>='a'&&graph[0][i]<='z') {
                    qu.offer(new location(0,i));
                    hs.add(graph[0][i]);
                } else if (graph[0][i]>='A'&&graph[0][i]<='Z') {
                    door.add(new locationF(0,i,graph[0][i],false));
                }

                visited[0][i] = true;
            }



        }
    }

    public static boolean function () {
        boolean ttt = false;
        while (!qu.isEmpty()) {
            location lc = qu.poll();

            for (int i = 0; i < 4; i++) {
                int x = lc.x + dx[i];
                int y = lc.y + dy[i];

                if (x == -1 || x == a || y == -1 || y == b) continue; //그래프 범위 박 구문
                if (graph[x][y] == '*') continue; //벽 구문
                if (visited[x][y]) continue; //재방문 구문
                visited[x][y] = true;
                ttt = true;

                if (graph[x][y] == '.') {
                    qu.offer(new location(x, y));
                    continue;
                }

                if (graph[x][y] >= 'A' && graph[x][y] <= 'Z') {
                    if (hs.contains((char) (graph[x][y] + 32))) {
                        qu.offer(new location(x, y));
                        continue;
                    }
                    door.add(new locationF(x, y, graph[x][y], false));
                }

                if (graph[x][y] >= 'a' && graph[x][y] <= 'z') {
                    hs.add(graph[x][y]);
                    qu.offer(new location(x,y));
                }

                if (graph[x][y] == '$') {
                    result++;
                    qu.offer(new location(x, y));
                }

            }

        }
        return ttt;
    }
}