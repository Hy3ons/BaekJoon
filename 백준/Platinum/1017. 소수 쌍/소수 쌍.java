import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static int mki(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
    public static ArrayList<ArrayList<Integer>> al;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        arr = new int[size];
        for (int i=0;i<arr.length;i++) {
            arr[i] = mki(st);
            ordered[arr[i]] = i;
        }

        boolean[] eratos = new boolean[2500];
        eratos[0] = eratos[1] = true;
        for (int i=2;i<2500;i++) {
            if (!eratos[i]) {
                for (int j=i*i;j<2500;j+=i) {
                    eratos[j] = true;
                }
            }
        }

        for (int i=0;i<arr.length;i++) takeNum.add(new ArrayList<>());

        takes = new int[1001];
        visited = new int[arr.length];

        for (int i=0;i<arr.length;i++) {
            for (int j=i+1;j<arr.length;j++) {
                if (!eratos[arr[i]+arr[j]]) {
                    takeNum.get(i).add(j);
                    takeNum.get(j).add(i);
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();

        K: for (int k=0;k<takeNum.get(0).size();k++) {

            neverTouch = takeNum.get(0).get(k);

            Arrays.fill(takes, 0);
            Arrays.fill(visited, 0);
            takes[arr[0]] = arr[neverTouch];
            takes[arr[neverTouch]] = arr[0];

            for (nowNum=1;nowNum<arr.length;nowNum++) {
                if (takes[arr[nowNum]]!=0) continue;

                for (int j=0;j< takeNum.get(ordered[arr[nowNum]]).size();j++) {
                    int go = takeNum.get(ordered[arr[nowNum]]).get(j);

                    if (go == 0 || go == neverTouch) continue;

                    if (visited[go] == nowNum) continue;
                    visited[go] = nowNum;

                    if (takes[arr[go]] ==0 || dfs(takes[arr[go]])) {
                        takes[arr[nowNum]] = arr[go];
                        takes[arr[go]] = arr[nowNum];
                        break;
                    }
                }
            }

            for (int j : arr) {
                if (takes[j] == 0) {
                    continue K;
                }
            }
            result.add(arr[neverTouch]);
        }

        if (result.size()==0) {
            System.out.println(-1);
        } else {
            StringBuilder sb = new StringBuilder();
            Collections.sort(result);
            for (int e : result) {
                sb.append(e+" ");
            }
            System.out.println(sb);
        }



    }
    public static int[] ordered = new int[1001], arr, takes, visited;
    public static int nowNum, neverTouch;
    public static ArrayList<ArrayList<Integer>> takeNum = new ArrayList<>();
    public static boolean dfs (int num) {
        for (int i=0;i<takeNum.get(ordered[num]).size();i++) {
            int go = takeNum.get(ordered[num]).get(i);

            if (go == 0 || go == neverTouch) continue;

            if (visited[go] == nowNum) continue;
            visited[go] = nowNum;

            if (takes[arr[go]] ==0 || dfs(takes[arr[go]])) {
                takes[arr[ordered[num]]] = arr[go];
                takes[arr[go]] = arr[ordered[num]];
                return true;
            }
        }
        return false;

    }
}
