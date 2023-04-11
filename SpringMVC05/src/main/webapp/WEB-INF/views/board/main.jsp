<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Spring MVC03</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
	var csrfHeaderName = "${_csrf.headerName}";
    var csrfTokenValue = "${_csrf.token}";
  	$(document).ready(function(){
		loadList();	
		});
	
	
	function loadList(){  //서버와 통신:게시판 리스트 가져오기
		
		$.ajax({
			url:"board/all",
			type:"get",
			dataType:"json",
			success:makeView, //콜백함수
			error:function(){ alert("error"); }
		
		});
	}

	function makeView(data){
		var listHtml ="<table class='table table-bordered'>";
		listHtml+="<tr>"
		listHtml+="<td>번호</td>";
		listHtml+="<td>제목</td>";
		listHtml+="<td>작성자</td>";
		listHtml+="<td>작성일</td>";
		listHtml+="<td>조회수</td>";
		listHtml+="</tr>";
		
		$.each(data,function(index,obj){
			listHtml+="<tr>"
			listHtml+="<td>"+obj.idx+"</td>";
			listHtml+="<td id='t"+obj.idx+"'><a href='javascript:goContent("+obj.idx+")'>"+obj.title+"</a></td>";
			listHtml+="<td>"+obj.writer+"</td>";
			listHtml+="<td>"+obj.indate.split(' ')[0]+"</td>";
			listHtml+="<td id='cnt"+obj.idx+"'>"+obj.count+"</td>";
			listHtml+="</tr>";		
			
			listHtml+="<tr id='c"+obj.idx+"' style='display:none'>";
			listHtml+="<td>내용</td>";
			listHtml+="<td colspan='4'>";
			listHtml+="<textarea  id='ta"+obj.idx+"' readonly rows='7' class='form-control'></textarea>";
			if("${loginuser.memID}"==obj.memID){
				
			listHtml+="<br/>";
			listHtml+="<span id='ub"+obj.idx+"'><button class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp";
			listHtml+="<button class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</button>";
			}else{
				listHtml+="<br/>";
				listHtml+="<span id='ub"+obj.idx+"'><button disabled class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp";
				listHtml+="<button disabled class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</button>";
				
			}
			listHtml+="</td>";
			listHtml+="</tr>";
		});
		
			//로그인을 해야 보이는 부분
			if(${!empty loginuser}){
				
			listHtml+="<tr>"
			listHtml+="<td colspan='5'>"
			listHtml+="<button class='btn btn-primary btn-sm' onclick='goForm()'>글쓰기</button>"
			listHtml+="</td>"
			listHtml+="</tr>"
			}
			
			listHtml+="</table>";
			$("#view").html(listHtml);	
			
			$("#view").css("display","block"); // 보임
			$("#wform").css("display","none"); //감추고
	}
	
	//글쓰기 버튼 누르면 리스트가 감춰지고 글쓰기 폼 보임
	function goForm(){
		
		$("#view").css("display","none"); //감추고
		$("#wform").css("display","block"); //보임
		
		
	}
	
	//리스트 버튼을 누르면 리스트 보임
	function goList(){
		
		$("#view").css("display","block"); // 보임
		$("#wform").css("display","none"); //감추고
		
	}
	
	//작성
	function goInsert(){
			// var title=$("#title").val();
			//var content=$("#content").val();
			// var writer=$("#writer").val();

			var fData=$("#frm").serialize();//직렬화[객체를 데이터스트림으로 만드는것]
			$.ajax({
				url:"board/new",
				type:"post",
				data:fData,
				beforeSend:function(xhr){
					xhr.setRequestHeader(csrfHeaderName,  csrfTokenValue)
				},
				success:loadList,
				error:function(){  alert("error");  }
			});
		//데이터 초기화 	
			/* $("#title").val("");
			$("#content").val("");
			$("#writer").val(""); */
			$("#fclear").trigger("click");
			
		}
	//상세보기
	function goContent(idx){//"obj.idx"
		if($("#c"+idx).css("display")=="none"){
			
			$.ajax({
				url:"board/"+idx,
				type:"get",
				dataType:"json",
				success:function(data){
					$("#ta"+idx).val(data.content);
					
				},
				error:function(){alert("error");}
			});
			
			$("#c"+idx).css("display","table-row");// 보이게 설정
			$("#ta"+idx).attr("readonly",true); //읽기전용으로 설정
			
			
		}else{
			
			$("#c"+idx).css("display","none");// 감추게 설정
			 $.ajax({
				 url:"board/count/"+idx,
				 type:"put",
				 dataType:"json",
				 beforeSend:function(xhr){
						xhr.setRequestHeader(csrfHeaderName,  csrfTokenValue)
					},
				 success: function(data){
					 
					 $("#cnt"+idx).text(data.count);
				 },
				 error:function(){alert("error"); }
				 
			 });
			
		}
	}
	
	//삭제
	function goDelete(idx){
		$.ajax({
			url:"board/"+idx,
			type:"delete",
			 beforeSend:function(xhr){
					xhr.setRequestHeader(csrfHeaderName,  csrfTokenValue)
				},
			success:loadList,
			error: function(){alert("error");}
		});
	}
	
	//수정
	function goUpdateForm(idx){ 
		$("#ta"+idx).attr("readonly",false); //1
		
		var title=$("#t"+idx).text();
		var newInput = "<input type='text' id='nt"+idx+"' class='form-control' value='"+title+"'/>";
		$("#t"+idx).html(newInput); //2	//수정화면 버튼을 눌렀을때 있던 데이터를 가져오고 수정 페이지로 넘어감 
		
		var newbutton="<button class='btn btn-primary btn-sm' onclick='goUpdate("+idx+")'>수정</button>"
		$("#ub"+idx).html(newbutton); //3  //수정화면 버튼을 눌렀을때 수정버튼으로 바뀜 
		
	}
		function goUpdate(idx){
			var title=$("#nt"+idx).val();
			var content=$("#ta"+idx).val();
			
			$.ajax({
				url:"board/update",
				type:"put",
				contentType:'application/json;charset=utf-8',
				data: JSON.stringify({"idx":idx,"title":title,"content":content}),
				 beforeSend:function(xhr){
						xhr.setRequestHeader(csrfHeaderName,  csrfTokenValue)
					},
				success:loadList,
				error:function(){ alert("error"); }
			});
		}
		
	
		
	
   </script>
