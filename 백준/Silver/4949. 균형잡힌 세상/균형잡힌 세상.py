import sys

while True:
    s = sys.stdin.readline().rstrip()
    if s == '.':
        break

    k = []
    for i in s:
        if i == '[' or i == ']' or i == '(' or i == ')':
            k.append(i)

    st = []
    if len(k) == 0:
        print('yes')
        continue
    st.append(k[0])

    for i in range(1, len(k)):
        if len(st) == 0:
            st.append(k[i])
            continue

        if k[i] == ']':
            if st[-1] == '[':
                st.pop()
            else:
                st.append(']')
        elif k[i] == ')':
            if st[-1] == '(':
                st.pop()
            else:
                st.append(')')
        else:
            st.append(k[i])

    if len(st) == 0:
        print('yes')
    else:
        print('no')
