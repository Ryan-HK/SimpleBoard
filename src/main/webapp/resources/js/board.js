// 게시글 리스트이동 / 수정하기 / 삭제하기
$(function() {
	// console.log("버튼 자바스크립트 ");
	
	var sch = location.search;
	var params = new URLSearchParams(sch);
	
	var currPage = params.get('currPage');

	// 게시글 리스트로 이동
	$("#list-btn").on('click', function(){
		location.href = `/board/list?currPage=${currPage}`;
	})

	// 게시글 수정하기 버튼 눌렀을 때, 수정버튼 활성화
	$("#modify-btn").on('click', function(){
		$("#modify-btn").hide();

		$("#modify-btn-submit").show();

		$(".modify-col").css({
			boxShadow: "0px 0px 3px 3px rgba(255, 189, 7, 0.589)"
		}).attr("disabled", false);

		$(".board-title span").text("게시글 수정하기");

		$(".comment-box").hide();
		$(".hiddenlabel").show();
	})

	// 게시글 삭제하기
	$("#delete-btn").on('click', function(){
		let formObj = $(".board-register");

		formObj.attr("action", "/board/remove");

		formObj.submit();
	})
	
	
	// 게시글 이미지 바로 수정
	$(".file-modify").on('change', function(){
		readURL(this);
	});

})

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
		$(input).nextAll("img").attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}