// 댓글 수정 활성화

function f_modifyReplyBtnOn(e) {

		let ev = $(event.target);

		ev.hide();
		ev.next().show();
		ev.parents(".comment-div1").children(".comment-col2").attr('disabled', false);
		ev.parents(".comment-div1").children(".comment-col2").css({
			boxShadow: "0px 0px 3px 3px rgba(255, 7, 7, 0.589)"
		})

}

// $(".modify-comment-btn").on('click', function(){
// 	console.log("댓글 수정활성화 버튼이 클릭되었습니다.");

// 	$(this).hide();
// 	$(this).next().show();
// 	$(this).parents(".comment-div1").children(".comment-col2").attr('disabled', false);

// })




// 댓글 수정완료 버튼
// function f_modifyReplySubmitBtn(e) {

// 	let ev = $(event.target);

// 	ev.hide();
// 	ev.parent().children(".modify-comment-btn").show();
// 	ev.parents(".comment-div1").children(".comment-col2").attr('disabled', true);
// }


// $(".modify-comment-submit").on('click', function (){
// 	console.log("댓글 수정완료 버튼이 클릭되었습니다.");

// 	$(this).hide();
// 	$(this).parent().children(".modify-comment-btn").show();
// 	$(this).parents(".comment-div1").children(".comment-col2").attr('disabled', true);
	
// })


function f_ReplyReplyBtnOn(e) {

	let check = $(event.target).parents(".comment").children(".comment-div2");

	if(check.css("display") == "none"){
		check.show();
	} else {
		check.hide();
	}
}



// 댓글 답글달기 활성화
// $(".show-comment-form").on('click', function(){
// 	console.log("댓글 답글달기 버튼이 클릭되었습니다.");
// 	let check = $(this).parents(".comment").children(".comment-div2");

// 	if(check.css("display") == "none"){
// 		check.show();
// 	} else {
// 		check.hide();
// 	}
// })


