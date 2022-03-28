## Fundamentos da Programacao

```
Ano lectivo 2019-
Segundo Projecto
22 de Novembro de 2019
```
# Tecnico Battle Simulator

O segundo projecto de Fundamentos da Programa c ao consiste em escrever um programa
em Python de simula c ao de batalhas entre dois exercitos formados por unidades que se
movimentam e atacam dentro de um labirinto 2D, podendo conter obst aculos.

## 1 Simula c ao de batalhas

### 1.1 Labirinto, unidades e exercitos

Olabirinto e definido de forma identica a do primeiro projeto, ou seja, e uma estrutura
rectangular com posi c oes indexadas a partir do canto superior esquerdo do labirinto, com
paredesnas posi c oes do limite exterior, onde as restantes posi c oes podem corresponder
a paredes ou acorredores. Os corredores podem ou n ao ser ocupados porunidades.
Neste segundo projeto, cada unidade pertence a um de dois poss iveisex ercitos. As
unidades, para al em de se movimentarem no labirinto, atacam as unidades do ex ercito
contr ario. Cada unidade, para al em da posi c ao no labirinto e o seu exercito, e carateri-
zada pelos seuspontos de vidae pela suafor ca de ataque.
Aordem de leituradas posicoes do labirinto e definida como no primeiro projeto:
da esquerda para a direita seguida de cima para baixo.

### 1.2 Turno, movimento e ataque das unidades

A simula c ao de uma batalha consiste na execu c ao de m ultiplosturnos de batalha, at e
que um dos dois ex ercitos ganhe, isto e, at e todas as unidades de um ex ercito terem sido
eliminadas, ou ate nao existirem mais movimentos poss iveis. Em cada turno de batalha,
cada unidade -seguindo a ordem de leitura do labirinto- realiza um movimento e um
ataque.
Omovimentoconsiste num unico passo para uma posi c ao adjacente seguindo as
regras de movimento das unidades descritas no primeiro projeto, com a unica diferen ca de
que apenas s ao consideradas comoposs iveis posi c oes objetivoas posi c oes adjacentes
livres de cada uma das unidadesinimigas (isto e, do ex ercito contr ario). Tal como no
primeiro projeto, uma unidade fica parada se j a se encontra numa posi c ao adjacente a
um inimigo ou se n ao existir nenhuma posi c ao objetivo alcan c avel.
Ap os ter completado um movimento, se a unidade se encontra adjacente a pelo menos
uma unidade inimiga, ent ao realiza umataque. Se existir mais de uma unidade inimiga
adjacente, o alvo do ataque e a primeira de acordo com a ordem de leitura do mapa.
O efeito de um ataque consiste em subtrair os pontos de for ca de ataque da unidade
atacante aos pontos de vida da unidade atacada. Uma unidade atacada que fique sem
pontos de vida e eliminada, deixando de existir no turno de batalha corrente bem como
nos subsequentes.


## 2 Trabalho a realizar

O objetivo deste segundo projecto e definir um conjunto de Tipos Abstratos de Dados
(TAD) que dever ao ser utilizados para representar a informa c ao necess aria, bem como
um conjunto de fun c oes adicionais que permitir ao executar corretamente o simulador de
batalhas.

### 2.1 Tipos Abstratos de Dados

Aten c ao:

- Apenas os construtores e as fun c oes para as quais a verifica c ao da corre c ao dos
    argumentos e explicitamente pedida devem verificar a validade dos argumentos.
- Os modificadores, e as fun c oes de alto n ivel que os utilizam, alteram de modo
    destrutivo o seu argumento.
- Todas as fun c oes de alto n ivel (ou seja, que n ao correspondem a opera c oes b asicas)
    devem respeitar as barreiras de abstra c ao.

2.1.1 TADposicao (1.5 valores)

O TADposicao e usado para representar uma posi c ao (x, y) de um labirinto arbitrari-
amente grande, sendoxeydois valores inteiros n ao negativos. As opera c oes b asicas
associadas a este TAD s ao:

