#Sara Aguincha, ist195674

#----------------------------- Funcao Auxiliar --------------------------#

# verifica se a unidade (x,y) e possivel no labirinto
def unidade_livre(maze,unidade):
    x,y = unidade
    a,b = tamanho_labirinto(maze)
    if x >= a or y >= b:            #limita as opcoes de ser uma unidade livre utilizando o tamanho do labirinto
        return False
    if maze[x][y] == 0:
        return True
    return False

#----------------------------- Funcoes ----------------------------------#

#2.1.1
def eh_labirinto(maze):
    if not isinstance(maze,tuple):
        return False    
    if len(maze) <= 2 or len(maze[0]) <= 2 or not isinstance(maze,tuple):     # o tamanho minimo e 3x3 e o labirinto tem que ser um tuplo
        return False
    x = len(maze[0])                            # todos os tuplos tem que ter a mesma quantidade de elementos 
    for c in maze:                              # ve de tuplo em tuplo (x)
        if not isinstance (c,tuple):            # o labirinto tem que ser um conjunto de tuplos (colunas)
            return False
        if len(c) != x:
            return False
        if c == maze[0] or c == maze[-1]:       # o primeiro e ultimo tuplo do labirinto tem que ser so constituido por 1 
            for el in c:
                if el != 1 or not isinstance(el,int):
                    return False
        elif c[0] != 1 or c[-1] != 1 or not isinstance(c[0],int) or not isinstance(c[-1],int):           # para os outros tuplos
                    return False
        for el in c:
            if not (el == 0 or el == 1) or not isinstance(el,int):
                return False
    return True

#2.1.2 definir se e, ou nao, uma posicao; nao pode ter mais que dois elementos e ambos tem que ser positivos
def eh_posicao(posicao):
    if not isinstance(posicao,tuple) or len(posicao) != 2 :
        return False
    for el in posicao:
        if not isinstance(el,int) or el < 0:
            return False
    return True

#2.1.3 devolve um conjunto de posicoes (pode nao pertencer ao maze)
def eh_conj_posicoes(unidades):
    if unidades == ():
        return True
    elif not isinstance (unidades,tuple) or len(unidades) < 1:
        return False
    for unidade in unidades:
        if not eh_posicao(unidade):
            return False
        a = unidades.count(unidade)   #ve se ha unidades repetidas
        if a != 1:
            return False        
    return True

#2.1.4 tamanho do labirinto; ve se atraves do len do maze (absissas) e o len de uma das colunas do maze (ordenadas)
def tamanho_labirinto(maze):
    if eh_labirinto(maze):
        y = len(maze[0])
        x = len(maze)
        return ((x,y))
    raise ValueError ('tamanho_labirinto: argumento invalido')

#2.1.5 ve se as unidades definidas podem ser, ou nao, parte do labirinto
def eh_mapa_valido(maze,unidades):
    if eh_labirinto(maze) and eh_conj_posicoes(unidades):
        for unidade in unidades:
            if unidade_livre(maze,unidade) == False:
                return False
        return True   
    raise ValueError ('eh_mapa_valido: algum dos argumentos e invalido')

#2.1.6 ve se a posicao e livre, ou seja, se nao esta ocupada nem por paredes nem por unidades
def eh_posicao_livre(maze,unidades,posicao):
    if eh_labirinto(maze) and eh_conj_posicoes(unidades) and eh_posicao(posicao) and eh_mapa_valido(maze,unidades):
        for unidade in unidades:
            if unidade_livre(maze,posicao) == False or unidade == posicao:
                return False
        return True
    raise ValueError ('eh_posicao_livre: algum dos argumentos e invalido')

#2.1.7 verifica quais sao as posicoes adjacentes
def posicoes_adjacentes(posicao):
    if eh_posicao(posicao):
        #lista com a ordem de posicoes(posicao acima,direita,esquerda,baixo)
        adjacentes_lst = [(posicao[0],posicao[1]-1),(posicao[0]-1,posicao[1]),(posicao[0]+1,posicao[1]),(posicao[0],posicao[1]+1)]
        adjacentes = ()
        for pos in adjacentes_lst:
            if eh_posicao(pos):   #se os adjacentes sao posicoes
                adjacentes = adjacentes + ((pos,))                
        return adjacentes
    raise ValueError ('posicoes_adjacentes: argumento invalido')

#2.1.8 
def mapa_str(maze,unidades):
    if eh_labirinto(maze) and eh_conj_posicoes(unidades):   #confirma se e labirinto e se as unidades sao positivas e nao repetidas
        (x,y) = tamanho_labirinto(maze)
        pos_y = 0
        strng = ''
        for pos_y in range(y):                                              #estes 2 ciclos de for vao percorrer todas as posicoes possiveis
            pos_x = 0
            for pos_x in range(x):
                if (pos_x,pos_y) in unidades and unidade_livre(maze,(pos_x,pos_y)) == False:  #caso nos seja dado uma unidade que coincida com uma parede ou outra unidade
                    raise ValueError ('mapa_str: algum dos argumentos e invalido')
                if (pos_x,pos_y) in unidades and unidade_livre(maze,(pos_x,pos_y)) == True:   
                    strng += 'O'
                elif maze[pos_x][pos_y] == 0:
                    strng += '.'
                elif maze[pos_x][pos_y] == 1:
                    strng += '#'        
            strng += '\n'
        return strng[:-1]
    raise ValueError ('mapa_str: algum dos argumentos e invalido')

