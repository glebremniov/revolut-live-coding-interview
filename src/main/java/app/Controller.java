package app;

import io.jooby.annotation.GET;
import io.jooby.annotation.Path;

@Path("/")
public class Controller {

  @GET
  public String sayHi() {
    return "Welcome to Jooby!";
  }
}
