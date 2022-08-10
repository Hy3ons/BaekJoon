add = '[+]'
minus = '[-]'
mul = '[*]'
divide = '[/]'

answer = []

s = input()
number = int(s)
sBin = list(map(int, bin(number)[2:]))

def main():
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
            answer.append(add)
        answer.append(mul)

    if len(answer) > 99:
        print(-1)
    else:
        print(len(answer))
        for i in answer:
            print(i, end=' ')

main()