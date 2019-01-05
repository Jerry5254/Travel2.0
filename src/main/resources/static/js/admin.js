$(function () {
    var getPassCityListUrl = '/travel/city/citylist';
    var getNCityListUrl = '/travel/city/ncitylist';
    var getAllCityListUrl = '/travel/city/allcitylist';
    var registerCityUrl = '/travel/city/addcity';

    getCityList(getAllCityListUrl);

    $('#food-button').click(function () {
        $('#left-space li').css({"color": "white"});
        $('#food-button').css({"color": "#EEDC82"});
        $('#city').hide();
        $('#tn').hide();
        $('#scenic').hide();
        $('#food').show();
    });

    $('#tn-button').click(function () {
        $('#left-space li').css({"color": "white"});
        $('#tn-button').css({"color": "#EEDC82"});
        $('#city').hide();
        $('#tn').show();
        $('#scenic').hide();
        $('#food').hide();
    });

    $('#scenic-button').click(function () {
        $('#left-space li').css({"color": "white"});
        $('#scenic-button').css({"color": "#EEDC82"});
        $('#city').hide();
        $('#food').hide();
        $('#tn').hide();
        $('#scenic').show();
    });

    $('#city-button').click(function () {
        $('#left-space li').css({"color": "white"});
        $('#city-button').css({"color": "#EEDC82"});
        $('#scenic').hide();
        $('#food').hide();
        $('#tn').hide();
        $('#city').show();
    });


    $('#show-all-city').click(function () {
        getCityList(getAllCityListUrl);
        $('#cityshow-type span').text("所有城市");
    });
    $('#show-pass-city').click(function () {
        getCityList(getPassCityListUrl);
        $('#cityshow-type span').text("已通过城市");
    });
    $('#show-n-city').click(function () {
        getCityList(getNCityListUrl);
        $('#cityshow-type span').text("审核中城市");
    });

    $supDialog = $('#J_addsuppliersDialog');
    $supDialog.on('click', '.J_addOneSupplier', function (e) {
        $supDialog.modal('shadeIn');
        var city = {};
        city.cname = $('#new-city-name').val();
        city.cdes = $('#new-city-desc').val();

        var cityImg = $('#new-city-pic')[0].files;
        var imgAmount = cityImg.length;
        var formData1 = new FormData();
        formData1.append('imgAmount', imgAmount.toString());
        for (var i = 0; i < imgAmount; i++) {
            var name = 'cityImg[' + i + ']';
            formData1.append(name, cityImg[i]);
        }
        formData1.append("newCityInfo", JSON.stringify(city));
        return $.confirm({
            title: '确认',
            body: '您确认添加这个城市吗？',
            backdrop: false,
            okHide: function () {
                $.ajax({
                    url: '/travel/city/addcitybyadmin',
                    type: 'post',
                    data: formData1,
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

    function getCityList(url) {
        $.getJSON(url, function (data) {
            if (data.success) {
                var cityHtml = '';
                data.cityList.map(function (item, index) {
                    if (item.cstatus == "审核通过") {
                        cityHtml += '<tr><td>' + item.cid + '</td><td>' + item.cname + '</td><td>' + item.cstatus + '</td><td>' + item.cdes + '</td><td>' + item.chitNumber + '</td><td  width="13%" class="center">\n' +
                            '        <ul class="unstyled">\n' +
                            '          <li><a href="javascript:void(0);" data-id="' + item.cid + '">删除</a></li>\n' +
                            '          <li><a href="javascript:void(0);"data-id="' + item.cid + '">整理城市图片</a></li>\n' +
                            '          <li><a href="javascript:void(0);"data-id="' + item.cid + '">修改</a></li>\n' +
                            '        </ul>\n' +
                            '      </td></tr>';
                    } else {
                        cityHtml += '<tr><td>' + item.cid + '</td><td>' + item.cname + '</td><td>' + item.cstatus + '</td><td>' + item.cdes + '</td><td>' + item.chitNumber + '</td><td  width="13%" class="center">\n' +
                            '        <ul class="unstyled">\n' +
                            '          <li><a href="javascript:void(0);" data-id="' + item.cid + '">删除</a></li>\n' +
                            '          <li><a href="javascript:void(0);"data-id="' + item.cid + '">整理城市图片</a></li>\n' +
                            '          <li><a href="javascript:void(0);"data-id="' + item.cid + '">修改</a></li>\n' +
                            '          <li><a href="javascript:void(0);" data-id="' + item.cid + '">通过审核</a></li>\n' +
                            '        </ul>\n' +
                            '      </td></tr>';
                    }
                });
            } else {
                alert("出错啦");
            }
            $('#show-citylist').html(cityHtml);

        });


    }


    //删除城市方法
    $('#show-citylist').on('click', 'a,li', function (e) {
        $("a").attr('disabled', 'true');
        var id = $(this).attr('data-id');
        var operation = $(this).text();
        if (operation == "删除") {
            $.ajax({
                url: '/travel/city/deletecity',
                type: 'post',
                data: {"cityId": id},
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
        } else if (operation == "通过审核") {
            $.ajax({
                url: '/travel/city/changecitystatus',
                type: 'post',
                data: {"cityId": id},
                success: function (data) {
                    if (data.success) {
                        $.alert({
                            hasfoot: false,
                            backdrop: true,
                            title: '系统提示',
                            body: '<div style="height: 50px;color:darkred;margin: 0 auto;">该城市已通过审核</div>',
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
                            body: '<div style="height: 50px;color:darkred;margin: 0 auto;">' + data.errMsg + '</div>',
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
                url: '/travel/city/city_id',
                data: {cityId: id},
                dataType: 'json',
                success: function (data) {
                    $.confirm({
                        title: '系统提示',
                        body: '<div id="confirm-modify">\n' +
                            '    城市id' + data.city.cid + '<br/>\n' +
                            '    城市名字<input id="change-city-name" type="text" value="' + data.city.cname + '"/><br/>\n' +
                            '    城市描述<textarea id="change-city-desc">' + data.city.cdes + '</textarea>\n' +
                            '</div>',
                        width: 500,
                        okHide: function (e) {
                            var changeCityId = data.city.cid;
                            var changeCityName = $('#change-city-name').val();
                            var changeCityDesc = $('#change-city-desc').val();
                            $.ajax({
                                url: '/travel/city/modifycity',
                                type: 'post',
                                data: {"cityId": changeCityId, "cityName": changeCityName, "cityDesc": changeCityDesc},
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
                },
                error: function (msg) {//ajax请求失败后触发的方法
                    alert("出错啦");//弹出错误信息
                }
            });
        } else if (operation == "整理城市图片") {
            $.ajax({
                url: '/travel/city/getcitypic',
                type: 'get',
                data: {"cityId": id},
                success: function (data) {
                    var cityPicHtml = '';
                    if (data.success) {
                        if (data.havePic) {
                            data.picList.map(function (item, index) {
                                cityPicHtml += '<div style="display:inline-block;position: relative;">' +
                                    '<img src="http://localhost:8080/travel/images/' + item + '" width="175px"/>' +
                                    '<span class="image-remove"  data-id="' + id + '" data-addr="' + item + '" style="font-color:white;text-shadow:5px 5px 5px white;font-size:30px;width:30px;height:30px;text-align:center;transform:rotate(45deg);cursor:pointer;opacity:1;top:2px;right:2px;display:block;position:absolute;">+</span></div> ';
                            });
                        }
                        $.confirm({
                            title: '整理图片',
                            backdrop: true,
                            keyboard: true,
                            body: '<div>\n' +
                                '    <div id="show-pic-list">\n' +
                                '        ' + cityPicHtml + '\n' +
                                '    </div>\n' +
                                '    <input id="add-pic" type="file" multiple="multiple"/>\n' +
                                '</div>',
                            shown: function (e) {
                                $('span').click(function () {
                                        if ($(this).text() == '+') {
                                            var cityPath = $(this).data('addr');
                                            var cityId = $(this).data('id');

                                            $.ajax({
                                                url: '/travel/city/deletepic',
                                                type: 'post',
                                                data: {"cityPath": cityPath, "cityId": cityId},
                                                success: function (data) {
                                                    if (data.success) {
                                                        $.alert({
                                                            hasfoot: false,
                                                            backdrop: false,
                                                            title: '系统提示',
                                                            body: '<div style="height: 50px;color:darkred;margin: 0 auto;">您已删除</div>',
                                                            timeout: 1000,
                                                            width: 250,
                                                            height: 100,
                                                            hidden: function (e) {
                                                                window.location.reload();
                                                                getCityList(getAllCityListUrl);
                                                            }
                                                        });
                                                    } else {
                                                        $.alert({
                                                            hasfoot: false,
                                                            backdrop: false,
                                                            title: '系统提示',
                                                            body: '<div style="height: 50px;color:darkred;margin: 0 auto;">' + '图片删除失败' + '</div>',
                                                            timeout: 1000,
                                                            width: 250,
                                                            height: 100,
                                                        });
                                                        refreshCity();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                );
                            },
                            okHide: function (e) {
                                var cityImg = $('#add-pic')[0].files;
                                var imgAmount = cityImg.length;
                                var formData = new FormData();
                                formData.append('cityId', id);
                                formData.append('imgAmount', imgAmount.toString());
                                for (var i = 0; i < imgAmount; i++) {
                                    var name = 'cityImg[' + i + ']';
                                    formData.append(name, cityImg[i]);
                                }
                                $.ajax({
                                    url: '/travel/city/addcitypic',
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
                                                body: '<div style="height: 50px;color:darkred;margin: 0 auto;">' + '提交失败' + '</div>',
                                                timeout: 1000,
                                                width: 250,
                                                height: 100,
                                            });
                                        }
                                        refreshCity();
                                    }

                                });
                            },
                            okHidden: function () {
                                refreshCity();
                            }
                        });

                    }

                }
            });

        }
        e.stopPropagation();
    });

    //刷新城市页面
    function refreshCity() {
        if ($('#cityshow-type span').text() != null) {
            var chooseStatus = $('#cityshow-type span').text();
            if (chooseStatus == "所有城市") {
                getCityList(getAllCityListUrl);
            } else if (chooseStatus == "审核中城市") {
                getCityList(getNCityListUrl);
            } else {
                getCityList(getPassCityListUrl);
            }
        } else {
            getCityList(getAllCityListUrl);
        }
    }
})