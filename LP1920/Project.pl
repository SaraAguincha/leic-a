% Sara Aguincha ist195674
:- use_module(library(clpfd)).

%------------- Predicados para a inicializacao de puzzles

% O predicado auxiliar vai adicionando no primeiro elemento a lista de letras da palavra ate a List_Pal ficar vazia.
obtem_letras_palavras_aux([],Temp):-
    Temp = [].

obtem_letras_palavras_aux([H|T],[Char|Temp]) :-
    atom_chars(H,Char), 
    obtem_letras_palavras_aux(T,Temp).

% Faz sort da List_pal e chama uma recursiva que a percorre. 
obtem_letras_palavras(List_pal,Letras) :-
    sort(List_pal,Sorted), 
    obtem_letras_palavras_aux(Sorted, Letras).

%------------------------
% Verifica se o elemento e uma variavel ou um #
% Caso seja uma variavel adiciona a List_aux e add 1 ao contador
% Se for # verifica se o contador e >= 3 adiciona aos Espacos e da reset a List_aux e ao contador
% se o contador for <3 simplesmente da reset.
espacos_fila_aux([H|T],List_aux,Count,Espacos) :-
    H \== # ,espacos_fila_aux(T,[H|List_aux],Count+1,Espacos).
    
espacos_fila_aux([H|T],List_aux,Count,[X|Espacos]) :-
    H == # ,Count >= 3, 
    reverse(List_aux,X),
    espacos_fila_aux(T,[],0,Espacos).

espacos_fila_aux([H|T],_,Count,Espacos) :-
    H == # , Count < 3, 
    espacos_fila_aux(T,[],0,Espacos).

espacos_fila_aux([],List_aux,Count,[X|M]):-
    Count >= 3, 
    reverse(List_aux,X),
    M = []. 

espacos_fila_aux([],_,Count,M):-
    Count < 3, M = [].

% Chama um predicado auxiliar com os argumentos: Fila, Aux(Vazia), Contador e Espacos 
espacos_fila(Fila, Espacos):-
    espacos_fila_aux(Fila,[],0,Espacos).

%-------------------------------------------------------
% Devolve todos os resultados do espacos_fila
espaco_fila(Fila,Espaco):-
    espacos_fila(Fila,Esp),
    member(Espaco,Esp).

%-----------------------------------------------
mat_transposta(Matriz, Transp) :-
    transpose(Matriz, Transp).

espacos_puzzle_aux([],Espaco):-
    Espaco = [].

espacos_puzzle_aux([H|T],[EspFila|Espaco]):-
    espacos_fila(H,EspFila), EspFila \== [],
	espacos_puzzle_aux(T,Espaco).

% Predicado auxiliar que devolve os espacos de todas as filas da grelha
espacos_puzzle_aux([_|T],Espaco):-
    espacos_puzzle_aux(T,Espaco).

espacos_puzzle(Grelha, Espacos):-
    espacos_puzzle_percorre(Grelha, Espacos),!.

% Predicado que devolve os espacos verticais e horizontais. Recorre ao predicado auxiliar
espacos_puzzle_percorre(Grelha, Espacos):-
    espacos_puzzle_aux(Grelha,EspHorizontal1),
    append(EspHorizontal1,EspHorizontal2),
    transpose(Grelha, Transp),
    espacos_puzzle_aux(Transp,EspVertical1),
    append(EspVertical1,EspVertical2),
    append(EspHorizontal2,EspVertical2,Espacos).

%-------------------------------
comum([H1|T1],Esp1):-
    igual(H1,Esp1)
            ;
    comum(T1,Esp1).

igual(H1,[H2|T2]):-
    H1 == H2
            ;
    igual(H1,T2).

% Testa todos os espacos,1 a 1, para ver se tem algo em comum com o Esp1
espacos_com_posicoes_comuns_aux([H|T],Esp1,[H|Esp_com]):-
    comum(H, Esp1), H \== Esp1,
    espacos_com_posicoes_comuns_aux(T,Esp1,Esp_com).

