(function($) {
  $(document).ready(function() {
    $.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievements', function(data) {

      $.each(data, function(){
        var achievement = this;
        var options = {
          position: 'center',
          corners: '30px',
          theme: 'jirachivements',
//          sticky: true,
          life: 5000,
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

        var imgUrl = AJS.params.baseURL + '/download/resources/com.madgnome.jira.plugins.jirachievements/images/achievements/' + this.imageRef + '_small.png';
        var content =
        '<div class="achievement-image">' +
          '<img src="' + imgUrl + '"/>' +
        '</div>' +
        '<div class="achievement-content">' +
          '<h4>' + this.name + '</h4>' +
          '<span class="catchPhrase">' + this.catchPhrase + '</span>' +
        '</div>';
//                +
//        '<div class="achievement-level achievement-' + this.difficulty.toLowerCase() + '">&nbsp;</div>';

        $.jGrowl(content, options);
      });

            
    });
  });
  
})(jQuery);