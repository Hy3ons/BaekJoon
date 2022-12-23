import java.io.*;
import java.util.*;

class Hero{
    int x, y, hp, damage, defence, level, exp, weapon, shield;

    HashSet<String> acc = new HashSet<>();

    public void setDamage (int d) {
        hp = Math.max(hp-d, 0);
    }
}

class Monster{
    String name;
    int attack;
    int defence;
    int maxHp;
    int currentHp;
    int exp;

    Monster(String n, int a, int d, int m, int e) {
        name = n;
        attack = a;
        defence = d;
        currentHp = maxHp = m;
        exp = e;
    }

    public void setDamage (int d) {
        currentHp = Math.max(currentHp-d, 0);
    }

}

class Item {
    String type;
    String what;

    Item(String t, String w) {
        type = t;
        what = w;
    }
}

public class Main {
    static int N, M, turn;
    static int startX, startY, maxHP, maxDamege, maxDefence;
    static String map[][];
    static Monster mon[][];
    static Item item[][];

    static int dx[] = {0, 0, -1, 1};
    static int dy[] = {-1, 1, 0, 0};
    static Hero hero;
    public static boolean checkingAcc(String acc) {
        return hero.acc.contains(acc);
    }

    public static void levelUp(int exp) {
        hero.exp += exp;

        if(hero.exp >= (5 * hero.level)) {
            hero.level++;
            hero.exp = 0;

            maxHP += 5;
            hero.hp = maxHP;
            hero.damage += 2;
            hero.defence += 2;
        }
    }

    public static void heroWin() {
        int exp = mon[hero.x][hero.y].exp;
        if(checkingAcc("EX")) exp = (int) (exp * 1.2);
        levelUp(exp);

        if(checkingAcc("HR")) {
            hero.hp = Math.min(maxHP, hero.hp + 3);
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = 0;
        int L = 0;

        map = new String[N][M];
        for(int i = 0; i < N; i++) {
            String s = br.readLine();
            for(int j = 0; j < M; j++) {
                map[i][j] = ""+s.charAt(j);
                if(map[i][j].equals("&") || map[i][j].equals("M")) {
                    K++;
                }
                if(map[i][j].equals("B")) {
                    L++;
                }
                if(map[i][j].equals("@")) {
                    hero = new Hero();
                    hero.x = i;
                    hero.y = j;
                    startX = i;
                    startY = j;
                    map[i][j] = ".";
                }
            }
        }

        String movement = br.readLine();

        mon = new Monster[N][M];
        while (K --> 0) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            String name = st.nextToken();
            int a = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int maxhp = Integer.parseInt(st.nextToken());
            int ex = Integer.parseInt(st.nextToken());

            mon[r][c] = new Monster(name, a, d, maxhp, ex);
        }

        item = new Item[N][M];
        while (L --> 0) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            String t = st.nextToken();
            String s = st.nextToken();

            item[r][c] = new Item(t, s);
        }
        //input end

        maxHP = 20;
        maxDamege = 2;
        maxDefence = 2;
        hero.exp = 0;
        hero.hp = 20;
        hero.damage = 2;
        hero.defence = 2;
        hero.level = 1;
        hero.weapon = 0;
        hero.shield = 0;

        turn = 0;

        boolean hpZeroTrap = false;
        boolean monsterZero = false;
        boolean heroFinalWin = false;

