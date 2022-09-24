import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<a;i++) {
            setA.add(Integer.parseInt(st.nextToken()));
        }
        st = new StringTokenizer(br.readLine());
        for (int i=0;i<b;i++) {
            setB.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(setA);
        Collections.sort(setB);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        ArrayList<Integer> ans = new ArrayList<>();
        for (int e : setA) {
            if (!find(e)) ans.add(e);
        }

        bw.write(ans.size()+"\n");
        for(int e : ans) bw.write(e+" ");
        bw.flush();
    }
    public static ArrayList<Integer> setA = new ArrayList<>(), setB = new ArrayList<>();
    public static boolean find (int goal) {
        int left = -1, right = setB.size();

        while(true) {
            int mid = left + right >> 1;

            if (goal <= setB.get(mid) ) {
                right = mid;
            } else {
                left = mid;
            }

            if (right - left == 1) {
                if (right == setB.size()) return false;
                return setB.get(right) == goal;
            }
        }
    }
}