- Construtor
    - criaposicao: N^2 7posicao
       criaposicao(x,y)recebe os valores correspondentes `as coordenadas de uma
       posi c ao e devolve a posi c ao correspondente. O construtor verifica a validade
       dos seus argumentos, gerando umValueErrorcom a mensagem'criaposicao:
       argumentos invalidos'caso os seus argumentos n ao sejam v alidos.
    - criacopiaposicao: posicao7posicao
       criacopiaposicao(p)recebe uma posi c ao e devolve uma c opia nova da posi c ao.
- Seletores
    - obterposx: posicao7N
       obterposx(p)devolve a componentexda posi c aop.
    - obterposy: posicao7N
       obterposy(p)devolve a componenteyda posi c aop.
- Reconhecedor
    - ehposicao:universal7booleano
       ehposicao(arg)devolveTruecaso o seu argumento seja um TADposicao e
       Falsecaso contr ario.


- Teste
    - posicoesiguais:posicao posicao7booleano
       posicoesiguais(p1, p2)devolveTrueapenas sep1 ep2 s ao posi c oes iguais.
- Transformador
    - posicaoparastr:posicao7str
       posicaoparastr(p)devolve a cadeia de caracteres'(x, y)'que representa o
       seu argumento, sendo os valoresxeyas coordenadas dep.

As fun c oes de alto n ivelassociadas a este TAD s ao:

- obterposicoesadjacentes:posicao7tuplo de posicoes
    obterposicoesadjacentes(p)devolve umtuplocom as posi c oes adjacentes `a posi c ao
    pde acordo com a ordem de leitura de um labirinto.

Exemplos de interac c ao:

```
>>> p1 = cria_posicao(-1, 2)
Traceback (most recent call last): <...>
ValueError: cria_posicao: argumentos invalidos
>>> p1 = cria_posicao(2, 3)
>>> p2 = cria_posicao(7, 0)
>>> posicoes_iguais(p1, p2)
False
>>> posicao_para_str(p1)
'(2, 3)'
>>> tuple(posicao_para_str(p) for p in obter_posicoes_adjacentes(p2))
('(6, 0)', '(8, 0)', '(7, 1)')
```
2.1.2 TADunidade (3 valores)

O TAD unidade e usado para representar as unidades de combate no simulador de
batalhas presentes num labirinto. Cada unidade e caracterizada pela sua posi c ao, for ca
de ataque, pontos de vida e ex ercito. A for ca de ataque e os pontos de vida s ao
valores inteiros positivos e o ex ercito e qualquer cadeia de caracteres n ao vazia. As
opera c oes b asicasassociadas a este tipo s ao:

- Construtor
    - criaunidade:posicaoNNstr7unidade
       criaunidade(p, v, f, str)recebe umaposicaop, dois valores inteiros maiores
       que 0 correspondentes `a vida e for ca da unidade, e uma cadeia de caracteres
       n ao vazia correspondente ao ex ercito da unidade; e devolve aunidadecorre-
       spondente. O construtor verifica a validade dos seus argumentos, gerando um
       ValueError com a mensagem'criaunidade: argumentos invalidos'
       caso os seus argumentos n ao sejam v alidos.


- criacopiaunidade:unidade7unidade
    criacopiaunidade(u)recebe uma unidadeue devolve uma nova c opia da
    unidade.
- Seletores
- obterposicao: unidade7posicao
obterposicao(u)devolve aposi c aodaunidadeu.
- obterexercito:unidade7str
obterexercito(u)devolve a cadeia de carateres correspondente ao ex ercito da
unidade.
- obterforca:unidade7N
obterforca(u)devolve o valor corresponde `a for ca de ataque daunidade.
- obtervida:unidade7N
obtervida(u)devolve o valor corresponde aos pontos de vida daunidade.
- Modificadores
- mudaposicao: unidadeposicao7unidade
mudaposicao(u, p) modifica destrutivamente a unidade u alterando a sua
posi c ao com o novo valorp, e devolve a pr opriaunidade.
- removevida: unidadeN7unidade
removevida(u, v) modifica destrutivamente a unidade u alterando os seus
pontos de vida subtraindo o valorv, e devolve a pr opriaunidade.
- Reconhecedor
- ehunidade: universal7booleano
ehunidade(arg)devolveTruecaso o seu argumento seja um TADunidadee
Falsecaso contr ario.
- Teste
- unidadesiguais:unidadeunidade7booleano
unidadesiguais(u1, u2)devolveTrueapenas seu1 eu2 s ao unidades iguais.
- Transformadores
- unidadeparachar:unidade7str
unidadeparachar(u) devolve a cadeia de caracteres dum unico elemento,
correspondente ao primeiro car acter em mai uscula do ex ercito daunidade
passada por argumento.
- unidadeparastr:unidade7str
unidadeparastr(u)devolve a cadeia de caracteres que representa aunidade
como mostrado nos exemplos a seguir.


