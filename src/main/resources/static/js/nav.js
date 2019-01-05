$(function () {
    var getCityUrl = '/travel/city/citylist';

    getCityList();

    $('#about_us').click(function () {
        $.alert({
            hasfoot: false,
            backdrop: false,
            title: '关于我们',
            body: '<div style="height: 50px;color:black;margin: 0 auto;">' + '我们是一家旅游网站' + '</div>',
            width: 250,
            height: 100,
        });
    });
    $('#contact_us').click(function () {
        $.alert({
            hasfoot: false,
            backdrop: false,
            title: '联系我们',
            body: '<div style="height: 50px;color:black;margin: 0 auto;">' + '联系方式：j.y5254@foxmail.com' + '</div>',
            width: 250,
            height: 100,
        });
    });


    function getCityList() {
        $.getJSON(getCityUrl, function (data) {
            if (data.success) {
                var cityHtml = '';
                data.cityList.map(function (item, index) {
                    cityHtml += '<li role="presentation" ><a role="menuitem" tabindex="-1" href="/travel/city/getcity?cityId=' + item.cid + '' + '" data-id="' + item.cid + '">' + item.cname + '</a></li>';

                });
                $('#city-nav').html(cityHtml);
            }
        });
    }


})