package model.Data;

import java.io.*;
import java.util.*;

public class Data {

    public static void saveToFile(double[][] matrix, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (double[] row : matrix) {
                for (double value : row) {
                    writer.write(value + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + filename);
        }
    }

    public static void saveToFile(double[] vector, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (double value : vector) {
                writer.write(value + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving to file: " + filename);
        }
    }

    public static double[][] loadMatrixFromFile(String filename, int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        try (Scanner scanner = new Scanner(new File(filename))) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (scanner.hasNextDouble()) {
                        matrix[i][j] = scanner.nextDouble();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + filename);
        }
        return matrix;
    }

    public static double[] loadVectorFromFile(String filename, int size) {
        double[] vector = new double[size];
        try (Scanner scanner = new Scanner(new File(filename))) {
            for (int i = 0; i < size; i++) {
                if (scanner.hasNextDouble()) {
                    vector[i] = scanner.nextDouble();
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + filename);
        }
        return vector;
    }

    public static void backupFile(String original, String backup) {
        try (InputStream in = new FileInputStream(original);
             OutputStream out = new FileOutputStream(backup)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            System.out.println("Error creating backup: " + e.getMessage());
        }
    }

    /**
     * Saves the best error found so far to a file.
     * The method reads the currently saved best error from the file (if it exists)
     * and only overwrites it if the new error is smaller (i.e., the model has improved).
     *
     * @param currentError The current average error from the training step.
     * @param filePath The file path where the best error is stored.
     */
    public static void saveBestError(double currentError, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(Double.toString(currentError));
            writer.close();
            System.out.println("New best error saved: " + currentError);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the best error value saved in the specified file.
     * If the file does not exist or is empty, returns Double.MAX_VALUE as default.
     *
     * @param filePath The path to the file where the best error is stored.
     * @return The best error loaded from the file, or Double.MAX_VALUE if no file exists.
     */
    public static double loadBestError(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return Double.MAX_VALUE; // no saved error yet
        }
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE; // fallback if file is empty or error occurs
    }
     /**
     * Loads a matrix (2D double array) from a text file.
     * Each line in the file represents one row of the matrix,
     * with elements separated by commas.
     *
     * @param filename the path to the file
     * @return a 2D double array with the matrix data
     * @throws IOException if file reading fails
     */
    public static double[][] loadMatrix(String filename) throws IOException {
        List<double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] row = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Double.parseDouble(parts[i].trim());
                }
                rows.add(row);
            }
        }

        double[][] matrix = new double[rows.size()][];
        return rows.toArray(matrix);
    }


}
