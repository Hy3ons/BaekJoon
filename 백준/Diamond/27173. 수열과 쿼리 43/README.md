# [Diamond III] 수열과 쿼리 43 - 27173 

[문제 링크](https://www.acmicpc.net/problem/27173) 

### 성능 요약

메모리: 24236 KB, 시간: 996 ms

### 분류

자료 구조(data_structures), 느리게 갱신되는 세그먼트 트리(lazyprop), 세그먼트 트리(segtree)

### 문제 설명

<p>길이가 N인 수열 A<sub>1</sub>, A<sub>2</sub>, ..., A<sub>N</sub>이 주어진다. 이때, 다음 쿼리를 수행하는 프로그램을 작성하시오.</p>

<ul>
	<li><code>1 i</code>: A<sub>i</sub> 를 출력한다. (1 ≤ i ≤ N)</li>
	<li><code>2 x y t</code>: 만약 A<sub>x</sub>, A<sub>x+1</sub>, ..., A<sub>y</sub> 중 t 미만의 수가 있다면 이 쿼리를 무시한다. 그렇지 않다면 A<sub>x</sub>, A<sub>x+1</sub>, ..., A<sub>y</sub> 에 t를 뺀다. (1 ≤ x ≤ y ≤ N, 1 ≤ t ≤ 10<sup>18</sup>)</li>
	<li><code>3 x y t</code>: x ≤ i ≤ y에 대해, A<sub>i</sub> = t + i - y 로 둔다. (1 ≤ x ≤ y ≤ N, t - y + x ≥ 0, 1 ≤ t ≤ 10<sup>18</sup>)</li>
	<li><code>4 x y</code>: x ≤ i ≤ y에 대해, <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-msub><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D434 TEX-I"></mjx-c></mjx-mi><mjx-script style="vertical-align: -0.15em;"><mjx-mi class="mjx-i" size="s"><mjx-c class="mjx-c1D456 TEX-I"></mjx-c></mjx-mi></mjx-script></mjx-msub><mjx-mo class="mjx-n" space="4"><mjx-c class="mjx-c3D"></mjx-c></mjx-mo><mjx-mo class="mjx-n" space="4"><mjx-c class="mjx-c230A"></mjx-c></mjx-mo><mjx-msub><mjx-msqrt><mjx-sqrt><mjx-surd><mjx-mo class="mjx-n"><mjx-c class="mjx-c221A"></mjx-c></mjx-mo></mjx-surd><mjx-box style="padding-top: 0.15em;"><mjx-mi class="mjx-i"><mjx-c class="mjx-c1D434 TEX-I"></mjx-c></mjx-mi></mjx-box></mjx-sqrt></mjx-msqrt><mjx-script style="vertical-align: -0.15em;"><mjx-mi class="mjx-i" size="s"><mjx-c class="mjx-c1D456 TEX-I"></mjx-c></mjx-mi></mjx-script></mjx-msub><mjx-mo class="mjx-n"><mjx-c class="mjx-c230B"></mjx-c></mjx-mo></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><msub><mi>A</mi><mi>i</mi></msub><mo>=</mo><mo fence="false" stretchy="false">⌊</mo><msub><msqrt><mi>A</mi></msqrt><mi>i</mi></msub><mo fence="false" stretchy="false">⌋</mo></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">$A_i = \lfloor \sqrt A_i \rfloor$</span></mjx-container>로 둔다. (1 ≤ x ≤ y ≤ N)</li>
</ul>

### 입력 

 <p>첫째 줄에 수열의 크기 N, 쿼리의 개수 Q가 주어진다. (1 ≤ N ≤ 100,000, 1 ≤ Q ≤ 500,000)</p>

<p>둘째 줄에는 A<sub>1</sub>, A<sub>2</sub>, ..., A<sub>N</sub>이 주어진다. (1 ≤ A<sub>i</sub> ≤ 10<sup>18</sup>)</p>

<p>이후 Q개의 줄에 위에서 설명한 것과 같은 쿼리가 주어진다.</p>

### 출력 

 <p><code>1 i</code> 형태의 쿼리 결과를 순서대로 한 줄에 하나씩 출력한다.</p>

