#include <iostream>
#include <fstream>
#include <vector>
#include<bits/stdc++.h>
#include<algorithm>
#include <stdio.h>

using namespace std;

int N,M,L;
int K = 0;

class Vertice {
private:
    int number, longest_path = 0;
    bool visited, has_father;
    vector <Vertice> last;
    vector <Vertice> next;

public:
    void addNext(Vertice v);
    
    void addLast(Vertice v);
};

void Vertice::addNext(Vertice v){
    this->next.push_back(v);
}

void Vertice::addLast(Vertice v){
    this->last.push_back(v);
}


void algoritmoPath(vector<Vertice> no_father);
vector<Vertice> input(FILE *input_file);
void algoritmoDfs(vector<Vertice> no_father);

vector<Vertice> input(FILE *input_file) {
    int i, x, y;
    vector<Vertice> no_father;
    
    fscanf(input_file,"%d %d",&N,&M);
    Vertice* list_v = new Vertice[N+1];

    for(i = 1; i <= N; i++ ){
        Vertice NewV;
        NewV.number = i;
        &list_v[i]= NewV;
    }
    
    for (i = 0; i < M; i++){
        fscanf(input_file,"%d %d",&x,&y);
        //printf("x = %d e y = %d \n",x,y);
        &list_v[y].has_father = true;
        &list_v[x].addNext(list_v[y]);
        //printf("pai = %d e filho = %d \n",list_v[x].number,list_v[x].next[0].number);
        &list_v[y].addLast(list_v[x]);
    }
    
    for (i = 1; i <= N; i++){
        if (&list_v[i].has_father == false){
            no_father.push_back(list_v[i]);
            K++;
        }
    }

    cout << "6 - " << &list_v[6].next[1].number<< endl;

    //cout <<"N and M and K " << N << " " << M << " " << K << endl;

    return no_father;
}

void algoritmoDfs(Vertice v){
    int i;
    char x;
    Vertice father;
    stack<Vertice> stack;
    stack.push(v);
    father = stack.top(); //vertice inicial


    while (!stack.empty()){
        v = stack.top();
        cin >> x;
        printf("vertice %d e visitado %d \n",v.number,v.visited);
        //cout <<"SIZE " << v.next.size() << "\n";
        if (v.next.empty()){ // caso nao tenha filhos nenhuns
            v.visited = true;
            v.longest_path = 0;
            stack.pop();
            continue;
        }
        // vertice visitado só temos que descobrir qual é o maior path
        if (v.visited){
            for (i = 0; i < v.next.size(); i++) {
                v.longest_path = max(v.longest_path,v.next[i].longest_path);
            }
            
            stack.pop();
            continue;
        }
        v.visited = true;     
        //cout <<"SIZE " << v.next.size() << "\n";
        for (i = 0; i < v.next.size(); i++) {
            //cout << "test\n";

            if (!v.next[i].visited){
                //cout << "test22\n";
                //printf("proximo vertice %d\n",v.next[i].number);
                stack.push(v.next[i]); // adiciona os filhos ao stack que nao foram visitados
            }
        }
        // ficamos a saber que ja foram adicionados na pilha os filhos, 
        //logo quando aparecer denovo os filhos ja foram visitados logo este ja é visitado, é so alterar o valor do path
        v.visited = true;

    }

};

void algoritmoPath(vector<Vertice> no_father){
    int x;
    for(int i = 0; i < K; i++){
        if (!no_father[i].visited){
            algoritmoDfs(no_father[i]);
        }
    }

    int ans = 0;

    for (int i = 0; i < K;i++){
        ans = max(ans,no_father[i].longest_path);
    }
    L = ans;
    
}



int main(){

   FILE *input_file;
   vector<Vertice> no_father;
   

   input_file = fopen("input.txt","r");
    
    if (input_file == NULL){
        return EXIT_FAILURE;
    }

    no_father = input(input_file);

    algoritmoPath(no_father);

    cout << K << " " << L << endl;

    fclose(input_file);
    return 0;
}
