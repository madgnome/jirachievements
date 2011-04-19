(function($) {
  $(document).ready(function() {
    $.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievements', function(data) {
      var items = [];

      $.each(data, function(){
        var achievement = this;
        var options = {
          corners: '30px',
          theme: 'jirachivements',
          sticky: true,
          header: this.ref,
          close: function(e,m,o)
          {
            // Change notified attribute to true
            $.ajax({
              url: AJS.params.baseURL + '/rest/jirachievement/1.0/achievements/' + achievement.id,
              type: 'PUT',
              data: {
                notified: true
              }
            })
          }
        };
        $.jGrowl(this.ref, options);
      });

            
    });
  });
  
})(jQuery);