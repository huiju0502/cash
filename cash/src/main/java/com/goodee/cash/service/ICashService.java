package com.goodee.cash.service;

import java.util.Map;

public interface ICashService {
	public Map<String, Object>getCalendar(String memberId, Integer targetYear, Integer targetMonth);
}
