import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static int[] dx = {-1,-1,1,1}, dy = {-1,1,-1,1};
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int size = Integer.parseInt(br.readLine())
                ,block = Integer.parseInt(br.readLine());

        boolean[] board = new boolean[size*size];
        hope = new ArrayList<>(size*size);
        for (int i=0;i<size*size*8;i++) hope.add(new ArrayList<>());

        while(block-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;

            board[x * size + y] = true;
        }

        int line = 0;
        int[][] numbering= new int[size][size];
        for (int i=0;i<size;i++) {
            line++;
            for (int j=0;j<=i;j++) {
                int x = i - j;
                int y = j;

                if (board[x * size + y]) {
                    line++;
                    continue;
                }

                numbering[x][j] = line;
            }
        }
        for (int i = 0;i<size-1;i++) {
            line++;
            for (int j=0;j<=i;j++) {
                int x = size-1-j;
                int y = size-1-i+j;

                if (board[x * size + y]) {
                    line++;
                    continue;
                }

                numbering[x][y] = line;
            }
        }

        for (int i=0;i<size;i++) {
            line++;
            for (int j=0;j<=i;j++) {
                int x = i - j;
                int y = size -1- j;
                if (board[x * size + y]) {
                    line++;
                    continue;
                }

                hope.get(line).add(numbering[x][y]);
            }
        }
        for (int i = 0;i<size-1;i++) {
            line++;
            for (int j=0;j<=i;j++) {
                int x = size-1-i+j;
                int y = j;

                if (board[x * size + y]) {
                    line++;
                    continue;
                }

                hope.get(line).add(numbering[x][y]);
            }
        }

        takes = new int[line+1];
        visited = new int[line+1];
        int result = 0;

        for(int i=0;i<=line;i++) {
            count++;
            if (dfs(i)) result++;
        }

        System.out.println(result);
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