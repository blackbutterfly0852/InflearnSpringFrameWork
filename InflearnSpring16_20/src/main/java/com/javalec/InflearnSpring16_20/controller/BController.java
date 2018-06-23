package com.javalec.InflearnSpring16_20.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.InflearnSpring16_20.command.BCommand;
import com.javalec.InflearnSpring16_20.command.BContentCommand;
import com.javalec.InflearnSpring16_20.command.BDeleteCommand;
import com.javalec.InflearnSpring16_20.command.BListFCommand;
import com.javalec.InflearnSpring16_20.command.BModifyCommand;
import com.javalec.InflearnSpring16_20.command.BReplyCommand;
import com.javalec.InflearnSpring16_20.command.BReplyViewCommand;
import com.javalec.InflearnSpring16_20.command.BWriteCommand;

@Controller
public class BController {

	BCommand command;
	
	// 글 리스트 
	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("list()");
		
		command = new BListFCommand();
		command.execute(model);
			
		return "list";
	}
	
	
	// 작성화면
	@RequestMapping("/write_view")
	public String write_view(Model model){
		
		System.out.println("write_view()");
	
		
		return "write_view";
	
	}
	
	@RequestMapping("/write")
	
	// 작성 글 DB 저장, write_view 요청을 request 객체로 받는다.
	public String write(HttpServletRequest request, Model model){
		 System.out.println("write()");
		 
		 model.addAttribute("request",request);
		 
		 command = new BWriteCommand();
		 
		 command.execute(model);
		 
		 return "redirect:list"; // DB 저장 후, List 페이지로 이동
		
	}
	
	// 글 상세보기
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model){
		System.out.println("content_view() 들어옴");
		
		model.addAttribute("request",request);
		command = new BContentCommand();
		
		command.execute(model);
		
		return "content_view";
		
	}
	
	// 글 수정
	@RequestMapping(method = RequestMethod.POST, value = "/modify" ) // 수정 요청은 포스트 방식으로 전달
	public String modify(HttpServletRequest request, Model model){
		model.addAttribute("request",request);
		
		command = new BModifyCommand();
		command.execute(model);
		
		return "redirect:list"; // 글 수정후, List 페이지로 이동
	}
	
	//삭제
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model){
		
		System.out.println("delete()");
		
		model.addAttribute("request",request);
		
		command = new BDeleteCommand();
		
		command.execute(model);
		
		return "redirect:list";
	}
	
	// 답글 보기
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model){
		
		//request 객체 : content_view의 ID값이 넘어온다.
		System.out.println("reply_view()");
		
		model.addAttribute("request",request);
		
		command = new BReplyViewCommand();
		
		command.execute(model);
		
		return "reply_view";
	}
	
	// 답글 쓰기
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model){
		System.out.println("reply()");
		
		model.addAttribute("request",request);
		
		command = new BReplyCommand();
		
		command.execute(model);
		
		return "redirect:list";
		
		
	}
	
	
	
}
