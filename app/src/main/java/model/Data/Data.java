package model.Data;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A utility class for handling data persistence operations including saving and loading
 * matrices and vectors to/from files, creating backups, and managing best error values.
 */
public class Data {

    /**
     * Saves a 2D matrix to a text file.
     * Each row of the matrix is written as a line in the file, with values separated by spaces.
     *
     * @param matrix the 2D double array to be saved
     * @param filename the path of the file to save to
     */
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

    /**
     * Saves a vector (1D array) to a text file.
     * All values are written on a single line, separated by spaces.
     *
     * @param vector the double array to be saved
     * @param filename the path of the file to save to
     */
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

    /**
     * Loads a matrix from a text file with specified dimensions.
     * The file should contain values separated by whitespace.
     *
     * @param filename the path of the file to load from
     * @param rows the number of rows in the matrix
     * @param cols the number of columns in the matrix
     * @return the loaded 2D double array, or a zero matrix if loading fails
     */
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

    /**
     * Loads a vector from a text file with specified size.
     * The file should contain values separated by whitespace on a single line.
     *
     * @param filename the path of the file to load from
     * @param size the expected size of the vector
     * @return the loaded double array, or a zero array if loading fails
     */
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

    /**
     * Creates a backup copy of a file.
     *
     * @param original the path of the source file to be backed up
     * @param backup the path where the backup should be created
     */
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
     * The method overwrites any existing value in the file with the new error value.
     *
     * @param currentError the current error value to be saved
     * @param filePath the path of the file where the error should be stored
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
     * Loads the best error value from a file.
     * If the file doesn't exist or is empty, returns Double.MAX_VALUE.
     *
     * @param filePath the path of the file containing the saved error
     * @return the loaded error value, or Double.MAX_VALUE if no value is available
     */
    public static double loadBestError(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return Double.MAX_VALUE;
        }
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }

    /**
     * Loads a matrix from a JSON-formatted text file.
     * The file should contain a JSON array of arrays representing the matrix rows.
     *
     * @param filePath the path of the JSON file to load
     * @return the loaded 2D double array, or null if loading fails
     */
    public static double[][] loadMatrix(String filePath) {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            // Versuche zuerst, eine Liste von Matrizen zu laden (3D-Liste)
            Type listOfMatricesType = new TypeToken<List<List<List<Double>>>>() {}.getType();
            List<List<List<Double>>> data = null;
            try {
                data = gson.fromJson(br, listOfMatricesType);
            } catch (Exception e) {
                // Falls das nicht klappt, versuchen wir die Datei neu zu lesen als einzelne Matrix
                br.close();
                br = new BufferedReader(new FileReader(filePath));
            }

            if (data != null && !data.isEmpty()) {
                // Lade die erste Matrix aus der Liste
                List<List<Double>> firstMatrixList = data.get(0);
                return convertListToMatrix(firstMatrixList);
            } else {
                // Wenn nicht 3D-Array, dann 2D-Array laden
                Type matrixType = new TypeToken<List<List<Double>>>() {}.getType();
                List<List<Double>> matrixList = gson.fromJson(br, matrixType);
                return convertListToMatrix(matrixList);
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Datei: " + filePath);
            e.printStackTrace();
            return null;
        }
    }


    private static double[][] convertListToMatrix(List<List<Double>> list) {
        int rows = list.size();
        int cols = list.get(0).size();
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            List<Double> row = list.get(i);
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = row.get(j);
            }
        }
        return matrix;
    }



}