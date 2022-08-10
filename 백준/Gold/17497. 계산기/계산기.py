add = '[+]'
minus = '[-]'
mul = '[*]'
divide = '[/]'

answer = []

s = input()
number = int(s)
sBin = list(map(int, bin(number)[2:]))

temp = 0
limit = 1<<63

def make(n):
    global temp
    if n == 2:
        temp <<= 1
        temp |= 6
        temp <<= 1
        answer.append(add)
        answer.append(mul)
        answer.append(add)
        answer.append(mul)
    elif n == 1:
        temp |= 2
        temp <<= 1
        answer.append(add)
        answer.append(mul)
    else :
        pass


def main():
    global temp, limit

    for k in range(len(sBin)):
        i = sBin[k]

        if k == len(sBin) -1 and i == 0:
            answer.pop()
            break
        elif k == len(sBin) - 1 and i == 1:
            answer.append(add)
            answer.append(divide)
            break

        if i == 1:
            temp |= 2
            answer.append(add)
        temp <<= 1
        answer.append(mul)

        if temp > limit:
            print(-1)
            return

    if len(answer) > 99:
        print(-1)
    else:
        print(len(answer))
        for i in answer:
            print(i, end=' ')


if number >= limit and number & 1 == 1:
    print(-1)
else:
    main()