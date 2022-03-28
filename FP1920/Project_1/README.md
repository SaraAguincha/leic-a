## Fundamentos da Programacao

```
Ano lectivo 2019-
Primeiro Projecto
17 de Outubro de 2019
```
# The Maze

Neste primeiro projecto de Fundamentos da Programacao os alunos ir ao desenvolver
as funcoes que permitam implementar um programa para simular o movimento de uma
unidade atraves de um labirinto 2D contendo obstaculos e outras unidades. Esta unidade
movimenta-se sempre para um espa co adjacente de forma a aproximar-se de uma das
unidades alcancaveis mais proximas restantes no labirinto.

## 1 Movimento num labirinto

### 1.1 O labirinto e as unidades

O labirinto e uma estrutura rectangular de tamanhoNxNy, sendoNxo tamanho
maximo do eixo de abcissas eNyo tamanho maximo do eixo de ordenadas. Cada posi c ao
(x, y) do labirinto e indexada a partir da posi c ao de origem (0,0) que corresponde ao
canto superior esquerdo do labirinto. Num labirinto, todas as posi c oes do limite exterior
s aoparedes, ou seja, correspondem a posicoes que n ao podem ser ocupadas. As restantes
posi c oes podem corresponder a paredes ou acorredores. Corredores (ou espa cos vazios)
s ao posi c oes pass iveis de serem ocupadas por unidades que se movimentam no labirinto.
Cada unidade apenas ocupa uma unica posi c ao no labirinto em cada instante de tempo.
O exemplo da Figura 1a) mostra um labirinto de tamanho 75, com paredes a toda a
volta, e com duas unidades situadas nas posicoes (2,1) e (4,3).
Aordem de leituradas posi c oes do labirinto e sempre feita da esquerda para a direita
seguida de cima para baixo. Assim sendo, a ordem de leitura das unidades existentes
no labirinto da Figura 1b) e a indicada pela numeracao do labirinto da Figura 1c).

```
a) b) c)
```
Figura 1: a) Labirinto com duas unidades situadas nas posicoes (2,1) e (4,3). As
posi c oes cinzentas correspondem a paredes enquanto que as brancas correspondem a
corredores. A posicao destacada a vermelho indica o origem das coordenadas. b, c)
Ordem de leitura das posicoes do labirinto.


```
a) Poss iveis objetivos b) Caminho m inimo c) Movimento
Figura 2: Processo de movimento da unidade situada na posicao (2,1).
```
### 1.2 Regras de movimento das unidades

O movimento de uma unidade e calculado atrav es da aplicacao das seguintes regras:

- Uma unidade movimenta-se dando passos. Umpasso e definido como sendo um
    unico movimento realizado para uma posicao adjacente. O conjunto deposi c oes
adjacentesa uma unidade e definido como sendo as posi c oes situadas imedi-
atamente acima, abaixo, `a direita ou `a esquerda dela. As unidades n ao podem
atravessar paredes nem outras unidades.
- Para escolher a posi c ao seguinte de uma dada unidade, come ca-se por identificar
    o conjunto deposs iveis posi c oes objetivo, sendo estas as posi c oes adjacentes
    livres de cada uma das restantes unidades. Se inicialmente a unidade j a se encontra
    adjacente a uma outra unidade, fica parada. Por exemplo, no labirinto da Figura
    2a) as posi c oes marcadas com o s imbolo?, s ao as poss iveis posi c oes objetivo da
    unidade destacada com fundo verde e situada na posi c ao (2,1).
