import math

load = [[] for _ in range(50001)]

count = 0

eratos = [False for _ in range(100500)]
eratos[0] = True
eratos[1] = True
for i in range(2, 100500):
    if not eratos[i]:
        for j in range(i * i, 100500, i):
            eratos[j] = True


def FFT(vector, w):
    if len(vector) == 1:
        return

    even = []
    odd = []

    for i in range(len(vector)):
        if i & 1:
            odd.append(vector[i])
        else:
            even.append(vector[i])

    FFT(even, w * w)
    FFT(odd, w * w)

    mul = complex(1, 0)
    n = len(vector) // 2

    for i in range(n):
        vector[i] = even[i] + mul * odd[i]
        vector[i + n] = even[i] - mul * odd[i]

        mul *= w


def start(a, b):
    size = 1
    while size <= len(a) or size <= len(b):
        size <<= 1
    size <<= 1

    while len(a) < size:
        a.append(0)
    while len(b) < size:
        b.append(0)

    init = complex(math.cos(2*math.pi / size), math.sin(2*math.pi/size))

    FFT(a, init)
    FFT(b, init)

    c = []
    for i in range(len(a)):
        c.append(a[i] * b[i])

    FFT(c, 1/init)

    for i in range(len(c)):
        c[i] /= size
        c[i] = round(c[i].real)

    return c

cent = [False for _ in range(50001)]
treeS = [0 for _ in range(50001)]

def getSize(node, prev):
    treeS[node] = 1
    for go in load[node]:
        if go != prev and not cent[go]:
            treeS[node] += getSize(go, node)
    return treeS[node]

def getCent(node, prev, limit):
    for go in load[node]:
        if go != prev and not cent[go] and treeS[go] * 2 > limit:
            return getCent(go, node, limit)

    return node

def check(vector, node, prev, depth):
    if len(vector) <= depth:
        vector.append(1)
    else:
        vector[depth] += 1

    for go in load[node]:
        if go != prev and not cent[go]:
            check(vector, go, node, depth+1)




def function(anyNode):
    global count

    limit = getSize(anyNode, 0)
    thisCent = getCent(anyNode, 0, limit)

    cent[thisCent] = True

    a = [0]

    for go in load[thisCent]:
        if not cent[go]:
            f = [0]
            check(f, go, 0, 1)
            c = start(a.copy(), f)

            check(a, go, 0, 1)

            for i in range(len(c)):
                if not eratos[i]:
                    count += c[i]

    for i in range(len(a)):
        if not eratos[i]:
            count += a[i]

    for go in load[thisCent]:
        if not cent[go]:
            function(go)


import sys

sys.setrecursionlimit(50000)

def main():
    input = sys.stdin.readline
    n = int(input().rstrip())

    for _ in range(n-1):
        a, b = map(int, input().rstrip().split())
        load[a].append(b)
        load[b].append(a)

    function(1)
    all = (n * (n-1)) >> 1

    print(count / all)
main()