import java.io.*;
import java.util.StringTokenizer;

class Game {
    public static char[][] board;
}
class Entity extends Game{
    Location loc;
    String name;

    int power, armor;
    int nowHealth, maxHealth;

    Entity (int x, int y) {
        loc = new Location(x,y);
    }
    public boolean isAlive () {
        return nowHealth > 0;
    }

    public void setDamage (int damage) {
        nowHealth -= damage;
        if (nowHealth < 0)
            nowHealth = 0;
    }
}

class Monster extends Entity {
    public static Monster[] monsters;
    int exp;
    boolean isBoss;


    Monster (int x, int y) {
        super(x,y);
        isBoss = board[x][y]=='M';
    }

}
class Player extends Entity {
    int nowExp, goalExp=5, level=1;
    Pocket[] pockets = new Pocket[4];
    Weapon wp;
    Armor ar;
    Location start;
    Player (int x, int y) {
        super(x,y);
        start = new Location(x,y);
        maxHealth = nowHealth = 20;
        power = 2;
        armor = 2;
    }
    public void getExp (int exp) {
        if (existEX())
            exp = (int) (exp * 1.2);

        nowExp += exp;

        if (nowExp>=goalExp) {
            level++;
            maxHealth+= 5;
            nowHealth = maxHealth;
            power += 2;
            armor +=2;

            nowExp = 0;
            goalExp = level*5;
        }
    }
    public void setItem (Item it) {
        if (it instanceof Weapon) {
            wp = (Weapon) it;
        } else if (it instanceof Armor) {
            ar = (Armor) it;
        } else {
            Pocket p = (Pocket) it;
            for (int i=0;i<4;i++) {
                if (pockets[i]!=null) {
                    if (pockets[i].type.equals(p.type)) {
                        return;
                    }
                }
            }

            for (int i=0;i<4;i++) {
                if (pockets[i]==null) {
                    pockets[i] = p;
                    return;
                }
            }
        }
    }
    public void inTrap () {
        if (existDX()) setDamage(1);
        else setDamage(5);
    }

    public int getPower() {
        int result = wp == null ? 0 : wp.power;
        result += this.power;
        return result;
    }

    public boolean existRe () {
        for (int i=0;i<4;i++) {
            if (pockets[i]==null) continue;

            if (pockets[i].type.equals("RE")) {
                pockets[i] = null;
                return true;
            }
        }
        return false;
    }
    public boolean existDX () {
        for (int i=0;i<4;i++) {
            if (pockets[i]==null) continue;

            if (pockets[i].type.equals("DX")) {
                return true;
            }
        }
        return false;
    }
    public boolean existHU () {
        for (int i=0;i<4;i++) {
            if (pockets[i]==null) continue;

            if (pockets[i].type.equals("HU")) {
                return true;
            }
        }
        return false;
    }
    public boolean existCO () {
        for (int i=0;i<4;i++) {
            if (pockets[i]==null) continue;

            if (pockets[i].type.equals("CO")) {
                return true;
            }
        }
        return false;
    }
    public boolean existEX () {
        for (int i=0;i<4;i++) {
            if (pockets[i]==null) continue;

            if (pockets[i].type.equals("EX")) {
                return true;
            }
        }
        return false;
    }
    public boolean existHR () {
        for (int i=0;i<4;i++) {
            if (pockets[i]==null) continue;

            if (pockets[i].type.equals("HR")) {
                return true;
            }
        }
        return false;
    }
    public int getArmor () {
        int result = ar == null ? 0 : ar.armor;
        result += this.armor;
        return result;
    }
    public void heal (int heal) {
        nowHealth += heal;
        if (nowHealth > maxHealth)
            nowHealth = maxHealth;

    }
}

class Item {
    public static Item[] items;
    Location loc;

    Item(int x, int y) {
        loc = new Location(x,y);
    }
}

class Weapon extends Item {
    int power;
    Weapon(int x, int y, int power) {
        super(x, y);
        this.power = power;
    }
}
class Armor extends Item {
    int armor;
    Armor(int x, int y, int armor) {
        super(x, y);
        this.armor = armor;
    }
}
class Pocket extends Item {
    String type;
    Pocket(int x, int y, String type) {
        super(x, y);
        this.type = type;
    }
}

