
function enableTooltips(id){
var links,i,h;
    if (!document.getElementById || !document.getElementsByTagName) 
    	return;
    
    h=document.createElement("div");
    h.id="btc";
    h.setAttribute("id","btc");
    h.style.position="absolute";
	h.style.zIndex="99999999";
    document.getElementsByTagName("body")[0].appendChild(h);
    if (id==null) 
    	links=document.getElementsByTagName("img");
    else 
    	links=document.getElementById(id).getElementsByTagName("img");
    
    for (i=0; i < links.length; i++) {
    	Prepare(links[i]);
    }
}

function Prepare(el) {
    var tooltip,t,b,s,l,i;
    
	if (el.getAttribute('activityDescription')) {
        t = el.getAttribute('activityDescription');

		var actName='';
		
        tooltip=CreateEl("span","tooltip");
        s=CreateEl("span","top");
        var s2 =document.createElement('div');
        s2.innerHTML = "<table width=100% ><td nowrap align=center>"+actName+"</td></table>";
        s.appendChild(s2);
        tooltip.appendChild(s);
        
        var htmlString="<table width=100% cellpadding=4 cellspacing=4><tr><td align=center>" + t + "</td></tr></table>";
        
        i=CreateEl("b","bottom");
        var img =document.createElement('div');
        img.innerHTML = htmlString;
        i.appendChild(img);
        tooltip.appendChild(i);
	} else if (el.getAttribute('strtgDesc')) {
        t=el.getAttribute('strtgDesc');
        
        if(t==null){
			return;
		}
		var valuesArray = t.split(';');
		
		var htmlString="<table width=100% cellpadding=4 cellspacing=4><tr><td align=center><font size=1>";
		var actName='';
		var desc='';
		var descValue='';
		for(i=0 ; valuesArray.length ; i++){
	
			if(valuesArray[i]==''||valuesArray[i].indexOf('==')==0){
				break;
			}
			var values = valuesArray[i].split('==');
			
			var color='';
			if(values[1] == ''){
				color='color=black';
			}
			if(i==0){
				actName = values[1];
			}else if(i==2){
				var percent = values[1].split('/');
				var tdWidth = "1%";
				var htmlPercent= "";
				if(percent[0] !=='0'){
					tdWidth = Math.round((percent[0]/percent[1])*100)+"%";
					htmlPercent ="<table border=1 width=100% height=10><tr><td width="+tdWidth+" bgcolor=yellow align=right>"+percent[0]+"&nbsp;</td><td align=right>"+percent[1]+"&nbsp;</td></tr></table>";
				}else{
					htmlPercent ="<table border=1 width=100% height=10><tr><td width="+tdWidth+" bgcolor=yellow></td><td align=right>"+percent[1]+"&nbsp;</td></tr></table>";
				}
				
				htmlString+="<tr><td width=130 nowrap><font "+color+" size=1>&nbsp;&nbsp;<b>"+values[0]+"</b></td><td>:</td><td> "+htmlPercent+"</td></tr>"; 
			}else{
				htmlString+="<tr><td width=130 nowrap><font "+color+" size=1>&nbsp;&nbsp;<b>"+values[0]+"</b></td><td>:</td><td> "+values[1]+"</td></tr>"; 
			}
		}
 
		htmlString+="</td></tr></table>";	
		
        tooltip=CreateEl("span","tooltip");
        s=CreateEl("span","top");
        var s2 =document.createElement('div');
        s2.innerHTML = "<table width=100% ><td nowrap align=center><b>"+actName+"</td></table>";
        s.appendChild(s2);
        tooltip.appendChild(s);
        
        i=CreateEl("b","bottom");
        var img =document.createElement('div');
        img.innerHTML = htmlString;
        i.appendChild(img);
        tooltip.appendChild(i);
	}else {
        t=el.getAttribute('titles');
        
        if(t==null){
			return;
		}
		
		var valuesArray = t.split(';');
		
		var htmlString="<table width=100% cellpadding=4 cellspacing=4><tr><td align=center><font size=1>";
		var actName='';
		var desc='';
		var descValue='';
		for(i=0 ; valuesArray.length ; i++){
			if(valuesArray[i]==''||valuesArray[i].indexOf('==')==0){
				break;
			}
			
			var values = valuesArray[i].split('==');
			if(i==0){
				htmlString+="<img width=65 src="+values[1]+" onerror='setUnkwonImage(this);'><br>"; //img path
			}else if(i==1){
				htmlString+=values[1]+"</td><td><table width=100% cellpadding=0 cellspacing=0>"; //user name
			}else if(i==2){
				actName = values[1];
			}else if(i==3){
				desc = values[0];
				descValue = values[1];
			}else{
				htmlString+="<tr><td width=80 nowrap><font size=1><b>"+values[0]+"</b></td><td>: "+values[1]+"</td></tr>"; 
			}
		}
 
		htmlString+="<tr><td width=80 nowrap><font size=1><b>"+desc+"</b></td><td>: "+descValue+"</td></tr>";
		htmlString+="</table></td></tr></table>";	
				
        tooltip=CreateEl("span","tooltip");
        s=CreateEl("span","top");
        var s2 =document.createElement('div');
        s2.innerHTML = "<table width=100% ><td nowrap align=center>"+actName+"</td></table>";
        s.appendChild(s2);
        tooltip.appendChild(s);
        
        i=CreateEl("b","bottom");
        var img =document.createElement('div');
        img.innerHTML = htmlString;
        i.appendChild(img);
        tooltip.appendChild(i);
	}
        
    setOpacity(tooltip);
    el.tooltip=tooltip;
    el.onmouseover=showTooltip;
    el.onmouseout=hideTooltip;
    el.onmousemove=Locate;
}

function showTooltip(e){
    document.getElementById("btc").appendChild(this.tooltip);
    Locate(e);
}

function hideTooltip(e){
    var d=document.getElementById("btc");
    if(d.childNodes.length>0) d.removeChild(d.firstChild);
}

function setOpacity(el){
    el.style.filter="alpha(opacity:95)";
    el.style.KHTMLOpacity="0.95";
    el.style.MozOpacity="0.95";
    el.style.opacity="0.95";
}

function CreateEl(t,c){
    var x=document.createElement(t);
        x.className=c;
        x.style.display="block";
    return(x);
}

function Locate(e){
var posx=0,posy=0;
if(e==null) e=window.event;
if(e.pageX || e.pageY){
    posx=e.pageX; posy=e.pageY;
    }
else if(e.clientX || e.clientY){
    if(document.documentElement.scrollTop){
        posx=e.clientX+document.documentElement.scrollLeft;
        posy=e.clientY+document.documentElement.scrollTop;
        }
    else{
        posx=e.clientX+document.body.scrollLeft;
        posy=e.clientY+document.body.scrollTop;
        }
    }
document.getElementById("btc").style.top=(posy+10)+"px";
document.getElementById("btc").style.left=(posx-20)+"px";
}





