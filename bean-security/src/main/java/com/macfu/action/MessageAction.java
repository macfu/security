package com.macfu.action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller										// 定义控制器
@RequestMapping("/pages/info/*")				// 定义该类的访问父路径，与方法中的路径进行组合为完整路径
public class MessageAction {						// 自定义Action程序类
	@GetMapping("/url")					// 访问的路径为“url.action”
	@ResponseBody
	public Object echo() { // 接收请求参数
		return "www.mldn.cn" ;
	}
}

