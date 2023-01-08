import sys

times = []


def gcd(a, b):
    mul = a // b
    remain = a % b

    if remain == 0:
        return b

    times.append(mul)

    return gcd(b, remain)


def gcdReal(a, b):
    if b == 0:
        return a
    return gcdReal(b, a % b)


def function(a, b, goal):
    g = gcd(a, b)
    x, y = 0, 1
    prevX, prevY = 1, 0

    for mul in times:
        tempX = x * -mul + prevX
        tempY = y * -mul + prevY

        prevX, prevY = x, y
        x, y = tempX, tempY
    x *= (goal // g)
    y *= (goal // g)

    return x, y, g


def main():
    A, B, G = map(int, input().split())
    A, B = max(A, B), min(A, B)

    if A == G or B == G:
        print('YES')
        return

    if A == 0:
        if G == 0:
            print('YES')
        else :
            print('NO')
        return
    if B == 0:
        if G % A == 0:
            print('YES')
        else:
            print('NO')
        return

    if G == 0:
        if A == 0 or B == 0:
            print('YES')
        else :
            print('NO')
        return

    if G % gcdReal(A, B) != 0:
        print('NO')
        return

    x, y, g = function(A, B, G)

    addX, addY = B // g, A // g
    # addX, addY = (B // g) * G, (A // g) * G
    if x < 1:
        mul = -x // addX
        x += addX * mul
        y -= addY * mul

        if x < 1:
            x += addX
            y -= addY
    if y < 1:
        mul = -y // addY
        x -= addX * mul
        y += addY * mul

        if y < 1:
            x -= addX
            y += addY

    if x <= 0 or y <= 0:
        print("NO")
        return

    if gcdReal(x, y) == 1:
        print('YES')
        return

    oX = x
    oY = y

    while y - addY >= 1:
        x += addX
        y -= addY
        if gcdReal(x, y) == 1:
            print('YES')
            return

    while 1 <= oX - addX:
        oX -= addX
        oY += addY
        if gcdReal(oX, oY) == 1:
            print('YES')
            return

    print('NO')


if __name__ == '__main__':
    main()