espacos_com_posicoes_comuns_aux([_|T],Esp1,Esp_com):-
    espacos_com_posicoes_comuns_aux(T,Esp1,Esp_com).

espacos_com_posicoes_comuns_aux([],_,Esp_com):-
    Esp_com = [].

% Chama um predicado auxiliar que percorre os Espacos
espacos_com_posicoes_comuns(Espacos,Esp1,Esp_com):-
    espacos_com_posicoes_comuns_aux(Espacos,Esp1,Esp_com),!.

%--------------------------------------
% Devolve os argumentos dados caso True se nao devolve False.(Nao altera nada).
palavra_possivel_esp(Pal, Esp, Espacos, Letras):-
    \+(valor(Pal, Esp, Espacos, Letras)).

% Caso tenha dado false transforma em True e Vice versa.
valor(Pal, Esp, Espacos, Letras):-
    \+(palavra_possivel_esp_aux(Pal, Esp, Espacos, Letras)).

% Devolve False caso seja verdade logo desfaz o unifica 
palavra_possivel_esp_aux(Pal, Esp, Espacos, Letras):-
    same_length(Pal, Esp),
    verifica_pal(Pal,Esp),
    espacos_com_posicoes_comuns(Espacos,Esp,Esp_com),
    Esp = Pal,verifica_block(Esp_com,Letras).


% Verifica letra a letra se a palavra pode estar no espaco.
verifica_pal([H|T],[F|B]):-
    var(F),verifica_pal(T,B)
            ;
    H == F,verifica_pal(T,B).

verifica_pal([],[]).

verifica_lista(Esp,[H|T],List_aux):-
    verifica_pal(H,Esp),
    verifica_lista(Esp,T,[H|List_aux]).
    
verifica_lista(Esp,[_|T],List_aux):-    
    verifica_lista(Esp,T,List_aux).

verifica_lista(_,[],List_aux):- List_aux \== [].

% Verifica se os espacos em comum com a palavra dada continuam a ser possiveis
verifica_block(Esp_com,Letras):-
    verifica_block_aux(Esp_com,Letras,[]),!.

verifica_block_aux([H|T],Letras,[]):-
    verifica_lista(H,Letras,[]),
    verifica_block_aux(T,Letras,[]).

verifica_block_aux([],_,_).

%--------------------------------------
% Para um espaco especifico devolve as palavras possiveis para este, percorrendo Letras ao usar member. 
palavras_possiveis_esp(Letras, Espacos, Esp, Pals_Possiveis):-
    bagof(Pal,(member(Pal,Letras),palavra_possivel_esp(Pal, Esp, Espacos, Letras)),Pals_Possiveis).

%--------------------------------------
% Utiliza o palavras_possiveis_esp para obter as palavras para o espaco que esta a verificar
% e adiciona as Pals_Possiveis (recursivamente).
palavras_possiveis_aux(Letras,Espacos,[H|T],[[H,Pals_Poss]|Pals_Possiveis]):-
    palavras_possiveis_esp(Letras, Espacos, H, Pals_Poss),
    palavras_possiveis_aux(Letras,Espacos,T,Pals_Possiveis).

palavras_possiveis_aux(_,_,[],Pals_Possiveis):-
    Pals_Possiveis = [].

% Chama um predicado auxiliar que percorre os Espacos
palavras_possiveis(Letras,Espacos,Pals_Possiveis):-
    palavras_possiveis_aux(Letras,Espacos,Espacos,Pals_Possiveis),!.

%--------------------------------------
% Compara os caracteres da primeira palavra com os das palavras seguintes, de acordo com o indice
letras_comuns_aux1([L1|R],Lst_Pals,Count,[(Count, L1)|Letras_comuns]):-
    letras_comuns_aux2(L1,Lst_Pals,Count,Letras_comuns),N is Count + 1,
    letras_comuns_aux1(R,Lst_Pals,N,Letras_comuns).

letras_comuns_aux1([_|R],Lst_Pals,Count,Letras_comuns):-
    N is Count + 1,
    letras_comuns_aux1(R,Lst_Pals,N,Letras_comuns).

letras_comuns_aux1([],_,_,Letras_comuns):-
    Letras_comuns = [].