#2.2.1
def obter_objetivos(maze,unidades,posicao):  
    if eh_labirinto(maze) and eh_conj_posicoes(unidades) and eh_posicao(posicao) and (posicao in unidades) and eh_mapa_valido(maze,unidades): 
        conj_adjacentes = ()
        objetivos = ()
        x,y = tamanho_labirinto(maze)
        pos_x = 0
        for pos_x in range(x):  #percorre todos os elementos do maze
            pos_y = 0
            for pos_y in range(y):
                if (pos_x,pos_y) in unidades and (pos_x,pos_y) != posicao:    
                    conj_adjacentes += posicoes_adjacentes((pos_x,pos_y))       #obtem a lista com todas as posicoes adjacentes incluindo repetidas, paredes, unidades e posicao
                    for adjacente in conj_adjacentes:
                        a,b = adjacente
                        if (adjacente in objetivos) or (adjacente in unidades) or (a == 0 or b == 0 or a == x or b == y): #nao soma os repetidos e nao soma os que coincidem com as unidades e nao soma os que estao nas paredes                         
                            next
                        elif unidade_livre(maze,adjacente):
                            objetivos += (adjacente,) 
        return objetivos
    raise ValueError ('obter_objetivos: algum dos argumentos e invalido')
#2.2.2
def obter_caminho(maze,unidades,posicao): 
    if eh_labirinto(maze) and eh_conj_posicoes(unidades) and (posicao in unidades) and eh_posicao(posicao) and eh_mapa_valido(maze,unidades):
        if obter_objetivos(maze,unidades,posicao) == ():
            return ()     
        unidades_existentes = ()                   #verifica se a unica unidade e a posicao, caso seja devolve um conjunto vazio
        for unidade in unidades:
            unidades_existentes += unidade
        if posicao == unidades_existentes:
            return ()
        objetivos = obter_objetivos(maze,unidades,posicao) #objetivos possiveis
        lista_exploracao = [[posicao,()],]
        posicoes_visitadas = ()
        while lista_exploracao != []:               #inicia se um ciclo enuanto a lista de exploracao nao e vazia
            posicao_atual = lista_exploracao[0][0]  #seleciona na lista_exploracao[0] a posicao(posicao_atual) e o caminho(caminho_atual)
            caminho_atual = lista_exploracao[0][1]
            if posicao_atual not in posicoes_visitadas:
                posicoes_visitadas += (posicao_atual,)
                caminho_atual += (posicao_atual,)
                if posicao_atual in objetivos:
                    caminho_resposta = caminho_atual
                    return caminho_resposta
                else:
                    for posicao_adjacente in posicoes_adjacentes(posicao_atual):
                        if unidade_livre(maze,posicao_adjacente):
                            lista_exploracao += [[posicao_adjacente,caminho_atual]]
            del lista_exploracao[0]     #depois fazer o ciclo apaga a primeira posicao da lista
        return ()                       #se a lista ficar vazia e nao tiver feito return no ciclo entao a resposta e um tuplo vazio
    raise ValueError ('obter_caminho: algum dos argumentos e invalido')

##2.2.3 altera a posicao selecionada no conjunto de unidades
def mover_unidade(maze,unidades,posicao):
    if eh_labirinto(maze) and eh_conj_posicoes(unidades) and (posicao in unidades) and eh_posicao(posicao) and eh_mapa_valido(maze,unidades):
        if obter_caminho(maze,unidades,posicao) == ():   #caso a unica unidade seja a posicao
            return unidades
        novo_conj_unidades = ()  #cria se um novo conjunto para as unidades que vai conter as unidades anteriores e a posicao alterada
        nova_posicao = () #posicao alterada
        for unidade in unidades: #se a unidade ja esta nas posicoes adjacentes entao nao muda
            if unidade in posicoes_adjacentes(posicao):
                return unidades
            elif unidade != posicao: #mantem as unidades que nao se vao movimentar 
                novo_conj_unidades += (unidade,)
            elif unidade == posicao: #muda a posicao para a posicao adjacente a esta segundo o obter_caminho
                nova_posicao = (obter_caminho(maze,unidades,posicao)[1],) #a nova posicao vai ser o segundo elemento do caminho (pois o primeiro e ela mesma)
                novo_conj_unidades += nova_posicao
        return novo_conj_unidades
    raise ValueError ('mover_unidade: algum dos argumentos e invalido')