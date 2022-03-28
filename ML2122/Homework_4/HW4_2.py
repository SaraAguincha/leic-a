#Homework 4
from scipy.sparse.construct import random
from sklearn.model_selection import cross_validate, StratifiedKFold,train_test_split, cross_val_predict
from sklearn.feature_selection import mutual_info_classif
from sklearn.feature_selection import SelectKBest
from sklearn.neural_network import MLPClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import confusion_matrix,silhouette_score
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from sklearn import metrics
from scipy.io import arff
import pandas as pd
import numpy as np

data_1 = [2,5,10,12,13]             #3 retas
list_1,list_2,list_3 = [],[],[]
data_2 = [2,5,10,30,100,300,1000]   #1 e 3 reta

'''
def do():
    for i in data_1:
        a2 = (3**i)
        a1 = ((i**2) +i) * 3 + i +1
        a3 = (i + (i**2 + i)/2) * 2 +1
        list_1.append(a1)
        list_2.append(a2)
        list_3.append(a3)
    print(list_1,list_2,list_3)
    return plt.plot(data_1,list_1,label='MLP'),plt.plot(data_1,list_2,label='Tree'),plt.plot(data_1,list_3,label='Bayes');
'''
def do():
    for i in data_2:
        #a2 = (3**i)
        a1 = ((i**2) +i) * 3 + i +1
        a3 = (i + (i**2 + i)/2) * 2 +1
        list_1.append(a1)
        #list_2.append(a2)
        list_3.append(a3)
    print(list_1,list_3)
    return plt.plot(data_2,list_1,label='MLP'),plt.plot(data_2,list_3,label='Bayes');



do();
plt.legend(loc='upper left');
plt.savefig("plots1.png");  #savefig, don't show