As fun c oes de alto n ivelassociadas a este TAD s ao:

- unidadeataca:unidadeunidade7booleano
    unidadeataca(u1, u2)modifica destrutivamente a unidadeu2 retirando o valor de
    pontos de vida correspondente `a for ca de ataque da unidadeu1. A fun c ao devolve
    Truese a unidadeu2 for destru ida ouFalsecaso contr ario.
- ordenarunidades: tuplo unidades7tuplo unidades
    ordenarunidades(t) devolve um tuplo contendo as mesmas unidades do tuplo
    fornecido como argumento, ordenadas de acordo com a ordem de leitura do labir-
    into.

Exemplos de interac c ao:

```
>>> p = cria_posicao(2, 3)
>>> u1 = cria_unidade(p, 30, -5, 'elfos')
Traceback (most recent call last): <...>
ValueError: cria_unidade: argumentos invalidos
>>> u1 = cria_unidade(p, 30, 4, 'elfos')
>>> unidade_para_str(u1)
'E[30, 4]@(2, 3)'
>>> unidade_para_char(u1)
'E'
>>> u2 = cria_copia_unidade(u1)
>>> unidades_iguais(u1, u2)
True
>>> u1 = muda_posicao(u1, cria_posicao(3, 3))
>>> unidade_para_str(u1)
'E[30, 4]@(3, 3)'
>>> unidade_para_str(u2)
'E[30, 4]@(2, 3)'
>>> unidades_iguais(u1, u2)
False
>>> tuple(unidade_para_str(u) for u in ordenar_unidades((u1, u2)))
('E[30, 4]@(2, 3)', 'E[30, 4]@(3, 3)')
>>> u2 = remove_vida(u2, 25)
>>> unidade_ataca(u1, u2)
False
>>> unidade_para_str(u2)
'E[1, 4]@(2, 3)'
>>> unidade_ataca(u1, u2)
True
```

2.1.3 TADmapa (4 valores)

O TADmapa e usado para representar um labirinto e as unidades que se encontram
dentro do labirinto. As opera c oes b asicasassociadas a este TAD s ao:

- Construtor
    - criamapa: tuplotuplotuplotuplo7mapa
       criamapa(d, w, e1, e2) recebe um tuplodde 2 valores inteiros correspon-
       dentes `as dimens oesNxeNydo labirinto seguindo as restri c oes do 1projecto,
       um tuplowde 0 ou mais posi c oes correspondentes `as paredes que n ao s ao
       dos limites exteriores do labirinto, um tuploe1 de 1 ou mais unidades do
       mesmo ex ercito, e um tuploe2 de um ou mais unidades de um outro ex ercito;
       e devolve o mapa que representa internamente o labirinto e as unidades pre-
       sentes. O construtor verifica a validade dos seus argumentos, gerando um
       ValueErrorcom a mensagem'criamapa: argumentos invalidos'caso
       os seus argumentos n ao sejam v alidos.
       Nota: pode assumir que os dois tuplos representam ex ercitos distintos, que
       cada tuplo s o cont em unidades do mesmo ex ercito e que as unidades dos dois
       ex ercitos est ao todas em posi c oes distintas e livres dentro do labirinto.
    - criacopiamapa:mapa7mapa
       criacopiamapa(m)recebe ummapae devolve uma nova c opia domapa.
- Seletores
    - obtertamanho:mapa7tuplo
       obtertamanho(m)devolve um tuplo de dois valores inteiros correspondendo
       o primeiro deles `a dimens aoNxe o segundo `a dimens aoNydo mapa.
    - obternomeexercitos:mapa7tuplo
       obternomeexercitos(m) devolve um tuplo ordenado com duas cadeias de
       caracteres correspondendo aos nomes dos ex ercitos do mapa.
    - obterunidadesexercito:mapastr7tuplo unidades
       obterunidadesexercito(m, e) devolve um tuplo contendo as unidades do
       mapa pertencentes ao ex ercito indicado pela cadeia de caracteres e, orde-
       nadas em ordem de leitura do labirinto.
    - obtertodasunidades:mapa7tuplo
       obtertodasunidades(m) devolve um tuplo contendo todas as unidades do
       mapa, ordenadas em ordem de leitura do labirinto.
    - obterunidade:mapaposicao7unidade
       obterunidade(m, p)devolve a unidade do mapa que se encontra na posi c ao
       p.


- Modificadores
    - eliminarunidade:mapaunidade7mapa
       eliminarunidade(m, u) modifica destrutivamente o mapam eliminando a
       unidadeudo mapa e deixando livre a posi c ao onde se encontrava a unidade.
       Devolve o pr oprio mapa.
    - moverunidade: mapaunidadeposicao7mapa
       moverunidade(m, u, p)modifica destrutivamente o mapame a unidadeu
       alterando a posi c ao da unidade no mapa para a nova posi c aop e deixando
       livre a posi c ao onde se encontrava. Devolve o pr oprio mapa.
- Reconhecedores
    - ehposicaounidade: mapaposicao 7booleano
       ehposicaounidade(m, p)devolveTrueapenas no caso da posi c aopdo mapa
       estar ocupada por uma unidade.
    - ehposicaocorredor:mapaposicao7booleano
       ehposicaocorredor(m, p)devolveTrueapenas no caso da posi c aopdo mapa
       corresponder a um corredor no labirinto (independentemente de estar ou n ao
       ocupado por uma unidade).
    - ehposicaoparede: mapaposicao7booleano
       ehposicaoparede(m, p)devolveTrueapenas no caso da posi c aopdo mapa
       corresponder a uma parede do labirinto.
- Teste
    - mapasiguais:mapamapa7booleano
       mapasiguais(m1, m2)devolveTrueapenas sem1 em2 forem mapas iguais.
- Transformador
    - mapaparastr: mapa7str
       mapaparastr(u) devolve uma cadeia de caracteres que representa o mapa
       como descrito no primeiro projeto, neste caso, com as unidades representadas
       pela sua representa c ao externa.

As fun c oes de alto n ivelassociadas a este TAD s ao:

- obterinimigosadjacentes:mapaunidade7tuplo unidades
    obterinimigosadjacentes(m, u)devolve um tuplo contendo as unidades inimigas
    adjacentes `a unidadeude acordo com a ordem de leitura do labirinto.
