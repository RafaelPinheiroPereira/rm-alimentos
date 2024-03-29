package br.com.app.trinitymobileapp.repository;

import br.com.app.trinitymobileapp.model.entity.Client;
import br.com.app.trinitymobileapp.utils.ClientFile;
import br.com.app.trinitymobileapp.utils.Constants;
import br.com.app.trinitymobileapp.utils.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientFileRepository implements IFileRepository {

  List<Client> clients = new ArrayList<>();
  ClientFile clientFile;

  public ClientFileRepository() throws IllegalAccessException, InstantiationException {

    clientFile = Singleton.getInstance(ClientFile.class);
  }

  public List<Client> getClients() {
    return clients;
  }

  private void setClients(final List<Client> clients) {
    this.clients = clients;
  }

  @Override
  public void readFile() throws IOException, InstantiationException, IllegalAccessException {
    File file = clientFile.createFile(Constants.APP_DIRECTORY, Constants.INPUT_FILES[2]);
    clientFile.readFile(file);
    this.setClients(clientFile.getClients());
  }
}
