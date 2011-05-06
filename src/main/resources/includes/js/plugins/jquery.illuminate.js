(function($){
	$.fn.extend({
		illuminate: function(custom_settings) {
            var defaults = {
                overlayColor: "#000",
                overlayOpacity: 0.4,
                overlayTimeOut: 600,
                overlayOpen: "both",
                overlayClose: "both",
                itemPadding: 5,
                balloonWidth: 300,
                balloonColor: "#06c",
                balloonBackground: "#e5effa"
            };

            var settings,           // Extends defaults with the custom_settings
                illuminateStatus,   // Turn the illuminate plugin onn or off (True / False)
                statusPopUp,        // Pop up if there is no cookie present
                statusButton,       //
                obj,                // The object: $(this)
                openOverlayCheck,   // If the overlay shuold be opened (True / False)
                item,               // Object that should pup up
                itemPosition,       //
                itemWidth,          // The width of the item
                itemHeight,         // The height of the item
                itemPadding,        // The padding around the item
                itemBorder,         // The border around the padding
                itemOffset,         //
                isOpenOverlay,      // If the overlay is open or not (True / False)
                overlayColor,       //
                overlayOpacity,     //
                balloonContent,     // The content of the info balloon
                balloonOffsetLeft,  // The distance between the right border and the balloon
                balloonOffsetRight; // The distance between the left border and the balloon

            settings         = $.extend(defaults, custom_settings);
            statusPopUp      = $("#illuminateCookiePopUp").html();
            openOverlayCheck = false;
            isOpenOverlay    = false;

            if($.cookie('illuminateStatus')){
                checkStatus();
            }else{
                if(statusPopUp){
                    openOverlay("setStatus");
                }else{
                    illuminateStatus = true;
                    $.cookie('illuminateStatus', "demoOn", { expires: 365 });

                    location.reload(true);
                }
            }

            $("#illuminateStatus").change(function(){
                changeStatus(false);
            });

            return this.each(function(){
                obj = $(this);

                obj.css("float", "left")
                   .css("display", "block");

                if(settings.overlayOpen == "hover"){
                    obj.mouseenter(function(){ countOpen("start") }).mouseleave(function(){ countOpen("stop") });
                }else if(settings.overlayOpen == "click"){
                    obj.click(function(){ countOpen("click") });
                }else{
                    obj.mouseenter(function(){ countOpen("start") }).mouseleave(function(){ countOpen("stop") });
                    obj.mouseenter(function(){ countOpen("start") }).mouseleave(function(){ countOpen("stop") });

                    obj.click(function(){ countOpen("click") });
                }
            });

            function countOpen(countStatus)
            {
                if(illuminateStatus){
                    if(countStatus == "start"){
                        openOverlayCheck = true;
                        setTimeout(checkOverlayOpen, settings.overlayTimeOut);
                    }else if(countStatus == "stop"){
                        openOverlayCheck = false;
                    }else if(countStatus == "click"){
                        openOverlayCheck = true;
                        checkOverlayOpen();
                    }
                }
            }

            function checkOverlayOpen()
            {
                if(openOverlayCheck){
                    if(!isOpenOverlay){
                        openOverlay("openItem");
                    }
                }
            }

            function countClose(countStatus)
            {
                if(countStatus == "start"){
                    closeOverlayCheck = true;
                    setTimeout(checkOverlayClose, settings.overlayTimeOut);
                }else if(countStatus == "stop"){
                    closeOverlayCheck = false;
                }else if(countStatus == "click"){
                    closeOverlayCheck = true;
                    checkOverlayClose();
                }
            }

            function checkOverlayClose()
            {
                if(closeOverlayCheck){
                    if(isOpenOverlay){
                        closeOverlay();
                    }
                }
            }

            function openOverlay(afterOpen)
            {
                isOpenOverlay = true;

                if(afterOpen == "setStatus"){
                    overlayColor   = "#000";
                    overlayOpacity = 0.4;
                }else{
                    overlayColor   = settings.overlayColor;
                    overlayOpacity = settings.overlayOpacity;
                }

                $("body").append("<div id=\"illuminateOverlay\"></div>");
                $("#illuminateOverlay").css("background", overlayColor);
                $("#illuminateOverlay").fadeTo("fast", overlayOpacity);

                $("body").append("<div id=\"itemClose\"></div>");

                if(afterOpen == "openItem"){
                    openItem()
                }else if(afterOpen == "setStatus"){
                    setStatus()
                }
            }

            function closeOverlay()
            {
                $("#itemOverlay").fadeOut("fast", function(){
                    $("#illuminateOverlay").fadeOut("fast", function(){
                        $("#itemOverlay").remove();
                        $("#itemClose").remove();
                        $("#illuminateOverlay").remove();

                        isOpenOverlay = false;
                    });
                });
            }

            function openItem()
            {
                item         = obj.html();
                itemPosition = obj.offset();
                itemWidth    = obj.width();
                itemHeight   = obj.height();
                itemPadding  = settings.itemPadding;
                itemBorder   = 1;
                itemOffset   = itemPadding + itemBorder;

                $("body").append("<div id=\"itemOverlay\"><div id=\"itemOverlayContent\"></div></div>");
                $("#itemOverlay").fadeIn("fast", addTextBalloon);

                $("#itemOverlay").css("left", itemPosition.left - itemOffset +"px")
                                 .css("top", itemPosition.top - itemOffset +"px")
                                 .css("position", "absolute");

                $("#itemOverlayContent").css("width", itemWidth +"px")
                                        .css("height", itemHeight +"px")
                                        .css("padding", itemPadding +"px")
                                        .append(item);

                if(settings.overlayClose == "hover"){
                    $("#itemOverlay").mouseleave(function(){ countClose("start") }).mouseenter(function(){ countClose("stop") });
                }else if(settings.overlayClose == "click"){
                    $("#itemClose").fadeIn("fast");
                    $("#itemClose").click(closeOverlay);
                }else{
                    $("#itemOverlay").mouseleave(function(){ countClose("start") }).mouseenter(function(){ countClose("stop") });

                    $("#itemClose").fadeIn("fast");
                    $("#itemClose").click(function(){ countClose("click") });
                }
            }

            function addTextBalloon()
            {
                balloonContent = obj.find(".balloonText").html();

                if(balloonContent){
                    $("#itemOverlay").append("<div id=\"textBalloonWrapper\"><div id=\"textBalloon\"></div></div>");

                    balloonOffsetLeft  = $(window).width() - obj.offset().left - itemWidth;
                    balloonOffsetRight = $(window).width() - balloonOffsetLeft - itemWidth;

                    if(balloonOffsetLeft > settings.balloonWidth + 15){
                        balloonLeft    = itemWidth + (itemPadding * 2) + itemBorder;
                        balloonPadding = "padding-left";
                    }else if(balloonOffsetRight > settings.balloonWidth + 15){
                        balloonLeft    = -settings.balloonWidth - 10;
                        balloonPadding = "padding-right";
                    }else{
                        balloonLeft    = 10;
                        balloonPadding = "padding-right";
                    }

                    $("#textBalloonWrapper").css("left", balloonLeft +"px")
                                            .css("top", 0 +"px")
                                            .css("width", settings.balloonWidth +"px")
                                            .css(balloonPadding, 10 +"px");

                    $("#textBalloon").css("color", settings.balloonColor)
                                     .css("background", settings.balloonBackground)

                    $("#textBalloon").append(balloonContent);

                    setTimeout(function(){ $("#textBalloon").fadeIn("slow"); }, 100);
                }
            }

            function checkStatus()
            {
                if($.cookie('illuminateStatus') == "demoOn"){
                    $('input#illuminateStatus').attr('checked', true);
                    illuminateStatus = true;
                }else if($.cookie('illuminateStatus') == "demoOff"){
                    $('input#illuminateStatus').attr('checked', false);
                    illuminateStatus = false;
                }else{
                    return false;
                }
            }

            function setStatus()
            {
                $("body").append("<div id=\"itemOverlay\"><div id=\"illuminateCookie\">"+ statusPopUp +"<div style=\"margin-top: 15px;\"><span id=\"illuminateCookieON\"></span><span id=\"illuminateCookieOFF\"></span></div></div></div>");

                $("#itemOverlay").css("top", ($(window).height() / 2) - ($("#itemOverlay").height() / 2) +"px")
                                 .css("left", ($(window).width() / 2) - ($("#itemOverlay").width() / 2) +"px")
                                 .css("position", "absolute");

                $("#itemOverlay").fadeIn("normal");

                $("#illuminateCookieON").click(function(){
                    changeStatus("demoOn");
                    closeOverlay();
                });

                $("#illuminateCookieOFF").click(function(){
                    changeStatus("demoOff");
                    closeOverlay();
                });
            }

            function changeStatus(manualStatus)
            {
                if(!manualStatus){
                    if($('input#illuminateStatus').is(':checked')){
                        illuminateStatus = true;
                        $.cookie('illuminateStatus', "demoOn", { expires: 365 });
                    }else{
                        illuminateStatus = false;
                        $.cookie('illuminateStatus', "demoOff", { expires: 365 });
                    }
                }else{
                    if(manualStatus == "demoOn"){
                        illuminateStatus = true;
                        $.cookie('illuminateStatus', "demoOn", { expires: 365 });
                        $('input#illuminateStatus').attr('checked', true);
                    }else{
                        illuminateStatus = false;
                        $.cookie('illuminateStatus', "demoOff", { expires: 365 });
                        $('input#illuminateStatus').attr('checked', false);
                    }
                }
            }
		}
	});
})(jQuery);