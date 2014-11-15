package net.vizbits.gladiator.server.response;

public class ConnectingResponse {
  private Integer position;
  private Integer total;
  private Boolean connected;
  private Integer users_online;

  public ConnectingResponse() {}

  public ConnectingResponse(Boolean connected, Integer position, Integer total, Integer online) {
    this.connected = connected;
    this.position = position;
    this.total = total;
    this.users_online = online;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Boolean getConnected() {
    return connected;
  }

  public void setConnected(Boolean connected) {
    this.connected = connected;
  }

  public void setUsersOnline(Integer online) {
    this.users_online = online;
  }

  public Integer getUserOnline() {
    return users_online;
  }


}
