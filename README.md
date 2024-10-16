# BORA_Ecore
## BORA - a model reuse algorithm based on the RDF graph repository and N-grams

# Activity Diagram and Source Code Overview
To facilitate replication and understanding, we provide an activity diagram (see Figure 1) and the relevant source code on GitHub. This diagram illustrates the sequential steps in transforming models into RDF graphs, merging these graphs, persisting the final graph, applying edge weights, and executing BORA's endpoints, either as a REST API or within a Java project.

# Process Steps
## 1. Model Transformation to RDF
Models are transformed into RDF format using specialized classes:

XML Models: XML2RDF
JSON Models: JsonToRDF
This transformation enables compatibility with the zAppDev LCDP platform. Depending on the model type, additional steps like removing comments or adding namespaces may be necessary.

## 2. Merging RDF Graphs
After converting individual models to RDF, the RDF files are merged into a single RDF graph using the RDFMerge class. This unified graph is then stored in a TDB database via the RepoConnect class and createDataset(file, repoPath) method, where:
file: The RDF file (knowledge graph)
repoPath: Path for TDB storage

## 3.Adding Weights
Using AddWeights.insertAllWeights(), weights are assigned to graph edges, enhancing the graph’s analytical utility.

## 4. Executing BORA Endpoints
Choose either to:

REST API Execution: Trigger endpoints such as:
localhost:8080/classRecommendationsFromAttributes?class_attrs=ClassName
localhost:8080/attributes?cl=ClassName
Java Project Execution: Run BoraMain.main() to directly execute the required operations.
This structured workflow supports effective RDF graph management and leverages BORA's analytical functions. For more details, please refer to the source code in this repository.

Also, find below a video of how BORA does work within the zAppDev Low-code:
https://www.youtube.com/watch?v=Hid5RsviKBU
