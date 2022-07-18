import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int left_Size = Integer.parseInt(st.nextToken());
        int right_Size = Integer.parseInt(st.nextToken());

        hope = new ArrayList<>(left_Size + 1);
        for (int i=0;i<=left_Size;i++) hope.add(new ArrayList<>());
        result_Left = new boolean[left_Size+1];
        result_Right = new boolean[right_Size + 1];
        boolean[] start = new boolean[left_Size + 1];
        visited = new int[right_Size + 1];
        takes = new int[right_Size + 1];

        for (int i=1;i<=left_Size;i++) {
            st = new StringTokenizer(br.readLine());

            int time = Integer.parseInt(st.nextToken());
            while (time-->0) {
                int matches = Integer.parseInt(st.nextToken());

                hope.get(i).add(matches);
            }
        }
        int result = 0;
        for (int i=1;i<=left_Size;i++) {
            count++;
            for (int right : hope.get(i)) {
                if (visited[right] == count) continue;
                visited[right] = count;

                if (takes[right] == 0 || dfs (takes[right])) {
                    takes[right] = i;
                    result++;
                    break;
                }
            }
        }

        for (int i=1;i<=right_Size;i++) {
            start[takes[i]] = true;
        }

        for (int i=1;i<=left_Size;i++) {
            if (!start[i] && !result_Left[i]) {
                count++;
                result_Left[i] = true;
                visit(i);
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        bw.write(result+"\n");
        int sel = 0;
        for (int i=1;i<=left_Size;i++) {
            if (!result_Left[i]) {
                sel++;
                sb.append(i).append(" ");
            }
        }
        bw.write(sel+" "+sb+"\n");
        sel = 0;
        sb = new StringBuilder();
        for (int i=1;i<=right_Size;i++) {
            if (result_Right[i]) {
                sel++;
                sb.append(i).append(" ");
            }
        }
        bw.write(sel+" "+sb+"\n");
        bw.flush();

    }
    public static ArrayList<ArrayList<Integer>> hope;
    public static int[] takes, visited;
    public static int count = 2;
    public static boolean[] result_Left, result_Right;
    public static boolean dfs (int hang) {
        for (int row : hope.get(hang)) {
            if (visited[row] == count) continue;
            visited[row] = count;

            if (takes[row] == 0 || dfs (takes[row])) {
                takes[row] = hang;
                return true;
            }
        }
        return false;
    }
    public static boolean visit (int left) {
        for (int right : hope.get(left)) {
            if (result_Right[right]) continue;
            result_Right[right] = true;

            if (takes[right] != 0 && !result_Left[takes[right]]) {
                result_Left[takes[right]] = true;
                visit(takes[right]);
            }
        }
        return false;
    }
}