package daggerok.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPage {

  @GetMapping({
      "/",
      "/404"
  })
  public String index() {
    return "index";
  }
}
