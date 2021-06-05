<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="article" value="${articleMap.article}" />
<c:set var="imageFileList" value="${articleMap.imageFileList}" />
<%
request.setCharacterEncoding("UTF-8");
%>

<head>
<meta charset="UTF-8">
<title>글보기</title>
<style>
#tr_btn_modify {
	display: none;
}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
     function backToList(obj){
	    obj.action="${contextPath}/board/listArticles.do";
	    obj.submit();
     }
 
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("i_content").disabled=false;
		 document.getElementById("tr_btn_modify").style.display="block";
		 document.getElementById("tr_btn").style.display="none";
		 
		 var imageTotal = document.getElementsByClassName("tr_file_upload");
		 var imageCnt = 1;
		 for(var i=0; i<imageTotal.length; i++){
			 document.getElementById("i_imageFileName"+imageCnt).disabled=false;
			 document.getElementById("i_imageFileName"+imageCnt).style.visibility="visible";			 
			 imageCnt++;
		 }
	 }
	 
	 function fn_modify_article(obj){
		 obj.action="${contextPath}/board/modArticle.do";
		 obj.submit();
	 }
	 
	 function fn_originalFilesList(){
		 var FilesCnt = $("input[name=originalFileName]").length;
		 var FilesList = new Array(FilesCnt);
		 for(var i=0; i<FilesCnt; i++){
			 FilesList[i] = $("input[name=originalFileName]").eq(i).val();
			 console.log(FilesList[i]);
		 }
		 $("#originalFilesList").append("<input type='hidden' name='originalFilesList' value='"+FilesList+"' />");
	 }
	 
	 function fn_deleteFile(count){
		 var Cnt = count-1;
		 var FilesCnt = $("input[name=originalFileName]").length;
		 var FilesList = new Array(FilesCnt);
		 for(var i=0; i<FilesCnt; i++){
			 FilesList[i] = $("input[name=originalFileName]").eq(i).val();
		 }
		 $("#originalFilesList").append("<input type='hidden' name='originalFilesList' value='"+FilesList[Cnt]+"' />");		 
	 }
	 
	 function fn_remove_article(url,articleNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 }
	 
	 function fn_reply_form(url, parentNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var parentNOInput = document.createElement("input");
	     parentNOInput.setAttribute("type","hidden");
	     parentNOInput.setAttribute("name","parentNO");
	     parentNOInput.setAttribute("value", parentNO);
		 
	     form.appendChild(parentNOInput);
	     document.body.appendChild(form);
		 form.submit();
	 }
	 
	 function readURL(input, count) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $("#preview"+count).attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	     fn_deleteFile(count);
	 }  
 </script>
</head>
<body>
	<form name="frmArticle" method="post" action="${contextPath}" enctype="multipart/form-data">
		<table border=0 align="center">
			<tr>
				<td width=150 align="center" bgcolor=#FF9933>글번호</td>
				<td><input type="text" value="${article.articleNO}" disabled /><input type="hidden" name="articleNO" value="${article.articleNO}" /></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">작성자 아이디</td>
				<td><input type=text value="${article.id}" name="writer" disabled /></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">제목</td>
				<td><input type=text value="${article.title}" name="title" id="i_title" disabled /></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">내용</td>
				<td><textarea rows="20" cols="60" name="content" id="i_content" disabled />${article.content}</textarea></td>
			</tr>

			<c:if test="${not empty imageFileList && imageFileList!='null'}">
				<c:forEach var="item" items="${imageFileList}" varStatus="status">
					<tr>
						<td width="150" align="center" bgcolor="#FF9933" rowspan="2">이미지${status.count}</td>
						<td><input type="hidden" name="originalFileName" value="${item.imageFileName}" />
						<img src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${item.imageFileName}" id="preview${status.count}" /><br></td>
					</tr>
					<tr class="tr_file_upload">
						<td><input type="file" name="imageFileName${status.count}" id="i_imageFileName${status.count}" disabled onchange="readURL(this, ${status.count});" style="visibility:hidden" /></td>
					</tr>
					<input type="hidden" name="imageCnt" value="${status.end}" />
				</c:forEach>
			</c:if>
						
<%-- 			<c:choose>
				<c:when test="${not empty article.imageFileName && article.imageFileName !='null'}">
					<tr>
						<td width="150" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
						<td><input type="hidden" name="originalFileName" value="${article.imageFileName}" /> <img
							src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview" /><br></td>
					</tr>
					<tr id="tr_file_upload" style="visibility: hidden">
						<td><input type="file" name="imageFileName " id="i_imageFileName" disabled onchange="readURL(this);" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td width="150" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
						<td><input type="hidden" name="originalFileName" value="${article.imageFileName}" /></td>
					</tr>
					<tr id="tr_file_upload" style="visibility: hidden">
						<td><img id="preview" /><br>
						<input type="file" name="imageFileName " id="i_imageFileName" disabled onchange="readURL(this);" /></td>
					</tr>
				</c:otherwise>
			</c:choose> --%>
			
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">등록일자</td>
				<td><input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled /></td>
			</tr>
			<tr id="tr_btn_modify" align="center">
				<td colspan="2"><input type=button value="수정반영하기" onClick="fn_modify_article(frmArticle)"> <input type=button value="취소" onClick="backToList(frmArticle)"></td>
			</tr>

			<tr id="tr_btn">
				<td colspan="2" align="center"><c:if test="${member.id == article.id}">
						<input type=button value="수정하기" onClick="fn_enable(this.form)">
						<input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})">
					</c:if> <input type=button value="리스트로 돌아가기" onClick="backToList(this.form)"> <input type=button value="답글쓰기" onClick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNO})">
				</td>
			</tr>
		</table>
		<div id="originalFilesList" style="visibility:hidden"	></div>		
	</form> 
</body>
</html>