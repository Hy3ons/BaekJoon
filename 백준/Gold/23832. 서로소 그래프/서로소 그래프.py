import math
import sys
import random as ran

check = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,51, 61]
outputs = []


def gcd(a, b):
    if b == 0:
        return a
    return gcd(b, a % b)


def function(x, c, n):
    return ((x ** 2) % n + c) % n


def mill(P, a, k, d):
    temp = pow(int(a), int(d), int(P))
    if (temp - 1) % P == 0:
        return True

    for i in range(k):
        if (temp + 1) % P == 0:
            return True

        temp = (temp * temp) % P
    return False


def isPrime(P):
    if P == 1:
        return True
    if P == 2:
        return True

    if P % 2 == 0:
        return False

    for a in check:
        if P == a:
            return True

    k = 0
    d = P - 1
    while d % 2 == 0:
        k += 1
        d //= 2

    for a in check:
        if not (mill(P, a, k, d)):
            return False
    return True


def rho(n):
    while True:
        x = y = ran.randint(0, n-1)
        c = ran.randint(0, n-1)

        if ran.randint(1, 20) % 2 == 0:
            c *= -1

        g = 1
        count = 0
        while g == 1:
            x = function(x, c, n)
            y = function(function(y, c, n), c, n)

            g = gcd(abs(x - y), n)
            count += 1
            if (count == 1000000) :
                g = n
                break
        if g != n:
            return g


def PollarRho(n):
    if isPrime(n):
        outputs.append(n)
    else:
        temp = rho(n)
        PollarRho(n // temp)
        PollarRho(temp)


def main(number):
    if number == 1:
        print(0)
        return
    else:
        for c in check :
            while number % c == 0 :
                number //= c
                outputs.append(c)

        if number != 1:
            PollarRho(number)

        outputs.sort()

    d = {}
    for e in outputs:
        if e in d.keys():
            d[e] += 1
        else:
            d[e] = 1

    result = 1

    for e in d.keys():
        result *= (e ** d[e]) - (e ** (d[e] - 1))
    return result

nu = int(input())
if nu == 1:
    print(0)
elif nu == 2:
    print(1)
else:
    ans = 0
    for i in range(2, nu+1):
        outputs.clear()
        ans += main(i)
    print(ans)