
(function($) {
    var printAreaCount = 0;

    $.fn.printArea = function()
        {
            var ele = $(this);

            var idPrefix = "printArea_";

            removePrintArea( idPrefix + printAreaCount );

            printAreaCount++;

            var iframeId = idPrefix + printAreaCount;

            var iframe = document.createElement('IFRAME');

            $(iframe).attr('style','position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
            $(iframe).attr('id', iframeId);

            document.body.appendChild(iframe);

            var doc = iframe.contentWindow.document;

            var links = window.document.getElementsByTagName('link');

            for( var i = 0; i < links.length; i++ )
                if( links[i].rel.toLowerCase() == 'stylesheet' )
                    doc.write('<link type="text/css" rel="stylesheet" href="' + links[i].href + '"></link>');

            doc.write('<div class="' + $(ele).attr("class") + '">' + $(ele).html() + '</div>');
            doc.close();

            iframe.contentWindow.focus();
            iframe.contentWindow.print();
        }

    var removePrintArea = function(id)
        {
            $( "iframe#" + id ).remove();
        };

})(jQuery);

