s = input()
list = []

for i in range(len(s)) :
    if len(list)==0 or list[-1] != s[i] :
        list.append(s[i])
count = 0
for i in list :
    if i == '0' :
        count += 1
print(min(count,len(list)-count))