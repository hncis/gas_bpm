function fnSetDocumentReady(){
}

function doPrint(id){
/*	alertUI("Hear");
	$(".printPopupSec").printArea();*/
	  var html = "<html><head>";

	  $('link').each(function() { // find all <link tags that have
	    if ($(this).attr('rel').indexOf('stylesheet') !=-1) { // rel="stylesheet"
	      html += '<link rel="stylesheet" href="'+$(this).attr("href")+'" />';
	    }
	  });
	  html+="</head>";
	  html += '<body onload="window.focus(); window.print()" class="printBody">'+$("#"+id).html()+'</body></html>';
	  var w = window.open("","print");
	  if (w) { w.document.write(html); w.document.close(); }

}