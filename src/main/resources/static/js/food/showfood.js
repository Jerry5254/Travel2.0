$(function () {
    getFoodList();

    $('#main-food').on('click', 'h3', function () {
        var name = $(this).text();
        $.ajax({
            type: 'get',
            url: '/travel/city/getcitybyname',
            data: {"cityName": name},
            success: function (data) {
                var id = data.city.cid;
                $.ajax({
                    type: 'get',
                    url: '/travel/city/addpoint',
                    data: {"cityId": id}
                });
                $(location).attr('href', '/travel/city/getcity?cityId=' + id);
            }
        });
        e.stopPropagation();
    });

    function getFoodList() {
        $.ajax({
            type: 'get',
            url: '/travel/food/getfoodlist',
            success: function (data) {
                if (data.success) {
                    var cityHtml = '';
                    data.foodList.map(function (item, index) {
                        cityHtml += '<div style="margin-bottom:50px;width:180px;height:230px;border: 1px solid lightgrey;box-shadow: 2px 1px 2px lightgrey;margin-right: 50px;display:block;float:left">' +
                            '<img style="width: 180px;height:120px" src="http://localhost:8080/travel/images/' + item.fpic + '"/>\n' +
                            '            <div>\n' +
                            '                <p style="margin-top:30px;font-family: \'微软雅黑 Light\';font-size: 18px;text-align: center;">' + item.fname + '</p>\n' +
                            '                <h3 style="cursor:pointer;margin-top:20px;font-family: \'微软雅黑 Light\';font-size: 18px;text-align: center;color:#CD853F">' + item.fcity + '</h3>\n' +
                            '            </div></div>';

                    });
                    $('#main-food').html(cityHtml);
                }
            }
        });
    }
})