- obtermovimento:mapaunidade7posicao
    obtermovimento(m, u)devolve a posi c ao seguinte da unidadeude acordo com as
    regras de movimento das unidades no labirinto. Esta fun c ao e fornecida pelo
    corpo docente, e deve ser copiada para dentro do vosso programa.


Exemplos de interac c ao:

```
>>> d = (7, 5)
>>> w = (cria_posicao(4,2), cria_posicao(5,2))
>>> e1 = tuple(cria_unidade(cria_posicao(p[0], p[1]), 20, 4, 'humans')
for p in ((3, 2),(1, 1)))
>>> e2 = tuple(cria_unidade(cria_posicao(p[0], p[1]), 10, 2, 'cylons')
for p in ((3, 1), (1, 3), (3, 3), (5, 3)))
>>> m1 = cria_mapa(d, w, e1, e2)
>>> print(mapa_para_str(m1))
#######
#H.C..#
#..H###
#C.C.C#
#######
>>> obter_nome_exercitos(m1)
('cylons', 'humans')
>>> u1 = obter_unidade(m1, cria_posicao(1,1))
>>> unidade_para_str(u1)
'H[20, 4]@(1, 1)'
>>> tuple(unidade_para_str(u) for u in \
obter_unidades_exercito(m1, 'humans'))
('H[20, 4]@(1, 1)', 'H[20, 4]@(3, 2)')
>>> print(mapa_para_str(mover_unidade(m1, u1, cria_posicao(2,1))))
#######
#.HC..#
#..H###
#C.C.C#
#######
>>> u2 = obter_unidade(m1, cria_posicao(5,3))
>>> print(mapa_para_str(eliminar_unidade(m1, u2)))
#######
#.HC..#
#..H###
#C.C..#
#######
>>> u3 = obter_unidade(m1, cria_posicao(3,2))
>>> tuple(unidade_para_str(u) for u in obter_inimigos_adjacentes(m1,u3))
('C[10, 2]@(3, 1)', 'C[10, 2]@(3, 3)')
>>> obter_movimento(m1, u3)
(3, 2)
>>> u4 = obter_unidade(m1, cria_posicao(1,3))
>>> obter_movimento(m1, u4)
(1, 2)
```

