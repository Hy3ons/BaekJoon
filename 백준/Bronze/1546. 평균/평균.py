t = int(input())
list = list(map(int, input().split(" ")))
sum = 0
ma = max(list)
for i in list :
    sum += (i/ma) * 100
print(sum / t);