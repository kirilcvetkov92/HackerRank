package com.company;

import java.util.Scanner;
import java.util.*;

import java.io.*;

public class Main {
    private static int[] st; // Array to store segment tree

    static int dp[][];
    public static int[] constructSegmentTree(int[] arr) {
        int height = (int) Math.ceil(Math.log(arr.length) / Math.log(2));
        int size = 2 * (int) Math.pow(2, height) - 1;
        st = new int[size];
        constructST(arr, 0, arr.length - 1, 0);
        return st;
    }


    public static int constructST(int[] arr, int ss,
                                  int se, int si) {
        if (ss == se) {
            st[si] = arr[ss];
            return st[si];
        }
        int mid = ss + (se - ss) / 2;
        st[si] = lcm(constructST(arr, ss, mid, si * 2 + 1),
                constructST(arr, mid + 1, se, si * 2 + 2));
        return st[si];
    }

    static int lcm(int a, int b)
    {
        return a*b/gcd(a,b);
    }

    private static int gcd(int a, int b) {
        if (a < b) {
            int temp = b;
            b = a;
            a = temp;
        }

        if (b == 0)
            return a;
        return gcd(b, a % b);
    }


    static float lcm2(float a, float b)
    {
        return a*b/gcd2(a,b);
    }

    private static float gcd2(float a, float b) {
        if (a < b) {
            float temp = b;
            b = a;
            a = temp;
        }

        if (b <= 0.0001)
            return a;
        return gcd2(b, a % b);
    }

    public static int findRangeGcd(int ss, int se, int[] arr) {
        int n = arr.length;

        if (ss < 0 || se > n - 1 || ss > se)
            throw new IllegalArgumentException("Invalid arguments");

        return findGcd(0, n - 1, ss, se, 0);
    }


    public static int findGcd(int ss, int se, int qs, int qe, int si) {
        if (ss > qe || se < qs)
            return 0;

        if (qs <= ss && qe >= se)
            return st[si];

        int mid = ss + (se - ss) / 2;

        return gcd(findGcd(ss, mid, qs, qe, si * 2 + 1),
                findGcd(mid + 1, se, qs, qe, si * 2 + 2));
    }

    public static int findRangeLcm(int ss, int se, int[] arr) {
        int n = arr.length;

        if (ss < 0 || se > n - 1 || ss > se)
            throw new IllegalArgumentException("Invalid arguments");

        return findLcm(0, n - 1, ss, se, 0);
    }


    public static int findLcm(int ss, int se, int qs, int qe, int si) {
        if (ss > qe || se < qs)
            return 0;

        if (qs <= ss && qe >= se)
            return st[si];

        int mid = ss + (se - ss) / 2;

        return lcm(findLcm(ss, mid, qs, qe, si * 2 + 1),
                findLcm(mid + 1, se, qs, qe, si * 2 + 2));
    }

    // Driver Code
    public static int numberOfElements(int a,int b, int number)
    {
        if(a==0)
            return dp[b][number];
        else return dp[b][number]-dp[a-1][number];
    }
    public static void main(String[] args)throws IOException
    {

        System.out.println(lcm2(1,1.2f));
        Scanner in = new  Scanner(System.in);
        int n = 40000;
        int q = 1;

        int a[] = new int[n];
        for(int i=0; i<n; i++)
        {
            a[i] = i+1;
        }
//USE MAP
//        dp = new int [n][40001];
        //dp[0][a[0]]=1;
//        for(int i=1; i<n; i++)
//        {
//            dp[i][a[i]] = dp[i-1][a[i]]+1;
//        }

        constructSegmentTree(a);

        System.out.println(findRangeGcd(0,40000,a));
        for(int i=0; i<q; i++)
        {
            int l = in.nextInt();
            int r = in.nextInt();
            int modulo = in.nextInt();
            int result = in.nextInt();

            int lcm = findRangeLcm(l,r,a);
            int ans =0;
            for (int k=1; i<=Math.sqrt(lcm)+1; k++)
            {
                if (lcm%k==0)
                {
                    // If divisors are equal, print only one
                    if (lcm/k == k)
                       ans+=numberOfElements(l,r,k);

                    else // Otherwise print both
                    {
                        ans+=numberOfElements(l,r,k);
                        int rr = (lcm/k);

                        if (rr<40001)
                        ans+=numberOfElements(l,r,lcm/k);
                    }
                }
            }
            System.out.println(ans);


        }
    }
}
