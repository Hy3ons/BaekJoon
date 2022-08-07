import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Pair {
    long now, goal;
    long used;

    Pair (int now, int goal) {
        if (now < goal) {
            int dist = goal - now;

            if (dist % 30 == 0) {
                now += 30 * (dist / 30);
                used = dist/30;
            } else {
                now += 30 * (dist / 30 + 1);
                used = dist / 30 + 1;
            }
        }

        this.now = now;
        this.goal = goal;
    }

    public static long using (long goal, long now) {
        long dist = goal - now;

        if (dist % 30 == 0) {
            return dist/30;
        } else {
            return dist / 30 + 1;
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine())
                , st2 = new StringTokenizer(br.readLine());

        Pair[] pairs = new Pair[length];

        for (int i=0;i<length;i++) {
            pairs[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st2.nextToken()));
        }

        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.goal, o2.goal);
            }
        });
        long result = 0;
        long dayMax = 0, tempMax = 0;
        long goalDay = 0;


        for (int i=0;i<pairs.length;i++) {
            if (goalDay != pairs[i].goal) {
                goalDay = pairs[i].goal;
                dayMax = Math.max(dayMax, tempMax);

                if (dayMax <= pairs[i].now) {
                    result += pairs[i].used;
                    tempMax = pairs[i].now;
                } else {
                    long used = Pair.using(dayMax, pairs[i].now);
                    result += used + pairs[i].used;
                    tempMax = pairs[i].now + 30 * used;
                }
            } else {
                if (dayMax <= pairs[i].now) {
                    result += pairs[i].used;
                    tempMax = Math.max(tempMax, pairs[i].now);
                } else {
                    long used = Pair.using(dayMax, pairs[i].now);
                    tempMax = Math.max(tempMax, pairs[i].now + 30 * used);
                    result += pairs[i].used;
                    result += used;
                }
            }
        }
        System.out.println(result);

    }
}