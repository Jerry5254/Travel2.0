$(function(){
    getTravelNotes();

    //获取所有游记--以收藏人数排序
   function getTravelNotes() {
       url='/travel/tn/getalltn';
       $.getJSON(url, function (data) {
           if (data.success) {
               var travelnoteHtml = '';
               data.tnlist.map(function (item, index) {
                   travelnoteHtml+='<div id="tn"><div id="pic"><a href="/travel/tn/totravelnote?travelnoteid='+item.tnid+'"><img width="280px" height="200px" src="http://localhost:8080/travel/images/'+item.tn_Pics+'"/></a></div>' +
                       '<div id="right"><a id="title" href="/travel/tn/totravelnote?travelnoteid='+item.tnid+'">'+item.tn_Title+'</a>' +
                       '<div id="content">作者：'+item.users.uname+'</div>' +
                       '<div id="city">城市：'+item.tncity+'</div>'+
                       '<div id="time">发表时间：'+item.tn_Date+'</div>' +
                       '<div id="collect">收藏人数：'+item.tnhit_Number+'</div>' +
                       '</div>' +
                       '</div>';
                   $('#TravelNotes').html(travelnoteHtml);
               });
           }
       });
   }
});