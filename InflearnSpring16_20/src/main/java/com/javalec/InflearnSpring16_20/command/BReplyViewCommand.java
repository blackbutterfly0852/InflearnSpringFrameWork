package com.javalec.InflearnSpring16_20.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.InflearnSpring16_20.dao.BDao;
import com.javalec.InflearnSpring16_20.dto.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(Model model) {
	
		Map<String, Object> map = model.asMap();
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		
		String bId = request.getParameter("bId");
		
		
		BDao dao = new BDao();
		
		BDto dto = dao.reply_View(bId);
		
		model.addAttribute("reply_view", dto);
		
		
	}

}
