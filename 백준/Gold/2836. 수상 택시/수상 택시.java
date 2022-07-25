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

        ArrayList<Line> lines = new ArrayList<>();

        for (int i=0;i<testcase;i++) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            if (left > right) {
                lines.add(new Line(right, left));
            }
        }
        long result = goal;
        lines.sort(new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return Integer.compare(o1.left, o2.left);
            }
        });

        // 앞의 손님들이 현재 내위치 뒤로 이동하지 않아도 된다는 확신이 있을때
        // 내 현재 손님들을 모두 역주행하면서 도착시켜주면 된다.
        int nowLeft = 0;
        int nowRight = 0;

        for (int i=0;i<lines.size();i++) {
            if (nowRight >= lines.get(i).left) {
                nowRight = Math.max(lines.get(i).right, nowRight);
            } else {
                result += ( (long) nowRight - nowLeft) * 2;
                nowLeft = lines.get(i).left;
                nowRight = lines.get(i).right;
            }
            if (i == lines.size()-1)
                result += ( (long) nowRight - nowLeft) * 2;
        }

        System.out.println(result);

    }
}
