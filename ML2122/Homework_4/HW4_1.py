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

data = arff.loadarff('breast.w.arff');
df = pd.DataFrame(data[0]);		
df['Class'] = df['Class'].str.decode('utf-8');

X = df.drop(columns='Class');							#turning dataframes into lists, one list
dic = {'benign' : 0, 'malignant' : 1};                  #turning 'bening' and 'malignant' to 0 and 1
df['y'] = list(map(dic.get, df['Class']));

k_2, k_3 = [], []

X_features = SelectKBest(mutual_info_classif, k=2);
X_features.fit(X, df['y']);
X_copy = X[X_features.get_feature_names_out()];

def do():
    #--------------Exercise 4 -----------------#
    model_2 = KMeans(n_clusters = 2)
    model_3 = KMeans(n_clusters = 3)
    k_2 = model_2.fit_predict(X)
    k_3= model_3.fit_predict(X)
    ECR_2 = ECR(2,k_2)
    ECR_3 = ECR(3,k_3)
    silhouette_2 = silhouette_score(X,k_2)
    silhouette_3 = silhouette_score(X,k_3)
    print (ECR_2,ECR_3)
    print(silhouette_2,silhouette_3)
    
    #--------------Exercise 5 -----------------#
    df['clusters'] = model_3.fit_predict(X)
    
    colours = {0:'green', 1:'red', 2:'blue'}
    df['colours'] = df.clusters.map(colours)
    df['cancer'] = df.y.map(colours)   
    plt.scatter(X_copy[X_copy.columns[0]], X_copy[X_copy.columns[1]], c=df.colours, alpha = 0.7)
    plt.savefig("Clusters.png");
    plt.clf()
    plt.scatter(X_copy[X_copy.columns[0]], X_copy[X_copy.columns[1]], c=df.cancer, alpha = 0.7)
    plt.savefig("clustersCancer.png");  #savefig, don't show
    #------------------------------------------#    

def ECR(k,c):
    i,aux = 0,0
    n_list,max_list = [],[]
    for i in range(k):
        n_list += [[0,0],]
        max_list +=[[0],]
    for i in range(len(df['y'])):
        n_list[c[i]][df['y'][i]] += 1
    for i in range(len(max_list)):
        max_list[i] = max(n_list[i])
    for i in range(len(n_list)):
        aux += sum(n_list[i]) - max_list[i] 
    ECR = 1/k * (aux)
    return ECR    
do();
