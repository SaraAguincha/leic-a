#include <iostream>
#include <ostream>
#include <fstream>
#include <vector>
#include <stack>
#include <algorithm>
#include <limits.h>
#include <queue>
#include <string.h>
#include <utility>

using namespace std;

//-----------------------------------------------------GLOBAIS--------------------------------------------------------------
// n nr de processos e k nr de entradas diferentes da matriz (ligacoes)
int n, k;

// X vertice source, Y vertice sink
int X = 0;
int Y;
int MAX_flow = 0;



void addEdge(vector <vector <pair<int, int> > > & aux, int u, int v, int peso)
{
    aux[u].push_back(make_pair(v,peso));
    aux[v].push_back(make_pair(u,peso));
}

void input(vector< vector <pair <int,int> > > & adj) {
    int a,b,c;

    for(int i = 1; i < n+1; i++ ){
        if(scanf("%d %d",&a,&b) ==0)
            exit(EXIT_FAILURE);
        addEdge(adj,0,i,a);
        addEdge(adj,i,n+1,b);
    }
    
    for (int i = 0; i < k; i++){
        if(scanf("%d %d %d",&a,&b,&c) ==0 )
            exit(EXIT_FAILURE);
        addEdge(adj,a,b,c);
    }
}

bool bfs(vector <vector <pair<int, int> > > & aux, int s, int t, int parent[])
{
    // Create a visited array and mark all vertices as not
    // visited
    bool visited[n+2];
    memset(visited, 0, sizeof(visited));
    // Create a queue, enqueue source vertex and mark source
    // vertex as visited
    queue<int> q;
    q.push(s);
    visited[s] = true;
    parent[s] = -1;
 
    // Standard BFS Loop
    while (!q.empty()) {
        int u = q.front();
        q.pop();
        
        for (auto x : aux[u]){
            if (visited[x.first] == false && x.second > 0)  {
                // If we find a connection to the sink node,
                // then there is no point in BFS anymore We
                // just have to set its parent and can return
                // true
                if (x.first == t) {
                    parent[x.first] = u;
                    return true;
                }
                q.push(x.first);
                parent[x.first] = u;
                visited[x.first] = true;
            }
        }
    }
 
    // We didn't reach sink in BFS starting from source, so
    // return false
    return false;
}




void fordFulkerson(vector< vector <pair <int,int> > > & adj) {
    int a;
    vector <vector <pair<int,int> > > residual_adj(adj.begin(), adj.end());    

    int parents[n + 2];
    
    while (bfs(residual_adj, X, Y, &parents[0])) {
        
        int flow_total = INT_MAX;
        for (int i = Y; i != X; i = parents[i]) {
            a = parents[i];
            for (auto x : residual_adj[a]){
                if (x.first == i){
                    flow_total = min(flow_total, x.second);
                    break;
                }
            }
        }
 

        for (int i = Y; i != X; i = parents[i]) {
            a = parents[i];
            for (long unsigned int x = 0; x < residual_adj[a].size(); x++){
            //for (auto x : residual_adj[a]){
                if (residual_adj[a][x].first == i){
                    pair <int, int> aux = make_pair(residual_adj[a][x].first, residual_adj[a][x].second - flow_total);
                    residual_adj[a][x].swap(aux);
                    break;
                }
            }
            for (long unsigned int x = 0; x < residual_adj[i].size(); x++){
                if (residual_adj[i][x].first == a){
                    pair <int, int> aux = make_pair(residual_adj[i][x].first, residual_adj[i][x].second - flow_total);
                    residual_adj[i][x].swap(aux);
                    break;
                }
            }
        }
 
        // Adiciona flow total to overall MAX_flow
        MAX_flow += flow_total;
    }
 
}

int main() {

    if(scanf("%d %d",&n,&k) == 0)
        exit(EXIT_FAILURE);
    
    Y = n+1;
    vector <vector <pair <int,int> > > adj;
    adj.resize(n+2);
    input(adj);
    

    fordFulkerson(adj);
    printf ("%d\n",MAX_flow);
    
    return 0;


}