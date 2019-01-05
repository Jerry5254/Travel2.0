$(function () {
    var registerFoodUrl = '/travel/food/addfood';
    var getPassCityListUrl = '/travel/city/citylist';
    var getFoodListUrl = '/travel/food/getfoodlist';
    getCityForFood(getPassCityListUrl);


    getFoodList(getFoodListUrl);


    function getCityForFood(url) {
        $.getJSON(url, function (data) {
            if (data.success) {
                var showCityHtml = '';
                data.cityList.map(function (item, index) {
                    showCityHtml += '<option value="' + item.cname + '">' + item.cname + '</option>';
                });
                $('#food-city').html(showCityHtml);
            }
        });
    }

    function getFoodList(url) {
        $.getJSON(url, function (data) {
            if (data.success) {
                var scenicHtml = '';
                data.foodList.map(function (item, index) {
                    scenicHtml += '<tr><td>' + item.fid + '</td><td>' + item.fname + '</td><td>' + item.fcity + '</td><td  width="13%" class="center">\n' +
                        '        <ul class="unstyled">\n' +
                        '          <li><a href="javascript:void(0);" data-id="' + item.fid + '">删除</a></li>\n' +
                        '          <li><a href="javascript:void(0);"data-id="' + item.fid + '">修改</a></li>\n' +
                        '        </ul>\n' +
                        '      </td></tr>';
                });
            } else {
                alert("出错啦");
            }
            $('#show-foodlist').html(scenicHtml);

        });
    }


    $supDialog = $('#J_addsuppliersDialog_food');
    $supDialog.on('click', '.J_addOneSupplier', function (e) {
        $supDialog.modal('shadeIn');

        var food = {};
        food.fname = $('#food-name').val();
        food.fcity = $('#food-city').val();

        var foodImg = $('#food-img')[0].files[0];
        var formData = new FormData();
        formData.append('foodImg', foodImg);
        formData.append('foodStr', JSON.stringify(food));
        return $.confirm({
            title: '确认',
            body: '您确认添加这个美食吗？',
            backdrop: false,
            okHide: function () {
                $.ajax({
                    url: registerFoodUrl,
                    type: 'post',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            $.alert({
                                hasfoot: false,
                                backdrop: false,
                                title: '系统提示',
                                body: '<div style="height: 50px;color:darkred;margin: 0 auto;">您已提交</div>',
                                timeout: 1000,
                                width: 250,
                                height: 100,
                            });
                        } else {
                            $.alert({
                                hasfoot: false,
                                backdrop: false,
                                title: '系统提示',
                                body: '<div style="height: 50px;color:darkred;margin: 0 auto;">' + data.errMsg + '</div>',
                                timeout: 1000,
                                width: 250,
                                height: 100,
                            });
                        }
                        refreshCity();
                    }
                });
            },
            hide: function () {
                return $supDialog.modal('shadeOut');
            }
        });
    });

    //刷新景点页面
    function refreshCity() {
        getFoodList(getFoodListUrl);
    }

    $('#show-foodlist').on('click', 'a,li', function (e) {
        $("a").attr('disabled', 'true');
        var id = $(this).attr('data-id');
        var operation = $(this).text();
        if (operation == "删除") {
            $.ajax({
                url: '/travel/food/deletefood',
                type: 'post',
                data: {"foodId": id},
                success: function (data) {
                    if (data.success) {
                        $.alert({
                            hasfoot: false,
                            backdrop: true,
                            title: '系统提示',
                            body: '<div style="height: 50px;color:darkred;margin: 0 auto;">您已删除</div>',
                            timeout: 1000,
                            width: 250,
                            height: 100,
                        });
                        refreshCity();
                    } else {
                        $.alert({
                            hasfoot: false,
                            backdrop: true,
                            title: '系统提示',
                            body: '<div style="height: 50px;color:darkred;margin: 0 auto;">' + '删除有问题' + '</div>',
                            timeout: 1000,
                            width: 250,
                            height: 100,
                        });
                        refreshCity();
                    }
                }
            });
        } else if (operation == "修改") {
            $.ajax({
                type: 'get',
                url: '/travel/food/food_id',
                data: {"foodId": id},
                dataType: 'json',
                success: function (data) {
                    var showScenicHtml = '';
                    $.getJSON(getPassCityListUrl, function (data1) {
                        if (data1.success) {
                            showScenicHtml = '<select id="change-food-city">';
                            data1.cityList.map(function (item, index) {
                                showScenicHtml += '<option value="' + item.cname + '">' + item.cname + '</option>';
                            });
                            showScenicHtml += '</select><br/>';
                        }
                        $.confirm({
                            title: '修改美食',
                            body: '<div id="confirm-modify">\n' +
                                '    美食id' + data.food.fid + '<br/>\n' +
                                '    美食名字<input id="change-food-name" type="text" value="' + data.food.fname + '"/><br/>\n' +
                                '    美食所在城市' + showScenicHtml + '\n' +
                                '</div>',
                            width: 500,
                            okHide: function (e) {
                                var changeFoodId = data.food.fid;
                                var changeFoodName = $('#change-food-name').val();
                                var changeFoodCity = $('#change-food-city').val();
                                $.ajax({
                                    url: '/travel/food/modifyfood',
                                    type: 'post',
                                    data: {
                                        "foodId": changeFoodId,
                                        "foodName": changeFoodName,
                                        "foodCity": changeFoodCity
                                    },
                                    success: function (data) {
                                        if (data.success) {
                                            $.alert({
                                                hasfoot: false,
                                                backdrop: true,
                                                title: '系统提示',
                                                body: '<div style="height: 50px;color:darkred;margin: 0 auto;">您已提交</div>',
                                                timeout: 1000,
                                                width: 250,
                                                height: 100,
                                            });
                                            refreshCity();
                                        } else {
                                            $.alert({
                                                hasfoot: false,
                                                backdrop: true,
                                                title: '系统提示',
                                                body: '<div style="height: 50px;color:darkred;margin: 0 auto;">' + '提交失败' + '</div>',
                                                timeout: 1000,
                                                width: 250,
                                                height: 100,
                                            });
                                        }
                                    }

                                });
                                return true;
                            }
                        });
                    });

                },
                error: function (msg) {//ajax请求失败后触发的方法
                    alert("出错啦");//弹出错误信息
                }
            });
        }
        e.stopPropagation();
    });

})