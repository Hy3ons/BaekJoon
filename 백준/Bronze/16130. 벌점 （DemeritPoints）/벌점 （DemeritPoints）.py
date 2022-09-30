N = int(input())

def solve ():
    S = input()
    sum = 0
    k = 0
    b = [True for _ in range(3)]

    for i in range(len(S)):
        if ord('0') <= ord(S[i]) <= ord('9'):
            sum += ord(S[i]) - 48
        else:
            sum += ord(S[i]) - 55

        if sum >= 50 :
            print(k, '(09)', sep='')
            return
        elif sum >= 40:
            print(k, '(weapon)', sep='')
            return
        elif sum >= 30 :
            if b[0]:
                b[0] = False
                k += 3
        elif sum >= 20:
            if b[1]:
                k += 2
                b[1] = False
        elif sum >= 10:
            if b[2]:
                b[2] = False
                k += 1
    print(k)
for _ in range(N):
    solve()