class Location {
    int x, y;

    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    Location () {
        this(0,0);
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int mki(String s) {
        return Integer.parseInt(s);
    }
    
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int height = mki(st.nextToken());
        int width = mki(st.nextToken());

        char[][] board = new char[height][];
        Game.board = board;

        for (int i=0;i<height;i++)
            board[i] = br.readLine().toCharArray();

        char[] input = br.readLine().toCharArray();
        Player player = null;
        //주인공 캐릭터를 만듭니다.

        K: for (int i=0;i<height;i++) {
            for (int j=0;j<width;j++) {
                if (board[i][j]=='@') {
                    board[i][j]='.';
                    player = new Player(i, j);
                    break K;
                }
            }
        }

        int itemCount = 0, monsterCount = 0;

        for (int i=0;i<height;i++) {
            for (int j=0;j<width;j++) {
                if (board[i][j]=='B') {
                    itemCount++;
                } else if (board[i][j]=='&'|| board[i][j]=='M') {
                    monsterCount++;
                }
            }
        }

        Item.items = new Item[itemCount];
        Monster.monsters = new Monster[monsterCount];

        for (int i=0;i<monsterCount;i++) {
            String[] s = br.readLine().split(" ");

            int x = mki(s[0])-1;
            int y = mki(s[1])-1;

            Monster m = new Monster(x,y);
            Monster.monsters[i] = m; m.name = s[2];
            m.power = mki(s[3]); m.armor = mki(s[4]);
            m.nowHealth = m.maxHealth = mki(s[5]);
            m.exp = mki(s[6]);
        }
        for (int i=0;i<itemCount;i++) {
            String[] s = br.readLine().split(" ");
            int x = mki(s[0]) - 1;
            int y = mki(s[1]) - 1;
            char type = s[2].charAt(0);
            if (type=='W') {
                Item.items[i] = new Weapon(x,y, mki(s[3]));
            } else if (type=='A') {
                Item.items[i] = new Armor(x,y, mki(s[3]));
            } else {
                Item.items[i] = new Pocket(x,y, s[3]);
            }
        }

        for (int i = 0;i < input.length;i++) {
            turn++;
            int value = move(input[i]);
            int x = player.loc.x + dx[value];
            int y = player.loc.y + dy[value];

            //무시되는 경우를 먼저 접근한다.
            if (x==-1||x==height||y==-1||y==width||board[x][y]=='#') {
                x = player.loc.x;
                y = player.loc.y;
            }

            player.loc.x = x;
            player.loc.y = y;

            switch (board[x][y]) {
                case '^' :
                    player.inTrap();
                    if (!player.isAlive()) {
                        if (player.existRe()) {
                            useRe(player);
                        } else {
                            end(player);
                            bw.write("YOU HAVE BEEN KILLED BY SPIKE TRAP..");
                            bw.flush();
                            return;
                        }
                    }
                    break;
                case 'B' :
                    int itemIdx = getItem(new Location(x,y));
                    player.setItem(Item.items[itemIdx]);
                    board[x][y] = '.';
                    break;
                case '.' :
                    break;
                default :
                    int idx = getMonster(new Location(x,y));
                    Monster m = Monster.monsters[idx];
                    if (fight(m, player)) {
                        player.getExp(m.exp);
                        board[x][y] = '.';
                        if (player.existHR()) player.heal(3);
                        //보스일 경우 바로 끝내기
                        if (m.isBoss) {
                            end(player);
                            bw.write("YOU WIN!");
                            bw.flush();
                            return;
                        }
                    } else {
                        //졋다.
                        if (player.existRe()) {
                            m.nowHealth = m.maxHealth;
                            useRe(player);
                        } else {
                            end(player);
                            bw.write(String.format("YOU HAVE BEEN KILLED BY %s..",m.name));
                            bw.flush();
                            return;
                        }
                    }
            }
        }
        end(player);
        bw.write("Press any key to continue.");
        bw.flush();

    }
    public static void useRe (Player p) {
        p.loc.x = p.start.x;
        p.loc.y = p.start.y;
        p.nowHealth = p.maxHealth;
    }
    public static int turn;
    public static void end(Player p) throws IOException {
        if (p.isAlive()) {
            for (int i=0;i<Game.board.length;i++) {
                for (int j=0;j<Game.board[0].length;j++) {
                    if (i==p.loc.x&&j==p.loc.y) bw.write('@');
                    else bw.write(Game.board[i][j]);
                }
                bw.write('\n');
            }
        } else {
            for (int i=0;i<Game.board.length;i++) {
                for (int j=0;j<Game.board[0].length;j++) {
                    bw.write(Game.board[i][j]);
                }
                bw.write('\n');
            }
        }
        bw.write("Passed Turns : ");
        bw.write(turn+"\n");

        bw.write("LV : ");
        bw.write(p.level+"\n");
        bw.write(String.format("HP : %d/%d\n", p.nowHealth, p.maxHealth));
        bw.write(String.format("ATT : %d+%d\n", p.power, p.wp==null ? 0 : p.wp.power));
        bw.write(String.format("DEF : %d+%d\n", p.armor, p.ar==null ? 0 : p.ar.armor));
        bw.write(String.format("EXP : %d/%d\n", p.nowExp, p.goalExp));
    }
    public static boolean fight (Monster m, Player p) {
        for (int i=1;;i++) {
            int damage = p.getPower();
            if (i==1) {
                if (p.existCO()) {
                    if (p.existDX()) {
                        damage *= 3;
                    } else {
                        damage *= 2;
                    }
                }
            }

            m.setDamage(Math.max(1, damage - m.armor));
            if (!m.isAlive()) return true;

            p.setDamage(Math.max(1, m.power - p.getArmor()));
            if (i==1 && m.isBoss && p.existHU())
                p.nowHealth = p.maxHealth;

            if (!p.isAlive()) return false;
        }
    }

    public static int getItem (Location lc) {
        for (int i=0;i<Item.items.length;i++) {
            if (locMatch(lc, Item.items[i].loc)) {
                return i;
            }
        }
        return -1;
    }

    public static int getMonster (Location lc) {
        for (int i=0;i<Monster.monsters.length;i++) {
            if (locMatch(Monster.monsters[i].loc, lc)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean locMatch (Location l1, Location l2) {
        return l1.x == l2.x && l1.y == l2.y;
    }
    public static int[] dx = {0,0,-1,1} , dy = {-1,1,0,0};
    public static int move (char move) {
        switch (move) {
            case 'L' :
                return 0;
            case 'R' :
                return 1;
            case 'U' :
                return 2;
            default :
                return 3;
        }
    }

}
