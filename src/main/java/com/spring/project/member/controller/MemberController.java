package com.spring.project.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.project.member.vo.MemberVO;

public interface MemberController {
	
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView addMember(@ModelAttribute("member") MemberVO memberVO, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView removeMember(@RequestParam("id") String id, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
			
	public ModelAndView login(@ModelAttribute("member") MemberVO memberVO, 
			RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView logout(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) throws Exception;			

}			
			
			
			
			