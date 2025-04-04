//package boraproj.services;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class CrossValidationFolderSetup {
//
//    // Base path for the models and cross-validation folders
//    private static final String MODELS_DIRECTORY = "C:\\Users\\Ibrahimi\\Desktop\\BOs";  // Path where your models are located
//    private static final String OUTPUT_DIRECTORY = "C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models";  // Path where you want to create the folders
//
//    public static void main(String[] args) throws IOException {
//        // List all model files in the directory
//        File modelsFolder = new File(MODELS_DIRECTORY);
//        File[] models = modelsFolder.listFiles();
//
//        if (models == null) {
//            System.out.println("No models found in the specified directory.");
//            return;
//        }
//
//        // Convert models to a list and shuffle them for randomness
//        List<File> modelList = new ArrayList<>();
//        Collections.addAll(modelList, models);
//        Collections.shuffle(modelList);
//
//        // Create 10 folders for cross-validation
//        for (int i = 1; i <= 10; i++) {
//            // Create the main folder for fold i
//            Path foldPath = Paths.get(OUTPUT_DIRECTORY, String.valueOf(i));
//            Path trainPath = foldPath.resolve("train");
//            Path testPath = foldPath.resolve("test");
//
//            // Create the train and test folders
//            Files.createDirectories(trainPath);
//            Files.createDirectories(testPath);
//
//            // Randomly split models into 80% train and 20% test
//            int trainSize = (int) (modelList.size() * 0.8);
//            List<File> trainModels = modelList.subList(0, trainSize);
//            List<File> testModels = modelList.subList(trainSize, modelList.size());
//
//            // Copy models into the appropriate folders
//            copyModels(trainModels, trainPath);
//            copyModels(testModels, testPath);
//
//            // Shuffle models for the next fold
//            Collections.shuffle(modelList);
//        }
//        System.out.println("Cross-validation folders created successfully!");
//    }
//
//    private static void copyModels(List<File> models, Path targetDirectory) throws IOException {
//        for (File model : models) {
//            Path destination = targetDirectory.resolve(model.getName());
//            Files.copy(model.toPath(), destination);
//        }
//    }
//}
