package com.goodee.cash.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.service.ICashService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CashController {
	@Autowired
	private ICashService cashService;
	
	@GetMapping("/calendar")
	public String calendar(HttpSession session, Model model,
							@RequestParam(required = false, name="targetYear") Integer targetYear,
							@RequestParam(required = false, name="targetMonth") Integer targetMonth) {
		log.debug("CashController.calendar() param tagetYear : " + targetYear);
		log.debug("CashController.calendar() param targetMonth : " + targetMonth);
		// 세션에서 로그인된 memberId 추출
		// session.getAttribute("loginMember");
		String memberId = "user1";
		
		Map<String, Object> resultMap = cashService.getCalendar(memberId, targetYear, targetMonth);
		
		model.addAttribute("memberId", memberId);
		model.addAttribute("targetYear", resultMap.get("targetYear"));
		model.addAttribute("targetMonth", resultMap.get("targetMonth"));
		model.addAttribute("lastDate", resultMap.get("lastDate"));
		model.addAttribute("beginBlank", resultMap.get("beginBlank"));
		model.addAttribute("endBlank", resultMap.get("endBlank"));
		model.addAttribute("totalTd", resultMap.get("totalTd"));
		model.addAttribute("cashbookList", resultMap.get("cashbookList"));
		
		
		log.debug("CashController.calendar() resultMap : " + resultMap.toString());
		return "calendar";
	}
	
	

}
