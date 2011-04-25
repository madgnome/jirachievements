(function($) {
  
  $(document).ready(function() {

    $.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievementlevels/count', function(data) {
      var userHeader = $('#header-details-user');
      var content =
        '<div id="header-user-achievements">' +
          '<span title="0 red achievements">' +
            '<span class="achievement-red">&nbsp;</span>' +
            '<span class="achievement-count">' + data.RED + '</span>' +
          '</span>' +
          '<span title="1 bronze achievements">' +
            '<span class="achievement-bronze">&nbsp;</span>' +
            '<span class="achievement-count">' + data.BRONZE + '</span>' +
          '</span>' +
          '<span title="0 silver achievements">' +
            '<span class="achievement-silver">&nbsp;</span>' +
            '<span class="achievement-count">' + data.SILVER + '</span>' +
          '</span>' +
          '<span title="0 gold achievements">' +
            '<span class="achievement-gold">&nbsp;</span>' +
            '<span class="achievement-count">' + data.GOLD + '</span>' +
          '</span>' +
        '</div>';

      userHeader.prepend(content);
    });



  });

})(jQuery);