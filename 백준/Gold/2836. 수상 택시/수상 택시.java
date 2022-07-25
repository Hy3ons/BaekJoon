import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Line {
    int left, right;
    Line (int left, int right) {
        this.left = left;
        this.right = right;
    }
}

public class Main {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int testcase = Integer.parseInt(st.nextToken());
        int goal = Integer.parseInt(st.nextToken());

        HashMap<Integer, Integer> hm = new HashMap<>();
        HashSet<Integer> hs = new HashSet<>();
        for (int i=0;i<testcase;i++) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            if (left > right) {
                if (!hm.containsKey(left)) {
                    hm.put(left, 1);
                } else {
                    hm.put(left, hm.get(left)+1);
                }
                if (!hm.containsKey(right)) {
                    hm.put(right, -1);
                } else {
                    hm.put(right, hm.get(right)-1);
                }
                hs.add(left);
                hs.add(right);
            }
        }

        ArrayList<Integer> numbers = new ArrayList<>(hs);
        Collections.sort(numbers);

        // 앞의 손님들이 현재 내위치 뒤로 이동하지 않아도 된다는 확신이 있을때
        // 내 현재 손님들을 모두 역주행하면서 도착시켜주면 된다.

        long result = goal;
        int min = Integer.MAX_VALUE;
        int weight = 0;
        for (int num : numbers) {
            int temp = hm.get(num);
            weight+= temp;
            if (weight == 0) {
                result += ((long)num - min) * 2;
                min = Integer.MAX_VALUE;
            } else if (temp <0) {
                min = Math.min(min, num);
            }
        }
        System.out.println(result);

    }
}
