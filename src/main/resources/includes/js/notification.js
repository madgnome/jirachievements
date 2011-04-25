(function($) {
  $(document).ready(function() {
    $.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievements', function(data) {
      var items = [];

      $.each(data, function(){
        var achievement = this;
        var options = {
          position: 'center',
          corners: '30px',
          theme: 'jirachivements',
          sticky: true,
//          header: this.ref,
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

        var content =
        '<div class="achievement-image">' +
          '<img src="http://localhost:2990/jira/download/resources/com.madgnome.jira.plugins.jirachievements/images/achievements/welcome_small.png"/>' +
        '</div>' +
        '<div class="achievement-content">' +
          '<h4>' + this.name + '</h4>' +
          '<span class="catchPhrase">' + this.catchPhrase + '</span>' +
        '</div>' +
        '<div class="achievement-level achievement-${achievement.difficulty.name().toLowerCase()}">&nbsp;</div>';

        $.jGrowl(content, options);
      });

            
    });
  });
  
})(jQuery);