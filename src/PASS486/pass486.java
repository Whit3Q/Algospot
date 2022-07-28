package PASS486;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class pass486 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int C;
    static int N;
    static int lo;
    static int hi;

    static int TM = 10000000;

    static int[] minFactor;

    static byte[] minFactorPower;

    static int[] factors;

    static void eratosthenes2() {
        minFactor[0] = minFactor[1] = 1;
        for (int i = 2; i <= 10000000; i++) {
            minFactor[i] = i;
        }

        int sqrtn = (int) Math.sqrt(10000000);
        for (int i = 2; i <= sqrtn; i++) {
            if (minFactor[i] == i) {
                for (int j = i * i; j <= 10000000; j += i) {
                    if (minFactor[j] == j) {
                        minFactor[j] = i;
                    }
                }
            }
        }
    }

    static void getFactorsSmart() {
        factors[1] = 1;
        for (int n = 2; n <= TM; n++) {

            if (minFactor[n] == n) {
                minFactorPower[n] = 1;
                factors[n] = 2;
            } else {
                int p = minFactor[n];
                if (p < 1) {
                    System.out.println(p);
                }
                int m = n / p;

                if (p != minFactor[m]) {
                    minFactorPower[n] = 1;
                } else {
                    minFactorPower[n] = (byte) (minFactorPower[m] + 1);
                }
                int a = minFactorPower[n];
                factors[n] = (factors[m] / a) * (a + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        minFactor = new int[10000001];
        factors = new int[10000001];
        minFactorPower = new byte[10000001];

        eratosthenes2();
        getFactorsSmart();
        Scanner sc = new Scanner(System.in);
        C = sc.nextInt();
        while (C-- > 0) {
            N = sc.nextInt();
            lo = sc.nextInt();
            hi = sc.nextInt();
            int result = 0;
            for (int i = lo; i <= hi; i++) {
                if (N == factors[i]) {
                    result++;
                }
            }
            System.out.println(result);
        }
    }

}