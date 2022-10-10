<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

	    <!-- jQuery 라이브러리 연동 방법 - 네트워크 전송방법 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>

    <script>
        $(function () {
            var result = "${LOGIN_RESULT}";
			console.log(result);
            
            if(result !=null && result.length > 0) {
                alert(result);
            }
            
        });
    </script>

</head>
<body>
    <h1>/WEB-INF/views/user/loginPost.jsp</h1>
    <hr>
    
    <c:redirect url="/board/list"></c:redirect>

</body>
</html>