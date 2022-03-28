#Sara Aguincha, ist195674


#2.1.1 TAD posicao ------------------------------------------------------------#
#Construtor
def cria_posicao(x,y):
    """cria_posicao: N x N -> posicao 
    Recebe os valores correspondentes as coordenadas
    de uma posicao e devolve a posicao correspondente"""
    if isinstance(x,int) and isinstance(y,int) and x >= 0 and y >= 0:
        return (x,y)
    raise ValueError ('cria_posicao: argumentos invalidos')

def cria_copia_posicao(posicao):
    """cria_copia_posicao: posicao -> posicao
    Recebe uma posicao e devolve uma copia nova da posicao"""
    copia_posicao = cria_posicao(posicao[0],posicao[1])
    return copia_posicao

#Seletores
def obter_pos_x(posicao):
    """obter_pos_x: posicao -> N 
    Devolve a componente x da posicao p"""
    return posicao[0]

def obter_pos_y(posicao):
    """obter_pos_y: posicao -> N 
    Devolve a componente y da posicao p"""
    return posicao[1]

#Reconhecedor
def eh_posicao(arg):
    """eh_posicao: universal -> booleano 
    Devolve True caso o seu argumento seja um TAD posicao e False caso contrario"""
    #verifica se o argumento e um tuplo, com dois elementos inteiros positivos
    return isinstance(arg,tuple) and len(arg) == 2 and isinstance(arg[0],int) \
           and isinstance(arg[1],int) and arg[0] >= 0 and arg[1] >= 0

#Teste
def posicoes_iguais(p1,p2):
    """posicoes_iguais: posicao x posicao -> booleano 
    Devolve True apenas se p1 e p2 sao posicoes iguais"""
    return p1==p2

#Transformador
def posicao_para_str(posicao):
    """posicao_para_str: posicao -> str 
    Devolve a cadeia de caracteres '(x, y)' que representa o seu argumento, 
    sendo os valores x e y as coordenadas de p"""
    return str(posicao)

#Func. Alto nivel
def obter_posicoes_adjacentes(posicao):
    """obter_posicoes_adjacentes: posicao -> tuplo de posicoes
     Devolve um tuplo com as posicoes adjacentes a posicao p de 
     acordo com a ordem de leitura de um labirinto"""
    adjacentes = ()
    #lista com a ordem de posicoes(cima,direita,esquerda,baixo)
    adjacentes_lst = [(posicao[0],posicao[1]-1),(posicao[0]-1,posicao[1]),\
                      (posicao[0]+1,posicao[1]),(posicao[0],posicao[1]+1)]
    for pos in adjacentes_lst:
        if eh_posicao(pos):   #se os adjacentes sao posicoes
            adjacentes = adjacentes + ((pos,))                
    return adjacentes

#2.1.2 TAD unidade ------------------------------------------------------------#
#Construtor
def cria_unidade(posicao,vida,forca,army):
    """cria_unidade: posicao x N x N x str -> unidade 
     Recebe uma posicao p, dois valores inteiros maiores que 0 correspondentes 
     a vida e forca da unidade, e uma cadeia de caracteres nao vazia 
     correspondente ao exercito da unidade; e devolve a unidade correspondente"""
    #verifica se a posicao e valida, se a vida e a forca sao valores naturais e
    #que esta associada a um exercito ('army')
    if eh_posicao(posicao) and isinstance(vida,int) and isinstance(forca,int) \
       and isinstance(army,str) and army != '' and vida > 0 and forca > 0:
        return {'posicao':posicao, 'vida':vida, 'forca':forca, 'army':army}
    raise ValueError ('cria_unidade: argumentos invalidos')

def cria_copia_unidade(unidade):
    """ cria_copia_unidade: unidade -> unidade
    Recebe uma unidade e devolve uma nova copia da unidade"""
    copia_unidade = cria_unidade(unidade['posicao'],unidade['vida'],\
                                 unidade['forca'],unidade['army'])
    return copia_unidade

#Seletores
def obter_posicao(unidade):
    """obter_posicao: unidade -> posicao
    Devolve a posicao da unidade"""
    return unidade['posicao']

def obter_vida(unidade):
    """ obter_exercito: unidade -> str 
    Devolve a cadeia de carateres correspondente ao exercito da unidade"""
    return unidade['vida']

