
# <center>BaekJoon : Solved.ac

![Solved.ac hhs2003](http://mazassumnida.wtf/api/v2/generate_badge?boj=hhs2003)![Solved.ac hhs2003](http://mazandi.herokuapp.com/api?handle=hhs2003&theme=warm)
![Codeforces Badge](https://codeforces-readme-stats.vercel.app/api/badge?username=hy3ons)

---

### 이분 그래프
1. **NetWorkFlow**, 네트워크 플로우 / 최대유량 문제
   - Edmonds-Karp Algorithm, 애드몬드-카프 알고리즘
   - Ford-Fulkerson Algorithm, 포드-풀커슨 알고리즘
   - Dinic Algorithm, 디닉 알고리즘
2. **Bipartite Matching**, 이분 매칭 알고리즘
3. **Minimum Vertex Cover**, 최소 버텍스 커버 / *Kőnig's Theorem*, 쾨닉의 정리
4. **MCMF**, 최소 비용 최대 유량 문제

## 그래프
1. **Dijkstra**, 다익스트라 알고리즘 / 최단거리
2. **Union Find**, 유니온 파인드
   - Union by Path Compression, O(N) / 경로압축을 통한 구현
   - Union by Rank Compression, (height on log N) O(N log N)
3. **Floyd Warshall Algorithm**, 플로이드-워셜 알고리즘 / 최단거리
4. **Topological Sort**, 위상정렬
5. **Strongly Connected Components (SCC)**, 강한 연결 이론
   - 2 - SAT
6. **Tree**
   - SparseTable, 희소배열
   - Heavy-Light-Decomposition, HLD (세그먼트 트리 응용 필요)
   - Link-Cut-tree, 링크컷 트리
   - Divide on Centroid Decomposition and Conquer 센트로이드 분할
   - Lowest Common Ancestor (LCA) 최소 공통 조상
   - Kruskal Algorithm, 크루스칼 알고리즘
   - 오일러 경로 투어 테크닉
   - 트리에서의 파라매트릭 서치

## 자료구조 Data Structure

1. **HashSet / HashMap**
2. **LinkedList**, Queue, Deque, Stack
3. 값 / 좌표 압축
4. **Segment Tree**
   - Lichao Tree, 리차오 트리
   - Persistent Segment Tree, 퍼시스턴트 세그먼트 트리
   - Lazy Propagation Segment Tree 느리게 갱신되는 세그먼트 트리
   - Segment Tree beats! 세그먼트 트리 비츠
   - Kinetic Tournament 키네틱 세그먼트 트리
5. ~~펜윅 트리~~
6. **Trie**, 트라이 자료구조
7. **Splay Tree**, 스플레이 트리
8. **Link - Cut - Tree**, 링크 컷 트리
9. **Priority Queue**, 우선순위 큐
10. **Policy based data structures**, PBDS

## 다이나믹 프로그래밍, Dynamic Programming

1. 일반적인 다이나믹 프로그래밍 O(N), O(NM)
2. **knapSack(냅색)**, 배낭 문제
3. **Dynamic Programming on Bit fields**, 비트필드 위에서의 다이나믹 프로그래밍
4. **Convex Hull Trick (CHT)**, 컨벡스헐 트릭, 볼록껍질을 이용한 최적화
5. **Dynamic Programming Optimization with Divide and Conquer**, 분할정복을 이용한 최적화
6. **Dyanamic Programming on Tree**, 트리에서의 다이나믹 프로그래밍
7. **Aliens Trick**, 매개변수를 통한 제한으로 하는 다이나믹 프로그래밍

## 문자열, Strings

1. **Knuth–Morris–Pratt (KMP)**
2. **Z**
3. **Aho-Corasick**, 아호코라식
4. **Manacher**, 매내처
5. **Suffix Array, Lcp Array** 접미사 배열과 Lcp 배열

---

## 수학, Mathematics

1. **Gcd 알고리즘** O(logN)
2. **분할 정복을 이용한 거듭 제곱** O(log N)
3. **밀러-라빈 소수 판별**
4. **Pollard's rho Algorithm**, 폴라드 로 알고리즘
5. **Fast Fourier Transform**, FFT , 고속 푸리에 변환 알고리즘
6. **확장 유클리드 알고리즘**
7. **오일러 피함수**

## 기하학

1. **Convex Hull Algorithm**
   - Graham Scan 알고리즘
   - ~~Jarvis March 알고리즘~~
   - **Rotating Calipers**, 회전하는 캘리퍼스
2. **CCW 판별**
   - CCW를 이용한 선분 교차 판정
   - CCW를 이용한 (반)시계 방향 정렬
3. **Incremental delaunay triangulation Algorithm**
4. **Fortune's Algorithm**

---

## 그 외 알고리즘, etc Algorithm

1. **Mo's Algorithm**, 오프라인 쿼리 최적화 알고리즘 O(N sqrt(N))
2. ~~순열 사이클 분할~~
3. **Offline Dynamic Conectivity**, 오프라인 동적 연결성
4. **Slope Trick** 함수 개형을 통한 최적화
5. **Sqrt Decomposition**, 제곱근 분할법
6. **Paralled Binary Search**, 병렬 이분 탐색
7. **Smaller to Larger Technique**, 작은 집합에서 큰 집합으로 합치는 테크닉
8. **Hungarian Algorithm**, 헝가리안 알고리즘
9. **Hiersh Berg**, 히르쉬 버그 알고리즘
10. **Horp croftKarp**, 호프 크로프트 카프 알고리즘
11. **Sparse Table**, 희소배열
12. 유량 그래프 모델링 기법 중, 정점 분할
13. BBST에서 노드 정보 압축(dp)을 구현




| 내용 | 날짜 |
| ---- | ---- |
| 그래프탐색 / BFS, DFS (연구소, 구슬치기, 백조의호수, 탈옥, 달이 뜬다 가자 문제품) | 22. 4월 한달 간 공부 |
| 비트필드 위에서의 다이나믹 프로그래밍 | 22. 4월 말 |
| DP - 냅색 | 22. 4월 말 |
| DAG 그래프에서의 위상정렬 | 22. 5월 초 |
| 네트워크 플로우 | 22. 6월 초 |
| 세그먼트 트리 | 22. 6월 말 |
| 쾨닉의 정리 | 22. 7월 |
| 강한 연결 요소 | 22. 7월 중순 |
| 이분 매칭 | 22. 7월 중순 |
| 오일러 투어 트릭 | 22. 7월 말 |
| 작은 집합에서 큰 집합으로 합치기 | 22. 7월 말 |
| 트리에서의 다이나믹 프로그래밍 | 22. 8월 초 |
| Heavy Light Decomposition | 22. 8월 초 |
| Divide tree on Centroid and Conquer 센트로이드를 통한 트리에서의 분할정복 | 22. 8월 초 |
| 폴라드 로 소인수분해 | 22. 8월 중순 |
| 확장 유클리드 알고리즘 | 22. 8월 중순 |
| Mo's 알고리즘 | 22. 8월 중순 |
| sqrt Decomposition 제곱근 분할법 | 22. 8월 중순 |
| DP 다이나믹 프로그래밍의 완전한 이해 | 22. 9월 초 |
| Greedy 그리디 | 22. 9월 초 |
| ConvexHull 컨벡스헐 | 22. 9월 중순 |
| Spinning Calipers 회전하는 캘리퍼스 | 22. 9월 중순 |
| 컨벡스헐 트릭을 이용한 다이나믹 프로그래밍 최적화 | 22. 9월 중순 |
| 분할 정복을 이용한 다이나믹 프로그래밍 최적화 | 22. 9월 말 |
| 오프라인 동적 연결성을 공부 | 22. 9월 말 |
| Parlleled Binaray Search (PBS) 병렬 이분 탐색을 공부 | 22. 10. 28 |
| Hungarian 헝가리안을 공부 중 (구현 실패, 이해를 다시 해볼 예정) | 22. 11. 7 |
| Segment Tree beats 세그비츠 공부 | 22. 11. 14 ~ 22. 11. 16 |
| Splay Tree 공부 | 22. 11. 17 ~ 22. 12. 2 |
| Link---Cut tree 공부 | 22. 12. 05 ~ 22. 12. 07 |
| Esstree 회문 트리 공부 (포기, lcp를 몰라서) | 22. 12. 07 |
| Kinetic Tournament 키네 세그 공부 | 22. 12. 10 ~ 22. 12. 22 |
| 스케줄링 알고리즘 공부 (포기, 이해가 안되서) | 22. 12. 15 ~ 22. 12. 23 |
| Slope Trick 함수 개형을 이용한 최적화 공부 | 22. 12. 16 ~ 22. 12. 23 |
| HirschBerg 히르쉬버그 공부 | 22. 12. 24 ~ 22. 12. 25 |
| aliens Trick 에일리언 트릭 공부 | 22. 12. 29 ~ 22. 12. 30 |
| Incremental delaunay triangulation / Fortune's Algorithm | 23. 1.9 ~ |
| Esstree 회문 트리 공부 | 23. 5. 27 |
| Dynamic Tree Dp, (dp Chaining optimize) | 23.XX.XX|
| Fortune's Algorithm | 24.XX.XX |
