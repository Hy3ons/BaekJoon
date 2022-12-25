s = input()
cnt = 1e10
temp = 1e10

last = '.'

for i in s:
    if last == i:
        temp += 1
    else:
        cnt = min(cnt, temp)
        temp = 1
    last = i


cnt = min(cnt, temp)
print(cnt)