def obter_forca(unidade):
    """obter_forca: unidade -> N 
    Devolve o valor corresponde a forca de ataque da unidade"""
    return unidade['forca']

def obter_exercito(unidade):
    """obter_vida: unidade -> N
    Devolve o valor corresponde aos pontos de vida da unidade"""
    return unidade['army']

#Modificadores
def muda_posicao(unidade,posicao):
    """muda_posicao: unidade x posicao -> unidade
    Modifica destrutivamente a unidade alterando a sua posicao 
    com o novo valor, e devolve a propria unidade"""
    unidade['posicao'] = posicao
    return unidade

def remove_vida(unidade,valor):
    """remove_vida: unidade x N -> unidade
    Modifica destrutivamente a unidade alterando os seus pontos de vida 
    subtraindo o valor dado, e devolve a propria unidade"""
    unidade['vida'] = obter_vida(unidade) - valor
    return unidade

#Reconhecedor
def eh_unidade(arg):
    """eh_unidade: universal -> booleano 
    Devolve True caso o seu argumento seja um TAD unidade e False caso contrario """
    #verifica se o argumento e um dicionario, se a posicao, vida, forca e exercito existem
    if isinstance(arg,dict) and len(arg) == 4 and 'posicao' in arg \
       and 'vida' in arg and 'forca' in arg and 'army' in arg:
        #verifica se sao possiveis
        if isinstance(arg['posicao'],tuple) and eh_posicao(arg['posicao']) \
           and obter_vida(arg) > 0 and isinstance(obter_vida(arg),int) \
           and obter_forca(arg) > 0 and isinstance(obter_forca(arg),int) \
           and isinstance(obter_exercito(arg),str) and arg['army'] != '':
            return True
        return False
    return False

#Teste
def unidades_iguais(u1,u2):
    """unidades_iguais: unidade x unidade -> booleano
    Devolve True apenas se u1 e u2 sao unidades iguais"""
    return u1 == u2

#Transformadores
def unidade_para_char(unidade):
    """unidade_para_char: unidade -> str
    Devolve a cadeia de caracteres dum unico elemento, correspondente ao 
    primeiro caracter em maiuscula do exercito da unidade passada por argumento"""
    char_upper = obter_exercito(unidade)[0].upper()
    return char_upper

def unidade_para_str(unidade):
    """unidade_para_str: unidade -> str
    Devolve a cadeia de caracteres que representa a unidade"""
    string = unidade_para_char(unidade) + str([unidade['vida'],unidade['forca']])\
        + '@' + posicao_para_str(unidade['posicao'])
    return string

#Func. Alto nivel
def unidade_ataca(u1,u2):
    """unidade_ataca: unidade x unidade -> booleano
    Modifica destrutivamente a unidade u2 retirando o valor de pontos de vida
    correspondente a forca de ataque da unidade u1.
    A funcao devolve True se a unidade u2 for destruida ou False caso contrario"""
    u2 = remove_vida(u2,u1['forca'])
    if u2['vida'] <= 0:
        return True
    return False

def ordenar_unidades(t_unidades):
    """ordenar_unidades: tuplo unidades -> tuplo unidades 
    Devolve um tuplo contendo as mesmas unidades do tuplo fornecido
    como argumento, ordenadas de acordo com a ordem de leitura do labirinto"""
    lst_unidades = list(t_unidades)
    #num ciclo for sao alteradas as coordenadas das posicoes
    for unidade in lst_unidades:
        unidade['posicao'] = (unidade['posicao'][1],unidade['posicao'][0])
    #da sort segundo as posicoes
    lst_unidades = sorted((lst_unidades),key = lambda x: x['posicao'])
    #reverte as coordenadas denovo
    for unidade in lst_unidades:
        unidade['posicao'] = (unidade['posicao'][1],unidade['posicao'][0])
    return tuple(lst_unidades)

