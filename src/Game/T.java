package Game;

/**
 * Created by sniken on 10.10.2015.
 */

import java.util.*;

class T {
    public static void main(String[] a) {
        (new T()).s();
    }

    int[][] b = new int[4][4];
    int d, p, i, j, x, y, v, q, r;
    boolean I, V;

    void s() {
        p();
        do {
            d();
            do {
                char a = (new Scanner(System.in)).nextLine().charAt(0);
                y = a == 'u' ? f(0, 1) : a == 'd' ? f(1, 1) : a == 'l' ? f(0, 0) : a == 'r' ? f(1, 0) : 0;
            } while (y < 1);
            p();
        } while ((x = n()) > 0);
        d();
        c("you " + (x < 0 ? "win" : "lose"));
    }

    int h() {
        for (int[] y : b) for (int x : y) if (x < 2) return 1;
        return 0;
    }

    int n() {
        for (y = 0; y < 4; y++) {
            for (x = 0; x < 4; x++) {
                i = b[y][x];
                if (x < 3 && i == b[y][x + 1] || y < 3 && i == b[y + 1][x]) return 1;
                if (i > 2047) return -1;
            }
        }
        return h();
    }

    int f(int w, int z) {
        I = w > 0;
        V = z > 0;
        for (i = d = 0; i < 4; i++) {
            p = I ? 3 : 0;
            for (j = 1; j < 4; ) {
                v = V ? i : j;
                x = I ? 3 - v : v;
                v = V ? j : i;
                y = I ? 3 - v : v;
                q = V ? x : p;
                r = V ? p : y;
                if (b[y][x] == 0 || p == (V ? y : x)) j++;
                else if (b[r][q] == 0) {
                    d += b[r][q] = b[y][x];
                    b[y][x] = 0;
                    j++;
                } else if (b[r][q] == b[y][x]) {
                    d += b[r][q] *= 2;
                    b[y][x] = 0;
                    p += I ? -1 : 1;
                    j++;
                } else p += I ? -1 : 1;
            }
        }
        return d;
    }

    int v() {
        return (new Random()).nextInt(4);
    }

    void p() {
        if (h() < 1) return;
        do {
            x = v();
            y = v();
        } while (b[x][y] > 0);
        b[x][y] = 2;
    }

    void c(String a) {
        System.out.println(a);
    }

    String l(char n, char m) {
        String s = "" + n;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) s += m;
            s += n;
        }
        return s;
    }

    void d() {
        c(l('+', '-'));
        String p[] = new String[5];
        for (int[] y : b) {
            p[0] = p[1] = p[3] = l('|', ' ');
            p[2] = "";
            for (x = 0; x < 4; ) p[2] += String.format("|%4d", y[x++]);
            p[2] += "|";
            p[4] = l('+', '-');
            for (String q : p) c(q);
        }
    }
}