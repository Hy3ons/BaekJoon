N = int(input())

def pprint(a):
    print(a, end='')

def main ():
    pass

if N == 1:
    print("*")
elif N == 2:
    print("**\n**")
elif N == 3:
    print("***\n***\n***")
elif N == 4:
    print("****\n****\n****\n****")
else:
    temp = N- 2
    print("*"*N)

    ans = []
    for i in range((temp >> 1) + 1):
        s = '*'
        s += ' ' * i
        s += '*'
        s += ' ' * (N - 4 - i*2)
        s += '*'
        s += ' ' * i
        s += '*'
        ans.append(s)

    if N % 2== 0:
        ans.pop()
    else:
        ans.pop()
        ans.append('*' + ' ' * ((N-3) // 2) +'*'+' ' * ((N-3) // 2) +'*')

    for i in ans:
        print(i)

    if N & 1:
        ans.pop()

    ans.reverse()
    for i in ans:
        print(i)
    print('*' * N)