#2.1.3 TAD mapa ---------------------------------------------------------------#
#Construtor
def cria_mapa(dim,wall,e1,e2):
    """cria_mapa: tuplo x tuplo x tuplo x tuplo -> mapa
    Recebe um tuplo d de 2 valores inteiros correspondentes as dimensoes Nx e Ny
    do labirinto seguindo as restricoes do primeiro projecto,
    um tuplo w de 0 ou mais posicoes correspondentes as paredes que nao sao dos
    limites exteriores do labirinto, um tuplo e1 de 1 ou mais unidades do mesmo
    exercito, e um tuplo e2 de um ou mais unidades de um outro exercito;
    e devolve o mapa que representa internamente o labirinto e as unidades presentes"""
    #assumo que e1 e e2 sao um tuplo que tem varios dicionarios
    #verifica se a dimensao (2 elementos, minimo 3x3), a parede e os exercitos sao tuplos
    if isinstance(dim,tuple) and len(dim) == 2 and dim[0] >= 3 and dim[1] >= 3 \
       and isinstance(wall,tuple) and isinstance(e1,tuple) and isinstance(e2,tuple)\
       and e1 != () and e2 != ():
        for posicao in wall:
            if not eh_posicao(posicao) or not obter_pos_x(posicao) < dim[0] or not obter_pos_x(posicao) > 0 or \
               not obter_pos_y(posicao) < dim[1] or not obter_pos_y(posicao) > 0 :
                raise ValueError ('cria_mapa: argumentos invalidos')                
        for unidade in e1:
            if not eh_unidade(unidade):
                raise ValueError ('cria_mapa: argumentos invalidos')
        for unidade in e2:
            if not eh_unidade(unidade):
                raise ValueError ('cria_mapa: argumentos invalidos')
        return {'dim':dim,'wall':wall,'e1':e1,'e2':e2}
    raise ValueError ('cria_mapa: argumentos invalidos')

def cria_copia_mapa(mapa):
    """cria_copia_mapa: mapa -> mapa
    Recebe um mapa e devolve uma nova copia do mapa"""
    copia_e1 = []
    copia_e2 = []
    copia_wall = ()
    #faz varios ciclos for para criar uma copia de todas as unidades e walls
    for unidade in mapa['e1']:
        copia_e1.append(cria_copia_unidade(unidade))
    for unidade in mapa['e2']:
        copia_e2.append(cria_copia_unidade(unidade))    
    for posicao in mapa['wall']:
        copia_wall += (posicao,)
    copia_mapa = cria_mapa(cria_copia_posicao(mapa['dim']),\
                           copia_wall, tuple(copia_e1), tuple(copia_e2))
    return copia_mapa

#Seletores
def obter_tamanho(mapa):
    """obter_tamanho: mapa -> tuplo
    Devolve um tuplo de dois valores inteiros correspondendo o primeiro deles
    a dimensao Nx e o segundo a dimensao Ny do mapa"""
    return mapa['dim']

def obter_nome_exercitos(mapa):
    """obter_nome_exercitos: mapa -> tuplo
    Devolve um tuplo ordenado com duas cadeias de caracteres correspondendo
    aos nomes dos exercitos do mapa"""
    #caso o 'e1' ja esteja morto
    if len(mapa['e1']) == 0:
        return obter_exercito(mapa['e2'][0])
    #caso o exercito 'e2' ja esteja morto
    elif len(mapa['e2']) == 0:
        return obter_exercito(mapa['e1'][0])
    #seleciona os primeiros elementos de cada exercito e obtem o nome atraves da funcao sorted
    else:
        return tuple(sorted([obter_exercito(mapa['e1'][0]),obter_exercito(mapa['e2'][0])]))

def obter_unidades_exercito(mapa,army):
    """obter_unidades_exercito: mapa x str -> tuplo unidades
    Devolve um tuplo contendo as unidades do mapa pertencentes ao exercito
    indicado pela cadeia de caracteres e, ordenadas em ordem de leitura do labirinto"""
    if army not in obter_nome_exercitos(mapa):
        return ()
    #se o exercito for o 'e1' e o exercito 'e2' estiver morto
    elif len(mapa['e2']) == 0:
        return ordenar_unidades(mapa['e1'])
   
    #se o exercito for o 'e2' e o exercito 'e1' estiver morto
    elif len(mapa['e1']) == 0:
        return ordenar_unidades(mapa['e2'])
    
    #se nao for nenhuma das excecoes acima
    else:
        if obter_exercito(mapa['e1'][0]) == army:
                return ordenar_unidades(mapa['e1'])
        else:
            return ordenar_unidades(mapa['e2'])        

