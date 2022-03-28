#include <iostream>
#include <fstream>
#include <vector>
#include <stack>
#include<algorithm>

using namespace std;

int N,M,L;
int K = 0;

class Vertice {
public:
    int number, longest_path = 0;
    bool visited, has_father;
    vector <Vertice*> next;
    void addNext(Vertice* v);
    
};

void Vertice::addNext(Vertice* v){
    this->next.push_back(v);
}

void algoritmoPath();
void input();
void algoritmoDfs();

vector<Vertice*> lista_v;

void input() {
    int i, x, y;
    
    if(scanf("%d %d",&N,&M) == 0)
        exit(EXIT_FAILURE);
    
    for(i = 0; i < N; i++ ){
        Vertice* NewV = new Vertice;
        NewV->number = i+1;
        lista_v.push_back(NewV);
    }
    
    for (i = 0; i < M; i++){
        if(scanf("%d %d",&x,&y) ==0 )
            exit(EXIT_FAILURE);
        lista_v[y-1]->has_father = true;
        lista_v[x-1]->addNext(lista_v[y-1]);
    }
}

void algoritmoDfs(int a){
    unsigned long i;
    Vertice* v;
    stack<Vertice*> stack;
    stack.push(lista_v[a]);

    while (!stack.empty()){
        v = stack.top();
        if (v->next.empty()){ 
            v->visited = true;
            v->longest_path = 1;
            stack.pop();
            continue;
        }
        if (v->visited){
            for (i = 0; i < v->next.size(); i++) {
                v->longest_path = max(v->longest_path,(v->next[i]->longest_path + 1));
            }
            
            stack.pop();
            continue;
        }
        for (i = 0; i < v->next.size(); i++) {

            if (!v->next[i]->visited){
                stack.push(v->next[i]); // adiciona os filhos ao stack que nao foram visitados
            }
        }
        // ficamos a saber que ja foram adicionados na pilha os filhos, 
        //logo quando aparecer denovo os filhos ja foram visitados logo este ja é visitado, é so alterar o valor do path
        v->visited = true;

    }

};

void algoritmoPath(){

    for(int i = 0; i < N; i++){

        if (!lista_v[i]->has_father){
            algoritmoDfs(i);
            K++;
        }
    }

    int ans = 0;

    for (int i = 0; i < N;i++){
        ans = max(ans,lista_v[i]->longest_path);
    }
    L = ans;
    
}



int main(){

    input();

    algoritmoPath();

    cout << K << " " << L << endl;

    return 0;
}
