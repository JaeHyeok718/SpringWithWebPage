<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }
        #replyArea tbody td{
        	word-break:break-all; 
        }

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <%@ include file="../common/header.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${b.boardTitle}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
    						<c:when test="${empty b.originName }">
    							첨부파일이 없습니다.
    						</c:when>                	
                    		<c:otherwise>
		                        <a href="${b.changeName }" download="${b.originName }">${b.originName }</a>
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${b.boardContent }</p></td>
                </tr>
            </table>
            <br>
			
            	
			
            <div align="center">
                <a class="btn btn-primary" href="update.bo?bno=${b.boardNo}">수정하기</a>
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
                <a id="deleteBtn" class="btn btn-danger">삭제하기</a>
            </div>
            <br><br>
            

            <!-- delete(삭제처리) 경우는 get방식으로 요청하면 해당 게시글을 
            	 mapping주소만으로도 삭제처리 할 수 있다. 때문에 해당 중요작업들은 post 방식을 사용한다. -->
            	 
<!--             <form action="delete.bo" method="post"> -->
<!--             	<input type="hidden" name="bno" value="19"> -->
            	
<!--             	<button type="submit">전송</button> -->
<!--             </form>	  -->
            	 
            <script>
            	$(function(){
            		//삭제하기 버튼에 form submit 함수 작성하기 
            		
            		//버튼요소 잡기 
            		var dlBtn = $("#deleteBtn");
            		
            		$("#deleteBtn").click(function(){
            			//form 태그가 있어야하고 해당 form태그에 각 속성들을 채우고 
            			//원하는 데이터가 있다면 해당 데이터도 같이 태그로 작성하여 채워주고 
            			//마지막으로 submit을 진행하면 된다.
            			
            			//form 태그 직접 생성
            			var formObj = $("<form>");
            			var obj = $("<input>");
            			
            			//생성된 form 태그에 action속성과 method속성값 채워주기
            			formObj.prop("action","delete.bo").prop("method","post");
            			
            			//생성된 form태그로 전송할 데이터 추가하기 
            			//1번 bno (게시글 번호 )- 해당 게시글을 삭제하려면 게시글 번호가 있어야하기 때문에
            			//<input type="hidden" name="bno" value="19">
            			var bno = $("<input>").prop("type","hidden")
            								  .prop("name","bno")
            								  .prop("value","${b.boardNo}");
            			
            			//2번 첨부파일 경로 - 해당 게시글에 첨부파일이 있었으면 해당 첨부파일도 서버에서 삭제해야하기 때문에
            			//<input type="hidden" name="filePath" value="/resources/uploadFiles/123155116262.jpg">
            			var filePath = $("<input>").prop("type","hidden")
            									   .prop("name","filePath")
            									   .prop("value","${b.changeName}");
            			
            			
            			//생성한 form 태그에 bno태그와 filePath태그들 넣어주기 
            			var obj = formObj.append(bno).append(filePath);
            			
            			
            			//생성된 form을 body에 넣어서 문서에 포함시키기 
            			$("body").append(obj);
            			
            			//최종적으로 완성된 form 태그 obj를 submit() 하기 
            			obj.submit();
            		});
            		
            	});
            </script>	 

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                    	<c:choose>
                    		<c:when test="${empty loginUser }">
                    			<th colspan="3">
	                            	<textarea class="form-control" name="replyContent" 
	                            		id="content" cols="55" rows="2" style="resize:none; width:100%;" readonly>로그인 후 이용해주세요.</textarea>
	                        	</th>
                    		</c:when>
                    		<c:otherwise>
		                        <th colspan="2">
		                            <textarea class="form-control" name="replyContent" 
		                            id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th>
                    		</c:otherwise>
                    	</c:choose>
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount"></span>)</td>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
        </div>
        <br><br>
        
        <script>
        	function selectReply(){
        		$.ajax({
        			
        			url : "replyList.bo",
        			data : {
        				bno : "${b.boardNo}"
        			},
        			success : function(result){
                    	 
                    	 var str = "";
                    	 
                    	 for(var i in result){
                    		 str += "<tr>"
	                    		 	  +"<th>"+result[i].replyWriter+"</th>"
	                    		 	  +"<td>"+result[i].replyContent+"</td>"
	                    		 	  +"<td>"+result[i].createDate+"</td>"
	                    		 	  +"</tr>";
                    	 }
                    	 
                    	 //만든 댓글 목록 테이블위치에 넣기
                    	 $("#replyArea tbody").html(str);
                    	//댓글 개수 넣기
                    	 $("#rcount").text(result.length);
        				
        				
        			},error : function(){
        				console.log("통신 오류");
        			}
        			
        		});
        	}	
        	
        	$(function(){
	        	selectReply();
	        	
	        	
	        	$("#replyArea button").click(function(){
	        		
	        		
	        		$.ajax({
	        			url : "insert.re",
	        			data : {
	        				refBno : ${b.boardNo},
	        				replyContent : $("#content").val(),
	        				replyWriter : "${loginUser.userId}"
	        			},
	        			success : function(result){
	        				
	        				if(result>0){
	        					alert("댓글등록 성공!");
	        				}else{
	        					alert("댓글등록 실패!");
	        				}
	        				
	        				selectReply();
	        				$("#content").val("");//댓글작성란 비워주기
	        			},
	        			error : function(){
	        				console.log("통신 오류");
	        			}
	        		
	        		});
	        		
	        	});
	        	
	        	
	        	
        		
        	});
        	
        	
        	
        	
        	
        	
        
        </script>
        
        
        
        
        

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>