        G: for(int i = 0; i < movement.length(); i++) {
            char current = movement.charAt(i);
            turn++;

            int currentX = hero.x;
            int currentY = hero.y;

            if(current == 'L') {
                hero.x += dx[0];
                hero.y += dy[0];
            }else if(current == 'R'){
                hero.x += dx[1];
                hero.y += dy[1];
            }else if(current == 'U') {
                hero.x += dx[2];
                hero.y += dy[2];
            }else {
                hero.x += dx[3];
                hero.y += dy[3];
            }

            if(hero.x < 0 || hero.x >= N || hero.y < 0 || hero.y >= M || map[hero.x][hero.y].equals("#")) {
                hero.x = currentX;
                hero.y = currentY;
            }

            if(map[hero.x][hero.y].equals("B")) {
                Item item1 = new Item(item[hero.x][hero.y].type, item[hero.x][hero.y].what);

                if(item1.type.equals("W") || item1.type.equals("A")) {
                    int s = Integer.parseInt(item1.what);

                    if(item1.type.equals("W")) {
                        hero.weapon = s;
                    }else{
                        hero.shield = s;
                    }
                }else{
                    String tempAcc = item1.what;
                    if (hero.acc.size() < 4) hero.acc.add(tempAcc);
                }

                map[hero.x][hero.y] = ".";
            } else if(map[hero.x][hero.y].equals("&") || map[hero.x][hero.y].equals("M")) {

                Monster enemy = mon[hero.x][hero.y];
                boolean isBoss = map[hero.x][hero.y].equals("M");

                for (int j=1;true;j++) {
                    int damage = hero.damage + hero.weapon;
                    if (j == 1 && hero.acc.contains("CO")) {
                        if (hero.acc.contains("DX")) {
                            damage *= 3;
                        } else {
                            damage *= 2;
                        }
                    }

                    enemy.setDamage(Math.max(damage - enemy.defence, 1));

                    // 적이 뒤짐
                    if (enemy.currentHp == 0) {
                        heroWin();
                        map[hero.x][hero.y] = ".";

                        if (isBoss) {
                            heroFinalWin = true;
                            break G;
                        } else {
                            break;
                        }
                    }

                    hero.setDamage(Math.max(enemy.attack - hero.defence - hero.shield, 1));

                    if (j == 1 && hero.acc.contains("HU") && isBoss) {
                        hero.hp = maxHP;
                    }

                    //주인공 뒤짐
                    if (hero.hp == 0) {
                        if (hero.acc.contains("RE")) {
                            hero.acc.remove("RE");
                            hero.hp = maxHP;
                            hero.x = startX;
                            hero.y = startY;

                            enemy.currentHp = enemy.maxHp;
                            break;
                        } else {
                            monsterZero = true;
                            break G;
                        }
                    }
                }

            } else if (map[hero.x][hero.y].equals("^")) {
                hero.setDamage(hero.acc.contains("DX") ? 1 : 5);

                if (hero.hp == 0) {
                    if (hero.acc.contains("RE")) {
                        hero.acc.remove("RE");
                        hero.hp = maxHP;
                        hero.x = startX;
                        hero.y = startY;
                    } else {
                        hpZeroTrap = true;
                        break;
                    }
                }
            }

        }

        if(hero.hp != 0){
            map[hero.x][hero.y] = "@";
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                sb.append(map[i][j]);
            }
            sb.append('\n');
        }

        sb.append("Passed Turns : ").append(turn).append('\n');
        sb.append("LV : ").append(hero.level).append('\n');
        sb.append("HP : ").append(hero.hp).append("/").append(maxHP).append('\n');
        sb.append("ATT : ").append(hero.damage).append("+").append(hero.weapon).append('\n');
        sb.append("DEF : ").append(hero.defence).append("+").append(hero.shield).append('\n');
        sb.append("EXP : ").append(hero.exp).append("/").append(hero.level * 5).append('\n');

        if(hpZeroTrap) {
            sb.append("YOU HAVE BEEN KILLED BY SPIKE TRAP..").append('\n');
        } else if(monsterZero) {
            sb.append("YOU HAVE BEEN KILLED BY ").append(mon[hero.x][hero.y].name).append("..").append('\n');
        } else if(heroFinalWin) {
            sb.append("YOU WIN!").append('\n');
        } else {
            sb.append("Press any key to continue.").append('\n');
        }

        System.out.println(sb);
    }
}
