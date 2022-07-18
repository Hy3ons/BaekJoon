import sys

check = [2,3,5,7,11,13,17]

def mill (P, a, k, d) :
    temp = pow(int(a), int(d), int(P))
    if (temp - 1) % P == 0 :
        return True

    for i in range(k) :
        if (temp + 1) % P == 0:
            return True

        temp = (temp * temp) % P
    return False


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