package net.vizbits.gladiator.server.routing;

import net.vizbits.gladiator.server.GladiatorClient;
import net.vizbits.gladiator.server.request.BaseRequest;

public interface RouteAction {
  public void apply(GladiatorClient client, BaseRequest request, String json);
}
