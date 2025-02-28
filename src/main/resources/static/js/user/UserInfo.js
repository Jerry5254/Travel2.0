

$(function() {
    getUserInfo();
    alterUserInfo();
    alterIcon();
    getHistoryIcon();
    getTravelNote();
    getScore();
    getComment();
    getCollect();
    deletecollect();
    deleteComment();
    deleteTn();
    active();
    selectHistory();

    //获取用户信息
    function getUserInfo() {
        var url = '/travel/users/getuserbyid';
        $.getJSON(url, function (data) {
            if (data.success) {
                var userNameHtml = '<h1>' + data.user.uname + '</h1>';
                var userIconHtml = '<img height="120px" width="120px" style="border-radius: 55px" src="http://localhost:8080/travel/images/' + data.user.uicon + '"/>';
                var userInfoHtml = '<table id="uInfo"><tr><td></td><td><input id="user-id" value="' + data.user.uid + '"style="display: none"/> </td></tr>' +
                    '<tr><td>联系方式</td><td><input id="user-mobile" required="required" value=" ' + data.user.umobile + '"/></td></tr>' +
                    '<tr><td>密码</td><td><input id="user-password" required="required" type="password" value="' + data.user.upwd + '"/></td></tr>' +
                    '<tr><td>性别</td><td><select id="user-gender" required="required" name="gender"><option id="男" name="男" value="男">男</option><option id="女" name="女" value="女">女</option></select></td></tr></table>';
                $('#user-name').html(userNameHtml);
                $('#open_btn').html(userIconHtml);
                $('#userInfotable').html(userInfoHtml);
            }
        });
    }

    //获取历史头像
    function getHistoryIcon() {
        var history = '/travel/users/gethistoryicon';
        $.getJSON(history, function (data) {
            if (data.success) {
                var historyIconHtml = '';
                data.HistoryIconList.map(function (item, index) {
                    historyIconHtml += '<a class="hi" data-id="' + item + '"> <img height="80px" width="80px" src="http://localhost:8080/travel/images/' + item + '"/></a>'
                });
                $('#history-icon').html(historyIconHtml);
            }
        });
    }

    //修改用户信息
    function alterUserInfo() {
        var alterurl = '/travel/users/modifyuser';
        $('#submit').click(function () {
            var user = {};
            user.uid = $('#user-id').val();
            user.umobile = $('#user-mobile').val();
            user.upwd = $('#user-password').val();
            user.ugender = $('#user-gender option:selected').val();
            var formData = new FormData();
            formData.append('userInfo', JSON.stringify(user));
            $.ajax({
                url: alterurl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        getUserInfo();
                        alert('修改成功');
                    } else {
                        alert('修改失败' + data.errMsg);
                    }
                }
            });
        });
    }

    //修改用户头像
    function alterIcon() {
        var alterIconUrl = '/travel/users/altericon';
        $('#alter-icon').click(function () {
            var user = {};
            user.uid = $('#user-id').val();
            var userIcon = $('#user-alter-icon')[0].files[0];
            var formData = new FormData();
            formData.append('userInfo', JSON.stringify(user));
            formData.append('userIcon', userIcon);
            $.ajax({
                url: alterIconUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        getUserInfo();
                        alert('修改成功');
                    } else {
                        alert('修改失败' + data.errMsg);
                    }
                    getHistoryIcon();
                }

            });
        });
    }

    //获取该用户写的游记
    function getTravelNote() {
        var url = '/travel/tn/gettnlistbyauthor';
        $.getJSON(url, function (data) {
            if (data.success) {
                console.log(data.tnStatus);
                if((data.tnStatus)=='no'){
                   $('#noTn').attr("style","display:block");
                }else{
                    $('#hasTn').attr("style","display:block")
                    var tnTableHtml = '';
                    var html = '</table>';
                    var html2 = '<table width="700px" border="1" style=" margin-left:100px;"><tr class="head"><td>游记标题</td><td>提交日期</td><td>游记状态</td><td colspan="2">操作</td></tr>';
                    data.tnlist.map(function (item, index) {
                        tnTableHtml +=
                            '<tr style="height: 30px"><td><a href="/travel/tn/totravelnote?travelnoteid=' + item.tnid + '" >' + item.tn_Title + '</a></td>' +
                            '<td>' + item.tn_Date + '</td>' +
                            '<td>' + item.tn_Status + '</td>' +
                            '<td class="operation"><a href="/travel/tn/toupdatetravelnote?travelnoteid=' + item.tnid + '" >修改</a></td>' +
                            '<td class="operation"><a id="delete" href="javascript:void(0);" data-id="'+item.tnid+'" >删除</a></td></tr>';
                        $('#travelNoteTable').html(html2 + tnTableHtml + html);
                    });
                }
            }
        });
    }

    //获得用户参与的评分
    function getScore() {
        var url = '/travel/score/getscorebyuser';
        $.getJSON(url, function (data) {
            if (data.success) {
                if ((data.scorelist) != null) {
                    var scoreTableHtml = '';
                    var table='<table id="scoretable" style="width: 300px;height:50px;margin: 0 auto;" border="1">' +
                        '<tr class="head"><td>游记编号</td><td>分数</td></tr>';
                    var table2='</table>';
                    data.scorelist.map(function (item, index) {
                        scoreTableHtml +='<tr><td><a href="/travel/tn/totravelnote?travelnoteid=' + item.scnote_id + '" >' + item.scnote_id + '</a></td>' +
                            '<td>' + item.scscore + '</td></tr>';
                    });
                    $('#scoreNote').html(table+scoreTableHtml+table2);
                } else {
                    var html = '您还没有为游记评过分！';
                    $('#scoreNote').html(html);
                }
            }
        });
    }

    //获得用户写的评论
    function getComment() {
        var url = '/travel/comment/showusercomment';
        $.getJSON(url, function (data) {
            if (data.success) {
                var html = '';
                data.usercommentlist.map(function (item, index) {
                    html += '<a class="brown" href="/travel/tn/totravelnote?travelnoteid=' +item.tn.tnid+ '" >' + item.tn.tn_Title+ '</a><div class="details">'
                        + item.codetails + '</div><div class="time">' + item.codate
                        + '</div><a class="del" href="javascript:void(0);" data-id="'+item.coid+'">删除</a>' +
                        '<hr style="height:1px;border:none;border-top:1px solid #555555;" />';
                });
                $('#showcomment').html(html);
            }
        });
    }

    //选择历史头像
    function selectHistory(){
        var alterurl = '/travel/users/modifyuser';
        $('#history-icon').on('click','a',function(e){
            $('.hi').removeClass("seleted-his")
            $(this).addClass("seleted-his")
            var img=$(this).attr("data-id");
            var user = {};
            user.uicon = img;
            user.uid = $('#user-id').val();
            var formData = new FormData();
            formData.append('userInfo', JSON.stringify(user));
            $.ajax({
                url: alterurl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        getUserInfo();
                        alert('修改成功');
                    } else {
                        alert('修改失败' + data.errMsg);
                    }
                }
            });
        });
    }

    //删除用户收藏的游记
    function deletecollect() {
        var url = '/travel/collect/deletecollect';
        $('#showcollect').on('click','a',function(e){
          $('a').attr('disabled','true');
          var id=$(this).attr('data-id');
          var tnid=$('#tniddd').val();
          // var formdata=new FormData();
          // formdata.append('collectid',id);
          // formdata.append('tnid',tnid);
          var operation=$(this).text();
          if(operation=="删除"){
              $.ajax({
                  url: url,
                  type: 'POST',
                  data: {collectid:id,tnid:tnid},
                  cache: false,
                  async: false,
                  success: function (data) {
                      if (data.success) {
                          alert("删除成功！");
                          getCollect();
                      } else {
                          alert('删除失败：' + data.errMsg);
                      }
                  }
              });
          }
        });
    }

    //获得用户收藏的游记
    function getCollect() {
        var url = '/travel/collect/showcollect';
        $.getJSON(url, function (data) {
            console.log(data.success)
            console.log(data.collectlist)
            if (data.success) {
                var html = '';
                data.collectlist.map(function (item, index) {
                    html += '<a class="titl" href="/travel/tn/totravelnote?travelnoteid=' + item.tn.tnid + '" >' + item.tn.tn_Title + '</a><br/>' +
                        '<a href="/travel/tn/totravelnote?travelnoteid=' + item.tn.tnid + '" ><img src="http://localhost:8080/travel/images/'
                        + item.tn.tn_Pics + '"/></a></br><div id="time">'
                        + item.collect_Date+'<input type="text" style="display: none" id="tniddd" value="'+item.tn.tnid+'"/>'
                        + '</div><a class="delete" href="javascript:void(0);" data-id="'+item.collect_Id+'">删除</a>' +
                        '<hr style="height:1px;border:none;border-top:1px solid #555555;" />';
                });
                $('#showcollect').html(html);
            }
        });
    }

    //删除评论
    function deleteComment(){
        var url='/travel/comment/deletecomment';
        $('#showcomment').on('click','a',function(e){
            $('a').attr('disabled','true');
            var id=$(this).attr('data-id');
            var operation=$(this).text();
            if(operation=="删除"){
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {commentid:id},
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data.success) {
                            alert("删除成功！");
                            getComment();
                        } else {
                            alert('删除失败：' + data.errMsg);
                        }
                    }
                });
            }
        });
    }

    //删除游记
    function deleteTn(){
        $('#travelNoteTable').on('click','a',function(e){
            $('a').attr('disabled','true');
            var url='/travel/tn/deletetn';
            var id=$(this).attr('data-id');
            var operation=$(this).text();
            if(operation=="删除"){
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {tnid:id},
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data.success) {
                            alert("删除成功！");
                            getTravelNote();
                        } else {
                            alert('删除失败：' + data.errMsg);
                        }
                    }
                });
            }
        });
    }

    function active(){
        $('#travelnote').click(function(){
           $('#travelnote').addClass("active");
           $('#mycollect').removeClass("active");
           $('#edit').removeClass("active");
           $('#userTravelNote').attr("style","display:block");
           $('#manage').attr("style","display:none");
           $('#collect').attr("style","display:none");
        });

        $('#mycollect').click(function(){
            $('#mycollect').addClass("active");
            $('#travelnote').removeClass("active");
            $('#edit').removeClass("active");
            $('#userTravelNote').attr("style","display:none");
            $('#manage').attr("style","display:none");
            $('#collect').attr("style","display:block");
        });

        $('#edit').click(function(){
            $('#edit').addClass("active");
            $('#travelnote').removeClass("active");
            $('#mycollect').removeClass("active");
            $('#userTravelNote').attr("style","display:none");
            $('#manage').attr("style","display:block");
            $('#collect').attr("style","display:none");
        });

        $('#mage-info').click(function(){
            $('#mage-info').addClass("active");
            $('#mage-com').removeClass("active");
            $('#mage-score').removeClass("active");
            $('#userInfo').attr("style","display:block");
            $('#showcomment').attr("style","display:none");
            $('#showscore').attr("style","display:none");
        });

        $('#mage-com').click(function(){
            $('#mage-info').removeClass("active");
            $('#mage-com').addClass("active");
            $('#mage-score').removeClass("active");
            $('#userInfo').attr("style","display:none");
            $('#showcomment').attr("style","display:block");
            $('#showscore').attr("style","display:none");
        });

        $('#mage-score').click(function(){
            $('#mage-info').removeClass("active");
            $('#mage-com').removeClass("active");
            $('#mage-score').addClass("active");
            $('#userInfo').attr("style","display:none");
            $('#showcomment').attr("style","display:none");
            $('#showscore').attr("style","display:block");
        });

    }

})
