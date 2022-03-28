#Homework 2
from sklearn.model_selection import cross_validate, StratifiedKFold
from sklearn.feature_selection import mutual_info_classif
from sklearn.feature_selection import SelectKBest
from sklearn.tree import DecisionTreeClassifier         # Import Decision Tree Classifier
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

n_cv = StratifiedKFold(n_splits=10,random_state=7,shuffle=True);
n_list=[1,3,5,9];
test_list1,test_list2,train_list1,train_list2=[],[],[],[];

def function_i():           #for that does the tree with 1,3,5,9 features (mutual information)
    for i in n_list:
        X_features = SelectKBest(mutual_info_classif, k=i);
        X_features.fit(X, y);
        X_copy = X[X_features.get_feature_names_out()];
        model1 = DecisionTreeClassifier();             #Create Decision Tree classifer object
        score = cross_validate(model1,X_copy,y,scoring = 'accuracy',cv=n_cv, return_train_score = True);         #Uses StratifiedKFold (10 folds) to test and train the data
        test1 = score['test_score'].mean();
        train1 = score['train_score'].mean();
        test_list1.append(test1);
        train_list1.append(train1);
    return plt.plot(n_list,train_list1,label='Features Train'),plt.plot(n_list,test_list1,label='Features Test');

def function_ii():      #for that does the tree with 1,3,5,9 depth
    for i in n_list:
        model2 = DecisionTreeClassifier(max_depth=i);   #Create Decision Tree classifer object
        score = cross_validate(model2,X,y,scoring = 'accuracy',cv=n_cv, return_train_score = True);
        test2 = score['test_score'].mean();
        train2 = score['train_score'].mean();
        test_list2.append(test2);
        train_list2.append(train2);
    return plt.plot(n_list,train_list2,label ='Depth Train'),plt.plot(n_list,test_list2,label ='Depth Test');

function_i();
function_ii();

plt.legend(loc='lower right');
plt.savefig("matplotlib.png");  #savefig, don't show