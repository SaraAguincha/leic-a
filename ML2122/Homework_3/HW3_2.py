#Homework 3
from sklearn.model_selection import cross_validate, KFold,train_test_split, cross_val_predict
from sklearn.feature_selection import mutual_info_classif
from sklearn.feature_selection import SelectKBest
from sklearn.neural_network import MLPClassifier,MLPRegressor
from sklearn.tree import DecisionTreeClassifier 
from sklearn.metrics import confusion_matrix
import matplotlib.pyplot as plt
from sklearn import metrics
from scipy.io import arff
import pandas as pd
import numpy as np

data = arff.loadarff('kin8nm.arff');
df = pd.DataFrame(data[0]);		

all_features = ["theta1","theta2","theta3","theta4","theta5","theta6","theta7","theta8"];
X = df.drop(columns='y');						        #turning dataframes into lists, one list
y = df['y']

n_cv = KFold(n_splits=5,shuffle=True,random_state=0);

def do():
    model_reg = MLPRegressor(activation='relu',hidden_layer_sizes=(3,2),alpha = 5);
    model_not_reg = MLPRegressor(activation='relu',hidden_layer_sizes=(3,2),alpha=0);

    y_reg = cross_val_predict(model_reg,X.values,y,cv=n_cv);
    y_not_reg = cross_val_predict(model_not_reg,X.values,y,cv=n_cv);

    resid_reg = y - y_reg;
    resid_not_reg = y - y_not_reg;

    d = [resid_reg, resid_not_reg];

    plt.boxplot(d);
    plt.xticks([1, 2], ['Reg','Not Reg'])
    plt.savefig("boxplot.png");
    

do();