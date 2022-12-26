# [Diamond III] Quadrilaterals - 17973 

[문제 링크](https://www.acmicpc.net/problem/17973) 

### 성능 요약

메모리: 37416 KB, 시간: 248 ms

### 분류

기하학(geometry), 스위핑(sweeping)

### 문제 설명

<p>A <em>quadrilateral</em> is a polygon with exactly four distinct corners and four distinct sides, without any crossing between its sides. A quadrilateral is called <em>convex</em> if all the inner angles at its corners are less than 180 degrees, or called <em>non-convex</em>, otherwise. See the illustration below for a convex quadrilateral (left) and a non-convex quadrilateral (right).</p>

<p style="text-align: center;"><img alt="" src="https://upload.acmicpc.net/8c8a495b-2765-4a47-8654-8fcad1cba371/-/preview/" style="width: 409px; height: 162px;"></p>

<p>In a test problem, you are given a set <em>P</em> of <em>n</em> points in the plane, no three of which are collinear, and asked to find as many quadrilaterals as possible by connecting four points from <em>P</em>, while each point in <em>P</em> can be used limitlessly many times and those quadrilaterals you find may freely overlap each other. You will earn different credits for each quadrilateral you find, depending on its shape and area. In principle, you earn more credits for convex quadrilaterals and for quadrilaterals with minimum area.</p>

<p>More precisely, the rules for credits are as follows, where <em>a</em> denotes the minimum over the areas of all possible quadrilaterals formed by connecting four points in <em>P</em>:</p>

<ul>
	<li>For each distinct convex quadrilateral with area exactly <em>a</em>, you earn 4 credits.</li>
	<li>For each distinct non-convex quadrilateral with area exactly <em>a</em>, you earn 3 credits.</li>
	<li>For each distinct convex quadrilateral with area strictly larger than <em>a</em>, you earn 2 credits.</li>
	<li>For each distinct non-convex quadrilateral with area strictly larger than <em>a</em>, you earn 1 credit.</li>
</ul>

<p>Note that two quadrilaterals are distinct unless the corners and sides of one are exactly the same to the other’s, and that there may be two or more quadrilaterals of the minimum area <em>a</em>.</p>

<p>You of course want to find all possible quadrilaterals and earn the maximum possible total credits. Given a set <em>P</em> of <em>n</em> points in the plane, write a program that outputs the maximum possible total credits you can earn when you find all possible quadrilaterals from the set <em>P</em>.</p>

### 입력 

 <p>Your program is to read from standard input. The input starts with a line containing an integer <em>n</em> (4 ≤ <em>n</em> ≤ 1,000), where <em>n</em> denotes the number of points in the set <em>P</em>. In the following <em>n</em> lines, each line consists of two integers, ranging −10<sup>9</sup> to 10<sup>9</sup>, representing the coordinates of a point in <em>P</em>. There are no three points in <em>P</em> that are collinear.</p>

### 출력 

 <p>Your program is to write to standard output. Print exactly one line consisting of a single integer that represents the maximum possible total credits you can earn from the set <em>P</em>.</p>

