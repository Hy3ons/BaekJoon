# [Diamond III] Max or Min - 17955 

[문제 링크](https://www.acmicpc.net/problem/17955) 

### 성능 요약

메모리: 361496 KB, 시간: 1980 ms

### 분류

자료 구조(data_structures), 세그먼트 트리(segtree)

### 문제 설명

<p>Kevin has n integers a<sub>1</sub>, a<sub>2</sub>, . . . , a<sub>n</sub> arranged in a circle. That is, the numbers a<sub>i</sub> and a<sub>i+1</sub> (1 ≤ i < n) are neighbors. The numbers a<sub>1</sub> and a<sub>n</sub> are neighbors as well. Therefore, each number has exactly two neighbors.</p>

<p>In one minute, Kevin can set a<sub>i</sub> to the minimum among three numbers: a<sub>i</sub> and it’s two neighbors. Alternatively, Kevin can set a<sub>i</sub> to the maximum among the same numbers. For example, if a<sub>i</sub> = 5 and a<sub>i</sub> has two neighbors 3 and 2, and Kevin performs the minimum operation, a<sub>i</sub> will be equal to 2. However, if he performs the maximum operation, a<sub>i</sub> will remain 5.</p>

<p>For each x from 1 to m, find the minimum number of minutes to make all numbers equal x, or determine that it is impossible to do so.</p>

### 입력 

 <p>The first line contains two integers n and m (3 ≤ n ≤ 2 · 10<sup>5</sup>, 1 ≤ m ≤ 2 · 10<sup>5</sup>) — the number of integers in the circle, and the number of integers you need to find answers for.</p>

<p>The second line contains n integers a<sub>1</sub>, a<sub>2</sub>, . . . , a<sub>n</sub> (1 ≤ a<sub>i</sub> ≤ m) — the integers in the circle.</p>

### 출력 

 <p>Print m integers. The i-th integer should be equal to the minimum number of minutes that are needed to make all numbers equal i or −1 if it’s impossible.</p>

