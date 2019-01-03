package com.macfu.action;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: liming
 * @Date: 2019/1/3 14:36
 * @Description: controller接口
 */
@RestController
@RequestMapping("/page/message/*")
public class EchoAction {
    @GetMapping("/show")
    public ModelAndView echo(String msg) {
        ModelAndView mav = new ModelAndView("message/message_show");
        mav.addObject("echoMessage", "【echo】msg = " + msg);
        return mav;
    }

    @GetMapping("/input")
    public String input() {
        return "message/message_input";
    }
}
