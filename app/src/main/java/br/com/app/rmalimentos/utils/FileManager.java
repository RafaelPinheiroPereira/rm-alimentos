package br.com.app.rmalimentos.utils;

import android.os.Environment;
import br.com.app.rmalimentos.model.entity.Employee;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileManager {

    private Employee employee;

    public File createAppDirectory() {
        File directory = Environment.getExternalStoragePublicDirectory(Constants.APP_FOLDER_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public File createOutputFile() throws IOException {

        File outputFile = new File(Constants.APP_FOLDER_NAME + Constants.OUTPUT_FILE);
        // TODO verificar se o arquivo eh criado sem o metodo ->  outputFile.createNewFile();
        return outputFile;
    }

    public void downloadFiles() {

    }

    public boolean fileExists(final String inputFile) {
        File file = new File(Constants.APP_DIRECTORY);

        long count =
                Arrays.asList(file.listFiles()).stream()
                        .filter(item->item.getName().equals(inputFile))
                        .count();

        return count > 0;
    }

    public void readEmployeeFile() throws IOException {

        String line;
        File employeeFile = new File(Constants.APP_DIRECTORY, Constants.INPUT_FILES[0]);
        FileInputStream fileInputStream = new FileInputStream(employeeFile);

        BufferedReader br =
                new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1));

        while ((line = br.readLine()) != null && !line.equals("")) {
            Employee employee = new Employee();
            employee.setId(Long.valueOf(line.substring(0, 5)));
            employee.setName(line.substring(5, 45).trim());
            employee.setPassword(line.trim().substring(45));
            this.setEmployee(employee);
        }

        br.close();
    }

    public boolean containsAllFiles() {

        long countFilesInexists =
                Arrays.asList((Constants.INPUT_FILES)).stream()
                        .filter(inputFileName->!this.fileExists(inputFileName))
                        .count();
        return countFilesInexists <= 0;
    }

    public Employee getEmployee() {
        return employee;
    }

    public StringBuilder searchInexistsFilesNames() {

        Stream<String> namesFiles =
                Arrays.asList((Constants.INPUT_FILES)).stream()
                        .filter(inputFileName->!this.fileExists(inputFileName));
        StringBuilder nameStringBuilder = new StringBuilder();
        namesFiles.forEach(nameFile->nameStringBuilder.append(nameFile).append("\n"));
        return nameStringBuilder;
    }

    private void setEmployee(final Employee employee) {
        this.employee = employee;
    }
}
