import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Bucket {
    int x, y;
    int ballon;
    int dist, b;
    Bucket(int b, int x, int y) {
        this.x = x;
        this.y = y;
        ballon = b;

        dist = x- y;
        b = y * ballon;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input;

        PriorityQueue<Bucket> pq = new PriorityQueue<>(new Comparator<Bucket>() {
            @Override
            public int compare(Bucket o1, Bucket o2) {
                return Integer.compare(Math.abs(o2.dist), Math.abs(o1.dist));
            }
        });

        while(!(input = br.readLine()).equals("0 0 0")) {
            StringTokenizer st = new StringTokenizer(input);
            int N = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken()), B = Integer.parseInt(st.nextToken());

            long answer = 0;
            for (int i=0;i<N;i++) {
                st = new StringTokenizer(br.readLine());
                int b = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());

                pq.offer(new Bucket(b, x, y));
            }

            while(!pq.isEmpty()) {
                Bucket b = pq.poll();

                if (b.dist > 0) {
                    //x가 더 큰 경우이다.
                    //인수 a에 대해, 최소로 만들어야함.
                    int ballons_b = Math.min(B, b.ballon);
                    int ballons_a = b.ballon - ballons_b;
                    B -= ballons_b;
                    A -= ballons_a;
                    answer += (long) ballons_b * b.y;
                    answer += (long) ballons_a * b.x;
                } else if (b.dist < 0) {
                    int ballons_a = Math.min(A, b.ballon);
                    int ballons_b = b.ballon - ballons_a;
                    B -= ballons_b;
                    A -= ballons_a;
                    answer += (long) ballons_b * b.y;
                    answer += (long) ballons_a * b.x;
                } else {
                    answer += (long) b.x * b.ballon;
                }
            }
            bw.write(answer+"\n");
        }

        bw.flush();
    }
}