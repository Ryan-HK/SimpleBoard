<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>list</title>
	<link rel="stylesheet" href="/resources/css/default.css">
	<link rel="stylesheet" href="/resources/css/ui.css">
	<link rel="stylesheet" href="/resources/css/ui-page.css">
	<link rel="stylesheet" href="/resources/css/font.css">
	<link rel="stylesheet" href="/resources/css/board-list">

	 <!-- jQuery 라이브러리 연동 방법 - 네트워크 전송방법 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	

	<!-- Socket JS 라이브러리  -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

	<style>
		/* 게시판 - 전체 영역 */
		.board-register {
			display: flex;
			flex-direction: column;
			
			padding: 10px 10px;

			height: 100%;;
			align-items: center;
			background-color: white;
			/* border: 1px solid red; */
		}

		.board-title {
			/* border: 1px solid blue; */
			width: 100%;
		}

		.register-title {
			display: flex;
			justify-content: center;
			/* border: 1px solid blue; */
			width: 100%;
		}

		.register-writer {
			display: flex;
			justify-content: center;
			/* border: 1px solid blue; */
			width: 100%;
		}

		.register-content {
			display: flex;
			justify-content: center;
			/* border: 1px solid blue; */
			width: 100%;

		}

		.register-btns {
			display: flex;
			justify-content: center;
			width: 100%;
			/* border: 1px solid blue; */
		}

		.register-btn {
			background-color: #666;
			color: white;

			height: 30px;
			width: 80px;
			border-radius: 5px;

			margin: 10px 10px;
		}

		.register-col1 {
			display: flex;
			/* border: 1px solid green; */
			width: 100%;
		}
		

		.register-col2 {
			width: 100%;
			height: 30px;
		}
		
		.register-col3 {
			width: 100%;
			resize: none;
		}

		.register-text {
			display: flex;
			justify-content: center;
			width: 100%;

			margin-top: 10px;
			
			/* border: 5px solid red; */
		}

	</style>

	<script>
		$(function() {
			$("#list-btn").on('click', function(){
				console.log("리스트로 이동");
				location.href = "/board/list?currPage=${param.currPage}";
			})
		})
	</script>

</head>
<body>
	<div class="page">
		<!-- header -->
		<header class="header">
			<h1 class="website-title">웹사이트</h1>
			<form class="search-form">
				<input type="search">
				<input type="submit" value="검색">
			</form>
			<!-- <div id="modal-switch">â</div> -->
		</header>

		<!-- menu -->
		<ul class="menu">
			<li class="menu-item">
				<a href="#" class="menu-link">Home</a>
			</li>
			<li class="menu-item">
				<a href="#" class="menu-link">About</a>
			</li>
			<li class="menu-item">
				<a href="#" class="menu-link">Product</a>
			</li>
			<li class="menu-item">
				<a href="#" class="menu-link">Contact</a>
			</li>
		</ul>
		
		<!-- <div class="content-container"> -->
		<!-- primary -->
		<section class="primary">
			<form class="board-register" action="/board/register" method="POST" enctype="multipart/form-data">
				<div class="board-title">
					<span class="font-22-700">글쓰기</span>
				</div>

				<div class="register-text">
					<span class="register-col1 font-16-700">제목</span>
				</div>

				<div class="register-title">
					<input type="text" class="register-col2" name="title" id="title" placeholder="제목을 입력하세요." required>
				</div>

				<div class="register-text">
					<span class="register-col1 font-16-700">작성자</span>
				</div>

				<div class="register-writer">
					<input type="text" class="register-col2" name="writer" id="writer" value="${sessionScope.USER_KEY.userid}" readonly>
				</div>
				
				<div>
					<input type="file" name="files" multiple>
				</div>

				<div class="register-text">
					<span class="register-col1 font-16-700">내용</span>
				</div>

				<div class="register-content">
					<textarea name="content" class="register-col3" id="content"  rows="40" required></textarea>
				</div>

				<div class="register-btns">
					<button type="submit" class="register-btn">작성완료</button>
					<button type="button" class="register-btn" id="list-btn">글리스트</button>
				</div>

				
			</form>


		<!-- footer -->
		<footer class="footer">
			Lorem ipsum dolor sit amet.
		</footer>
	</div>

	<input type="checkbox" id="modal-switch">
	<label for="modal-switch">
		<span>modal ì´ê³  ë«ê¸°</span>
	</label>
	
	<div class="modal">
		<div class="dialog">
			Lorem ipsum dolor sit amet consectetur adipisicing elit. Atque exercitationem odio, modi laboriosam a perspiciatis est delectus quisquam obcaecati vel eos natus ipsam quasi reprehenderit nihil eligendi quam aliquid! Totam vitae quis, obcaecati quos, ut aut eveniet architecto sed harum ea deleniti itaque saepe unde nulla?
		</div>
	</div>




</body>
</html>