### 2.2 Fun c oes adicionais

2.2.1 calculapontos: mapastr 7int(0.5 valores)

Fun c ao auxiliar que recebe um mapa e uma cadeia de caracteres correspondente ao nome
de um dos ex ercitos do mapa e devolve a sua pontua c ao. A pontua c ao dum ex ercito e
o total dos pontos de vida de todas as unidades do ex ercito.

```
>>> d = (7, 6)
>>> w = (cria_posicao(2,3), cria_posicao(4,4))
>>> e1 = tuple(cria_unidade(cria_posicao(p[0], p[1]), 30, 5, 'elfos')
for p in ((4, 2), (5, 4)))
>>> e2 = tuple(cria_unidade(cria_posicao(p[0], p[1]), 20, 5, 'orcos')
for p in ((2, 1), (3, 4), (5, 2), (5, 3)))
>>> m1 = cria_mapa(d, w, e1, e2)
>>> print(mapa_para_str(m1))
#######
#.O...#
#...EO#
#.#..O#
#..O#E#
#######
>>> calcula_pontos(m1, 'elfos'), calcula_pontos(m1, 'orcos')
(60, 80)
```
2.2.2 simulaturno: mapa7mapa(1 valor)

Fun c ao auxiliar que modifica o mapa fornecido como argumento de acordo com a sim-
ula c ao de um turno de batalha completo, e devolve o pr oprio mapa. Isto e, seguindo
a ordem de leitura do labirinto, cada unidade (viva) realiza um unico movimento e
(eventualmente) um ataque de acordo com as regras descritas.

```
>>> print(mapa_para_str(simula_turno(m1)))
#######
#..O..#
#...EO#
#.#O.O#
#...#E#
#######
>>> calcula_pontos(m1,'elfos'), calcula_pontos(m1, 'orcos')
(50, 70)
>>> print(mapa_para_str(simula_turno(m1)))
```

#### #######

#### #...O.#

#### #..OEO#

#### #.#..O#

#### #...#E#

#### #######

```
>>> calcula_pontos(m1,'elfos'), calcula_pontos(m1, 'orcos')
(30, 60)
>>> print(mapa_para_str(simula_turno(m1)))
#######
#...O.#
#..O.O#
#.#..O#
#...#E#
#######
>>> calcula_pontos(m1,'elfos'), calcula_pontos(m1, 'orcos')
(15, 55)
>>> print(mapa_para_str(simula_turno(m1)))
#######
#...O.#
#..O.O#
#.#...#
#...#E#
#######
>>> calcula_pontos(m1,'elfos'), calcula_pontos(m1, 'orcos')
(10, 50)
```
2.2.3 simulabatalha: str booleano7str (2 valores)

