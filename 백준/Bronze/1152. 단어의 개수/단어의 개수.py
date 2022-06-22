str = input()
can = True
count = 0
for i in range(len(str)) :
    if can :
        if ord(str[i]) != ord(' '):
            count += 1
            can = False

    if ord(str[i])==ord(' ') :
        can = True
print(count)