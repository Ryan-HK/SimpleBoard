<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>list</title>
	
	 <!-- jQuery 라이브러리 연동 방법 - 네트워크 전송방법 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	
	
	<link rel="stylesheet" href="/resources/css/default.css">
	<link rel="stylesheet" href="/resources/css/ui.css">
	<link rel="stylesheet" href="/resources/css/ui-page.css">
	<link rel="stylesheet" href="/resources/css/font.css">
	<link rel="stylesheet" href="/resources/css/board-list">

	<style>
		/* 게시판 전체영역 */
		.board {
			display: flex;
			flex-direction: column;

			width: 100%;

			background-color: white;
			padding: 10px 10px;
			/* border: 1px solid red; */
		}

		
		.board-head {
			display: flex;
			justify-content: space-between;
			align-items: center;

			height: 30px;
			text-align: center;
			border-bottom: 1px double grey;
		}

	
		.board-content {
			display: flex;
			justify-content: space-between;
			align-items: center;

			/* height: 24px; */
			border-bottom: 1px solid rgb(230,230,230);

			/* flex-wrap: wrap; */
			padding: 5px 0;
		}

		.board-content span {
			/* height: 24px; */
		}

		
		.board-col1 {
			width: 8%;
			text-align: center;
			display: flex;
			justify-content: center;
			align-items: center;

			flex-wrap: wrap;
		}
		.board-col2 {
			width: 50%;
			margin-left: 20px;
			display: flex;
			align-items: center;
			flex-wrap: wrap;
		}

		.board-col2 a {
			width: 100%;
			cursor: pointer;
			text-decoration: none;
			color: black;
		}

		.board-col2 a span {
			color: red;
		}

		.board-col2 a:hover {
			background-color: rgb(230,230,230);
			
		}

		.board-col2 a:visited {
			color:#A8A8A8;
		}

		.board-col3 {
			display: flex;
			align-items: center;
			width: 20%;
			text-align: center;
			flex-wrap: wrap;		
		}
		.board-col4 {
			display: flex;
			align-items: center;
			width: 20%;
			text-align: center;
			flex-wrap: wrap;
		}

		
		.board-top {
			text-align: right;
			margin-bottom: 10px;
		}

		#btn-board-register, #btn-board-hot{
			background-color: #666;
			color: white;

			height: 30px;
			width: 80px;
			border-radius: 5px;

			margin: 10px 10px;
		}
		
		#btn-board-register:hover {
			box-shadow: 0px 0px 3px 3px rgba(255, 7, 7, 0.589);
		}


		/* 페이징처리 */
		.board-footer {
			display: flex;
			justify-content: space-between;
		}

		.board-footer ul {
			display: flex;
			/* border: 1px solid blue; */
		}

		.board-footer ul li {
			display: flex;
			justify-content: center;
			align-items: center;
			list-style: none;
			width: 30px;
		}

		.board-footer ul li a {
			text-decoration: none;
			text-align: center;
			width: 100%;
		}

		.currPage {
			background-color: rgb(182, 238, 182);
		}

		.prev {
			width: 50px !important;
		}
		
		.next {
			width: 50px !important;
		}

	</style>

	<script>
		$(function() {
			$("#btn-board-register").on('click', function(){
				location.href="/board/register?currPage=${pageMaker.cri.currPage}";
			})
		})

		let result = "${LOGIN_RESULT}";

		if(result != null && result.length > 0){
			alert(result);
		}

		
	</script>

</head>
<body>
	<div class="page">
		
		<!-- header -->
		<header class="header">
			<h1 class="website-title">나의 웹사이트</h1>
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
			<div class="board font-16-500">
				<div class="board-title">
					<span class="font-22-700">자유게시판</span>
				</div>

				<div class="board-top">
					<button type="button" id="btn-board-register" class="font-16-700">글등록</button>
				</div>

				<div class="board-head font-16-700">
					<span class="board-col1 ">글번호</span>
					<span class="board-col2">글제목</span>
					<span class="board-col3">글쓴이</span>
					<span class="board-col4">날짜</span>
				</div>

				<c:forEach var="board" items="${board}">
					<fmt:formatDate pattern="yy.MM.dd" value="${board.insert_ts}" var="boardTime"/>
					<fmt:formatDate pattern="yy.MM.dd" value="${currTime}" var="currentTime"/>
					

					<div class="board-content">
						<span class="board-col1">${board.bno}</span>
						<span class="board-col2">
							<a href="/board/get?bno=${board.bno}&currPage=${pageMaker.cri.currPage}">
								${board.title}
									<c:if test="${board.replyCnt > 0}">
										&nbsp;<span>[${board.replyCnt}]</span>
									</c:if>	
							</a>
						</span>
						<span class="board-col3">${board.writer}</span>
						<span class="board-col4">
							<c:choose>
								<c:when test="${boardTime==currentTime}"><fmt:formatDate pattern="HH:mm" value="${board.insert_ts}"></fmt:formatDate></c:when>
								<c:otherwise><fmt:formatDate pattern="yy.MM.dd" value="${board.insert_ts}"></fmt:formatDate></c:otherwise>
							</c:choose>
						</span>
					</div>
				</c:forEach>

			</div>

			<div class="board-footer">

				<ul>
					<c:if test="${pageMaker.prev}">
						<li class="prev"><a href="/board/list?currPage=${pageMaker.startPage - 1}">Prev</a></li>
					</c:if>

					<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li class="${pageMaker.cri.currPage == pageNum ? 'currPage' : ''}">
							<a href="/board/list?currPage=${pageNum}">${pageNum}</a>
						</li>
					</c:forEach>

					<c:if test="${pageMaker.next}">
						<li class="next"><a href="/board/list?currPage=${pageMaker.endPage + 1}">Next</a></li>
					</c:if>
				</ul>
			</div>
		</section>

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
			<form action="/user/loginPost" method="POST">
				<fieldset>
					<legend>Login Form</legend>
		
					<div><input type="text" name="userid" placeholder="User ID"></div>
					<div><input type="password" name="userpw" placeholder="User Password"></div>
					<div>Remember-me <input type="checkbox" name="rememberMe" value="1"></div>
					<p></p>
		
					<div><button type="submit">Sign-in</button></div>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>