import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static int L, K, C;
    public static ArrayList<Integer> ss;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());


        HashSet<Integer>hs = new HashSet<>();
        hs.add(L);
        hs.add(0);
        for (int i=0;i<K;i++) hs.add(Integer.parseInt(st.nextToken()));
        ss = new ArrayList<>(hs);
        Collections.sort(ss, Collections.reverseOrder());

        int left = 0;
        for (int i=1;i<ss.size();i++)
            left = Math.max(left, ss.get(i-1) - ss.get(i));

        int right = L+1;
        left--;

        while(true) {
            int limit = left + right >> 1;

            if (check(limit)) {
                right = limit;
            } else {
                left = limit;
            }
            if (right - left == 1) break;
        }

        System.out.print(right+" ");
        if (right == L) {
            System.out.print(ss.get(0));
            return;
        }

        int last = 0;
        int answer = 0, count = 0;
        for (int i=1;i<ss.size();i++) {
            int dist = ss.get(last) - ss.get(i);
            if (dist > right) {
                last = i-1;
                answer = ss.get(i-1);
                count++;
            }
        }

        if (count < C) {
            System.out.print(ss.get(ss.size()-2));
        } else
        System.out.print(answer);
    }

    public static boolean check(int limit) {
        int last = 0, count = 0;
        for (int i=1;i<ss.size();i++) {
            int dist = ss.get(last) - ss.get(i);
            if (dist > limit) {
                last = i-1;
                if (++count > C) return false;
            }
        }
        return true;
    }
}
