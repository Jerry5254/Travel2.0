$(function () {
    var getCityUrl = '/travel/city/citylist';

    getIndexHotCity();
    getFood("广州");
    getIndexScenic();

    $('#QD').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#QD').css({"background-color": " #1a4029", "color": "white"});
        getFood("青岛");
    });
    $('#GZ').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#GZ').css({"background-color": " #1a4029", "color": "white"});
        getFood("广州");
    });
    $('#SH').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#SH').css({"background-color": " #1a4029", "color": "white"});
        getFood("上海");
    });
    $('#XJ').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#XJ').css({"background-color": " #1a4029", "color": "white"});
        getFood("香港");
    });
    $('#SZ').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#SZ').css({"background-color": " #1a4029", "color": "white"});
        getFood("深圳");
    });
    $('#BJ').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#BJ').css({"background-color": " #1a4029", "color": "white"});
        getFood("北京");
    });
    $('#Other').click(function () {
        $('#city-food button').css({"background-color": " white", "color": "black"});
        $('#Other').css({"background-color": " #1a4029", "color": "white"});
        $(location).attr('href', 'http://localhost:8080/travel/food');
    });

    $('#GZ').css({"background-color": " #1a4029", "color": "white"});

    function getIndexScenic() {
        $.getJSON('/travel/scenic/indexsceniclist', function (data) {
            if (data.success) {
                var cityHtml = '';
                data.scenicList.map(function (item, index) {
                    cityHtml += '<div style="width: 220px;margin-right: 45px;display:block;float:left">\n' +
                        '                <img data-id="' + item.sid + '" src="http://localhost:8080/travel/images/' + item.spic + '" style="width: 220px;height:140px;border-radius: 5px"/>\n' +
                        '                <p data-id="' + item.sid + '" style="font-family: \'微软雅黑 Light\';font-size: 18px;text-align: center">' + item.sname + '</p>\n' +
                        '            </div>';
                });
                $('#show-scenic').html(cityHtml);
            }
        });
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
                }
            }
        });
    }

    function getIndexHotCity() {
        $.getJSON('/travel/city/indexcitylist', function (data) {
            if (data.success) {
                var cityHtml = '';
                data.cityList.map(function (item, index) {
                    cityHtml += '<div data-id="' + item.cid + '" style="cursor:pointer;width:180px;height:65px;border: 1px solid lightgrey;box-shadow: 2px 1px 2px lightgrey;margin-right: 20px;display:block;float:left"><img style="float: left;width: 100px;height: 65px" src="http://localhost:8080/travel/images/' + item.cpic + '"/>\n' +
                        '            <div data-id="' + item.cid + '" style="float: right">\n' +
                        '                <h4 data-id="' + item.cid + '" style="margin-right: 25px;margin-top:20px;font-family: \'微软雅黑 Light\'">' + item.cname + '</h4>\n' +
                        '            </div></div>';

                });
                $('#show-hot-city').html(cityHtml);
            }
        });
    }

    $('#show-hot-city').on('click', 'div', function () {
        var id = $(this).data('id');
        $.ajax({
            type: 'get',
            url: '/travel/city/addpoint',
            data: {"cityId": id}
        });
        $(location).attr('href', '/travel/city/getcity?cityId=' + id);
        e.stopPropagation();
    });


})