def obter_todas_unidades(mapa):
    """obter_todas_unidades: mapa -> tuplo
    Devolve um tuplo contendo todas as unidades do mapa, ordenadas em ordem 
    de leitura do labirinto"""
    #foi criado um tuplo com todas as unidades
    unidades = mapa['e1'] + mapa['e2']
    return ordenar_unidades(unidades)

def obter_unidade(mapa,posicao):
    """obter_unidade: mapa x posicao -> unidade
    Devolve a unidade do mapa que se encontra na posicao dada"""
    for unidade in mapa['e1']:
        if obter_posicao(unidade) == posicao:
            return unidade
    for unidade in mapa['e2']:
        if obter_posicao(unidade) == posicao:
            return unidade
        
#Modificadores
def eliminar_unidade(mapa,unidade):
    """eliminar_unidade: mapa x unidade -> mapa
    Modifica destrutivamente o mapa eliminando a unidade do mapa e deixando 
    livre a posicao onde se encontrava a unidade. Devolve o proprio mapa"""
    #se for do exercito nr1
    if unidade in mapa['e1']:
        for soldado in mapa['e1']:
            if soldado == unidade:
                new_e1 = list(mapa['e1'])
                new_e1.remove(unidade)
        mapa['e1'] = tuple(new_e1)
    #se for do exercito nr2
    elif unidade in mapa['e2']:
        for soldado in mapa['e2']:
            if soldado == unidade:
                new_e2 = list(mapa['e2'])
                new_e2.remove(unidade)
        mapa['e2'] = tuple(new_e2)
    return mapa

def mover_unidade(mapa,unidade,posicao):
    """mover_unidade: mapa x unidade x posicao -> mapa
    Modifica destrutivamente o mapa e a unidade alterando a posicao da unidade
    no mapa para a nova posicao e deixando livre a posicao onde se encontrava.
    Devolve o proprio mapa"""
    if unidade in mapa['e1']:
        for soldado in mapa['e1']:
            if soldado == unidade:
                unidade = muda_posicao(unidade,posicao)
    if unidade in mapa['e2']:
        for soldado in mapa['e2']:
            if soldado == unidade:
                unidade = muda_posicao(unidade,posicao)
    return mapa

#Reconhecedor
def eh_posicao_unidade(mapa,posicao):
    """eh_posicao_unidade: mapa x posicao -> booleano
    Devolve True apenas no caso da posicao estar ocupada por uma unidade"""
    if not eh_posicao(posicao):
        return False
    for unidade in obter_todas_unidades(mapa):
        if obter_posicao(unidade) == posicao:
            return True
    return False

def eh_posicao_corredor(mapa,posicao):
    """eh_posicao_corredor: mapa x posicao -> booleano
    Devolve True apenas no caso da posicao corresponder a um corredor no 
    labirinto (independentemente de estar ou nao ocupado por uma unidade)"""
    #verifica se nao esta dentro do mapa e nao e nenhuma parede
    return eh_posicao(posicao) and (posicao not in mapa['wall']) \
           and not eh_posicao_parede(mapa,posicao) \
           and (posicao[0] < (obter_tamanho(mapa)[0]-1) \
           and posicao[1] < (obter_tamanho(mapa)[1])-1)
           #a ultima abcissa vai ser a dimensao x - 1; Igualmente para y


def eh_posicao_parede(mapa,posicao):
    """eh_posicao_parede: mapa x posicao -> booleano
    Devolve True apenas no caso da posicao corresponder a uma parede do labirinto"""
    #verifica se e parede do mapa ou uma 'wall' dentro deste
    if eh_posicao(posicao):
        return posicao[0] == 0 or posicao[1] == 0 \
               or posicao[0] == obter_tamanho(mapa)[0] - 1 \
               or posicao[1] == obter_tamanho(mapa)[1] - 1 \
               or posicao in mapa['wall']
    return False

#Teste
def mapas_iguais(m1,m2):
    """mapas_iguais: mapa x mapa -> booleano
    Devolve True apenas se m1 e m2 forem mapas iguais"""
    return m1 == m2

