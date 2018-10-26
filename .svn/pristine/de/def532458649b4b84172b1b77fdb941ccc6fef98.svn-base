var requiredMajorVersion = 10;
var requiredMinorVersion = 0;
var requiredRevision = 0;
var hidCond;

function fnSetDocumentReady(){
	init();
}

function init(){
	if($("#hid_cond").val() != ""){
		hidCond = $("#hid_cond").val().split("|");
		fnOzCall();
	}
}

function fnOzCall(){
	var hasProductInstall = DetectFlashVer(6, 0, 65);

	//Version check based upon the values defined in globals
	var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

	if(hasProductInstall && !hasRequestedVersion) {
		alertUI(1);
	  //DO NOT MODIFY THE FOLLOWING FOUR LINES
	  //Location visited after installation is complete if installation is required
	  var MMPlayerType = "PlugIn";
	  var MMredirectURL = window.location;
	  document.title = document.title.slice(0, 47) + " - Flash Player Installation";
	  var MMdoctitle = document.title;
	  AC_FL_RunContent(
	    "src", "http://112.217.187.186:80/oz70/ozfviewer/playerProductInstall",
	    "FlashVars", "MMredirectURL=" + MMredirectURL + '&MMplayerType=' + MMPlayerType + '&MMdoctitle=' + MMdoctitle+"",
	    "width", "100%",
	    "height", "100%",
	    "align", "middle",
	    "id", "playerProductInstall",
	    "quality", "high",
	    "bgcolor", "#ffffff",
	    "name", "playerProductInstall",
	    "allowScriptAccess", "sameDomain",
	    "type", "application/x-shockwave-flash",
	    "pluginspage", "http://www.adobe.com/go/getflashplayer"
	  );
	} else if(hasRequestedVersion) {
		
		alertUI($("#ozrName").val()+":"+hidCond.length);
	  //if we've detected an acceptable version
	  //embed the Flash Content SWF when all tests are passed
	  function SetOZParamters_OZViewer() {
	    var oz;
	    if(navigator.appName.indexOf("Microsoft") != -1) {
	      oz = window["OZViewer"];
	    } else {
	      oz = document["OZViewer"];
	    }
	    oz.sendToActionScript("connection.servlet", "http://112.217.187.186:80/oz70/server");
	    oz.sendToActionScript("connection.reportname", "//Sample_Calendar.ozr");
		oz.sendToActionScript("connection.pcount", hidCond.length);
		
		for(var i=1; i<=hidCond.length; i++){
			alertUI(i+" : "+hidCond[i]);
			oz.sendToActionScript("connection.args"+i, hidCond[i]);
		}
		
//		oz.sendToActionScript("connection.args2", "Month=1");
//		oz.sendToActionScript("connection.args3", "Company=FORCS");
	    return true;
	  }
	  AC_FL_RunContent(
	    "src", "http://112.217.187.186:80/oz70/ozfviewer/OZViewer10",
	    "width", "100%",
	    "height", "100%",
	    "align", "middle",
	    "id", "OZViewer",
	    "quality", "high",
	    "bgcolor", "#ffffff",
	    "name", "OZViewer",
	    "allowScriptAccess", "sameDomain",
	    "type", "application/x-shockwave-flash",
	    "pluginspage", "http://www.adobe.com/go/getflashplayer",
	    "flashVars", "flash.objectid=OZViewer"
	  );
	} else { //flash is too old or we can't detect the plugin
	  var alternateContent = 'Alternate HTML content should be placed here. '
	                         + 'This content requires the Adobe Flash Player. '
	                         + '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
	  document.write(alternateContent); //insert non-flash content
	}
}