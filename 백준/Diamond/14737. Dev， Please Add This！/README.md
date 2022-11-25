# [Diamond I] Dev, Please Add This! - 14737 

[문제 링크](https://www.acmicpc.net/problem/14737) 

### 성능 요약

메모리: 23780 KB, 시간: 216 ms

### 분류

2-sat(2_sat), 그래프 이론(graphs), 구현(implementation), 강한 연결 요소(scc)

### 문제 설명

<p>재민이는 퍼즐게임 앱을 개발하였다.</p>

<p>격자에 공이 놓여있다. 공을 상하좌우 중 한 방향으로 이동시킬 수 있고, 이때 공은 벽에 부딪히거나 격자의 끝에 도달할 때까지 그 방향으로 굴러간다. 특정 칸에는 별이 놓여 있고, 공이 그 칸에 멈추거나 그 칸을 지나가면 사용자는 그 별을 얻게 된다. 목표는 격자에 있는 모든 별을 얻는 것이다.</p>

<p>이 앱에는 레벨 에디터가 있어서 사용자가 자신만의 레벨을 만들고 공유할 수 있다. 어느 날 재민이는 “만든 레벨이 해결 가능한 레벨인지 검사하는 기능을 만들어 주세요!”라는 건의사항을 받았다. 글쎄, 말이야 쉽지만...</p>

### 입력 

 <p>첫 번째 줄에 격자의 높이 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D43B TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>H</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$H$</span></mjx-container>와 너비 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D44A TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>W</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$W$</span></mjx-container>가 주어진다. (<mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mn class="mjx-n"><mjx-c class="mjx-c31"></mjx-c></mjx-mn><mjx-mo class="mjx-n" space="4"><mjx-c class="mjx-c2264"></mjx-c></mjx-mo><mjx-mi class="mjx-i" space="4"><mjx-c class="mjx-c1D43B TEX-I"></mjx-c></mjx-mi><mjx-mo class="mjx-n"><mjx-c class="mjx-c2C"></mjx-c></mjx-mo><mjx-mi class="mjx-i" space="2"><mjx-c class="mjx-c1D44A TEX-I"></mjx-c></mjx-mi><mjx-mo class="mjx-n" space="4"><mjx-c class="mjx-c2264"></mjx-c></mjx-mo><mjx-mn class="mjx-n" space="4"><mjx-c class="mjx-c35"></mjx-c><mjx-c class="mjx-c30"></mjx-c></mjx-mn></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mn>1</mn><mo>≤</mo><mi>H</mi><mo>,</mo><mi>W</mi><mo>≤</mo><mn>50</mn></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$1 \leq H, W \leq 50$</span></mjx-container>) 다음 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D43B TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>H</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$H$</span></mjx-container>줄에는 격자의 각 행을 나타내는 길이 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D44A TEX-I"></mjx-c></mjx-mi></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><mi>W</mi></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$W$</span></mjx-container>의 문자열이 차례대로 주어진다. "#"은 벽, "."은 공간, "O"는 공, "*"는 별이다. 격자에 공은 정확히 한 개 존재하고, 별은 적어도 한 개 존재한다.</p>

### 출력 

 <p>모든 별을 얻을 수 있으면 YES, 아니면 NO를 출력한다.</p>