#Transformador
def mapa_para_str(mapa):
    """mapa_para_str: mapa -> str
    Devolve uma cadeia de caracteres que representa o mapa como descrito no 
    primeiro projeto, neste caso, com as unidades representadas pela sua representacao externa"""
    (x,y) = obter_tamanho(mapa)
    pos_y = 0
    mapa_str = ''
    #estes 2 ciclos de for vao percorrer todas as posicoes do mapa
    for pos_y in range(y):
        pos_x = 0
        for pos_x in range(x):
            if eh_posicao_unidade(mapa,(pos_x,pos_y)):
                #percorre todas as unidades do mapa
                for unidade in obter_todas_unidades(mapa):
                    if obter_posicao(unidade) == (pos_x,pos_y):
                #quando a posicao da unidade e igual a testada escreve a letra do 'army'
                        mapa_str += unidade_para_char(unidade)                        
            elif eh_posicao_parede(mapa,(pos_x,pos_y)):  #caso seja seja parede
                mapa_str += '#'
            elif eh_posicao_corredor(mapa,(pos_x,pos_y)) \
                 and not eh_posicao_unidade(mapa,(pos_x,pos_y)): #caso seja corredor
                mapa_str += '.'
        mapa_str += '\n'
    return mapa_str[:-1]

#Func. Alto nivel
def obter_inimigos_adjacentes(mapa,unidade):
    """obter_inimigos_adjacentes: mapa x unidade -> tuplo unidades
    Devolve um tuplo contendo as unidades inimigas adjacentes a unidade de 
    acordo com a ordem de leitura do labirinto"""
    lst = obter_posicoes_adjacentes(obter_posicao(unidade)) #posicoes adjacentes
    unidades_adjacentes = []
    for soldado in obter_todas_unidades(mapa): ##soldado e um dicionario
        if obter_posicao(soldado) in lst and obter_exercito(soldado) != obter_exercito(unidade):
            unidades_adjacentes.append(soldado)
    return ordenar_unidades(tuple(unidades_adjacentes))

def obter_movimento(mapa, unit):
    '''
    A funcao obter_movimento devolve a posicao seguinte da unidade argumento
    de acordo com as regras de movimento das unidades no labirinto.

    obter_movimento: mapa x unidade -> posicao
    '''
    ######################
    # Funcoes auxiliares #
    ######################
    def pos_to_tuple(pos):
        return obter_pos_x(pos), obter_pos_y(pos)

    def tuple_to_pos(tup):
        return cria_posicao(tup[0], tup[1])

    def tira_repetidos(tup_posicoes):
        conj_tuplos = set(tuple(map(pos_to_tuple, tup_posicoes)))
        return tuple(map(tuple_to_pos, conj_tuplos))

    def obter_objetivos(source):
        enemy_side = tuple(filter(lambda u: u != obter_exercito(source), obter_nome_exercitos(mapa)))[0]
        target_units = obter_unidades_exercito(mapa, enemy_side)
        tup_com_repetidos = \
            tuple(adj
                  for other_unit in target_units
                  for adj in obter_posicoes_adjacentes(obter_posicao(other_unit))
                  if eh_posicao_corredor(mapa, adj) and not eh_posicao_unidade(mapa, adj))
        return tira_repetidos(tup_com_repetidos)

    def backtrack(target):
        result = ()
        while target is not None:
            result = (target,) + result
            target, _ = visited[target]
        return result

    ####################
    # Funcao principal #
    ####################
    # Nao mexer se ja esta' adjacente a inimigo
    if obter_inimigos_adjacentes(mapa, unit):
        return obter_posicao(unit)

    visited = {}
    # posicao a explorar, posicao anterior e distancia
    to_explore = [(pos_to_tuple(obter_posicao(unit)), None, 0)]
    # registro do numero de passos minimo ate primeira posicao objetivo
    min_dist = None
    # estrutura que guarda todas as posicoes objetivo a igual minima distancia
    min_dist_targets = []

    targets = tuple(pos_to_tuple(obj) for obj in obter_objetivos(unit))

    while to_explore:  # enquanto nao esteja vazio
        pos, previous, dist = to_explore.pop(0)

        if pos not in visited:  # posicao foi ja explorada?
            visited[pos] = (previous, dist)  # registro no conjunto de exploracao
            if pos in targets:  # se a posicao atual eh uma dos objetivos
                # se eh primeiro objetivo  ou se esta a  distancia minima
                if min_dist is None or dist == min_dist:
                    # acrescentor 'a lista de posicoes minimas
                    min_dist = dist
                    min_dist_targets.append(pos)
            else:  # nao 'e objetivo, acrescento adjacentes
                for adj in obter_posicoes_adjacentes(tuple_to_pos(pos)):
                    if eh_posicao_corredor(mapa, adj) and not eh_posicao_unidade(mapa, adj):
                        to_explore.append((pos_to_tuple(adj), pos, dist + 1))

        # Parar se estou a visitar posicoes mais distantes que o minimo,
        # ou se ja encontrei todos os objetivos
        if (min_dist is not None and dist > min_dist) or len(min_dist_targets) == len(targets):
            break

    # se encontrei pelo menos uma posicao objetivo, 
    # escolhe a de ordem de leitura menor e devolve o primeiro movimento
    if len(min_dist_targets) > 0:
        # primeiro dos objetivos em ordem de leitura
        tar = sorted(min_dist_targets, key=lambda x: (x[1], x[0]))[0]
        path = backtrack(tar)
        return tuple_to_pos(path[1])

    # Caso nenhuma posicao seja alcancavel
    return obter_posicao(unit)

