package net.vizbits.gladiator.server.response;

public class LoginResponse {
  private String message;
  private Boolean success;

  public LoginResponse() {

  }

  public LoginResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }



}
