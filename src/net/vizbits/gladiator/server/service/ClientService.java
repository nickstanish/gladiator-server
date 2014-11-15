package net.vizbits.gladiator.server.service;

import java.util.HashMap;
import java.util.Map;

import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.exceptions.AlreadyConnectedException;
import net.vizbits.gladiator.server.exceptions.GladiatorMissingInActionException;

public class ClientService {
  private Map<String, GladiatorClient> clientsList;

  public ClientService() {
    clientsList = new HashMap<String, GladiatorClient>();
  }

  public void addClient(GladiatorClient client) throws AlreadyConnectedException {
    String username = client.getUsername();
    if (clientsList.containsKey(username))
      throw new AlreadyConnectedException(username);
    clientsList.put(username, client);
  }

  public boolean usernameAvailable(String username) {
    try {
      return findGladiatorByName(username) == null;
    } catch (GladiatorMissingInActionException miaException) {
      return true;
    }
  }

  public GladiatorClient findGladiatorByName(String username)
      throws GladiatorMissingInActionException {
    GladiatorClient client = clientsList.get(username);
    if (client == null)
      throw new GladiatorMissingInActionException(username);
    return client;
  }

  public void removeClient(String username) {
    clientsList.remove(username);
  }

  public void removeClient(GladiatorClient client) {
    removeClient(client.getUsername());
  }
}
