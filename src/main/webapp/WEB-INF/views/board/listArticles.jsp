<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="listArticles" value="${mapArticles.listArticles}" />
<c:set var="totalArticlesNO" value="${mapArticles.totalArticlesNO}" />
<c:set var="pageNum" value="${mapArticles.pageNum}" />
<c:set var="section" value="${mapArticles.section}" />

<% 
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
 <style>
   .cls1{text-decoration:none;}
   .cls2{text-align:center; font-size:30px;}
</style>
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>
<script>
	function fn_articleForm(isLogOn,articleForm,loginForm){
	  if(isLogOn != '' && isLogOn != 'false'){
	    location.href=articleForm;
	  }else{
	    alert("로그인 후 글쓰기가 가능합니다.")
	    location.href=loginForm+'?action=/board/articleForm.do';
	  }
	}
</script>
<body>
<table align="center" border="1"  width="80%"  >
  <tr height="10" align="center"  bgcolor="lightgreen">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
  </tr>
<c:choose>
  <c:when test="${listArticles == null}" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${listArticles !=null }" >
    <c:forEach  var="article" items="${listArticles}" varStatus="articleNum" >
     <tr align="center">
	<%-- <td width="5%">${articleNum.count}</td> --%>
	<td width="5%">${article.articleNO}</td>
	<td width="10%">${article.id}</td>
	<td align='left'  width="35%">
	<span style="padding-right:20px"></span>
	   <c:choose>
	      <c:when test='${article.level > 1 }'>  
	         <c:forEach begin="1" end="${article.level}" step="1">
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	         <c:otherwise>
				<c:choose>
					<c:when test="${article.newArticle}">
						<a class='class1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
							<span style="font-size:12px;">[새글]</span>
					</c:when>
					<c:otherwise>
						<a class='class1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
	     </c:choose>
	  </td>
	  <td  width="10%">${article.writeDate}</td> 
	</tr>
    </c:forEach>
  </c:when>
</c:choose>
</table>
<div>
	<h1>section : ${section}</h1>
	<h1>pageNum : ${pageNum}</h1>
</div>
	<div class="cls2">
		<c:if test="${totalArticlesNO != null}">
			<c:choose>
				<c:when test="${totalArticlesNO > 50}">
					<c:forEach var="page" begin="1" end="${section == 1 ? 5 : (totalArticlesNO/10 - (section-1)*5)+1}" step="1">
						<c:if test="${section > 1 && page == 1}">
							<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section-1}&pageNum=${(section-1)*5}">pre</a>
						</c:if>
							<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${page}" >${(section-1)*5 + page}</a>
						<c:if test="${page == 5}">	
							<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section+1}&pageNum=${(section-1)*5 + 1}">next</a>
						</c:if>
					</c:forEach>
				</c:when>
				<c:when test="${totalArticlesNO == 50}">
					<c:forEach var="page" begin="1" end="5" step="1">
						<a class="no-uline" href="#">${page}</a>
					</c:forEach>
				</c:when>
				<c:when test="${totalArticlesNO < 50}">
					<c:choose>
						<c:when test="${totalArticlesNO%10 == 0}">
							<c:forEach var="page" begin="1" end="${totalArticlesNO/10}" step="1">
								<c:choose>
									<c:when test="${page == pageNum}">
										<a class="sel-page" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${page}">${page}</a>
									</c:when>
									<c:otherwise>
										<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${page}">${page}</a>
									</c:otherwise>
								</c:choose>	
							</c:forEach>		
						</c:when>
						<c:otherwise>
							<c:forEach var="page" begin="1" end="${totalArticlesNO/10 + 1}" step="1">
								<c:choose>
									<c:when test="${page == pageNum}">
										<a class="sel-page" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${page}">${page}</a>
									</c:when>
									<c:otherwise>
										<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${page}">${page}</a>
									</c:otherwise>
								</c:choose>	
							</c:forEach>						
						</c:otherwise>
					</c:choose>
				</c:when>		
			</c:choose>
		</c:if>
	</div>
<a class="cls1" href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do', '${contextPath}/member/loginForm.do')">
<p class="cls2">글쓰기</p></a>
</body>
</html>