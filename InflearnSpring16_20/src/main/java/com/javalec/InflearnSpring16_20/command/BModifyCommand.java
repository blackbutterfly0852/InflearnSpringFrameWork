package com.javalec.InflearnSpring16_20.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.InflearnSpring16_20.dao.BDao;

public class BModifyCommand implements BCommand {

	@Override
	public void execute(Model model) {
	
		
		Map<String, Object> map = model.asMap();
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao bDao = new BDao();
		
		bDao.modify(bId,bName,bTitle,bContent);
		

	}

}
