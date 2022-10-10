
console.log("Reply Module....");

var replyService = (function () {
    //--------------------
    // 댓글목록 불러오기
    //--------------------
    function getList(param, callback, error){

        let bno = param.bno;
        let page = param.page;

        $.ajax({
            url: "/replies/pages/" + bno + "/" + page,
            type: "GET",
            dataType: "json",

            // 성공시....
            success:function(data){
                if(callback){
                    callback(data);
                }
            },

            error: function(xhr, status, error){
                if(error){
                    error();
                }
            }
        })

    } // getList

    //--------------------
    // 댓글 추가
    //--------------------
    function add(reply, callback, error){

        $.ajax({
            type: 'POST',
            url : "/replies/new",
            data : JSON.stringify(reply),   // 전송할 DATA
            contentType : "application/json; charset=utf-8", // 전송할 DATA TYPE
            
            success : function (result, status, xhr){
                if(callback){
                    callback(result);
                }
            },

            error : function (xhr, status, er){
                if(error){
                    error(er);
                }
            }
        })

    } // add

    //--------------------
    // 댓글 수정
    //--------------------
    function modify(reply, callback, error){

        $.ajax({
            type: 'POST',
            url: '/replies/modify/',
            data: JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            
            success : function(result, status, xhr){
                if(callback){
                    callback(result);
                }
            },

            error : function (xhr, status, er){
                if(error){
                    error(er);
                }
            }

        })

    } // modify

    //--------------------
    // 댓글 삭제
    //--------------------
    function remove(reply, callback, error){
        
        $.ajax({
            type: 'DELETE',
            url: '/replies/articles/' + reply.bno + "/replys/" + reply.rno,

            success : function(result, status, xhr){
                if(callback){
                    callback(result);
                }
            },

            error : function (xhr, status, er){
                if(error){
                    error(er);
                }
            }

        })
    } // remove

    //--------------------
    // DB시간 구하기
    //--------------------
    function getCurrTime() {
        var currTime;

        $.ajax({
            type: 'POST',
            url: '/replies/getTime',
            dataType: 'JSON',
            async: false,
            success : function(result, status, xhr){
                currTime = result;
            },

            error : function (){
                
                var jsTime = new Date();
                currTime = jsTime.getTime();
                
            }

        })

        return currTime;

    } // getCurrTime


    //--------------------
    // 날짜포맷팅
    //--------------------
    function timeFormat(currTime, targetTime){

        // 현재시간
        var currentTime = new Date(currTime);

        // 포맷팅 대상 시간
        var formatTime = new Date(targetTime);

        // 현재시간 년/월/일/ 계산
        var currentY = ('0'+ currentTime.getFullYear()).slice(-2);
        var currentM = ('0'+ (currentTime.getMonth() + 1)).slice(-2);
        var currentD = ('0'+ currentTime.getDate()).slice(-2);

        // 포맷팅 대상 년/월/일 계산
        var formatY = ('0'+ formatTime.getFullYear()).slice(-2);
        var formatM = ('0'+ (formatTime.getMonth() + 1)).slice(-2);
        var formatD = ('0'+ formatTime.getDate()).slice(-2);
        var formatH = ('0'+ formatTime.getHours()).slice(-2);
        var formatMM = ('0'+ formatTime.getMinutes()).slice(-2);

        if(currentY == formatY && currentM == formatM && currentD == formatD){

            return '오늘 ' + formatH + ':' + formatMM;

        } else {

            return formatY + '.' + formatM + '.' + formatD;
        }

    } // timeFormat

    return {
        getList : getList,
        add : add,
        modify : modify,
        remove : remove,
        getCurrTime : getCurrTime,
        timeFormat : timeFormat
    }

})();


