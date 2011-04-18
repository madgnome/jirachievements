(function($) {
  $(document).ready(function() {
    $.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievement/user', function(data) {
      var items = [];

      $.each(data, function(){
        var options = {
          corners: '30px',
          close: function(e,m,o)
          {
            // Change notified attribute to true
          }
        };
        $.jGrowl(this.ref, options);
      });

            
    });
  });
  
})(jQuery);