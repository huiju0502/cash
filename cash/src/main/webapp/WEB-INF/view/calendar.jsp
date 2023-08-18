<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
		<a href="/cash/calendar?targetYear=${targetYear}&targetMonth=${targetMonth-1}">이전달</a>
		${targetYear}년 ${targetMonth+1}월 달력
		<a href="/cash/calendar?targetYear=${targetYear}&targetMonth=${targetMonth+1}">다음달</a>
	</h1>
	<table border="1">
		<tr>
			<td>일</td>
			<td>월</td>
			<td>화</td>
			<td>수</td>
			<td>목</td>
			<td>금</td>
			<td>토</td>
		</tr>
		<tr>
			<c:forEach var="i" begin="0" end="${totalTd-1}" step="1">
				<c:if test="${i!=0 && i%7==0}">
					</tr><tr>
				</c:if>
				<c:set var="d" value="${i - beginBlank + 1}"></c:set>
				<c:if test="${d < 1 || d > lastDate}">
					<td></td>
				</c:if>
				<c:if test="${d >= 1 && d <= lastDate}">
					<td>${d}
						<c:forEach var="c" items="${cashbookList}">
							<c:if test="${d == fn:substring(c.cashbookDate,8,10)}">
								<div>
									<c:if test="${c.category == '수입'}">
										<span>+${c.price}</span>
									</c:if>
									<c:if test="${c.category == '지출'}">
										<span style="color:red;">-${c.price}</span>
									</c:if>
								</div>
							</c:if>
						</c:forEach>
					</td>
				</c:if>
			</c:forEach>
		</tr>
	</table>
</body>
</html>