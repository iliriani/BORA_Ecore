package RefClasses;

import boraproj.services.RepoConnect;

import java.util.*;

public class AttributeProcessor {

    private RepoConnect conn = new RepoConnect();
    public void getAverageNrOfAttributes() {
        ArrayList<String> allClasses = conn.getAllClasses();
        // Store attributes for each class
        Map<String, ArrayList<String>> classAttributes = new HashMap<>();
        // Track which classes have each attribute
        Map<String, Set<String>> attributeToClasses = new HashMap<>();

        // Step 1: Collect attributes and remove unwanted ones
        for (String cl : allClasses) {
            ArrayList<String> allAttributes = conn.getAttributes(cl);

            // Remove unwanted attributes
//            Iterator<String> iter = allAttributes.iterator();
//            while (iter.hasNext()) {
//                String attr = iter.next();
//                if (attr.equalsIgnoreCase("id") ||
//                        attr.equalsIgnoreCase("name") ||
//                        attr.equalsIgnoreCase("length")) {
//                    iter.remove();
//                }
//            }

            // Store attributes for this class
            classAttributes.put(cl, new ArrayList<>(allAttributes));

            // Track which classes have each attribute
            for (String attr : allAttributes) {
                attributeToClasses.computeIfAbsent(attr, k -> new HashSet<>()).add(cl);
            }

            // Print class and its attributes
//            System.out.println("Class: " + cl);
//            System.out.println("Attributes: " + allAttributes);
        }

        // Step 2: Identify unique attributes for each class
        Map<String, ArrayList<String>> uniqueAttributes = new HashMap<>();
        for (String cl : allClasses) {
            uniqueAttributes.put(cl, new ArrayList<>());
            ArrayList<String> attributes = classAttributes.get(cl);
            for (String attr : attributes) {
                // An attribute is unique if it appears in exactly one class
                if (attributeToClasses.get(attr).size() == 1) {
                    uniqueAttributes.get(cl).add(attr);
                }
            }
            // Print unique attributes for this class
            System.out.println("Unique Attributes: " + uniqueAttributes.get(cl));
        }

        // Step 3: Calculate average number of unique attributes
        int sumUniqueAttributes = uniqueAttributes.values().stream()
                .mapToInt(List::size)
                .sum();
        double average = allClasses.isEmpty() ? 0.0 : (double) sumUniqueAttributes / allClasses.size();

        // Print results
        System.out.println("Sum of unique attributes: " + sumUniqueAttributes);
        System.out.println("Number of classes: " + allClasses.size());
        System.out.printf("Average number of unique attributes per class: %.2f%n", average);
    }
}
