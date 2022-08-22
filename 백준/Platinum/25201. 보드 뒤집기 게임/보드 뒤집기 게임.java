import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Location {
    int x, y;
    Location (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int lightAmount = Integer.parseInt(st.nextToken());
        int TurnAmount = Integer.parseInt(st.nextToken());


        HashSet<Location> hs = new HashSet<>();
        for (int i=0;i<lightAmount;i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            hs.add(new Location(x,y));
        }
        for (int i=0;i<TurnAmount;i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            Location temp = new Location(x,y);
            if (hs.contains(temp)) {
                hs.remove(temp);
            } else {
                hs.add(temp);
            }
        }

        ArrayList<Location> al = new ArrayList<>(hs);
        al.sort(new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                if (o1.x == o2.x) {
                    return o1.y - o2.y;
                } else return o1.x - o2.x;
            }
        });
        int mins = Integer.MAX_VALUE;
        int[] count = new int[100001];
        int[] count2 = new int[100001];
        for (Location lc : al) {
            ++count2[lc.y];
            ++count[lc.x];
        }

        for (int i=0;i<count.length;i++) {
            if (count[i]!=0) {
                mins = Math.min(mins, count[i]);
                if (count[i]%2==1) {
                    mins=1;
                    break;
                }
            }

            if (count2[i]!=0) {
                mins = Math.min(mins, count2[i]);
                if (count2[i]%2==1) {
                    mins=1;
                    break;
                }
            }
        }

        if (mins==1) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }

    }
}
