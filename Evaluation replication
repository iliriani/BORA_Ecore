This repository provides a workflow for processing models, merging them, and evaluating class and attribute similarity using RDF and cross-validation methods.

# Evaluation Process

## 1. Convert models to RDF
Use Classes like Json2RDf, XML2Rdf to convert different models to the specific RDF format.

## 2. Use CrossValidationFolderSetup to split folders for evaluation
Currently, 80% of the models are split for training and 20% for testing.

## 3. Merge Models:
```java
RDFMerge m = new RDFMerge();
m.iterate();
```

## 4. Create Repo by using an initial model and the destination
```java
String repoTest = "path_to_repo";
String repoTrain = "path_to_repo";

EvaluationForKfoldValidation comp = new EvaluationForKfoldValidation(repoTrain);
comp.createRepo("path_to_repo\\AccessComponent.bo (2).rdf", repoTest);
```

## 5. Insert Weights:
```java
AddWeights w = new AddWeights(repoTrain);
w.insertAllWeight();
```

## 6. Run the evaluation:
```java
EvaluationForKfoldValidation comp = new EvaluationForKfoldValidation(repoTrain);
```

- **For class name similarity evaluation:**
  ```java
  comp.compare(repoTest, repoTrain);
  ```

- **For attribute similarity evaluation:**
  ```java
  comp.compareAtttributeAndRelations(repoTest, repoTrain);
  ```
