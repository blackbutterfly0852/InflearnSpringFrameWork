package com.javalec.InflearnSpring16_20_command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.InflearnSpring16_20.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override // model을 통해 Request가 넘어온다.
	public void execute(Model model) {
		
		
		Map<String, Object> map = model.asMap(); // model -> Map 변환
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		
		dao.write(bName, bTitle, bContent);
	}

}