#Funcoes Adicionais
#2.2.1
def calcula_pontos(mapa,army):
    """calcula_pontos: mapa x str -> int 
    Funcao auxiliar que recebe um mapa e uma cadeia de caracteres 
    correspondente ao nome de um dos exercitos do mapa e devolve a sua pontuacao"""
    pontos = 0
    exercito = obter_unidades_exercito(mapa,army)
    if exercito == ():
        return 0
    for unidade in exercito:
        pontos += obter_vida(unidade)
    return pontos

#2.2.2
def simula_turno(mapa):
    """simula_turno: mapa -> mapa 
    Funcao auxiliar que modifica o mapa fornecido como argumento de acordo 
    com a simulacao de um turno de batalha completo, e devolve o proprio mapa.
    Seguindo a ordem de leitura do labirinto, cada unidade (viva) realiza um 
    unico movimento e (eventualmente) um ataque de acordo com as regras descritas"""
    #cria uma lista com todass as unidades por ordem de leitura
    todas_unidades = ordenar_unidades(obter_todas_unidades(mapa))
    eliminadas = []
    #atualiza se necessario as posicoes das unidades
    for unidade in todas_unidades:
        if obter_movimento(mapa,unidade) != obter_posicao(unidade):
            mover_unidade(mapa,unidade,obter_movimento(mapa,unidade))
        #verifica se a unidade ja foi eliminada
        if unidade not in eliminadas:
            #verifica se ha unidades inimigas que possam ser atacadas
            inimigos = list(ordenar_unidades(obter_inimigos_adjacentes(mapa,unidade)))
            if inimigos != []:
                #se a unidade mata o inimigo, este e eliminado e adicionado a lista 'eliminadas'
                if unidade_ataca(unidade,inimigos[0]):
                    eliminadas.append(inimigos[0])
                    eliminar_unidade(mapa,inimigos[0])
                    if obter_unidades_exercito(mapa,obter_exercito(inimigos[0])) == ():
                        break
    return mapa

#2.2.3
def simula_batalha(string,value):
    """simula_batalha: str x booleano -> str 
    Funcao principal que permite simular uma batalha completa. A batalha 
    termina quando um dos exercitos vence ou, se apos completar um turno de
    batalha, nao ocorreu nenhuma alteracao ao mapa e as unidades.
    A funcao simula batalha recebe uma cadeia de caracteres e um valor booleano
    e devolve o nome do exercito ganhador. Em caso de empate, a funcao deve 
    devolver a cadeia de caracteres 'EMPATE'. A cadeia de caracteres passada 
    por argumento corresponde ao ficheiro de configuracao do simulador.
    O argumento booleano ativa o modo verboso (True) ou o modo quiet (False)"""
    
    #abre se o ficheiro
    ficheiro = open(string,'r')
    #obtem se os valores do ficheiro
    dim = eval(ficheiro.readline())
    #listas com os elementos 'army', vida, forca dos dois exercitos
    avf_e1= eval(ficheiro.readline())
    avf_e2= eval(ficheiro.readline())
    wall = eval(ficheiro.readline())
    p_e1 = eval(ficheiro.readline())
    p_e2 = eval(ficheiro.readline())
    ficheiro.close()
    e1 = []
    e2 = []
    for posicao in p_e1:
        e1.append(cria_unidade(posicao,avf_e1[1],avf_e1[2],avf_e1[0]))
    for posicao in p_e2:
        e2.append(cria_unidade(posicao,avf_e2[1],avf_e2[2],avf_e2[0]))
    #cria mapa    
        mapa = cria_mapa(dim,wall,tuple(e1),tuple(e2))
        exercitos = obter_nome_exercitos(mapa)
    #contador inicial (onde aparece o nome dos exercitos com as pontuacoes)    
        contador = '[' + ' ' + exercitos[0] + ':' + str(calcula_pontos(mapa,exercitos[0])) \
            + ' ' + exercitos[1] + ':' + str(calcula_pontos(mapa,exercitos[1])) + ' ' + ']'
   
