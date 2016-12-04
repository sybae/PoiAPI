package com.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

	/**
	 * 초기 화면 제공
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView guidebook(Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index.html");
		return mv;
	}
}