letras_comuns_aux2(L1,[H2|T2],Count,Letras_comuns):-
    nth1(Count,H2,L2), L1 == L2, 
    letras_comuns_aux2(L1,T2,Count,Letras_comuns).

letras_comuns_aux2(_,[],_,_).

% Chama um predicado auxiliar com os argumentos: Primeira palavra (H1), 
% Lista_Pal([H1|T1]), Contador (para o indice), e as Letras_comuns 
letras_comuns([H1|T1], Letras_comuns):-
    letras_comuns_aux1(H1,[H1|T1], 1,Letras_comuns),!.

%------------------------------------------
substitui(Esp,[H|T]):-
    (Num,Char) = H, 
    nth1(Num,Esp,El), 
    El = Char,
    substitui(Esp,T).

substitui(_,[]).

% Verifica se para o elemento ha letras comuns. Caso haja substitui no elemento.
atribui_comuns_aux([H|T]):-
    append(T,Lst_Possiveis),
    letras_comuns(Lst_Possiveis,Comuns),
    substitui(H,Comuns).

atribui_comuns_aux([]).

% Predicado recursivo, percorre todos os elementos das Pals_Possiveis,
% Chama um predicado auxiliar para cada elemento.
atribui_comuns([H|T]):-
    atribui_comuns_aux(H),
    atribui_comuns(T).

atribui_comuns([]).

%-------------------------------------------------
% Chama um predicado auxiliar
retira_impossiveis(Pals_Possiveis, Novas_Pals_Possiveis):-
    retira_impossiveis_aux(Pals_Possiveis,Novas_Pals_Possiveis),!.

% Percorre recursivamente os elementos de Pals_Possiveis, e verifica
% se alguma das palavras associadas aos espacos e impossivel.
retira_impossiveis_aux([H|T],[Elem|Novas_Pals_Possiveis]):-
    verifica(H,Elem),
    retira_impossiveis_aux(T,Novas_Pals_Possiveis).

retira_impossiveis_aux([],Novas_Pals_Possiveis):- 
    Novas_Pals_Possiveis = [].

verifica([Esp|List_Pal],Elem):-
    append(List_Pal,Flat_List),
    ve_lista(Esp,Flat_List,Lista),
    Elem = [Esp,Lista].

ve_lista(Esp,[H|T],[H|Lista]):-
    verifica_pal(H,Esp),
    ve_lista(Esp,T,Lista).

ve_lista(Esp,[_|T],Lista):-
    ve_lista(Esp,T,Lista).

ve_lista(_,[],Lista):- 
    Lista = [].

%-----------------------------------------------------
% Percorre todos os elementos do Pals_Possiveis, e se Lst_Pals 
% tem apenas um elemento adiciona a lista Unicas.
obtem_unicas(Pal_Possiveis, Unicas):-
    findall(Unicas,(member(Elem,Pal_Possiveis),nth1(2,Elem,Lst_Pals),
        length(Lst_Pals,L),L == 1,append(Lst_Pals,Unicas)),Unicas).

%-------------------------------------------------------
% Utiliza o predicado obtem_unicas e um auxiliar recursivo.
retira_unicas(Pals_Possiveis, Novas_Pals_Possiveis):-
    obtem_unicas(Pals_Possiveis,Pal_unicas),
    retira_unicas_aux(Pals_Possiveis, Pal_unicas,Novas_Pals_Possiveis),!.

% Percorre as Pals_Possiveis e quando encontra uma palavra que faz parte de Pal_unicas
% retira a da lista de palavras associadas ao espaco e cria uma nova sem essa palavra (Lista).
retira_unicas_aux([H|T], Pal_unicas,[Lista|Novas_Pals_Possiveis]):-
    ve_unicas(H,Pal_unicas,Lista),
    retira_unicas_aux(T, Pal_unicas,Novas_Pals_Possiveis).

retira_unicas_aux([], _, Novas_Pals_Possiveis):- 
    Novas_Pals_Possiveis = [].

