import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

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
        int testcase = Integer.parseInt(br.readLine());

        Line[] lines = new Line[testcase];
        for (int i=0;i<lines.length;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            lines[i] = new Line(left,right);
        }

        Arrays.sort(lines, new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return Integer.compare(o1.left, o2.left);
            }
        });

        int nowL = -2100000000;
        int nowR = -2100000000;
        long result = 0;

        for (int i=0;i<lines.length;i++) {
            if (nowR < lines[i].left) {
                result += (long) nowR - nowL;
                nowL = lines[i].left;
                nowR = lines[i].right;
            } else{
                nowR = Math.max(lines[i].right, nowR);
            }
            if (i == lines.length-1) result+= (long) nowR - nowL;
        }
        System.out.println(result);
    }
}