# -------------------------------------------------------------------------------------------- #   
   
    #se for True
    if value:
        #print inicial do mapa e do contador
        print(mapa_para_str(mapa))
        print(contador)
        
        while calcula_pontos(mapa,exercitos[0]) != 0 or calcula_pontos(mapa,exercitos[1]) != 0:
            
            mapa_anterior  = cria_copia_mapa(mapa)
           
            #print repetidament das atualizacoes do mapa
            print(mapa_para_str(simula_turno(mapa)))
            
            #ve se ja algum dos exercitos esta morto
            if calcula_pontos(mapa,exercitos[0]) == 0:
                contador = '[' + ' ' + exercitos[0] + ':' + '0' \
                    + ' ' + exercitos[1] + ':' + str(calcula_pontos(mapa,exercitos[1])) + ' ' + ']'                            
                print(contador)                
                return exercitos[1]
            
            elif calcula_pontos(mapa,exercitos[1])== 0:
                contador = '[' + ' ' + exercitos[0] + ':' + str(calcula_pontos(mapa,exercitos[0])) \
            + ' ' + exercitos[1] + ':' + '0' + ' ' + ']'            
                print(contador)                
                return exercitos[0]                
           
            #caso empatam
            elif mapas_iguais(mapa_anterior,mapa):
                print(contador)
                return 'EMPATE'
                
            contador = '[' + ' ' + exercitos[0] + ':' + str(calcula_pontos(mapa,exercitos[0])) \
                + ' ' + exercitos[1] + ':' + str(calcula_pontos(mapa,exercitos[1])) + ' ' + ']'                        
            print(contador)
            

# -------------------------------------------------------------------------------------------- #    
    
    #se for False
    elif not value:
        print(mapa_para_str(mapa))
        print(contador)
        
        while calcula_pontos(mapa,exercitos[0]) != 0 or calcula_pontos(mapa,exercitos[1]) != 0:
            
            mapa_anterior  = cria_copia_mapa(mapa)
            simula_turno(mapa)
            
            #ve se algum do exercitos esta morto 
            if calcula_pontos(mapa,exercitos[0]) == 0:
                print(mapa_para_str(mapa))
                contador = '[' + ' ' + exercitos[0] + ':' + str(calcula_pontos(mapa,exercitos[0])) \
                + ' ' + exercitos[1] + ':' + str(calcula_pontos(mapa,exercitos[1])) + ' ' + ']'
                print(contador)                
                return exercitos[1]
            
            elif calcula_pontos(mapa,exercitos[1])== 0:
                print(mapa_para_str(mapa))
                contador = '[' + ' ' + exercitos[0] + ':' + str(calcula_pontos(mapa,exercitos[0])) \
                + ' ' + exercitos[1] + ':' + str(calcula_pontos(mapa,exercitos[1])) + ' ' + ']'
                print(contador)                
                return exercitos[0]
            
            #caso empatam
            elif mapas_iguais(mapa_anterior,mapa):
                print(mapa_para_str(mapa))
                contador = '[' + ' ' + exercitos[0] + ':' + str(calcula_pontos(mapa,exercitos[0])) \
                + ' ' + exercitos[1] + ':' + str(calcula_pontos(mapa,exercitos[1])) + ' ' + ']'
                print(contador)
                return 'EMPATE'            