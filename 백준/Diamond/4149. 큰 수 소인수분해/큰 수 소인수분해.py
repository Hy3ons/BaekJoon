import sys
import random as ran

check = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 61]
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
        d /= 2

    for a in check:
        if not (mill(P, a, k, d)):
            return False
    return True


def rho(n):
    while True:
        x = y = ran.randint(0, 20) % n
        c = ran.randint(1, 20)

        if ran.randint(1, 20) % 2 == 0:
            c *= -1

        g = 1
        while g == 1:
            x = function(x, c, n)
            y = function(function(y, c, n), c, n)

            g = gcd(abs(x - y), n)
        if g != n:
            return g


def main(n):
    if isPrime(n):
        outputs.append(n)
    else:
        temp = rho(n)
        main(n // temp)
        main(temp)


number = int(sys.stdin.readline())
if number == 1:
    print(number)
else :
    while number % 2 == 0:
        number //= 2
        outputs.append(2)

    if number != 1:
        main(number)

    outputs.sort()
    for i in outputs:
        print(i)