- De seguida, determina-se o caminho de n umero m inimo de passos desde a
    unidade at e a posi c ao objetivo. A posi c ao objetivo e aquela (dentre as poss iveis
    posi c oes objetivo) que se encontra a n umero m inimo de passos. Se mais do que
    uma posi c ao objetivo estiver `a mesma distancia (n umero de passos) m inima, ent ao
    a posi c ao objetivo e a primeira seguindo aordem de leiturado labirinto. Se n ao
    existe nenhum caminho poss ivel entre a unidade e as poss iveis posi c oes objetivo, a
    unidade n ao se move. Por exemplo, no labirinto da Figura 2b) mostra-se a posi c ao
    objetivo assinalada com o s imboloX.
- Na processo de determina c ao do caminho de numero minimo de passos, se m ultiplas
    posicoes adjacentes colocam a unidade a exactamente a mesma distancia do obje-
    tivo (m ultiplos caminhos m inimos), ent ao a posi c ao seguinte escolhida e a primeira
    de acordo com aordem de leiturado labirinto. Por exemplo, no labirinto da
    Figura 2b) mostra-se o caminho de numero minimo de passos destacado a laranja.
- Finalmente, a unidade avan ca uma posicao seguindo o caminho de n umero m inimo
    de passos encontrado, como mostrado no labirinto da Figura 2c).


### 1.3 Procura do caminho de n umero m inimo de passos

Existem diversos algoritmos que permitem resolver o problema para a obtencao do cam-
inho de numero minimo de passos entre duas posicoes num labirinto. Uma possivel
abordagem e o algoritmo de Lee^1 que se baseia no Breadth-First Search (BFS)^2 , um
algoritmo para atravessar ou procurar em grafos.
O algoritmo BFS baseia-se em garantir a explora c ao, ou visita, em primeiro lugar
de todas as posi c oes ating iveis com igual n umero de passos, antes de passar a explorar
posi c oes ating iveis com um n umero de passos maior. Para isso, recorre a uma estru-
tura de dados linear conhecida comofila onde as novas posi c oes a serem exploradas
s ao acrescentadas no final da fila. Chamamos a esta estrutura afila de explora c ao.
Inicialmente, a fila de explora c ao cont em apenas a posi c ao inicial. De seguida, o algo-
ritmo processa em ciclo as posi c oes encontradas na fila de explora c ao retirando sempre
aquela que se encontra na primeira posi c ao at e atingir a condicao de terminacao (por
exemplo, a posi c ao retirada e a de destino) ou ate que a fila de exploracao se encontrar
vazia (neste caso, n ao se conseguiu atingir a condicao de terminacao). Para cada posi c ao
da fila de explora c ao, o ciclo de processamento come ca por verificar se a posi c ao j a foi
visitadapreviamente. Se sim, a posi c ao e retirada da fila e passa-se `a pr oxima posi c ao.
Se n ao, explora-se a posi c ao e assinala-se como visitada. Para que isto seja poss ivel, e
preciso guardar as posi c oes j a visitadas numa estrutura chamadaposi c oes visitadas.
A explora c ao de uma posi c ao consiste em acrescentar `a fila de explora c ao as posi c oes
adjacentes vazias. De forma a poder recuperar o caminho quando atinjamos a posi c ao
destino, e necess ario guardar asequencia de posi c oesadjacentes que nos levou at e
cada posi c ao explorada.
Neste primeiro projecto de FP, o objetivo e encontrar ocaminhode n umero m inimo
de passos desde uma posi c ao dada at e uma das poss iveis posi c oes objetivo, de acordo com
as regras de desempate e de movimento descritas na sec c ao anterior. O algoritmo BFS
garante que a primeira posi c ao visitada que corresponder a uma das poss iveis posi c oes
objetivo encontra-se a n umero m inimo de passos. Por outro lado, para garantir que se
cumprem as regras de desempate na escolha da posi c ao seguinte e da posi c ao objetivo,
basta que na fase de explora c ao sejam acrescentadas as posi c oes `a fila de explora c ao pela
ordem adequada (de menor a maior ordem de leitura). O pseudo-c odigo correspondente
e descrito no Algoritmo 1.

## 2 Trabalho a realizar

