import java.io.*;
import java.util.*;

class Pair {
    int color, score;
    int idx;
    Pair(int c, int s, int idx) {
        color = c;
        score = s;
        this.idx = idx;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        ArrayList<Pair> al = new ArrayList<>(N);
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            al.add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
        }

        al.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.score, o2.score);
            }
        });

        int[] myteam = new int[N+1];
        long[] answer = new long[N+1];
        long sum = 0, temp = 0;
        int score = -1;
        Queue<Pair> qu = new LinkedList<>(), qu2 = new LinkedList<>();
        for (int i=0;i<al.size();i++) {
            Pair now = al.get(i);
            if (score < now.score) {
                score = now.score;
                while(!qu.isEmpty()) {
                    Pair k = qu.poll();
                    answer[k.idx] = sum - myteam[k.color];
                    qu2.offer(k);
                }

                while(!qu2.isEmpty()) {
                    Pair k = qu2.poll();
                    sum += k.score;
                    myteam[k.color] += k.score;
                }
            }
            qu.offer(now);
        }
        while(!qu.isEmpty()) {
            Pair k = qu.poll();
            answer[k.idx] = sum - myteam[k.color];
            qu2.offer(k);
        }

        for (int i=0;i<N;i++) {
            bw.write(answer[i]+"\n");
        }
        bw.flush();

    }
}