$(function(){
    getNoteInfo();
    score();
    addcomment();
    getcomment();
    getAvg();
    addCollect();
    setCollect();

    //获取游记内容
    function getNoteInfo(){
        var url='/travel/tn/gettravelnote';
        $.getJSON(url,function (data){
            if(data.success){
                var titlehtml='<span style="margin-top: 20px;display: inline-block;">'+data.showtn.tn_Title+'</span>'+'<input id="tnid" style="display: none" value="' + data.showtn.tnid + '"/> ';
                var otherInfohtml='作者：<img height="30px" width="30px" style="border-radius:50%" src="http://localhost:8080/travel/images/'+
                    data.showtn.users.uicon+'"/>'
                    +data.showtn.users.uname+'&nbsp;&nbsp;城市：'+data.showtn.tncity+
                    '<input type="text" id="authorid" style="display: none" value="' + data.showtn.users.uid + '"/>';
                var contenthtml=data.showtn.tn_Content;
                var timehtml='发表于：'+data.showtn.tn_Date;
                var Img=data.showtn.tn_Pics;
                var Img2=Img.replace(/\\/g,"\/");
                $('#fengmian').attr("style","background-image:url('http://localhost:8080/travel/images/"+Img2+"');background-size: 100% 100%");
                $('#content').html(contenthtml);
                $('#title').html(titlehtml);
                $('#otherInfo').html(otherInfohtml);
                $('#time').html(timehtml);
            }
        });
    }


    //获取该游记平均分
    function getAvg(){
        var avgurl="/travel/score/getavgscore";
        $.getJSON(avgurl,function (data){
            if(data.success){
                console.log(data.avg);
                if((data.avg)!=null){
                    console.log(data.avg)
                    var avghtml='<span>'+data.avg+'</span>分/5分';
                    $('#avgScore').html(avghtml);
                }else{
                    var html='该游记还未获得评分';
                    $('#avgScore').html(html);
                }
            }
        });
    }

    //与分数相关的操作
    function score(){
        var imgs=document.getElementsByName("sco");
        var url="/travel/score/addscore";

        //评分时星星图片的切换与分数的显示
        for(var i=0;i<imgs.length;i++){
            imgs[i].setAttribute("score",i+1);//设置分数
            imgs[i].onclick=function(){   //点击时调用方法
                var srcEl=event.srcElement;//通过event来获取原元素
                var score=srcEl.getAttribute("score");//获取分数
                for(var j=0;j<score;j++){
                    imgs[j].src="http://localhost:8080/travel/images/local/redstar.png";
                }
                for(var j=score;j<imgs.length;j++){
                    imgs[j].src="http://localhost:8080/travel/images/local/greystar.png";
                }
                document.getElementById("lab").innerHTML=srcEl.getAttribute("score");//通过srcEl调用getAttribute方法获取分数
            }
        }

        //提交分数
        $("#submit").click(function() {
            var scnote_id = $('#tnid').val();
            var scscore = document.getElementById("lab").innerHTML;
            var authorid=$('#authorid').val();
            console.log(scscore);
            if (scscore==("0")) {
                alert("分数不能为0！");
            } else {
                var formdata = new FormData();
                formdata.append("score", scscore);
                formdata.append("tnid", scnote_id);
                formdata.append("authorid",authorid);
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formdata,
                    contentType: false,
                    processData: false,
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data.success) {
                            alert('评分成功');
                            $('#score').css('display','none');
                            getAvg();
                        } else {
                            alert('评分失败：' + data.errMsg);
                        }
                    }
                });
            }
        });
    }

    //获取该游记的评论
    function getcomment() {
        var showurl='/travel/comment/showcomment';
        $.getJSON(showurl, function (data) {
            if (data.success) {
                if ((data.colist) != null) {
                    var commentTableHtml = '';
                    data.colist.map(function (item, index) {
                        commentTableHtml += '<div><div id="usericon"><img height="60px" width="60px" style="border-radius: 25px" src="http://localhost:8080/travel/images/'+item.users.uicon+'"/></div>' +
                            '<div id="right"><span style=" font-size: 12px; margin-bottom: 5px">'+
                            item.users.uname +'</span><br/>'+item.codetails + '</br><span style="color:grey; font-size: 12px">发表于：' + item.codate+'</span>'
                            + '</div><br/>'+'<hr style="height:1px;border:none;border-top:1px solid grey;" /></div>';
                        $('#showComment').html(commentTableHtml);
                    });

                } else {
                    var commentTableHtml = 'fails';
                    $('#showComment').html(commentTableHtml);
                }
            }
        });
    }

    //添加评论
    function addcomment() {
        var inserturl='/travel/comment/addcomment';
        $('#insert').click(function() {
            var details=$('#details').val();
            var formdata=new FormData();
            formdata.append('details',details);
            $.ajax({
                url: inserturl,
                type: 'POST',
                data: formdata,
                contentType: false,
                processData: false,
                cache: false,
                async: false,
                success: function (data) {
                    if (data.success) {
                        alert('评论成功！');
                        getcomment();
                    } else {
                        alert('评论失败：' + data.errMsg);
                    }
                }
            });
        });
    }

    //收藏该游记
    function addCollect(){
        setCollect();
        $('#collectImg').click(function() {
            var src=document.getElementById("collectImg").src;
            if(src=='http://localhost:8080/travel/images/local/collect_hou.png'){
                //document.getElementById("collectImg").src="http://localhost:8080/travel/images/local/collect_qian.png";
                var url='/travel/collect/deletecollect';
                var collectid=$('#collect-id').val();
                var formdata=new FormData();
                formdata.append('collectid',collectid);
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formdata,
                    contentType: false,
                    processData: false,
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data.success) {
                            setCollect();
                            alert("取消收藏成功！");
                        } else {
                            alert('取消收藏失败：' + data.errMsg);
                        }
                    }
                });
            }else{
                var url='/travel/collect/insertcollect';
                var formdata=new FormData();
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formdata,
                    contentType: false,
                    processData: false,
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data.success) {
                            alert("收藏成功！");
                            //document.getElementById("collectImg").src="http://localhost:8080/travel/images/local/collect_hou.png";
                            setCollect();
                        } else {
                            alert('收藏失败：' + data.errMsg);
                        }
                    }
                });
            }
        });
    }

    //设置收藏图片（已收藏和未收藏图片不同）
    function setCollect(){
        var url='/travel/collect/getcollectUserTn';
        $.getJSON(url,function (data){
            if(data.success){
                if(data.status=='已收藏'){
                    document.getElementById("collectImg").src="http://localhost:8080/travel/images/local/collect_hou.png";
                    var html='<input type="text" style="display: none;" id="collect-id" value="'+data.collect.collect_Id+'"/>';
                    $('#collectid').html(html);

                }else{
                    document.getElementById("collectImg").src="http://localhost:8080/travel/images/local/collect_qian.png";
                }
            }
        });
    }
});