Fun c ao principal que permite simular uma batalha completa. A batalha termina quando
um dos ex ercitos vence ou, se ap os completar um turno de batalha, n ao ocorreu nen-
huma altera c ao ao mapa e `as unidades. A fun c aosimulabatalharecebe uma cadeia de
caracteres e um valor booleano e devolve o nome do ex ercito ganhador. Em caso de
empate, a fun c ao deve devolver a cadeia de caracteres'EMPATE'. A cadeia de caracteres
passada por argumento corresponde ao ficheiro de configura c ao do simulador. O argu-
mento booleano ativa o modoverboso(True) ou o modoquiet(False). No modoquiet
mostra-se pela sa idastandard o mapa e a pontua c ao no in icio da simula c ao e ap os do
ultimo turno de batalha. No modoverboso, mostra-se tamb em o mapa e a pontua c ao
ap os de cada turno de batalha. O exemplo seguinte mostra o conte udo de um ficheiro
de configura c ao:


Exemplo ficheiro configura c ao'config.txt':

#### (7,5)

```
('empire', 30, 4)
('rebellion', 10, 2)
((4, 2), (4, 3))
((1, 1),)
((4, 1), (1, 3), (2, 3), (5, 3))
```
A primeira linha contem a dimens ao do mapa; a segunda o nome, pontos de vida
e for ca de ataque das unidades do primeiro ex ercito; a terceira o nome, pontos de
vida e for ca de ataque das unidades do segundo ex ercito; a quarta a representa c ao ex-
terna das posi c oes das paredes do mapa; a quinta a representa c ao externa das posi c oes
das unidades do primeiro ex ercito; e a sexta a representa c ao externa das posi c oes das
unidades do segundo ex ercito. Pode assumir que o ficheiro de configura c ao est a sempre
bem formado.

Exemplo

```
>>> simula_batalha('config.txt', True)
#######
#E..R.#
#...#.#
#RR.#R#
#######
[ empire:30 rebellion:40 ]
#######
#..R..#
#ER.#.#
#R..#R#
#######
[ empire:26 rebellion:36 ]
#######
#.R...#
#ER.#.#
#R..#R#
#######
[ empire:22 rebellion:32 ]
#######
#R....#
#ER.#.#
#R..#R#
#######
```

```
[ empire:16 rebellion:28 ]
#######
#R....#
#ER.#.#
#R..#R#
#######
[ empire:10 rebellion:24 ]
#######
#.....#
#ER.#R#
#R..#.#
#######
[ empire:4 rebellion:22 ]
#######
#....R#
#.R.#.#
#R..#.#
#######
[ empire:0 rebellion:18 ]
'rebellion'
>>> simula_batalha('config.txt', False)
#######
#E..R.#
#...#.#
#RR.#R#
#######
[ empire:30 rebellion:40 ]
#######
#....R#
#.R.#.#
#R..#.#
#######
[ empire:0 rebellion:18 ]
'rebellion'
```
## 3 Condi c oes de Realiza c ao e Prazos

- A entrega do 2oprojecto ser a efetuada exclusivamente por via eletr onica. Dever a
    submeter o seu projecto atrav es do sistema Mooshak, at e `as17:00 do dia 5 de
    Dezembro de 2019. Depois desta hora, n ao ser ao aceites projectos sob pretexto
    algum.
- Dever a submeter um unico ficheiro com extens ao.pycontendo todo o c odigo do seu


```
projecto. O ficheiro de c odigo dever a conter em coment ario, na primeira
linha, o n umero e o nome do aluno.
```
- No seu ficheiro de c odigo n ao devem ser utilizados caracteres acentuados ou qual-
    quer outro car acter n ao pertencente `a tabela ASCII. Todos os testes autom aticos
    falhar ao, mesmo que os caracteres n ao ASCII sejam utilizados dentro de co-
    ment arios ou cadeias de caracteres. Programas que n ao cumpram este requisito
    ser ao penalizados em tres valores.
- N ao e permitida a utiliza c ao de qualquer m odulo ou fun c ao n ao dispon ivel built-in
    no Python 3, com exce c ao da fun c aoreducedofunctools.
- Pode, ou n ao, haver uma discuss ao oral do trabalho e/ou uma demonstra c ao do
    funcionamento do programa (ser a decidido caso a caso).
- Lembre-se que no T ecnico, a fraude acad emica e levada muito a s erio e que a c opia
    numa prova (projectos inclu idos) leva `a reprova c ao na disciplina. O corpo docente
    da cadeira ser a o unico juiz do que se considera ou n ao copiar num projecto.

## 4 Submiss ao

A submiss ao e avalia c ao da execu c ao do projecto de FP e feita utilizando o sistema
Mooshak^1. Para obter as necess arias credenciais de acesso e poder usar o sistema
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

(^1) A vers ao de Python utilizada nos testes autom aticos e Python 3.5.3.


