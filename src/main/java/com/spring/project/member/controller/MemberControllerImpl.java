package com.spring.project.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.project.member.service.MemberService;
import com.spring.project.member.vo.MemberVO;

@Controller
public class MemberControllerImpl implements MemberController {

	@Autowired
	private MemberVO memberVO;
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String) request.getAttribute("viewName");

		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		String viewName = (String) request.getAttribute("viewName");
		List<MemberVO> membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int result = memberService.addMember(memberVO);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int result = memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(MemberVO memberVO, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(memberVO);
		
		// 로그인 X -> 글쓰기 -> action 값 할당 -> 로그인 O -> action 실행
		HttpSession session = request.getSession();		
		String action = (String) session.getAttribute("action");
		
		if (memberVO != null) {
			session.setAttribute("isLogOn", true);
			session.setAttribute("member", memberVO);
				
			if(action != null) {
				session.removeAttribute("action");
				mav.setViewName("redirect:"+action);
			} else {
				mav.setViewName("redirect:/member/listMembers.do");
			}
		} else {
			rAttr.addAttribute("result", "loginFailed");
			// 로그인 실패 -> action 값 그대로 loginForm.do 전송
			rAttr.addAttribute("action", action);
			mav.setViewName("redirect:/member/loginForm.do");
		}
		
		System.out.println("login() - action : " + action);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		session.removeAttribute("isLogOn");
		session.removeAttribute("member");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");

		return mav;
	}

	@RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
	public ModelAndView form(@RequestParam(value="result", required=false) String result,
			@RequestParam(value="action", required=false) String action, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session=request.getSession();

		// 로그인 X -> 글쓰기 -> action 값 할당
		session.setAttribute("action", action);
		
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName(viewName);
		return mav;
	}
}