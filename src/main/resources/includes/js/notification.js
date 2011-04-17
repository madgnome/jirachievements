AJS.$(document).ready(function() {
  AJS.$.getJSON(AJS.params.baseURL + '/rest/jirachievement/1.0/achievement/user', function(data) {
    var items = [];

    AJS.$.each(data, function(key, val) {
      items.push('<li id="' + key + '">' + val + '</li>');
    });

    AJS.$('<ul/>', {
      'class': 'my-new-list',
      html: items.join('')
    }).appendTo('body');
  });
  /*var container = AJS.$('<div id="notify-container"/>');
  var notification = AJS.$('<div id="notify-1"/>');
  notification.append(AJS.$('<span class="notify-close"><a>x</a></span>'));
  notification.append(AJS.$('<span>You earn a new badge : Necromancer</span>'));

  container.append(notification);
  AJS.$('body').prepend(container);*/
});