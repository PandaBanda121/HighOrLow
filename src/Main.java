import java.awt.*;
import java.io.*;
import java.util.*;

import static java.lang.System.*;

public class Main {
    static int N = 20;

    static int rounds, attempts, quota;
    static double current, mult;
    static double deposit = 0;
    static int choice; // 0 = lower, 1 = higher

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        double[] results = new double[100];
        for(int i = 0; i < 100; i++) results[i] = testMultipliers(5, 15, 0, 10, 1);

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

    public static double testMultipliers(int testAttempts, int testQuota, double testDeposit, double testCurrent, double testMult) {
        mult = testMult;
        attempts = testAttempts;
        quota = testQuota;
        deposit = testDeposit;
        current = testCurrent;
        for(int i = 0; i < attempts; i++) {
            int num = (int) (Math.random()*N)+1;
            while(num == N) num = (int) (Math.random()*N)+1;
            choice = (num>N/2)?0:1; //0=pick lower, 1=pick higher
            int next = (int) (Math.random()*N)+1;
            while(next == num) next = (int) (Math.random()*N);
            //add to multiplier if 1) pick lower and is lower or 2) pick higher and is higher
            //else multiplier = 1
            addMultiplier(num, next);
            mult = Math.round(mult*100.0)/100.0;
            if(mult*current >= quota) {
                current = mult*current;
                deposit = deposit+current-1;
                current = 1;
                mult = 1;
            }
        }
        return Math.round(deposit*100.0)/100.0;
    }

    public static void initGame() {
        quota = 25;
        current = 10;
        attempts = 10;
        choice = 1;
    }

    public static void roundX() {
    }

    public static void addMultiplier(int num, int next) {
        if((choice == 0 && next < num) || (choice == 1 && next > num)) {
            if(choice == 0) {
                mult = mult + 1-(double)num/(double)N;
            } else {
                mult = mult+(double)num/(double)N;
            }
        } else {
            mult = 1;
        }
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


If 10: pick higher
01 02 03 04 05 06 07 08 09
11 12 13 14 15 16 17 18 19 20 21 22

If 11: pick higher
01 02 03 04 05 06 07 08 09 10
12 13 14 15 16 17 18 19 20 21 22

Fair



Higher = easier

Game | Round | Quota



*/