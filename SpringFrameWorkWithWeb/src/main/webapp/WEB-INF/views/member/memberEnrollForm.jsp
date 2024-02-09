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
    </style>
</head>
<body>
    
    <!-- 메뉴바 -->
<%--     <jsp:include page="../common/header.jsp" /> --%>
	<%@ include file="../common/header.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="insert.me" method="post">
                <div class="form-group">
                    <label for="enrollUserId">* ID : </label>
                    <input type="text" class="form-control" id="enrollUserId" placeholder="Please Enter ID" name="userId" required> <br>
					<div id="checkResult" style="font-size:0.8em; display=none;"></div>
                    <label for="userPwd">* Password : </label>
                    <input type="password" class="form-control" id="userPwd" placeholder="Please Enter Password" name="userPwd" required> <br>

                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender" checked>
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>
        <br><br>

		<script>
			$(function(){
				
				//userId 라는 id값은 header에 있는 login Form에 userId 아이디와 겹치기 때문에
				//더 위에 있는 loginForm userId 요소가 잡히게 된다.
				var inputUserId = $("#enrollUserId");
				
				
				inputUserId.keyup(function(){
					
					if(inputUserId.val().trim().length >= 5){
					
						$.ajax({
							url : "checkId.me",
							data : {checkId : inputUserId.val()},
							success : function(result){
								
								
								if(result=="NNNNN"){
									$("#checkResult").show();
									$("#checkResult").css("color","red").text("사용 불가능한 아이디입니다.");
									
									$("button[type=submit]").attr("disabled",true);
								}else{//사용가능
									$("#checkResult").show();
									$("#checkResult").css("color","green").text("사용가능한 아이디입니다.");
									
									$("button[type=submit]").attr("disabled",false);
								}
								
							},error : function(){
								console.log("통신 오류");
							}
						});
				
					}
				
				});
				
				
				
			});
		
		</script>





    </div>

    <jsp:include page="../common/footer.jsp" />

</body>
</html>