# [Ruby IV] Godzilla - 8496 

[문제 링크](https://www.acmicpc.net/problem/8496) 

### 성능 요약

메모리: 35448 KB, 시간: 348 ms

### 분류

이분 탐색(binary_search), 자료 구조(data_structures), 분리 집합(disjoint_set), 분할 정복(divide_and_conquer), 그래프 이론(graphs), 오프라인 쿼리(offline_queries), 병렬 이분 탐색(pbs), 강한 연결 요소(scc)

### 문제 설명

<p>Ulubionym zajęciem mieszkańców Bajtołów Dolnych jest namiętne oglądanie popularnego serialu Moda na sukces. Wszystko za sprawą telewizji kablowej, która dociera ze swoim programem do każdego domu we wsi. Sieć kablowa składa się z <em>n</em> węzłów i <em>m</em> jednokierunkowych połączeń. Do każdego węzła sieci jest podłączony co najmniej jeden dom. Niektóre węzły są wyróżnione i do nich bezpośrednio transmitowany jest program. W danym domu można go oglądać, jeśli istnieje połączenie (niekoniecznie bezpośrednie) od wyróżnionego węzła do węzła, do którego podłączony jest dom. Aby zminimalizować koszty, liczba wyróżnionych węzłów powinna być jak najmniejsza.</p>

<p>Niestety, widmo zagłady zawisło nad Bajtołami! Do wsi przypełzła złośliwa Godzilla, która nie wiedzieć czemu, żywi się infrastrukturą telewizji kablowej. Co dzień zjada jedno połączenie z sieci. Ponieważ właściciel telewizji nie może pozwolić sobie na stratę abonentów, jest zmuszony aktualizować wyróżnione węzły tak, by każdy mieszkaniec mógł oglądać Modę na sukces. Poprosił Cię, byś sprawdził, czy robi to optymalnie.</p>

### 입력 

 <p>W pierwszym wierszu wejścia znajdują się dwie liczby całkowite <em>n</em> i <em>m</em> (1 ≤ <em>n</em>, <em>m</em> ≤ 100,000), oznaczające liczbę węzłów i połączeń w sieci kablowej. W kolejnych <em>m</em> wierszach znajdują się opisy połączeń. Każdy z wierszy zawiera dwie liczby całkowite <em>a</em> i <em>b</em> (1 ≤ <em>a</em>, <em>b</em> ≤ <em>n</em>, <em>a</em> ≠ <em>b</em>), oznaczające jednokierunkowe połączenie od węzła <em>a</em> do węzła <em>b</em>. W kolejnym wierszu znajduje się liczba całkowita <em>k</em> (1 ≤ <em>k</em> ≤ <em>m</em>) oznaczająca liczbę zaatakowanych połączeń. Między daną parą wezłów może istnieć co najwyżej jedno bezpośrednie połączenie w danym kierunku; każde połączenie jest podane na wejściu dokładnie jeden raz. W kolejnych <em>k</em> wierszach znajdują się numery atakowanych połączeń. Połączenia są numerowane od 1 w kolejności występowania na wejściu.</p>

### 출력 

 <p>Na standardowe wyjście należy wypisać dokładnie <em>k</em> wierszy. Wiersz <em>i</em>-ty powinien zawierać jedną liczbę całkowitą, oznaczającą liczbę wyróżnionych węzłów, do których należy transmitować <i>Modę na sukces</i> po <em>i</em>-tym ataku Godzilli.</p>