</head>

<body>
<div class="container"> 
<jsp:include page="../common/header.jsp"/>
  <h2>회원게시판</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body" id="view">panel content</div>
    <div class="panel-body" id="wform"style="display: none">
    <form id="frm">
    <input type="hidden" name="memID" id="memID" value="${loginuser.memID}">
    		<table	class="table">
    			<tr>
					<td>제목</td>
					<td><input type="text" id="title" name="title"class="form-control"></td>    
    			</tr>
  				<tr>
					<td>내용</td>
					<td><textarea rows="7" class="form-control" id="content" name="content"></textarea></td>    
    			</tr>
    			<tr>
					<td>작성자</td>
					<td><input type="text" id="writer" name="writer" class="form-control" value="${loginuser.memName}" readonly="readonly"></td>    
    			</tr> 
    			<tr>
					<td colspan="2" align="center">
				    	<button type="button" class="btn btn-success btn-sm" onclick="goInsert()">등록</button>
				    	<button type="reset" class="btn btn-warning btn-sm" id="fclear">취소</button>
				    	<button type="button" class="btn btn-info btn-sm" onclick="goList()">리스트</button>
					</td>
    			</tr>   
    		</table>
    	</form>
    </div>
    <div class="panel-footer">인프런_스프1</div>
  </div>
</div>

</body>
</html>