ve_unicas([H|Palavras],Pal_unicas,Lista):-
    append(Palavras,Flat_Palavras),
    length(Flat_Palavras,1),
    Lista = [H|Palavras]
            ;
    append(Palavras,Flat_Palavras),
    tira_unicas(Pal_unicas,Flat_Palavras,Atual),
    Lista=[H|[Atual]]
            ;
    Lista=[H|Palavras].

tira_unicas([H|T],Palavras,Atual):-
    member(H,Palavras),
    select(H,Palavras,Filtered_Pals),
    tira_unicas(T,Filtered_Pals,Atual)
            ;
    tira_unicas(T,Palavras,Atual).

tira_unicas([],Palavras,Atual):-
    Atual = Palavras.
    
%--------------------------------------------
% Utiliza um predicado auxiliar
simplifica(Pals_Possiveis, Novas_Pals_Possiveis):-
    simplifica_aux(Pals_Possiveis, Novas_Pals_Possiveis),!.
 
 % Este calcula utiliza o predicado conta que devolve o numero de vezes que foi preciso
 % repetir a simplificacao ate acabar, e o predicado repete que aplica o altera.
 simplifica_aux(Pals_Possiveis,Novas_Pals_Possiveis):-
     conta(Pals_Possiveis,_,0,Repeticoes),
     repete(Repeticoes,Pals_Possiveis,Novas_Pals_Possiveis).
 
 conta(Pals_Possiveis,Novas_Pals_Possiveis,Contador,Repeticoes):-
     atribui_comuns(Pals_Possiveis),
     retira_impossiveis(Pals_Possiveis, Pals_Possiveis_meio),
     retira_unicas(Pals_Possiveis_meio, Novas_Pals_Possiveis),
     Novas_Pals_Possiveis \== Pals_Possiveis, conta(Novas_Pals_Possiveis,_,Contador+1,Repeticoes).
 
 conta(_,_,Contador,Repeticoes):-
     Repeticoes is Contador +1.
 
 repete(0,_,_).
 
 % Repete o minimo numero de vezes preciso para a simplificacao acabar. 
 % Pode repetir uma vez a mais ou menos devido ao conta alterar ligeiramente o Pals_possiveis.
 repete(Repeticoes,Pals_Possiveis,Novas_Pals_Possiveis):-
     altera(Pals_Possiveis,Novas_Pals_Possiveis),N is Repeticoes - 1,
     repete(N,Novas_Pals_Possiveis,Novas_Pals_Possiveis),!.
     
 % Predicado que altera como o algoritmo diz.
 altera(Pals_Possiveis,Novas_Pals_Possiveis):-
     atribui_comuns(Pals_Possiveis),
     retira_impossiveis(Pals_Possiveis, Pals_Possiveis_meio),
     retira_unicas(Pals_Possiveis_meio, Novas_Pals_Possiveis).

%-------------------------------------------
% Inicializa o puzzle, e simplifica o maximo possivel
inicializa([Palavras|Grelha], Pals_Possiveis):-
    obtem_letras_palavras(Palavras, Letras),
    append(Grelha,Flat_Grelha),
    espacos_puzzle(Flat_Grelha, Espacos),
    palavras_possiveis(Letras, Espacos, Possiveis),
    simplifica(Possiveis, Pals_Possiveis).

%-------------------------------------------
% Utiliza um predicado auxiliar recursivo que devolve a lista de palavras
escolhe_menos_alternativas(Pals_Possiveis, Escolha):-
    escolhe_menos_alternativas_aux(Pals_Possiveis,_,Pals),!,
    espaco(Pals,Pals_Possiveis,Escolha),!,
    nth1(2,Escolha,Palavras),
    length(Palavras,L), !,L>1.

% Este predicado percorre Pals_Possiveis e vai atualizando a Aux (Com lista de palavras da escolha)
escolhe_menos_alternativas_aux([H|T],Aux,Pals):-
% Primeiro caso de todos adiciona o primeiro valor a auxiliar para poder comparar
var(Aux),nth1(2,H,Palavras),
escolhe_menos_alternativas_aux(T,Palavras,Pals)
        ;
