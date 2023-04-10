<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <span class="navbar-brand">스프1탄</span>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${contextPath}/">Home</a></li>
        <li><a href="/boardMain.do">게시판</a></li>
      </ul>
      
     <c:if test="${empty loginuser}">
     	 <ul class="nav navbar-nav navbar-right">
            <li><a href="${contextPath}/memLoginForm.do"><span class="glyphicon glyphicon-log-in"></span>로그인</a></li>
            <li><a href="${contextPath}/memJoin.do"><span class="glyphicon glyphicon-user"></span>회원가입</a></li>
          </ul>
     </c:if>
     <c:if test="${!empty loginuser}">
      <div style="color: white; display: flex; justify-content: right; height:50px; align-items:center;" >
     	
     
      <ul class="nav navbar-nav navbar-right ">
            <li><a href="${contextPath}/memUpdateForm.do"><span class="glyphicon glyphicon-wrench"></span>회원정보수정</a></li>
            <li><a href="${contextPath}/memImageForm.do"><span class="glyphicon glyphicon-picture"></span>사진등록</a></li>
            <li><a href="${contextPath}/memLogout.do"><span class="glyphicon glyphicon-log-out"></span>로그아웃</a></li>
           <c:if test="${!empty loginuser}">
			  	<c:if test="${empty loginuser.memProfile}">
			  		<li><img src="${contextPath}/resources/images/main.png" style = "width: 50px; height: 50px; border-radius: 50%"/>${loginuser.memName}님</li>
			  	</c:if>
			  	
			  	<c:if test="${!empty loginuser.memProfile}">
			  		<li><img src="${contextPath}/resources/upload/${loginuser.memProfile}" style = "width: 50px; height: 50px; border-radius: 50%"/>${loginuser.memName}님</li>
			  	</c:if>
 		 </c:if>
          </ul>
      </div>
     </c:if>    
    </div>
  </div>
</nav>