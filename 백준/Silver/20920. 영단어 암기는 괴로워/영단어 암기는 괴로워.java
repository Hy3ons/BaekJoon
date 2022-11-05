import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

class Pair {
    String word;
    int amount;

    Pair (String word, int amount) {
        this.word = word;
        this.amount = amount;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()), min = Integer.parseInt(st.nextToken());

        HashMap<String, Integer> hm = new HashMap<>();
        String word;
        for (int i=0;i<N;i++) {
            if ((word = br.readLine()).length() < min) continue;
            hm.put(word, hm.getOrDefault(word, 0)+1);
        }

        ArrayList<Pair> list = new ArrayList<>();
        for (String w : hm.keySet()) list.add(new Pair(w, hm.get(w)));

        list.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.amount == o2.amount) {
                    if (o1.word.length() == o2.word.length()) {
                        return o1.word.compareTo(o2.word);
                    } else return Integer.compare(o2.word.length(), o1.word.length());
                } else return Integer.compare(o2.amount, o1.amount);
            }
        });

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (Pair p : list) bw.write(p.word+"\n");
        bw.flush();
    }
}