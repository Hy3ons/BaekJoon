import javax.swing.*;
import java.io.*;
import java.util.*;

class Bus {
    int l, r, cost;
    Bus (int l, int r, int cost) {
        this.l = l;
        this.r = r;
        this.cost = cost;
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        ArrayList<Bus> buses = new ArrayList<>(N);
        for (int i=0;i<N;i++) {
            StringTokenizer st=  new StringTokenizer(br.readLine());
            buses.add(new Bus(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        buses.sort(new Comparator<Bus>() {
            @Override
            public int compare(Bus o1, Bus o2) {
                if (o1.l == o2.l) return Integer.compare(o1.r, o2.r);
                return Integer.compare(o1.l, o2.l);
            }
        });

        int l = -1, r = -1, cost = 0;
        long sum = 0;
        ArrayList<Bus> answer = new ArrayList<>();

        for (Bus bus : buses) {
            if (r < bus.l) {
                answer.add(new Bus(l, r, cost));
                cost = bus.cost;
                l = bus.l;
                r = bus.r;
            } else {
                r = Math.max(bus.r, r);
                cost = Math.min(cost, bus.cost);
            }
        }
        answer.add(new Bus(l, r, cost));

        answer.remove(0);
        bw.write(answer.size()+"\n");
        for (Bus bus : answer) bw.write(bus.l+" "+bus.r+" "+bus.cost+"\n");
        bw.flush();

    }
}