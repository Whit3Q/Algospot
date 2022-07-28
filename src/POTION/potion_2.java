package POTION;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class potion_2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int T;
    static int N;
    static ArrayList<Integer> list;

    static int TM = 10000000;

    static int[] minFactor;

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

    static int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }

    static ArrayList<Integer> factor(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(n);
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                list.add(i);
                list.add(n / i);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {


//        minFactor = new int[10000001];
//        factors = new int[10000001];
//        minFactorPower = new byte[10000001];

//        eratosthenes2();
//        getFactorsSmart();
//        getFactorsBrute();

        T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());
            list = new ArrayList<>();//병에 필요한 약의 비율
            st = new StringTokenizer(br.readLine()); // 1병에 필요한 약의 비율
            int min = Integer.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < N; i++) {
                int tmp = Integer.parseInt(st.nextToken());
                list.add(tmp);
                if (min > tmp) {
                    index = i;
                    min = tmp;
                }
            }

            ArrayList<Integer> list2; //최대 공약수의 약수
            if (N >= 2) {
                if (index == 0) {
                    list2 = factor(gcd(list.get(1), list.get(index)));
                } else {
                    list2 = factor(gcd(list.get(0), list.get(index)));
                }
            } else {
                list2 = factor(list.get(0));
            }

            Collections.sort(list2);
            int gcdNum = 1;
            for (int i = list2.size() - 1; i >= 0; i--) {
                boolean check = true;
                for (int j = 0; j < N; j++) {
                    if (list.get(j) % list2.get(i) != 0) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    gcdNum = list2.get(i);
                    break;
                }
            }

            ArrayList<Integer> list3 = new ArrayList<>(list); //약의 비율
            for (int i = 0; i < N; i++) {
                list3.set(i, list3.get(i) / gcdNum);
            }

            st = new StringTokenizer(br.readLine());//현재 들어간 약의 양
            ArrayList<Integer> list4 = new ArrayList<>();
            int maxIndex = 0;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < N; i++) {
                int tmp = Integer.parseInt(st.nextToken());
                list4.add(tmp);
                if (max < tmp) {
                    max = tmp;
                    maxIndex = i;
                }
            }

            boolean check = true;
            int mul = 1;
            for (int i = 0; i < N; i++) {
                if (list.get(i) < list4.get(i)) {
                    int tmp = 1;
                    while (list3.get(i) * tmp < list4.get(i)) {
                        tmp++;
                    }
                    mul = Math.max(mul, tmp);
                }
            }

            for (int i = 0; i < N; i++) {
                bw.write(list3.get(i) * mul - list4.get(i) + " ");
            }
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}