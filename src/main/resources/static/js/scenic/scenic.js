$(function () {
    var getPassCityListUrl = '/travel/city/citylist';
    var getScenicListUrl = '/travel/scenic/sceniclist';


    $('#add-scenic-btn').click(function () {
        $('#add-scenic').css({"display": "block"});
    });
    getCityForScenic(getPassCityListUrl);
    getScenicList(getScenicListUrl);

    //填写景点时获取城市下拉框
    function getCityForScenic(url) {
        $.getJSON(url, function (data) {
            if (data.success) {
                var showCityHtml = '';
                data.cityList.map(function (item, index) {
                    showCityHtml += '<option value="' + item.cname + '">' + item.cname + '</option>';
                });
                $('#scenic-city').html(showCityHtml);
            }
        });
    }

    //提交景点
    $('#submit-scenic').click(function () {
        var scenic = {};
        scenic.sname = $('#scenic-name').val();
        scenic.scity = $('#scenic-city').val();
        scenic.sdes = $('#scenic-desc').val();
        var formData = new FormData();
        formData.append("scenic", JSON.stringify(scenic));
        var scenicImg = $('#scenic-img')[0].files;
        var imgAmount = scenicImg.length;
        formData.append("imgAmount", imgAmount);
        for (var i = 0; i < imgAmount; i++) {
            var name = 'scenicImg[' + i + ']';
            formData.append(name, scenicImg[i]);
        }
        $.ajax({
            url: '/travel/scenic/addscenic',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    alert('提交成功！');
                } else {
                    alert('提交失败！' + data.errMsg);
                }
            }
        });
    });

    function getScenicList(url) {
        $.getJSON(url, function (data) {
            if (data.success) {
                var scenicHtml = '';
                data.sceniclist.map(function (item, index) {
                    scenicHtml += '<tr><td>' + item.sid + '</td><td>' + item.sname + '</td><td>' + item.scity + '</td><td>' + item.sdes + '</td><td  width="13%" class="center">\n' +
                        '        <ul class="unstyled">\n' +
                        '          <li><a href="javascript:void(0);" data-id="' + item.sid + '">删除</a></li>\n' +
                        '          <li><a href="javascript:void(0);"data-id="' + item.sid + '">整理景点图片</a></li>\n' +
                        '          <li><a href="javascript:void(0);"data-id="' + item.sid + '">修改</a></li>\n' +
                        '        </ul>\n' +
                        '      </td></tr>';
                });
            } else {
                alert("出错啦");
            }
            $('#show-sceniclist').html(scenicHtml);

        });
    }

    //删除城市方法
    $('#show-sceniclist').on('click', 'a,li', function (e) {
        $("a").attr('disabled', 'true');
        var id = $(this).attr('data-id');
        var operation = $(this).text();
        if (operation == "删除") {
            $.ajax({
                url: '/travel/scenic/deletescenic',
                type: 'post',
                data: {"scenicId": id},
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
                url: '/travel/scenic/scenic_id',
                data: {scenicId: id},
                dataType: 'json',
                success: function (data) {
                    var showScenicHtml = '';
                    $.getJSON(getPassCityListUrl, function (data1) {
                        if (data1.success) {
                            showScenicHtml = '<select id="change-scenic-city">';
                            data1.cityList.map(function (item, index) {
                                showScenicHtml += '<option value="' + item.cname + '">' + item.cname + '</option>';
                            });
                            showScenicHtml += '</select><br/>';
                        }
                        $.confirm({
                            title: '修改景点',
                            body: '<div id="confirm-modify">\n' +
                                '    景点id' + data.scenic.sid + '<br/>\n' +
                                '    景点名字<input id="change-scenic-name" type="text" value="' + data.scenic.sname + '"/><br/>\n' +
                                '    景点所在城市' + showScenicHtml + '\n' +
                                '    景点描述<textarea id="change-scenic-desc">' + data.scenic.sdes + '</textarea>\n' +
                                '</div>',
                            width: 500,
                            okHide: function (e) {
                                var changeScenicId = data.scenic.sid;
                                var changeScenicName = $('#change-scenic-name').val();
                                var changeScenicDesc = $('#change-scenic-desc').val();
                                var changeScenicCity = $('#change-scenic-city').val();
                                $.ajax({
                                    url: '/travel/scenic/modifyscenic',
                                    type: 'post',
                                    data: {
                                        "scenicId": changeScenicId,
                                        "scenicName": changeScenicName,
                                        "scenicDesc": changeScenicDesc,
                                        "scenicCity": changeScenicCity
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
        } else if (operation == "整理景点图片") {
            $.ajax({
                url: '/travel/scenic/getscenicpic',
                type: 'get',
                data: {"scenicId": id},
                success: function (data) {
                    var scenicPicHtml = '';
                    if (data.success) {
                        if (data.havePic) {
                            data.picList.map(function (item, index) {
                                scenicPicHtml += '<div style="display:inline-block;position: relative;">' +
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
                                '        ' + scenicPicHtml + '\n' +
                                '    </div>\n' +
                                '    <input id="add-scenic-pic" type="file" multiple="multiple"/>\n' +
                                '</div>',
                            shown: function (e) {
                                $('span').click(function () {
                                        if ($(this).text() == '+') {
                                            var scenicPath = $(this).data('addr');
                                            var scenicId = $(this).data('id');

                                            $.ajax({
                                                url: '/travel/scenic/deletepic',
                                                type: 'post',
                                                data: {"scenicPath": scenicPath, "scenicId": scenicId},
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
                                var scenicImg = $('#add-scenic-pic')[0].files;
                                var imgAmount = scenicImg.length;
                                var formData1 = new FormData();
                                formData1.append('scenicId', id);
                                formData1.append('imgAmount', imgAmount.toString());
                                for (var i = 0; i < imgAmount; i++) {
                                    var name = 'scenicImg[' + i + ']';
                                    formData1.append(name, scenicImg[i]);
                                }
                                $.ajax({
                                    url: '/travel/scenic/addscenicpic',
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

    //刷新景点页面
    function refreshCity() {
        getScenicList(getScenicListUrl);
    }
})