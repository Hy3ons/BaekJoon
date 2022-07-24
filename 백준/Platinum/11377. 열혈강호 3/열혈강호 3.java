import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static int INF = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int people = Integer.parseInt(st.nextToken());
        int works = Integer.parseInt(st.nextToken());
        int chance = Integer.parseInt(st.nextToken());

        int size = people;
        boolean[] exist = new boolean[works+1];

        load = new ArrayList<>();
        for (int i=0;i<=people;i++) load.add(new ArrayList<>());

        for (int i=1;i<=people;i++) {
            st = new StringTokenizer(br.readLine());
            int times = Integer.parseInt(st.nextToken());

            while (times-->0) {
                int work = Integer.parseInt(st.nextToken());
                exist[work] = true;

                load.get(i).add(work);
            }
        }

        takes = new int[works+1];
        visited = new int[works+1];
        int result = 0;

        for (int i=1;i<=people;i++) {
            count++;
            if (dfs(i, load)) result++;
        }
        for (int i=1;i<=people;i++) {
            count++;
            if (dfs(i, load)) {
                if (chance-->0) {
                    result++;
                } else {
                    break;
                }
            }
        }

        System.out.println(result);
    }
    public static int[] takes, visited;
    public static int count = 2;
    public static ArrayList<ArrayList<Integer>> load;

    public static boolean dfs (int person, ArrayList<ArrayList<Integer>> load) {
        for (int work : load.get(person)) {
            if (takes[work] == person) continue;

            if (visited[work]==count) continue;
            visited[work] = count;

            if (takes[work] == 0 || dfs(takes[work], load)) {
                takes[work] = person;
                return true;
            }
        }
        return false;
    }
}