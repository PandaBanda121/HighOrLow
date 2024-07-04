import java.awt.*;
import java.io.*;
import java.util.*;

import static java.lang.System.*;

public class Main {
    static int N, quota, current, tries;
    static int rounds;
    static int choice; // 0 = lower, 1 = higher

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
//        N = sc.nextInt();
        N = 20;
        double[] results = new double[100];
        for(int i = 0; i < 100; i++) results[i] = testMultipliers();
        for(int i = 0; i < 100; i++) out.print(results[i] + " ");
        out.println();
        double avg = 0;
        double stdev = 0;
        for(int i = 0; i < 100; i++) avg = avg+results[i];
        avg = avg/100;
        for(int i = 0; i < 100; i++) stdev = stdev+(results[i]-avg)*(results[i]-avg);
        stdev = Math.sqrt(stdev/100);
        out.println(avg + " " + stdev);
        sc.close();
    }

    public static double testMultipliers() {
        double mult = 1;
        int turns = 3;
        for(int i = 0; i < turns; i++) {
            int num = (int) (Math.random()*N);
            choice = (num>=11)?0:1; //0=lower, 1=higher
            int next = (int) (Math.random()*N);
            while(next == num) next = (int) (Math.random()*N);
            //add to multiplier if 1) pick lower and is lower or 2) pick higher and is higher
            //else multiplier = 1
            mult = ((choice == 0 && next < num) || (choice == 1 && next > num))?mult+(1-(double)num/(double)N):1;
        }
        return Math.round(mult*100.0)/100.0;
    }

    public static void initGame() {
        quota = 25;
        current = 10;
        tries = 10;
        choice = 1;
    }

    public static void roundX() {
    }
}


/*
choose lower probability, get higher reward:
ex: given 18, if chose higher, reward=1+18/20
if chose lower, reward=1-18/20

at each round, there are x tries, and you have to reach a certain quota
at the end of each round (after x tries), if you reach quota, you proceed to next level with your current money, multiplier set to 1, and a new quota needs to be reached
when you reach the quota, you also get the choice to deposit and reset multiplier or keep going


If 9: pick higher
01 02 03 04 05 06 07 08
10 11 12 13 14 15 16 17 18 19 20

pick lower: 1-9/20 = 11/20
pick higher: 1-11/20 = 9/20

If correct, 1+x
If wrong, 1

If 10: pick higher
01 02 03 04 05 06 07 08 09
11 12 13 14 15 16 17 18 19 20

If 11: pick lower
01 02 03 04 05 06 07 08 09 10
12 13 14 15 16 17 18 19 20


Higher = easier

Game | Round | Quota



*/