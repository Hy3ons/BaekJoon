# [Platinum IV] 백남이의 여행 - 23060 

[문제 링크](https://www.acmicpc.net/problem/23060) 

### 성능 요약

메모리: 78408 KB, 시간: 940 ms

### 분류

구성적(constructive), 깊이 우선 탐색(dfs), 그래프 이론(graphs), 그래프 탐색(graph_traversal)

### 문제 설명

<p>체스판의 세계에는 귀여운 백마인 백남이가 살고 있다. 백남이는 방학을 맞아 체스판 여행을 떠나려고 한다.</p>

<p>백남이가 생각 중인 계획은 다음과 같은 규칙을 따른다.</p>

<ul>
	<li>체스판의 총 크기는 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D441 TEX-I"></mjx-c></mjx-mi><mjx-mo class="mjx-n" space="3"><mjx-c class="mjx-cD7"></mjx-c></mjx-mo><mjx-mi class="mjx-i" space="3"><mjx-c class="mjx-c1D441 TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>N</mi><mo>×</mo><mi>N</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$N\times N$</span></mjx-container>이다.</li>
	<li>체스판에 있는 모든 칸을 방문해야 한다.</li>
	<li>같은 칸을 여러 번 방문할 수 있다. 하지만 칸을 방문한 총 횟수는 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mn class="mjx-n"><mjx-c class="mjx-c32"></mjx-c></mjx-mn><mjx-msup><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D441 TEX-I"></mjx-c></mjx-mi><mjx-script style="vertical-align: 0.363em; margin-left: 0.054em;"><mjx-mn class="mjx-n" size="s"><mjx-c class="mjx-c32"></mjx-c></mjx-mn></mjx-script></mjx-msup></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mn>2</mn><msup><mi>N</mi><mn>2</mn></msup></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$2N^2$</span></mjx-container> 이하여야 한다.</li>
	<li>백남이가 이동하는 방법의 수는 8가지이며, 다음과 같다.</li>
</ul>

<p style="text-align: center;"><img alt="" src="" style="height: 500px; width: 500px;"><br>
 </p>

<p>MBTI가 N(직관형)인 백남이는 모든 계획을 짜지 않고는 여행을 시작할 수 없다!</p>

<p>계획 수립에 골머리를 앓고 있는 백남이를 도와 백남이의 여행 계획을 짜주자!</p>

<p style="text-align: center;"><img alt="" src="" style="width: 500px; height: 286px;"><br>
 </p>

### 입력 

 <p>첫 줄에 격자판의 행의 수이자 열의 수인 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D441 TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>N</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$N$</span></mjx-container> 이 주어진다. (<mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mn class="mjx-n"><mjx-c class="mjx-c31"></mjx-c></mjx-mn><mjx-mo class="mjx-n" space="4"><mjx-c class="mjx-c2264"></mjx-c></mjx-mo><mjx-mi class="mjx-i" space="4"><mjx-c class="mjx-c1D441 TEX-I"></mjx-c></mjx-mi><mjx-mo class="mjx-n" space="4"><mjx-c class="mjx-c2264"></mjx-c></mjx-mo><mjx-mn class="mjx-n" space="4"><mjx-c class="mjx-c35"></mjx-c><mjx-c class="mjx-c30"></mjx-c><mjx-c class="mjx-c30"></mjx-c></mjx-mn></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mn>1</mn><mo>≤</mo><mi>N</mi><mo>≤</mo><mn>500</mn></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$1\leq N \leq 500$</span></mjx-container>)</p>

<p>둘째 줄에는 현재 백남이의 좌표를 나타내는 정수 r, c가 주어진다. 이는 r번째 행, c번째 열에 위치한 칸을 의미한다.</p>

### 출력 

 <p>만약 여행이 불가능하다고 판단되면, -1을 출력하고,</p>

<p>여행이 가능하면, 첫째 줄에 칸을 방문한 총 횟수 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D43E TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>K</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$K$</span></mjx-container>를 출력하고, 이후 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D43E TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>K</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$K$</span></mjx-container>개의 줄에 한 줄에 하나씩 백남이가 방문한 칸의 좌표를 차례대로 출력한다.</p>

