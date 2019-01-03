

$(function() {
    getUserInfo();
    alterUserInfo();
    alterIcon();
    getHistoryIcon();
    alterIconHistory();
    getTravelNote();
    getScore();
    getComment();
    getCollect();
    deletecollect();
    deleteComment();
    deleteTn();

    //获取用户信息
    function getUserInfo() {
        var url = '/travel/users/getuserbyid';
        $.getJSON(url, function (data) {
            if (data.success) {
                var userNameHtml = '<h1>userName:' + data.user.uname + '</h1>';
                var userIconHtml = '<img height="120px" width="120px" style="border-radius: 55px" src="http://localhost:8080/travel/images/' + data.user.uicon + '"/>';
                var userInfoHtml = '<tr><td></td><td><input id="user-id" value="' + data.user.uid + '"style="display: none"/> </td></tr>' +
                    '<tr><td>联系方式</td><td><input id="user-mobile" value=" ' + data.user.umobile + '"/></td></tr>' +
                    '<tr><td>密码</td><td><input id="user-password" type="password" value="' + data.user.upwd + '"/></td></tr>' +
                    '<tr><td>性别</td><td><select id="user-gender" name="gender"><option id="男" name="男" value="男">男</option><option id="女" name="女" value="女">女</option></select></td></tr>';
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
                    historyIconHtml += '<div class="hist" id="' + item + '"> <img height="50px" width="50px" src="http://localhost:8080/travel/images/' + item + '"/></div>'
                });
                $('#history-icon').html(historyIconHtml);
            }
        });
    }

    //提交历史头像
    function alterIconHistory() {
        var alterurl = '/travel/users/modifyuser';
        $('#alter-icon-history').click(function () {
            // $('.hist').click(function () {
            //     //console.log()
            //     var val = $(this).attr('id');
            //     alert(val);
            // });
        });
        console.log($('#history-icon .hist'))
        $('.hist').click(function () {
            alert('12');
        });
        console.log('123');
    }

    //修改用户信息
    function alterUserInfo() {
        var alterurl = '/travel/users/modifyuser'
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
                var tnTableHtml = '';
                var html = '</table>';
                var html2 = '<table><tr class="head"><td>游记标题</td><td>提交日期</td><td>游记状态</td><td colspan="2">操作</td></tr>';
                data.tnlist.map(function (item, index) {
                    tnTableHtml +=
                        '<tr><td><a href="/travel/tn/totravelnote?travelnoteid=' + item.tnid + '" >' + item.tn_Title + '</a></td>' +
                        '<td>' + item.tn_Date + '</td>' +
                        '<td>' + item.tn_Status + '</td>' +
                        '<td><a href="/travel/tn/toupdatetravelnote?travelnoteid=' + item.tnid + '" >修改</a></td>' +
                        '<td><a id="delete" href="javascript:void(0);" data-id="'+item.tnid+'" >删除</a></td></tr>';
                    $('#travelNoteTable').html(html2 + tnTableHtml + html);
                });
            } else {
                var html = '<h3>您还未写过游记...</h3>';
                $('#addTravelNote').html(html);
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
                    data.scorelist.map(function (item, index) {
                        scoreTableHtml += '<table><tr class="head"><td>游记编号</td><td>分数</td></tr>' +
                            '<tr><td><a href="/travel/tn/totravelnote?travelnoteid=' + item.scnote_id + '" >' + item.scnote_id + '</a></td>' +
                            '<td>' + item.scscore + '</td>' +
                            '</tr></table>';
                    });
                    $('#scoreNote').html(scoreTableHtml);
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
                    html += '<hr style="height:1px;border:none;border-top:1px solid #555555;" />' +
                        '<a href="/travel/tn/totravelnote?travelnoteid=' + item.cotn_id + '" >' + item.cotn_id + '</a><br/>'
                        + item.codetails + '</br><div>' + item.codate
                        + '</div><a href="javascript:void(0);" data-id="'+item.coid+'">删除</a>';
                });
                $('#showcomment').html(html);
            }
        });
    }

    //删除用户收藏的游记
    function deletecollect() {
        var url = '/travel/collect/deletecollect';
        $('#showcollect').on('click','a',function(e){
          $('a').attr('disabled','true');
          var id=$(this).attr('data-id');
          var operation=$(this).text();
          if(operation=="删除"){
              $.ajax({
                  url: url,
                  type: 'POST',
                  data: {collectid:id},
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
                    html += '<hr style="height:1px;border:none;border-top:1px solid #555555;" />' +
                        '<a href="/travel/tn/totravelnote?travelnoteid=' + item.tn.tnid + '" >' + item.tn.tn_Title + '</a><br/>' +
                        '<img src="http://localhost:8080/travel/images/'
                        + item.tn.tn_Pics + '"/></br><div>'
                        + item.collect_Date
                        + '</div><a href="javascript:void(0);" data-id="'+item.collect_Id+'">删除</a>';
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

})
