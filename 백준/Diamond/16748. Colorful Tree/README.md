# [Diamond V] Colorful Tree - 16748 

[문제 링크](https://www.acmicpc.net/problem/16748) 

### 성능 요약

메모리: 532192 KB, 시간: 6260 ms

### 분류

자료 구조(data_structures), 오일러 경로 테크닉(euler_tour_technique), 최소 공통 조상(lca), 오프라인 쿼리(offline_queries), 세그먼트 트리(segtree), 트리(trees), 트리를 사용한 집합과 맵(tree_set)

### 문제 설명

<p>A tree structure with some colors associated with its vertices and a sequence of commands on it are given. A command is either an update operation or a query on the tree. Each of the update operations changes the color of a specified vertex, without changing the tree structure. Each of the queries asks the number of edges in the minimum connected subgraph of the tree that contains all the vertices of the specified color.</p>

<p>Your task is to find answers of each of the queries, assuming that the commands are performed in the given order.</p>

### 입력 

 <p>The input consists of a single test case of the following format.</p>

<pre>n
a<sub>1</sub> b<sub>1</sub>
.
.
.
a<sub>n−1</sub> b<sub>n−1</sub>
c<sub>1</sub> . . . c<sub>n</sub>
m
command<sub>1</sub>
.
.
.
command<sub>m</sub></pre>

<p>The first line contains an integer n (2 ≤ n ≤ 100 000), the number of vertices of the tree. The vertices are numbered 1 through n. Each of the following n − 1 lines contains two integers a<sub>i</sub> (1 ≤ a<sub>i</sub> ≤ n) and b<sub>i</sub> (1 ≤ b<sub>i</sub> ≤ n), meaning that the i-th edge connects vertices a<sub>i</sub> and b<sub>i</sub>. It is ensured that all the vertices are connected, that is, the given graph is a tree. The next line contains n integers, c<sub>1</sub> through c<sub>n</sub>, where c<sub>j</sub> (1 ≤ c<sub>j</sub> ≤ 100 000) is the initial color of vertex j. The next line contains an integer m (1 ≤ m ≤ 100 000), which indicates the number of commands. Each of the following m lines contains a command in the following format.</p>

<pre>U xk yk</pre>

<p>or</p>

<pre>Q yk</pre>

<p>When the k-th command starts with <code>U</code>, it means an update operation changing the color of vertex x<sub>k</sub> (1 ≤ x<sub>k</sub> ≤ n) to y<sub>k</sub> (1 ≤ y<sub>k</sub> ≤ 100 000). When the k-th command starts with <code>Q</code>, it means a query asking the number of edges in the minimum connected subgraph of the tree that contains all the vertices of color y<sub>k</sub> (1 ≤ y<sub>k</sub> ≤ 100 000).</p>

### 출력 

 <p>For each query, output the number of edges in the minimum connected subgraph of the tree containing all the vertices of the specified color. If the tree doesn’t contain any vertex of the specified color, output -1 instead.</p>

