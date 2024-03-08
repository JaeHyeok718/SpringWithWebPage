<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">

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

        #boardList {text-align:center;}
        #boardList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}


</style>

</head>
<body>

	<%@include file="common/header.jsp" %>
				
		
		
    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>조회수 TOP 5</h2>
            <br>
            <br>
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>첨부파일</th>
                    </tr>
                </thead>
                <tbody>
                     <c:if test="${empty list }">
                     	<tr>
                     		<td colspan="6">조회된 게시글이 없습니다.</td>
                     	</tr>
                     </c:if>
                  	<c:forEach items="${topList }" var="b">
                		<tr onclick="location.href='detail.bo?bno=${b.boardNo}'">
	                        <td>${b.boardNo }</td>
	                        <td>${b.boardTitle }</td>
	                        <td>${b.boardWriter }</td>
	                        <td>${b.count }</td>
	                        <td>${b.createDate }</td>
	                        <td>
		                        <c:if test="${not empty b.originName }">
		                        	★
		                        </c:if>
		                    </td>
                    	</tr>
                  	</c:forEach>
                  	
                </tbody>
            </table>
            <br>


            <br clear="both"><br>

            <br><br>
        </div>
        <br><br>

    </div>
		
		
		
		
		
		
	<jsp:include page="common/footer.jsp"/>

</body>
</html>