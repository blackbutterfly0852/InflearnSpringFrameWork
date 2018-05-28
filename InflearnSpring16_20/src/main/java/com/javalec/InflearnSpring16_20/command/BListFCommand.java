package com.javalec.InflearnSpring16_20.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.InflearnSpring16_20.dao.BDao;
import com.javalec.InflearnSpring16_20.dto.BDto;

public class BListFCommand implements BCommand {

	@Override
	public void execute(Model model) {
			
		BDao dao = new BDao();
		
		ArrayList<BDto> dtos = dao.list();
		
		model.addAttribute("list", dtos);
		

	}

}
