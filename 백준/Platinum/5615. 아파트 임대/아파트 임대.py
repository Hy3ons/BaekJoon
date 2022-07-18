import math
import sys

check = [2,3,5,7,11,13,17]

def powing (a, times, MOD) :
    if times == 0 :
        return 0

    times -= 1
    result = a
    while times > 0 :
        if times % 2 == 1 :
            result *= a
            result %= MOD

        a *= a
        a %= MOD

        times //= 2
    return result

def mill (P, a, k, d) :
    temp = powing(a, d, P)
    if (temp - 1) % P == 0 :
        return True

    for i in range(k) :
        if (temp + 1) % P == 0:
            return True

        temp *= temp
        temp %= P


def isPrime (P) :
    if P % 2 == 0 :
        return False

    for a in check :
        if P == a :
            return True

    k = 0
    d = P-1
    while d % 2 == 0 :
        k += 1
        d /= 2

    for a in check :
        if not(mill(P, a, k, d)) :
            return False
    return True

def nicePrime (P) :
    if P == 2 :
        return True

    if P % 2 == 0 :
        return False

    for i in range(3, int(math.sqrt(P)+1), 2) :
        if P % i == 0 :
            return False
    return True


result = 0

length = int(sys.stdin.readline())

for i in range(length) :
    P = int(sys.stdin.readline())
    if P < 4 :
        result+= 1
        continue

    if isPrime(P*2+1) :
        result += 1

print(result)