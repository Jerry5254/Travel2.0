$(function () {
    var cityId = getQueryString("cityId");
    var cityInfoUrl = '/travel/city/city_id';
    var getCityUrl = '/travel/city/citylist';

    getCityList();



    getCity();

    function getCity() {
        $.ajax({
            type: "get",
            url: cityInfoUrl,
            data: {cityId: cityId},
            dataType: "json",//后台处理后返回的数据格式
            success: function (data) {//ajax请求成功后触发的方法
                $('#intro-city').html(data.city.cname);
                $('#intro-article').html(data.city.cdes);
                var amount = data.picAmount;
                if (amount > 0) {
                    var picButtonHtml = '';
                    var picHtml = '';
                    for (var i = 0; i < amount; i++) {
                        if (i == 0) {
                            picButtonHtml = '<li data-target="#myCarousel" data-slide-to="0" class="active"></li>';
                            picHtml = '<div class="active item"><img src="http://localhost:8080/travel/images/' + data.picList[i] + '" style="width:480px;height:328px"></div>';
                        } else {
                            picButtonHtml += '<li data-target="#myCarousel" data-slide-to="' + i + '"></li>';
                            picHtml += '<div class="item"><img src="http://localhost:8080/travel/images/' + data.picList[i] + '" style="width:480px;height:328px"></div>';
                        }
                    }
                    $('#pic-button').html(picButtonHtml);
                    $('#show-city-pic').html(picHtml);
                } else {
                    var cityHtml = '<h3 style="text-align: center">(」゜ロ゜)」很可惜，该城市暂时还没有图片呢</h3>';
                    $('#myCarousel').hide();
                    $('#intro-pic').html(cityHtml);
                }


                getFood(data.city.cname);
                getScenic(data.city.cname);
            },
            error: function (msg) {//ajax请求失败后触发的方法
                alert(msg);//弹出错误信息
            }
        });
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    function getFood(city) {
        $.ajax({
            type: 'get',
            url: '/travel/food/cityfood',
            data: {"cityName": city},
            success: function (data) {
                if (data.success) {
                    var cityHtml = '';
                    data.cityfoodList.map(function (item, index) {
                        cityHtml += '<div style="width: 220px;margin-right: 45px;display:block;float:left">\n' +
                            '                <img data-id="' + item.fid + '" src="http://localhost:8080/travel/images/' + item.fpic + '" style="width: 220px;height:140px"/>\n' +
                            '                <p data-id="' + item.fid + '" style="font-family: \'微软雅黑 Light\';font-size: 18px;text-align: center;">' + item.fname + '</p>\n' +
                            '            </div>';

                    });
                    $('#show-food').html(cityHtml);
                } else {
                    var cityHtml = '<h3 style="text-align: center">(」゜ロ゜)」很可惜，该城市暂时还没有比较著名的美食呢</h3>';
                    $('#show-food').html(cityHtml);
                }
            }
        });
    }

    function getScenic(city) {
        $.ajax({
            type: 'get',
            url: '/travel/scenic/getscenicbycity',
            data: {"cityName": city},
            success: function (data) {
                if (data.success) {
                    var cityHtml = '';
                    data.scenicList.map(function (item, index) {
                        cityHtml += '<div style="width: 220px;margin-right: 45px;display:block;float:left">\n' +
                            '                <img data-id="' + item.sid + '" src="http://localhost:8080/travel/images/' + item.spic + '" style="width: 220px;height:140px;border-radius: 5px"/>\n' +
                            '                <p data-id="' + item.sid + '" style="font-family: \'微软雅黑 Light\';font-size: 18px;text-align: center;">' + item.sname + '</p>\n' +
                            '                <p data-id="' + item.sid + '" style="font-family: \'微软雅黑 Light\';font-size: 18px;text-align: center;">' + item.sdes + '</p>\n' +
                            '            </div>';

                    });
                    $('#show-scenic').html(cityHtml);
                } else {
                    var cityHtml = '<h3 style="text-align: center">(」゜ロ゜)」很可惜，该城市暂时还没有比较著名的景区呢</h3>';
                    $('#show-scenic').html(cityHtml);
                }
            }
        });
    }


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
        // $('#city-nav').on('click','a,li',function (e) {
        //     var id=$(this).attr('data-id');
        //     $.ajax({
        //         url:'travel/city/city_id',
        //         type:'get',
        //         data:id
        //     });
        // });
    }
})