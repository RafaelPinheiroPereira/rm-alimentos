package br.com.app.rmalimentos.utils;

import java.io.File;
import java.io.IOException;

public interface IFileManager {

  public File createAppDirectory();

  public boolean fileExists(final String inputFile);

  public File createFile(String nameDirectory, String nameFile);

  public abstract void readFile(File file)
      throws IOException, IllegalAccessException, InstantiationException;

  public boolean containsAllFiles();

  public StringBuilder searchInexistsFilesNames();

  public File createOutputFile() throws IOException;
}
