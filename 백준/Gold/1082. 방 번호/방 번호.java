import java.io.*;
import java.util.*;

class Pair {
    int num, price;
    Pair(int num, int price) {
        this.num = num;
        this.price = price;
    }
}

public class Main {
    public static int money, length;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        money = Integer.parseInt(br.readLine());

        ArrayList<Pair> test = new ArrayList<>();
        for (int i=0;i<N;i++) {
            test.add(new Pair(i, Integer.parseInt(st.nextToken())));
        }
        test.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o2.num, o1.num);
            }
        });
        int maxprice = Integer.MAX_VALUE;
        for (int i=0;i<test.size();i++) {
            if (maxprice > test.get(i).price) {
                maxprice = test.get(i).price;
                lists.add(test.get(i));
            }
        }
        length = getLength(lists);

        lists.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o2.num, o1.num);
            }
        });

        answer(new int[length], 0, money);
    }
    public static int minPrice;
    public static ArrayList<Pair> lists = new ArrayList<>();

    public static void answer(int[] mem, int depth, int money) {
        if (depth == length) {
            for (int e : mem) System.out.print(e);
            System.exit(0);
        }

        for(int i=0;i<lists.size();i++){
            if (money >= lists.get(i).price) {
                mem[depth] = lists.get(i).num;
                int remain = money - lists.get(i).price;
                int r = length - depth -1;
                if (remain < r * minPrice) continue;
                answer(mem, depth+1, remain);
            }
        }
    }

    public static int getLength (ArrayList<Pair> al) {
        al.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.price, o2.price);
            }
        });

        int temp = money;
        int max = 0;
        for (int i=0;i<al.size();i++) {
            if (money >= al.get(i).price) max = Math.max(max, al.get(i).num);
        }
        if (max == 0) {
            System.out.println(0);
            System.exit(0);
        }
        int res = 0;
        if (al.get(0).num == 0) {
            temp -= al.get(1).price;
            res++;
        }

        while(temp >= al.get(0).price) {
            temp -= al.get(0).price;
            res++;
        }
        minPrice = al.get(0).price;
        return res;
    }
}