compara(H,Aux),
nth1(2,H,Palavras),
escolhe_menos_alternativas_aux(T,Palavras,Pals)
        ;
escolhe_menos_alternativas_aux(T,Aux,Pals).

escolhe_menos_alternativas_aux([],Palavras,Pals):- 
append(Palavras,Flat_Palavras), 
length(Flat_Palavras,1),Pals = []
        ;
Pals = Palavras.

% Compara a Lista de Palavras com as Palavras_Atuais e 
% devolve true caso o length da Lista for menor que o das Palavras_atuais (ou for igual a um).
compara([_|Palavras],Palavras_Atuais):-
length(Palavras_Atuais,L2), L2 = 1
        ;
append(Palavras,Flat_Palavras),
length(Flat_Palavras,L1), !,L1>1,
length(Palavras_Atuais,L2), !,L1 < L2.

espaco(Pals,[H|_],H):-
    member(Pals,H).

espaco(Pals,[_|T],Escolha):-
    espaco(Pals,T,Escolha).
%-------------------------------------------------
% Devolve N respostas para os N membros de Lst_Pals, e utiliza um predicado auxiliar com flag.
experimenta_pal([Esp|Lst_Pals], Pals_Possiveis, Novas_Pals_Possiveis):-
    append(Lst_Pals,Flat_Lst_Pals),
    member(Pal,Flat_Lst_Pals),
    Esp = Pal,
    experimenta_pal_aux([Esp|Lst_Pals],Pal,0,Pals_Possiveis,Novas_Pals_Possiveis).

% Predicado adiciona recursivamente ao Novas_Pals_Possiveis. Caso a Flag esteja a 0
% e a palavra esteja na Lst_Pals atualiza esse espaco e a sua lista. 
experimenta_pal_aux([Esp|Lst_Pals],Pal,Flag,[H|T],[[Pal,[Pal]]|Novas_Pals_Possiveis]):-
    [Esp|Lst_Pals] == H,Flag == 0,
    experimenta_pal_aux([Esp|Lst_Pals],Pal,1,T,Novas_Pals_Possiveis),!.

% Caso contrario simplesmente adiciona elemento a elemento o que estava na lista
% Pals_Possiveis 
experimenta_pal_aux([Esp|Lst_Pals],Pal,Flag,[H|T],[H|Novas_Pals_Possiveis]):-
    experimenta_pal_aux([Esp|Lst_Pals],Pal,Flag,T,Novas_Pals_Possiveis),!.

experimenta_pal_aux(_,_,_,[],Novas_Pals_Possiveis):-
    Novas_Pals_Possiveis = [].

%--------------------------------------------------
% Predicado auxiliar resolve que resolve o puzzle, e dps retira se as impossiveis
% pois todos os espacos ja estao ocupados 
resolve_aux(Pals_Possiveis, Novas_Pals_Possiveis):-
    resolve(Pals_Possiveis, Pals_Possiveis_extra),!,
    retira_impossiveis(Pals_Possiveis_extra,Novas_Pals_Possiveis).

resolve(Pals_Possiveis, Novas_Pals_Possiveis):-
    experimenta(Pals_Possiveis, Aux1),
    simplifica(Aux1, Novas_Pals_Possiveis),
    resolve(Novas_Pals_Possiveis,_),!
            ;
    simplifica(Pals_Possiveis, Novas_Pals_Possiveis),!.

experimenta(Pals_Possiveis,Novas_Pals_Possiveis):-
    escolhe_menos_alternativas(Pals_Possiveis, Escolha),
    experimenta_pal(Escolha, Pals_Possiveis, Novas_Pals_Possiveis).
%----------------------------------------------
% Predicado dado.
escreve_Grelha(Grelha) :-
    maplist(escreve_Linha, Grelha).

escreve_Linha([]) :- nl, !.

escreve_Linha([P | R]) :-
    (var(P) -> write('- ')
            ;
     write(P), write(' ')),
     escreve_Linha(R).

% Inicializa o puzzle e resolve.
resolve(Puz):-
inicializa(Puz, Pals_Possiveis),resolve_aux(Pals_Possiveis, _).