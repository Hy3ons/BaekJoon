import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Box {
    int goal, value;
    Box (int goal, int value) {
        this.goal = goal;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        town = Integer.parseInt(st.nextToken());
        int capacity = Integer.parseInt(st.nextToken());

        int queries = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<Box>> al = new ArrayList<>(town+1);
        for (int i=0;i<=town;i++) al.add(new ArrayList<>());

        for (int i=0;i<queries;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()  );
            int goal = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            al.get(from).add(new Box(goal, value));
        }

        for (ArrayList<Box> arr : al) Collections.sort(arr, new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return Integer.compare(o1.goal, o2.goal);
            }
        });

        int nowCap = capacity;
        boxes = new int[town+1];
        int result = 0;

        for (int i=1;i<=town;i++) {
            result += boxes[i];
            nowCap += boxes[i];
            boxes[i] = 0;

            for (Box b : al.get(i)) {
                if (nowCap >= b.value) {
                    boxes[b.goal] += b.value;
                    nowCap -= b.value;
                } else {
                    boxes[b.goal] += nowCap;
                    b.value -= nowCap;
                    nowCap = 0;
                    int en = tuff(b.goal, b.value);
                    boxes[b.goal] += en;
                }
            }
        }
        System.out.println(result);

    }

    public static int[] boxes;
    public static int town;

    public static int tuff (int now, int goal) {
        int result = 0;
        for (int i = town; i>now;i--) {
            if (boxes[i] > goal) {
                boxes[i] -= goal;
                result += goal;
                return result;
            } else {
                result+=boxes[i];
                goal -= boxes[i];
                boxes[i] = 0;
            }

            if (goal == 0) return result;
        }

        return result;
    }
}