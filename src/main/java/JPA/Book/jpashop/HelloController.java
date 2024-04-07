package JPA.Book.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {


    @GetMapping("Hello")
    public String hello(Model model){
        model.addAttribute("data", "Hello World!");
        return "hello";
        //Model 이란?
        // 스프링 UI의 Model을 통해서 Controller의 데이터를 실어서 view에 전달 가능!
        //Key:Value 형태
        //Return은 데이터를 반환할 View 이며, .Html은 자동완성이기에 생략 가능.
    }
}
