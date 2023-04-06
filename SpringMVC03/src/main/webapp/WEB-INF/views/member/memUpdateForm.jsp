<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		if(${!empty msgType}){
				$("#messageType").attr("class","modal-content panel-warning"); 
				$("#MyMessage").modal("show");
		}	
	});
	
	function passwordCheck(){
	var memPassword=$("#memPassword").val();
	var memPassword2=$("#memPassword2").val();
	
	if(memPassword != memPassword2){
		$("#passMessage").html("비밀번호가 서로 일치하지 않습니다.").css("color","red");
		
	}else{
		$("#passMessage").html("비밀번호가 일치합니다.").css("color","green");
		$("#memPassword").val(mempassword);
	}
}
	
	function goUpdate(){
		var memAge=$("#memAge").val();
		if(memAge==null||memAge==""||memAge==0){
			
			alert("나이를 입력하세요");
			return false;
		}
		document.frm.submit(); //전송
	}

</script>	




</head>
<body>
<div class="container">
<jsp:include page="../common/header.jsp"/>
  <h2>Spring MVC03</h2>
  <div class="panel panel-default">
    <div class="panel-heading">회원정보 수정</div>
    <div class="panel-body">
    	<form name="frm" action="${contextPath}/memUpdate.do" method="post">
			<table class="table table-bordered" style="text-align: center; border: 1px solid #dddddd;">
    			<tr>
					<td style="width: 110px; vertical-align: middle;">아이디</td>    
					<td><input id="memID" name="memID" class="form-control" type="text" maxlength="20" value="${loginuser.memID}" readonly="readonly"/></td>    
					   
    			</tr>
    			<tr>
					<td style="width: 110px; vertical-align: middle;">비밀번호</td>    
					<td colspan="2"><input id="memPassword" name="memPassword" class="form-control" onkeyup="passwordCheck()" type="password" maxlength="20" placeholder="비밀번호를 입력하세요"/></td>    
    			</tr>
    			<tr>
					<td style="width: 110px; vertical-align: middle;">비밀번호확인</td>    
					<td colspan="2"><input id="memPassword2" name="memPassword2" onkeyup="passwordCheck()" class="form-control" type="password" maxlength="20" placeholder="비밀번호를 확인하세요"/></td>    
    			</tr>
    			<tr>
					<td style="width: 110px; vertical-align: middle;">이름</td>    
					<td colspan="2"><input id="memName" name="memName" class="form-control" type="text" maxlength="20" placeholder="이름을 입력하세요" value="${loginuser.memName}" readonly/></td>    
    			</tr>
    			<tr>
					<td style="width: 110px; vertical-align: middle;">나이</td>    
					<td colspan="2"><input id="memAge" name="memAge" class="form-control" type="number" maxlength="20" placeholder="나이를 입력하세요"  value="${loginuser.memAge}" readonly/></td>    
    			</tr>
    			<tr>
					<td style="width: 110px; vertical-align: middle;">성별</td>    
					<td colspan="2">
						<div class="form-group" style="text-align: center; margin: 0 auto;">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary <c:if test="${loginuser.memGender eq '남자'}"> active</c:if>">
									<input type="radio" id="memGender" name="memGender" autocomplete="off" value="남자" 
										<c:if test="${loginuser.memGender eq '남자'}"> checked</c:if> />남자								
								</label>
								<label class="btn btn-primary <c:if test="${loginuser.memGender eq '여자'}"> active</c:if>">
									<input type="radio" id="memGender" name="memGender" autocomplete="off" value="여자"
												<c:if test="${loginuser.memGender eq '여자'}"> checked</c:if> />여자
								</label>
							</div>
						</div>
					</td>    
    			</tr>
    			<tr>
					<td style="width: 110px; vertical-align: middle;">이메일</td>    
					<td colspan="2"><input id="memEmail" name="memEmail" class="form-control" type="text" maxlength="20" value="${loginuser.memEmail}" readonly/></td>    
    			</tr>
    			<tr>
					<td colspan="3" style="text-align: left">
						<span id="passMessage"></span><input type="button" class="btn btn-primary btn-sm pull-right" value="수정" onclick="goUpdate()"/>
					</td>    			   
    			</tr>     
			</table>    
    	</form>
    </div>
			 <!-- 실패 메세지(Modal) -->
			<div id="MyMessage" class="modal fade" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div id="messageType" class="modal-content panel-info">
						<div class="modal-header panel-heading">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">${msgType}</h4>
						</div>
						<div class="modal-body">
							<p>${msg}</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div>
			<div class="panel-footer">스프1탄</div>
  </div>
</div>
</body>

</html>  