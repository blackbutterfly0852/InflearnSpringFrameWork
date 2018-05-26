package com.javalec.InflearnSpring16_20.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.InflearnSpring16_20_command.BCommand;
import com.javalec.InflearnSpring16_20_command.BContentCommand;
import com.javalec.InflearnSpring16_20_command.BDeleteCommand;
import com.javalec.InflearnSpring16_20_command.BListFCommand;
import com.javalec.InflearnSpring16_20_command.BModifyCommand;
import com.javalec.InflearnSpring16_20_command.BReplyCommand;
import com.javalec.InflearnSpring16_20_command.BReplyViewCommand;
import com.javalec.InflearnSpring16_20_command.BWriteCommand;

@Controller
public class BController {

	BCommand command;
	
	// �� ����Ʈ 
	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("list()");
		
		command = new BListFCommand();
		command.execute(model);
		
		
		return "list";
	}
	// �ۼ�ȭ��
	@RequestMapping("write_view")
	public String write_view(Model model){
		
		System.out.println("write_view()");
	
		return "write_view";
	
	}
	
	@RequestMapping("/write")
	// �ۼ� �� DB ����, write_view ��û�� request ��ü�� �޴´�.
	public String write(HttpServletRequest request, Model model){
		 System.out.println("write()");
		 
		 model.addAttribute("request",request);
		 
		 command = new BWriteCommand();
		 
		 command.equals(model);
		 
		 return "redirect:list"; // DB ���� ��, List �������� �̵�
		
	}
	
	// �� �󼼺���
	@RequestMapping("content_view")
	public String content_view(HttpServletRequest request, Model model){
		System.out.println("content_view()");
		model.addAttribute("request",request);
		command = new BContentCommand();
		
		command.execute(model);
		
		return "content_view";
		
	}
	
	// �� ����
	@RequestMapping(method = RequestMethod.POST, value = "/modifty" ) // ���� ��û�� ����Ʈ ������� ����
	public String modify(HttpServletRequest request, Model model){
		model.addAttribute("request",request);
		
		command = new BModifyCommand();
		command.execute(model);
		
		return "redirect:list"; // �� ������, List �������� �̵�
	}
	
	// ��� ����
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model){
		System.out.println("reply_view()");
		
		model.addAttribute("request",request);
		
		command = new BReplyViewCommand();
		
		return "reply_view";
	}
	
	// ��� ����
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model){
		System.out.println("reply()");
		
		model.addAttribute("request",request);
		
		command = new BReplyCommand();
		
		command.execute(model);
		
		return "redirect:list";
		
		
	}
	
	//����
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model){
		
		System.out.println("delete()");
		
		model.addAttribute("request",request);
		
		command = new BDeleteCommand();
		
		command.execute(model);
		
		return "redirect:list";
	}
}
