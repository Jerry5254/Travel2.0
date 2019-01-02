$(function () {
    var getCityUrl = '/travel/city/citylist';

    getCityList();

    $('#GZ').css({"background-color": " #1a4029", "color": "white"});


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