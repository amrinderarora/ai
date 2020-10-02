# Load libraries
import pandas as pd
from sklearn.tree import DecisionTreeClassifier # Import Decision Tree Classifier
from sklearn.model_selection import train_test_split # Import train_test_split function
from sklearn import metrics #Import scikit-learn metrics module for accuracy calculation


col_names = ['skin', 'volume', 'weight','label']
# load data set
fruits = pd.read_csv("apple_oranges.csv", header=None, names=col_names)

#split data set in features and target variable
feature_cols = ['skin', 'volume', 'weight']
X = fruits[feature_cols] # Features

print(pima.shape)
print(X.shape)


y = fruits.label # Target variable
print(y.shape)

# Split data set into training set and test set
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=1) # 70% training and 30% test

# Create Decision Tree classifier object
clf = DecisionTreeClassifier()

# Train Decision Tree Classifier
clf = clf.fit(X_train,y_train)

#Predict the response for test data set
y_pred = clf.predict(X_test)

# Model Accuracy, how often is the classifier correct?
print("Accuracy:",metrics.accuracy_score(y_test, y_pred))