Submeta o seu projecto atempadamente, dado que as restri c oes seguintes podem n ao
lhe permitir faze-lo no ultimo momento:

- S o poder a efetuar uma nova submiss ao pelo menos 15 minutos depois da submiss ao
    anterior.
- S o s ao permitidas 10 submiss oes em simultaneo no sistema, pelo que uma sub-
    miss ao poder a ser recusada se este limite for excedido^2.
- N ao pode ter submiss oes duplicadas, ou seja submiss ao igual `a anterior.
- Ser a considerada para avalia c ao a ultima submiss ao (mesmo que tenha pontua c ao
    inferior a submiss oes anteriores). Dever a, portanto, verificar cuidadosamente que
    a ultima entrega realizada corresponde `a vers ao do projecto que pretende que seja
    avaliada. N ao h a excep c oes!
- Cada aluno tem direito a15 submiss oes sem penaliza c aono Mooshak. Por
    cada submiss ao adicional ser ao descontados 0.1 valores na componente de avalia c ao
    autom atica.

## 5 Classificacao

A nota do projecto ser a baseada nos seguintes aspetos:

1. Execu c ao correta (60%). A avalia c ao da correcta execu c ao ser a feita atrav es
    do sistema Mooshak. O tempo de execu c ao de cada teste est a limitado, bem como
    a mem oria utilizada.
    Existem v arios casos de teste configurados no sistema: testes p ublicos (disponi-
    bilizados na p agina da disciplina) valendo 0 pontos cada e testes privados (n ao
    disponibilizados). Como a avalia c ao autom atica vale 60% (equivalente a 12 val-
    ores) da nota, uma submiss ao obt em a nota m axima de 1200 pontos.
    O facto de um projecto completar com sucesso os testes p ublicos fornecidos n ao im-
    plica que esse projecto esteja totalmente correto, pois estes n ao s ao exaustivos.E 
    da responsabilidade de cada aluno garantir que o c odigo produzido est a de acordo
    com a especifica c ao do enunciado, para completar com sucesso os testes privados.
2. Respeito pelas barreiras de abstra c ao (20%).Esta componente da avalia c ao
    e feita automaticamente, recorrendo a um conjunto descripts(n ao Mooshak) que
    testam o respeito pelas barreiras de abstra c ao do c odigo desenvolvido pelos alunos.
3. Avalia c ao manual (20%). Estilo de programa c ao e facilidade de leitura^3. Em
    particular, ser ao consideradas as seguintes componentes:

(^2) Note que o limite de 10 submiss oes simultaneas no sistema Mooshak implica que, caso haja um
n umero elevado de tentativas de submiss ao sobre o prazo de entrega, alguns alunos poder ao n ao con-
seguir submeter nessa altura e verem-se, por isso, impossibilitados de submeter o c odigo dentro do
prazo.
(^3) Podem encontrar algumas boas pr aticas relacionadas em https://gist.github.com/
ruimaranhao/4e18cbe3dad6f68040c32ed6709090a


- Boas pr aticas (1.5 valores): ser ao considerados entre outros a clareza do
    c odigo, elementos de programa c ao funcional, integra c ao de conhecimento
    adquirido durante a UC, a criatividade das solu c oes propostas e a escolha
    da representa c ao adotada nos TADs.
- Coment arios (1 valor): dever ao incluir a assinatura dos TADs (incluindo rep-
    resenta c ao interna adotada e assinatura das opera c oes b asicas), assim como a
    assinatura de cada fun c ao definida, coment arios para o utilizador (docstring)
    e coment arios para o programador.
- Tamanho de fun c oes, duplica c ao de c odigo e abstra c ao procedimental (1 valor)
- Escolha de nomes (0.5 valores).

## 6 Recomendacoes e aspetos a evitar

As seguintes recomenda c oes e aspetos correspondem a sugest oes para evitar maus h abitos
de trabalho (e, consequentemente, m as notas no projecto):

- Leia todo o enunciado, procurando perceber o objetivo das v arias fun c oes pedidas.
    Em caso de d uvida de interpreta c ao, utilize o hor ario de d uvidas para esclarecer
    as suas quest oes.
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


