#include <vector>
#include <iostream>
#include <map>

#define MAX 200000
#define INF 2100000000

using namespace std;

map<int, int> hs;
vector<pair<int, int>> load[MAX];

int treeS[MAX] = {0};
bool isC[MAX] = {false};
int N, K, result = INF;

int getSize (int now, int prev) {
	treeS[now] = 1;
	
	for (int i=0;i<load[now].size();i++) {
		int go = load[now][i].first;
		if (go != prev && !isC[go]) {
			treeS[now] += getSize(go, now);
		}
	}
	return treeS[now];
}

int findRoid (int now, int prev, int limit) {
	for (int i=0;i<load[now].size();i++) {
		int go = load[now][i].first;
		
		if (go != prev && !isC[go] && treeS[go] * 2 > limit) {
			return findRoid(go, now, limit);
		}
	}
	return now;
}

void update (int now, int prev, int depth, int dist) {
	if (dist > K) return;
	
    if (dist == K) {
        result = min(result, depth);
        return;    
    }
    
    auto iter = hs.find(dist);
	
    if (iter != hs.end()) {
    	iter->second = min(iter->second, depth);
	} else {
		hs.insert(make_pair(dist, depth));
	}


    for (int i=0;i<load[now].size();i++) {
    	int go = load[now][i].first;
        if (go != prev && !isC[go]) {
            update(go, now, depth+1, dist + load[now][i].second);
        }
    }        
}

void check (int now, int prev, int depth, int dist) {
	if (dist > K) return;
	
    if (dist == K) {
        result = min(result, depth);
        return;    
    }
    
    auto iter = hs.find(K - dist);
	
    if (iter != hs.end()) {
    	result = min(result, depth + iter->second);
	}

    for (int i=0;i<load[now].size();i++) {
    	int go = load[now][i].first;
        if (go != prev && !isC[go]) {
            check(go, now, depth+1, dist + load[now][i].second);
        }
    }   
}

void function(int now) {
	int limit = getSize(now, -1);
	int roid = findRoid(now, -1, limit);
	
	if (isC[roid]) return;
	isC[roid] = true;
	
	hs.clear();
	
	for (int i=0;i<load[roid].size();i++) {
		if (!isC[load[roid][i].first]) {
			check(load[roid][i].first, roid, 1, load[roid][i].second);
			update(load[roid][i].first, roid, 1, load[roid][i].second);
		}
	}
	
	for (int i=0;i<load[roid].size();i++) {
		if (!isC[load[roid][i].first]) {
			function(load[roid][i].first);
		}
	}
}

int main () {
	cin >> N >> K;
	int n1, n2, weight;
	
	for (int i=1;i<N;i++) {
		cin >> n1 >> n2 >> weight;
		
		load[n1].push_back(make_pair(n2, weight));
		load[n2].push_back(make_pair(n1, weight));
	}
	
	function(0);
	
	if (result == INF) {
		cout << -1;
	} else {
		cout << result;
	}
	
	return 0;
}