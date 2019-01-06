$(function(){
    setcontent();
    updatetn();


    //设置UEditor里的内容
    function setcontent(){
        var url='/travel/tn/gettravelnote';
        $.getJSON(url,function (data){
            if(data.success){
                if((data.showtn.tn_Status)==('公开')){
                    selecthtml='<select id="status" name="status">\n' +
                        '                <option id="公开" name="公开" value="公开">公开</option>' +
                        '                <option id="私人" name="私人" value="私人">私人</option>' +
                        '            </select>'
                    $('#div2').html(selecthtml);
                }else{
                    selecthtml='<select id="status" name="gender">\n' +
                        '                <option id="私人" name="私人" value="私人">私人</option>' +
                        '                <option id="公开" name="公开" value="公开">公开</option>' +
                        '            </select>'
                    $('#div2').html(selecthtml);
                }
                var html= data.showtn.tn_Content;
                var idhtml='<input type="text" style="display: none" id="tnid" value="'+data.showtn.tnid+'"/>'
                $('#div1').html(idhtml);
                ue.ready(function(){
                    UE.getEditor('container').setContent(html);
                });
            }
        });
    }

    //更新游记
    function updatetn(){
        var url='/travel/tn/updatetn';
        $('#submit').click(function(){
            var status=$('#status option:selected').val();
            var html=UE.getEditor('container').getContent();
            var id=$('#id').val();
            var formdata=new FormData();
            formdata.append('tnId',id);
            formdata.append('ueditor',html);
            formdata.append('status',status);
            $.ajax({
                url:url ,
                type: 'POST',
                data: formdata,
                contentType: false,
                processData: false,
                cache: false,
                async:false,
                success: function (data) {
                    if (data.success) {
                        alert('修改成功');
                        window.location.href='http://localhost:8080/travel/users/touserinfo';
                    } else {
                        alert('修改失败：'+data.errMsg);
                    }
                    setcontent();
                }
            });
        });
    }
})