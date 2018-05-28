package com.javalec.InflearnSpring16_20.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.InflearnSpring16_20.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override // model�� ���� Request�� �Ѿ�´�.
	public void execute(Model model) {
		
		
		Map<String, Object> map = model.asMap(); // model -> Map ��ȯ
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		
		dao.write(bName, bTitle, bContent);
	}

}
