$(function () {
    var cityId = getQueryString("cityId");
    var cityInfoUrl = '/travel/city/city_id';

    getCity();

    function getCity() {
        $.ajax({
            type: "get",
            url: cityInfoUrl,
            data: {cityId: cityId},
            dataType: "json",//后台处理后返回的数据格式
            success: function (data) {//ajax请求成功后触发的方法
                var cityHtml = '';
                cityHtml += '<li role="presentation" ><a role="menuitem" tabindex="-1" href="' + 'javascript:void(0);' + '" data-id="' + data.city.cid + '">' + data.city.cname + '</a></li>';
                $('#city-info').html(cityHtml);
            },
            error: function (msg) {//ajax请求失败后触发的方法
                alert(msg);//弹出错误信息
            }
        });
        // $.getJSON(cityInfoUrl,formData, function (data) {
        //     alert("调用方法啦");
        //     if (data.success) {
        //         var cityHtml = '';
        //         cityHtml += '<li role="presentation" ><a role="menuitem" tabindex="-1" href="' + 'javascript:void(0);' + '" data-id="' + data.city.cid + '">' + data.city.cname + '</a></li>';
        //         $('#city-info').html(cityHtml);
        //     }else{
        //         alert("出错啦");
        //     }
        // });
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
})