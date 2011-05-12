(function($) {
  
  $(document).ready(function() {

//    $.ajax({
//      url: AJS.params.baseURL + '/rest/jirachievement/1.0/achievementlevels/count',
//      dataType: "json",
//      success: function(data, textStatus, jqXHR) {
//        var userHeader = $('#header-details-user');
//        var content =
//          '<div id="header-user-achievements">' +
//            '<span title="' + data.RED + ' red achievements">' +
//              '<span class="achievement-red">&nbsp;</span>' +
//              '<span class="achievement-count">' + data.RED + '</span>' +
//            '</span>' +
//            '<span title="' + data.BRONZE + ' bronze achievements">' +
//              '<span class="achievement-bronze">&nbsp;</span>' +
//              '<span class="achievement-count">' + data.BRONZE + '</span>' +
//            '</span>' +
//            '<span title="' + data.SILVER + ' silver achievements">' +
//              '<span class="achievement-silver">&nbsp;</span>' +
//              '<span class="achievement-count">' + data.SILVER + '</span>' +
//            '</span>' +
//            '<span title="' + data.GOLD + ' gold achievements">' +
//              '<span class="achievement-gold">&nbsp;</span>' +
//              '<span class="achievement-count">' + data.GOLD + '</span>' +
//            '</span>' +
//          '</div>';
//
//        userHeader.prepend(content);
//      },
//      error: function(jqXHR, textStatus, errorThrown) {
//
//      }
//    });

    $.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievementlevels/count', function(data) {
      if (data === null)
      {
        return;
      }

      var userHeader = $('#header-details-user');
      var content =
        '<div id="header-user-achievements">' +
          '<span title="' + data.RED + ' red achievements">' +
            '<span class="achievement-red">&nbsp;</span>' +
            '<span class="achievement-count">' + data.RED + '</span>' +
          '</span>' +
          '<span title="' + data.BRONZE + ' bronze achievements">' +
            '<span class="achievement-bronze">&nbsp;</span>' +
            '<span class="achievement-count">' + data.BRONZE + '</span>' +
          '</span>' +
          '<span title="' + data.SILVER + ' silver achievements">' +
            '<span class="achievement-silver">&nbsp;</span>' +
            '<span class="achievement-count">' + data.SILVER + '</span>' +
          '</span>' +
          '<span title="' + data.GOLD + ' gold achievements">' +
            '<span class="achievement-gold">&nbsp;</span>' +
            '<span class="achievement-count">' + data.GOLD + '</span>' +
          '</span>' +
        '</div>';

      userHeader.prepend(content);
    });
  });

})(jQuery);