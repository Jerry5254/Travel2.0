$(function () {
    var registerFoodUrl = '/travel/food/addfood';
    $('#submit').click(function () {
        var food = {};
        food.fname = $('#food-name').val();
        food.fcity = $('#food-city').val();
        food.fdes = $('#food-desc').val();

        var foodImg = $('#food-img')[0].files[0];
        var formData = new FormData();
        formData.append('foodImg', foodImg);
        formData.append('foodStr', JSON.stringify(food));
        $.ajax({
            url: registerFoodUrl,
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
})