O objetivo do primeiro projecto e escrever um programa em Python, correspondendo `as
fun c oes descritas nesta sec c ao, que permita movimentar uma unidade num labirinto con-
forme descrito anteriormente. Para isso, dever a definir o conjunto de fun c oes solicitadas,
assim como eventualmente algumas fun c oes auxiliares adicionais.

(^1) https://en.wikipedia.org/wiki/Lee_algorithm
(^2) https://en.wikipedia.org/wiki/Breadth-first_search


```
Algoritmo 1:Caminho de n umero m inimo de passos
Entrada:Labirinto com unidades. Uma unidade
Sa ida :Caminho de n umero m inimo de passos
inicializa c ao estruturas;
obter objetivos poss iveis;
enquantoa lista de explora c ao n ao for vaziarepete:
posi c aoatual, caminhoatual[primeira posi c ao e caminho da lista de
explora c ao;
seposi c aoatual n ao foi visitada anteriormente:
atualiza posi c oes visitadas;
atualiza caminhoatual;
seposi c aoatual e um dos objetivos:
resultado[caminhoatual;
sair do ciclo;
se n ao
por cadaposi c aoadjacente `a posi c aoatualrepete:
seposi c aoadjacente e livre:
acrescenta posi c aoadjacente e caminhoatual `a lista de
explora c ao;
fim
fim
fim
fim
fim
```
### 2.1 Representa c ao do labirinto e das unidades

Considere que um labirinto e representado internamente (ou seja, no seu programa)
por um tuplo comNx tuplos, cada um deles contendoNy valores inteiros. Os val-
ores inteiros representam cada uma das posi c oes do labirinto podendo tomar valores
igual a 1 ou 0 dependendo se a posi c ao corresponder a uma parede ou n ao. As-
sim, sem ter em conta as unidades, o labirinto da Figura 1a) e definido pelo tuplo
((1,1,1,1,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),
(1,1,1,1,1)).
Considere tamb em que as posi c oes ocupadas pelas unidades presentes num labirinto s ao
representadas internamente por umconjunto de posi c oes correspondente a um tuplo
de posi c oes. Umaposi c ao e representada internamente como um tuplo de dois inteiros
correspondendo o primeiro valor `a posi c ao no eixo de abcissasxe o segundo valor `a
posi c ao no eixo de ordenadas y. Assim, o conjunto de posi c oes que corresponde `as
unidades presentes na Figura 1a) ser a dado pelo tuplo((2,1),(4,3)).
As fun c oes a escrever durante o projecto s ao as seguintes:


2.1.1 ehlabirinto: universalbooleano (2 valores)

Esta fun c ao recebe um argumento de qualquer tipo e devolveTruese o seu argumento
corresponde a um labirinto eFalsecaso contr ario, sem nunca gerar erros. Nesta parte do
projecto, considere que um labirinto corresponde a um tuplo contendoNxtuplos, cada
um deles contendoNyinteiros, com valores 0 ou 1. O labirinto de tamanho m inimo e de
dimens ao 33. Num labirinto, as posi c oes externas correspondem sempre a paredes.

```
>>> maze = ((1,1,1,1),(1,0,0,1),(1,0,0,1),(1,0,0,1),(1,1,1,1))
>>> eh_labirinto(maze)
True
>>> maze = ((1,1,1,1),(1,0,0,1),(1,0,0,1),(1,0,0,1),(1,1,1))
>>> eh_labirinto(maze)
False
>>> maze = ((0,0,0,0),(1,0,0,1),(1,0,0,1),(1,0,0,1),(1,1,1,1))
>>> eh_labirinto(maze)
False
```
2.1.2 ehposicao: universalbooleano (1 valores)

Esta fun c ao recebe um argumento de qualquer tipo e devolveTruese o seu argumento
corresponde a uma posi c ao eFalsecaso contr ario, sem nunca gerar erros. Nesta parte do
projecto, considere que uma posi c ao corresponde a um tuplo contendo 2 valores inteiros
n ao negativos correspondendo a uma posi c ao (x, y) num labirinto.

```
>>> eh_posicao((0,2))
True
>>> eh_posicao((1,-2))
False
>>> eh_posicao((1,1,2))
False
```
2.1.3 ehconjposicoes: universalbooleano (1 valores)

Esta fun c ao recebe um argumento de qualquer tipo e devolveTruese o seu argumento
corresponde a um conjunto de posi c oes unicas eFalsecaso contr ario, sem nunca gerar
erros. Nesta parte do projecto, considere que um conjunto de posi c oes corresponde a
um tuplo contendo 0 ou mais posi c oes diferentes.

```
>>> eh_conj_posicoes(((1,1),(2,2)))
True
>>> eh_conj_posicoes(((1,1),(-5,5)))
False
```

```
>>> eh_conj_posicoes(((1,1),(2,2),(2,2)))
False
```
2.1.4 tamanholabirinto: labirintotuplo (0.5 valores)

Esta fun c ao recebe um labirinto e devolve um tuplo de dois valores inteiros corre-
spondendo o primeiro deles `a dimens aoNx e o segundo `a dimens aoNy do labirinto.
Se o argumento dado for inv alido, a fun c ao deve gerar um erro com a mensagem
'tamanholabirinto: argumento invalido'.

```
>>> maze = ((1,1,1,1),(1,0,0,1),(1,0,0,1),(1,0,0,1),(1,1,1,1))
>>> tamanho_labirinto(maze)
(5, 4)
>>> maze = ((1,1,1,1),(1,0,0,1),(1,0,0,1),(1,0,0,1),(1,1,1))
>>> tamanho_labirinto(maze)
Traceback (most recent call last): <...>
ValueError: tamanho_labirinto: argumento invalido
```
2.1.5 ehmapavalido: labirintoconjposicoesbooleano
(1.5 valores)

Esta fun c ao recebe um labirinto e um conjunto de posi c oes correspondente `as unidades
presentes no labirinto, e devolveTruese o segundo argumento corresponde a um conjunto
de posi c oes compat iveis (n ao ocupadas por paredes) dentro do labirinto eFalsecaso
contr ario. Se algum dos argumentos dado for inv alido, a fun c ao deve gerar um erro com
a mensagem'ehmapavalido: algum dos argumentos e invalido'.

```
>>> maze = ((1,1,1,1),(1,0,0,1),(1,0,0,1),(1,0,0,1),(1,1,1,1))
>>> eh_mapa_valido(maze, ((1,1),(2,2)))
True
>>> eh_mapa_valido(maze, ((1,1),(5,5)))
False
>>> eh_mapa_valido(maze, ((1,1),(-5,5)))
Traceback (most recent call last): <...>
ValueError: eh_mapa_valido: algum dos argumentos e
invalido
```
2.1.6 ehposicaolivre: labirintoconjposicoesposicaobooleano
(1 valor)

Esta fun c ao recebe um labirinto, um conjunto de posi c oes correspondente a unidades
presentes no labirinto e uma posi c ao, e devolveTruese a posi c ao corresponde a uma


posi c ao livre (n ao ocupada nem por paredes, nem por unidades) dentro do labirinto e
Falsecaso contr ario. Se algum dos argumentos dado for inv alido, a fun c ao deve gerar um
erro com a mensagem'ehposicaolivre: algum dos argumentos e invalido'.

```
>>> maze = ((1,1,1,1,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),
(1,0,0,0,1),(1,0,0,0,1),(1,1,1,1,1))
>>> unidades = ((2,1),(4,3))
>>> eh_posicao_livre(maze, unidades, (2,2)
True
>>> eh_posicao_livre(maze, unidades, (2,0))
False
>>> eh_posicao_livre(maze, unidades, (4,3))
False
>>> eh_posicao_livre(maze, unidades, (2,-1))
Traceback (most recent call last): <...>
ValueError: eh_posicao_livre: algum dos argumentos e invalido
>>> unidades = ((2,0), (4,3))
>>> eh_posicao_livre(maze, unidades, (2,2))
Traceback (most recent call last): <...>
ValueError: eh_posicao_livre: algum dos argumentos e invalido
```
2.1.7 posicoesadjacentes: posicaoconjposicoes (1 valor)

Esta fun c ao recebe uma posi c ao e devolve o conjunto de posi c oes adjacentes da posi c ao
em ordem de leitura de um labirinto. Se o argumento dado for inv alido, a fun c ao deve
gerar um erro com a mensagem'posicoesadjacentes: argumento invalido'.

```
>>> posicoes_adjacentes((2,1))
((2, 0), (1, 1), (3, 1), (2, 2))
>>> posicoes_adjacentes((3,2))
((3, 1), (2, 2), (4, 2), (3, 3))
>>> posicoes_adjacentes((0,0))
((1, 0), (0, 1))
>>> posicoes_adjacentes((-1,2))
Traceback (most recent call last): <...>
ValueError: posicoes_adjacentes: argumento invalido
```
2.1.8 mapastr: labirintoconjposicoescad. carateres (2 valores)

Esta fun c ao recebe um labirinto e um conjunto de posi c oes correspondente `as unidades
presentes no labirinto e devolve a cadeia de caracteres que as representa (a representa c ao
externa ou representa c ao "para os nossos olhos"), de acordo com o exemplo na seguinte
interac c ao. Se algum dos argumentos dado for inv alido, a fun c ao deve gerar um erro
com a mensagem'mapastr: algum dos argumentos e invalido'.


```
>>> maze = ((1,1,1,1,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),
(1,0,0,0,1),(1,0,0,0,1),(1,1,1,1,1))
>>> unidades = ((2,1),(4,3))
>>> mapa_str(maze, unidades)
'#######\n#.O...#\n#.....#\n#...O.#\n#######'
>>> print(mapa_str(maze, unidades))
#######
#.O...#
#.....#
#...O.#
#######
>>> mapa_str(maze, 3)
Traceback (most recent call last): <...>
ValueError: mapa_str: algum dos argumentos e invalido
>>> unidades = ((2, 0), (4, 3))
>>> mapa_str(maze, unidades)
Traceback (most recent call last): <...>
ValueError: mapa_str: algum dos argumentos e invalido
```
### 2.2 Fun c oes de movimento

2.2.1 obterobjetivos: labirinto conjposicoes posicao  conjposicoes
(2 valores)

Esta fun c ao recebe um labirinto, um conjunto de posi c oes correspondente `as unidades
presentes no labirinto e uma posi c ao correspondente a uma das unidades, e devolve o
conjunto de posi c oes (em qualquer ordem) n ao ocupadas dentro do labirinto correspon-
dente a todos os poss iveis objetivos da unidade correspondente `a posi c ao dada. Isto e, as
posi c oes livres dentro do labirinto adjacentes `as restantes unidades como representado
na Figura 2a). A sua fun c ao deve utilizar as fun c oes desenvolvidas nas sec c oes anteriores
sempre que poss ivel. Se algum dos argumentos dado for inv alido, a fun c ao deve gerar um
erro com a mensagem'obterobjetivos: algum dos argumentos e invalido'.

```
>>> maze = ((1,1,1,1,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),
(1,0,0,0,1),(1,0,0,0,1),(1,1,1,1,1))
>>> unidades = ((2,1),(4,3))
>>> obter_objetivos(maze, unidades, (2,1))
((4, 2), (3, 3), (5, 3))
>>> obter_objetivos(maze, unidades[:1], (2,1))
()
>>> unidades = ((2,1),(5,2),(4,3))
>>> obter_objetivos(maze, unidades, (2,1))
((5, 1), (4, 2), (5, 3), (3, 3))
```

```
>>> unidades = ((4,2),(4,3))
>>> obter_objetivos(maze, unidades, (4,2))
((3, 3), (5, 3))
>>> obter_objetivos(maze, unidades, (2,2))
Traceback (most recent call last): <...>
ValueError: obter_objetivos: algum dos argumentos e invalido
```
2.2.2 obtercaminho: labirintoconjposicoesposicaoconjposicoes
(2 valores)

Esta fun c ao recebe um labirinto, um conjunto de posi c oes correspondente `as unidades
presentes no labirinto, e uma posi c ao correspondente a uma das unidades, e devolve um
conjunto de posi c oes (potencialmente vazio caso n ao exista nenhuma unidade alcan c avel)
correspondente ao caminho de n umero m inimo de passos desde a posi c ao dada at e `a
posi c ao objetivo (ou seja, a posi c ao mais pr oxima de acordo com a ordem de leitura do
labirinto que se encontra ao n umero m inimo de passos). Um caminho e um conjunto
de posi c oes adjacentes, como o representado na Figura 2b), que come ca na posi c ao da
unidade dada e termina na posi c ao objetivo. Para encontrar o caminho deve concretizar
o Algoritmo 1 descrito na sec c ao 1.3. A sua fun c ao deve utilizar as fun c oes desenvolvidas
nas sec c oes anteriores sempre que poss ivel. Se algum dos argumentos dado for inv alido, a
fun c ao deve gerar um erro com a mensagem'obtercaminho: algum dos argumentos
e invalido'.

```
>>> maze = ((1,1,1,1,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),
(1,0,0,0,1),(1,0,0,0,1),(1,1,1,1,1))
>>> unidades = ((2,1),(4,3))
>>> obter_caminho(maze, unidades, (2,1))
((2, 1), (3, 1), (4, 1), (4, 2))
>>> obter_caminho(maze, unidades[:1], (2,1))
()
>>> unidades = ((2,1),(5,2),(4,3))
>>> obter_caminho(maze, unidades, (2,1))
((2, 1), (3, 1), (4, 1), (5, 1))
>>> obter_caminho(maze, unidades, (2,2))
Traceback (most recent call last): <...>
ValueError: obter_caminho: algum dos argumentos e invalido
```
2.2.3 moverunidade: labirinto conjposicoes posicao  conjposicoes
(2 valores)

Esta fun c ao recebe um labirinto, um conjunto de posi c oes correspondente `as unidades
presentes no labirinto, e uma posi c ao correspondente a uma das unidades, e devolve
o conjunto de posi c oes actualizado correspondente `as unidades presentes no labirinto


ap os a unidade dada ter realizado um unico movimento. A ordem das unidades deve
ser mantida, isto e, o conjunto de posi c oes de sa ida deve ser identico ao de entrada,
com a excep c ao da posi c ao correspondente `a unidade dada, se esta se mover. Como
descrito na sec c ao 1.2, a unidade n ao se move se j a se encontrar adjacente a uma outra
unidade ou se n ao existir nenhuma unidade ating ivel. Caso contr ario, a unidade avan ca
um passo no caminho de n umero m inimo de passos como representado na Figura 2c).
A sua fun c ao deve utilizar as fun c oes desenvolvidas nas sec c oes anteriores sempre que
poss ivel. Se algum dos argumentos dado for inv alido, a fun c ao deve gerar um erro com
a mensagem'moverunidade: algum dos argumentos e invalido'.

```
>>> maze = ((1,1,1,1,1),(1,0,0,0,1),(1,0,0,0,1),(1,0,0,0,1),
(1,0,0,0,1),(1,0,0,0,1),(1,1,1,1,1))
>>> unidades = ((2,1),(4,3))
>>> print(mapa_str(maze, unidades))
#######
#.O...#
#.....#
#...O.#
#######
>>> unidades = mover_unidade(maze, unidades, unidades[0])
>>> print(mapa_str(maze, unidades))
#######
#..O..#
#.....#
#...O.#
#######
>>> unidades = mover_unidade(maze, unidades, unidades[1])
>>> print(mapa_str(maze, unidades))
#######
#..O..#
#...O.#
#.....#
#######
```

```
>>> unidades = mover_unidade(maze, unidades, unidades[0])
>>> print(mapa_str(maze, unidades))
#######
#...O.#
#...O.#
#.....#
#######
>>> unidades = mover_unidade(maze, unidades, unidades[1])
>>> print(mapa_str(maze, unidades))
#######
#...O.#
#...O.#
#.....#
#######
>>> mover_unidade(maze, unidades, (2,2))
Traceback (most recent call last): <...>
ValueError: mover_unidade: algum dos argumentos e invalido
```
## 3 Condi c oes de Realiza c ao e Prazos

- A entrega do 1oprojecto ser a efetuada exclusivamente por via eletr onica. Dever a
    submeter o seu projecto atrav es do sistema Mooshak, at e `as17:00 do dia 31 de
    Outubro de 2019. Depois desta hora, n ao ser ao aceites projectos sob pretexto
    algum.
- Dever a submeter um unico ficheiro com extens ao.pycontendo todo o c odigo do seu
    projecto. O ficheiro de c odigo dever a conter em coment ario, na primeira
    linha, o n umero e o nome do aluno.
- No seu ficheiro de c odigo n ao devem ser utilizados caracteres acentuados ou qual-
    quer outro car acter n ao pertencente `a tabela ASCII. Todos os testes autom aticos
    falhar ao, mesmo que os caracteres n ao ASCII sejam utilizados dentro de co-
    ment arios ou cadeias de caracteres. Programas que n ao cumpram este requisito
    ser ao penalizados em tres valores.
- N ao e permitida a utiliza c ao de qualquer m odulo ou fun c ao n ao dispon ivel built-in
    no Python 3.
- Pode, ou n ao, haver uma discuss ao oral do trabalho e/ou uma demonstra c ao do
    funcionamento do programa (ser a decidido caso a caso).
- Lembre-se que no T ecnico, a fraude acad emica e levada muito a s erio e que a c opia
    numa prova (projectos inclu idos) leva `a reprova c ao na disciplina. O corpo docente
    da cadeira ser a o unico juiz do que se considera ou n ao copiar num projecto.


## 4 Submiss ao

A submiss ao e avalia c ao da execu c ao do projecto de FP e feita utilizando o sistema
Mooshak^3. Para obter as necess arias credenciais de acesso e poder usar o sistema
dever a:

- Obter uma password para acesso ao sistema, seguindo as instru c oes na p agina:
    [http://acm.tecnico.ulisboa.pt/~fpshak/cgi-bin/getpass.](http://acm.tecnico.ulisboa.pt/~fpshak/cgi-bin/getpass.) A password ser-
    lhe- a enviada para o email que tem configurado no Fenix. Se a password n ao lhe
    chegar de imediato, aguarde.
- Ap os ter recebido a sua password por email, deve efetuar o login no sistema atrav es
    da p agina: [http://acm.tecnico.ulisboa.pt/~fpshak/.](http://acm.tecnico.ulisboa.pt/~fpshak/.) Preencha os campos
    com a informa c ao fornecida no email.
- Utilize o bot ao"Browse...", selecione o ficheiro com extens ao.py contendo todo
    o c odigo do seu projecto. O seu ficheiro .py deve conter a implementa c ao das
    fun c oes pedidas no enunciado. De seguida clique no bot ao"Submit"para efetuar
    a submiss ao.
    Aguarde (20-30 seg) para que o sistema processe a sua submiss ao!!!
- Quando a submiss ao tiver sido processada, poder a visualizar na tabela o resul-
    tado correspondente. Receber a no seu email um relat orio de execu c ao com os
    detalhes da avalia c ao autom atica do seu projecto podendo ver o n umero de testes
    passados/falhados.
- Para sair do sistema utilize o bot ao"Logout".

Submeta o seu projecto atempadamente, dado que as restri c oes seguintes podem n ao
lhe permitir faze-lo no ultimo momento:

- S o poder a efetuar uma nova submiss ao pelo menos 15 minutos depois da submiss ao
    anterior.
- S o s ao permitidas 10 submiss oes em simultaneo no sistema, pelo que uma sub-
    miss ao poder a ser recusada se este limite for excedido^4.
- N ao pode ter submiss oes duplicadas, ou seja submiss ao igual `a anterior.
- Ser a considerada para avalia c ao a ultima submiss ao (mesmo que tenha pontua c ao
    inferior a submiss oes anteriores). Dever a, portanto, verificar cuidadosamente que
    a ultima entrega realizada corresponde `a vers ao do projecto que pretende que seja
    avaliada. N ao h a excep c oes!

(^3) A vers ao de Python utilizada nos testes autom aticos e Python 3.5.3.
(^4) Note que o limite de 10 submiss oes simultaneas no sistema Mooshak implica que, caso haja um
n umero elevado de tentativas de submiss ao sobre o prazo de entrega, alguns alunos poder ao n ao con-
seguir submeter nessa altura e verem-se, por isso, impossibilitados de submeter o c odigo dentro do
prazo.


- Cada aluno tem direito a15 submiss oes sem penaliza c aono Mooshak. Por
    cada submiss ao adicional ser ao descontados 0.1 valores na componente de avalia c ao
    autom atica.

## 5 Classifica c ao

A nota do projecto ser a baseada nos seguintes aspetos:

1. Avalia c ao autom atica (80%). A avalia c ao da correcta execu c ao ser a feita
    atrav es do sistema Mooshak. O tempo de execu c ao de cada teste est a limitado,
    bem como a mem oria utilizada.
    Existem 177 casos de teste configurados no sistema: 40 testes p ublicos (disponibi-
    lizados na p agina da disciplina) valendo 0 pontos cada e 137 testes privados (n ao
    disponibilizados). Como a avalia c ao autom atica vale 80% (equivalente a 16 val-
    ores) da nota, uma submiss ao obt em a nota m axima de 1600 pontos.
    O facto de um projecto completar com sucesso os testes p ublicos fornecidos n ao im-
    plica que esse projecto esteja totalmente correto, pois estes n ao s ao exaustivos.E 
    da responsabilidade de cada aluno garantir que o c odigo produzido est a de acordo
    com a especifica c ao do enunciado, para completar com sucesso os testes privados.
2. Avalia c ao manual (20%). Estilo de programa c ao e facilidade de leitura^5. Em
    particular, ser ao consideradas as seguintes componentes:
       - Boas pr aticas (1.5 valores): ser ao considerados entre outros a clareza do
          c odigo, a integra c ao de conhecimento adquirido durante a UC e a criativi-
          dade das solu c oes propostas.
       - Coment arios (1 valor): dever ao incluir a assinatura das fun c oes definidas,
          coment arios para o utilizador (docstring) e coment arios para o programador.
       - Tamanho de fun c oes, duplica c ao de c odigo e abstra c ao procedimental (1 valor)
       - Escolha de nomes (0.5 valores).

## 6 Recomenda c oes e aspetos a evitar

As seguintes recomenda c oes e aspetos correspondem a sugest oes para evitar maus h abitos
de trabalho (e, consequentemente, m as notas no projecto):

- Leia todo o enunciado, procurando perceber o objetivo das v arias fun c oes pedidas.
    Em caso de d uvida de interpreta c ao, utilize o hor ario de d uvidas para esclarecer
    as suas quest oes.

(^5) Podem encontrar algumas boas pr aticas relacionadas em https://gist.github.com/
ruimaranhao/4e18cbe3dad6f68040c32ed6709090a


- No processo de desenvolvimento do projecto, comece por implementar as v arias
    fun c oes pela ordem apresentada no enunciado, seguindo as metodologias estudadas
    na disciplina. Ao desenvolver cada uma das fun c oes pedidas, comece por perceber
    se pode usar alguma das anteriores.
- Para verificar a funcionalidade das suas fun c oes, utilize os exemplos fornecidos
    como casos de teste. Tenha o cuidado de reproduzir fielmente as mensagens de
    erro e restantesoutputs, conforme ilustrado nos v arios exemplos.
- N ao pense que o projecto se pode fazer nos ultimos dias. Se apenas iniciar o
    seu trabalho neste per iodo ir a ver a Lei de Murphy em funcionamento (todos os
    problemas s ao mais dif iceis do que parecem; tudo demora mais tempo do que n os
    pensamos; e se alguma coisa puder correr mal, ela vai correr mal, na pior das
    alturas poss iveis);
- N ao duplique c odigo. Se duas fun c oes s ao muito semelhantes e natural que estas
    possam ser fundidas numa unica, eventualmente com mais argumentos;
- N ao se esque ca que as fun c oes excessivamente grandes s ao penalizadas no que
    respeita ao estilo de programa c ao;
- A atitude "vou por agora o programa a correr de qualquer maneira e depois
    preocupo-me com o estilo" e totalmente errada;
- Quando o programa gerar um erro, preocupe-se em descobrir qual a causa do erro.
    As "marteladas" no c odigo tem o efeito de distorcer cada vez mais o c odigo.


