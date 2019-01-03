$(function(){
    addTravelNote();
    getCityForScenic();

    //获得城市
    function getCityForScenic(){
        var url='/travel/city/citylist';
        $.getJSON(url,function (data) {
            if(data.success){
                var showCityHtml = '';
                data.cityList.map(function(item,index){
                    showCityHtml +='<option value="'+item.cname+'">'+item.cname+'</option>';
                });
                $('#scenic-city').html(showCityHtml);
            }
        });
    }

    //添加游记
    function addTravelNote(){
        var url='/travel/tn/addtravelnote';
        $('#submit').click(function(){
            //var html=UE.getEditor('container').getContent();
            //document.getElementById("<%=hidtext.ClientID %>").value=html;
            var html=UE.getEditor('container').getContent();
            var title=$('#title').val();
            var city=$('#scenic-city').val();
            var pic = $('#pic')[0].files[0];
            var formdata=new FormData();
            formdata.append('ueditor',html);
            formdata.append('title',title);
            formdata.append('city',city);
            formdata.append('pic',pic)
            $.ajax({
                url:url ,
                type: 'POST',
                data: formdata,
                contentType: false,
                processData: false,
                cache: false,
                async:false,
                success: function (data) {
                    console.log(data.success);
                    if (data.success) {
                        alert('添加成功');
                    } else {
                        alert('添加失败：'+data.errMsg);
                    }
                }
            });

        });
    }
})