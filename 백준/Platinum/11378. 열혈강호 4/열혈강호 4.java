import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int people = Integer.parseInt(st.nextToken());
        int work_S = Integer.parseInt(st.nextToken());
        int badPoint = Integer.parseInt(st.nextToken());

        load = new ArrayList<>();
        for (int i=0;i<=people;i++) load.add(new ArrayList<>());

        boolean[] exist = new boolean[work_S+1];

        for (int i=1;i<=people;i++) {
            st = new StringTokenizer(br.readLine());
            int temp = Integer.parseInt(st.nextToken());

            while (temp-->0) {
                int work = Integer.parseInt(st.nextToken());
                load.get(i).add(work);
                exist[work] = true;
            }
        }

        takes = new int[work_S+1];
        visited = new int[work_S+1];
        int count , result = 0;
        K: for (person = 1; person <=people;person++) {
            stack++;
            for (int work : load.get(person)) {
                if (takes[work] == 0) {
                    takes[work] = person;
                    result++;
                    continue K;
                }
            }

            for (int work : load.get(person)) {
                if (visited[work] == stack) continue;
                visited[work] = stack;

                if (dfs(takes[work])) {
                    takes[work] = person;
                    result++;
                    break;
                }
            }
        }
        count = 0;

        for (int i=1;i<=work_S;i++) {
            if (exist[i] && takes[i] == 0) count++;
        }
        count = Math.min(badPoint, count);
        System.out.println(result + count);
    }
    public static int person, stack;
    public static int[] takes, visited;
    public static ArrayList<ArrayList<Integer>> load;
    public static boolean dfs (int person) {
        for (int work : load.get(person)) {
            if (visited[work] == stack) continue;
            visited[work] = stack;

            if (takes[work] == 0 || dfs(takes[work])) {
                takes[work] = person;
                return true;
            }
        }
        return false;
    }
}