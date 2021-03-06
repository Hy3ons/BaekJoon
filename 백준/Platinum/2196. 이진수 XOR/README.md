# [Platinum III] 이진수 XOR - 2196 

[문제 링크](https://www.acmicpc.net/problem/2196) 

### 성능 요약

메모리: 15284 KB, 시간: 148 ms

### 분류

너비 우선 탐색(bfs), 비트마스킹(bitmask), 다이나믹 프로그래밍(dp), 비트필드를 이용한 다이나믹 프로그래밍(dp_bitfield), 그래프 이론(graphs), 그래프 탐색(graph_traversal)

### 문제 설명

<p>길이 B(1 ≤ B ≤ 16)인 이진수들이 E(1 ≤ E ≤ 100)개 있다. 이 이진수들을 두 개씩 선택하여 XOR연산을 하여, 어떤 이진수를 만들려고 한다. 이 과정에서 만들어지는 이진수들을 이용하여 XOR연산을 해도 되며, 같은 두 이진수를 XOR연산을 해도 된다.</p>

<p>만약 우리가 만들고자 하는 이진수를 만들 수 없다면, 이 이진수와 제일 가까운 이진수를 만들려고 한다. 제일 가깝다는 것은, 두 이진수들에서 서로 다른 비트의 개수가 최소인 것을 말한다. 만약 여러 개의 이진수가 제일 가까운 경우에는, XOR 연산을 가장 적게 사용하는 이진수를 출력한다. 같은 회수의 연산을 사용한다면 사전식으로 제일 앞에 오는 이진수를 출력한다.</p>

<p>XOR 연산자는 ^이고, 0^0=0, 0^1=1, 1^0=1, 1^1=0이다. 10110과 11101을 XOR 연산을 하면 01011이 된다.</p>

### 입력 

 <p>첫째 줄에 B, E가 주어진다. 다음 줄에는 우리가 만들고자 하는 이진수를 나타내는 B개의 숫자(0 또는 1)이 주어진다. 다음 E개의 줄에는 각각의 이진수들이 주어진다.</p>

### 출력 

 <p>첫째 줄에 사용한 XOR 연산의 회수를 출력한다. 다음 줄에는 이진수를 출력한다. 첫째 줄에 출력한 연산의 회수는 둘째 줄의 이진수를 만들기 위해 사용한 XOR 연산의 회수이다. XOR 연산은 적어도 한 번 해야 한다.</p>

