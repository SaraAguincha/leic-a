#Homework 3
from scipy.sparse.construct import random
from sklearn.model_selection import cross_validate, StratifiedKFold,train_test_split, cross_val_predict
from sklearn.feature_selection import mutual_info_classif
from sklearn.feature_selection import SelectKBest
from sklearn.neural_network import MLPClassifier
from sklearn.tree import DecisionTreeClassifier         # Import Decision Tree Classifier
from sklearn.metrics import confusion_matrix
import matplotlib.pyplot as plt
from sklearn import metrics
from scipy.io import arff
import pandas as pd
import numpy as np

data = arff.loadarff('breast.w.arff');
df = pd.DataFrame(data[0]);		
df['Class'] = df['Class'].str.decode('utf-8');

all_features = ["Clump_Thickness","Cell_Size_Uniformity","Cell_Shape_Uniformity","Marginal_Adhesion","Single_Epi_Cell_Size","Bare_Nuclei","Bland_Chromatin","Normal_NucleoliMitoses"];
X = df.drop(columns='Class');							#turning dataframes into lists, one list
dic = {'benign' : 0, 'malignant' : 1};                  #turning 'bening' and 'malignant' to 0 and 1
y = list(map(dic.get, df['Class']));

n_cv = StratifiedKFold(n_splits=5,shuffle=True,random_state=0);


def do():
    model_false = MLPClassifier(activation='relu',hidden_layer_sizes=(3,2),early_stopping=False,random_state=0,shuffle=True);
    model_true = MLPClassifier(activation='relu',hidden_layer_sizes=(3,2),early_stopping=True,random_state=0,shuffle=True);

    y_false = cross_val_predict(model_false,X.values,y,cv=n_cv);
    y_true = cross_val_predict(model_true,X.values,y,cv=n_cv);
    cm_false = confusion_matrix(y,y_false);
    cm_true = confusion_matrix(y,y_true);
    tn, fp, fn, tp = cm_false.ravel();
    print("TN:" + str(tn) + "\t"+ "FP:" + str(fp) + "\t"+ "FN:" + str(fn) + "\t"+ "TP:" + str(tp));
    print("\n")
    tn, fp, fn, tp = cm_true.ravel();
    print("TN:" + str(tn) + "\t"+ "FP:" + str(fp) + "\t"+ "FN:" + str(fn) + "\t"+ "TP:" + str(tp));

do();
