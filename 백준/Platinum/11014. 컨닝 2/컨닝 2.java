import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static int[] dx = {-1,-1,0,0,1,1}, dy = {-1,1,-1,1,-1,1};
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        while(testcase-->0) {
            //최소 버택스를 구하면, 간단히 생각해서 그 나머지 자리는 최대한 선택못받은 것이고,
            //그걸 반대로 하면 최대한 많이 앉히게 되는거다.
            //이게 정리를 응용하라는 건가 모르겠다. 그냥 해본다.
            StringTokenizer st =  new StringTokenizer(br.readLine());

            int column = Integer.parseInt(st.nextToken());
            int row = Integer.parseInt(st.nextToken());
            int result = 0;
            int maxCount = 0, size = column * row;

            char[][] board = new char[column][];
            for (int i=0;i<column;i++) board[i] = br.readLine().toCharArray();

            hope = new ArrayList<>(size);
            for (int i=0;i<size;i++) hope.add(new ArrayList<>());
            takes = new int[size];
            visited = new int[size];
            ArrayList<Integer> starts = new ArrayList<>();

            for (int i=0;i<column;i++) {
                for (int j=0;j<row;j++) {
                    if (board[i][j] == 'x') continue;
                    maxCount++;
                    if (j % 2 ==0) continue;

                    int now = i * row + j;
                    starts.add(now);

                    for (int k=0;k<6;k++) {
                        int x = i + dx[k];
                        int y = j + dy[k];

                        if (x == -1 || x == column || y== -1 || y==row) continue;
                        if (board[x][y] == 'x') continue;

                        int go = x * row + y;
                        hope.get(now).add(go);
                    }
                }
            }

            for (int start : starts) {
                count++;
                for (int get : hope.get(start)) {
                    if (visited[get] == count) continue;
                    visited[get] = count;

                    if (takes[get] == 0 || dfs(takes[get])) {
                        takes[get] = start;
                        result++;
                        break;
                    }
                }
            }

            bw.write(maxCount - result+"\n");
        }
        bw.flush();
    }
    public static ArrayList<ArrayList<Integer>> hope;
    public static int[] takes, visited;
    public static int count = 2;
    public static boolean dfs (int column) {
        for (int row : hope.get(column)) {
            if (visited[row] == count) continue;
            visited[row] = count;

            if (takes[row] == 0 || dfs (takes[row])) {
                takes[row] = column;
                return true;
            }
        }
        return false;
    }
}