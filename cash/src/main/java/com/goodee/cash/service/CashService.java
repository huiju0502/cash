package com.goodee.cash.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.cash.mapper.CashMapper;
import com.goodee.cash.vo.Cashbook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CashService implements ICashService{
	@Autowired
	private CashMapper cashMapper;
	
	// 요청단에서 target Y,M 넘어오지 않으면 기본값 -1 
	public Map<String, Object>getCalendar(String memberId, Integer targetYear, Integer targetMonth) {
		
		Calendar firstDate = Calendar.getInstance();
		// 첫번째 날짜
		firstDate.set(Calendar.DATE, 1);
		
		// 원하는 년/월이 요청 매개값으로 넘어왔다면
		if(targetYear != null && targetMonth != null) {
			firstDate.set(Calendar.YEAR, targetYear);
			// API 에서 자동으로 분기 : 12가 입력되면 -> 0이 되고 년 +1, -1이 입력되면 11이되고 년 -1
			firstDate.set(Calendar.MONTH, targetMonth); 
		}
		
		targetYear = firstDate.get(Calendar.YEAR);
		targetMonth = firstDate.get(Calendar.MONTH);
		
		// 마지막 날짜
		int lastDate = firstDate.getActualMaximum(Calendar.DATE);
		
		// 1일의 요일을 이용하여 출력할 시작 공백수 -> 요일 맵핑 수 -1
		int beginBlank = firstDate.get(Calendar.DAY_OF_WEEK) -1;
		log.debug("beginBlank : " + beginBlank);
		
		// 마지막 날짜 이후 출력할 공백수
		int endBlank = 0;
		if((beginBlank + lastDate + endBlank) % 7 != 0) {
			endBlank = 7 -((beginBlank + lastDate) % 7);
		}
		
		int totalTd = beginBlank + lastDate + endBlank;
		
		// 반환값1
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("targetYear", targetYear);
		resultMap.put("targetMonth", targetMonth);
		resultMap.put("lastDate", lastDate);
		resultMap.put("beginBlank", beginBlank);
		resultMap.put("endBlank", endBlank);
		resultMap.put("totalTd", totalTd);
		
		// 반환값2(가계부 리스트)
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memberId", memberId);
		paramMap.put("targetYear", targetYear);
		paramMap.put("targetMonth", targetMonth +1);
		
		List<Cashbook> cashbookList = cashMapper.selectCashbookListByMonth(paramMap);
		resultMap.put("cashbookList", cashbookList);
		
		log.debug("CashService.getcalendar() resultMap : " + resultMap.toString());
		
		return resultMap;
		
	}

}
