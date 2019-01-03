package com.macfu.action;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liming
 * @Date: 2019/1/3 14:18
 * @Description:
 */
@RestController
@RequestMapping("/page/info/*")
public class MessageAction {
    @GetMapping("/url")
    public Object echo() {
        return "www.google.com";
    }
}
