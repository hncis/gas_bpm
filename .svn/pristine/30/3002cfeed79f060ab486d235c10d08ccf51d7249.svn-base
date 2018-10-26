
util = 
{
	/**
	 * 
	 * {a='1'} 형태의 Json 데이터를 파싱하여 Object 로 리턴한다.
	 * @param object
	 * @return object의 jsonData
	 */
	jsonToString : function(object)
	{
		var results = [];
		for (var property in object){
			var value = object[property].toString();
			if(value){
				results.push('"'+property.toString() + '" : "' + replaceStr(value) + '"');
			}
		}
		
		return '{' + results.join(', ') + '}';
	},
	
	/**
	 * 
	 * {a='1'} 형태의 Json 데이터를 파싱하여 Object 로 리턴한다.
	 * 값이 "" 인것들도 예외없이 처리한다. 값이 없는경우 배열객체에서 object가 제외되는것을 방지.
	 * @param object
	 * @return object의 jsonData
	 * @작성자 김수환
	 * 
	 */
	jsonToStringWithNull : function(object)
	{
		var results = [];
		for (var property in object){
			var value = object[property].toString();
			results.push('"'+property.toString() + '" : "' + replaceStr(value) + '"');
		
		}		
		return '{' + results.join(', ') + '}';
	},
	
	/**
	 * 
	 * ['1','2','3'] 형태의 Json 데이터를 파싱하여 Array 로 리턴한다.
	 * @param object
	 * @return object의 jsonData
	 */
	jsonToArray : function(object)
	{
		var results = [];
		for (var property in object){
			var value = object[property].toString();
			if(value){
				results.push(replaceStr(value));
			}
		}
	
		return '[' + results.join(', ') + ']';
	},
	
	/**
	 * 
	 * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
	 * 지정한 head값만 읽어들인다.
	 * @param object
	 * @return object의 jsonData
	 */
	jsonToList : function(object)
	{
		var k = 0;
		var results = "";
		for(var i = 0; i < object.length; i++){
			if(k == 0){
				results += "{";
			}
			else{
				results += ",{";
			}
			
			var n = 0;
			for(var key in object[i]){
				var value = object[i][key];
				if(value){
					if(n == 0){
						results += '"' + key.toString() + '" : "' + replaceStr(value) + '"';
					}
					else{
						results += ',"' + key.toString() + '" : "' + replaceStr(value) + '"';
					}
					
					n++;
				}
			}
			results += "}";
			
			k++;
		}
		
		return "[" + results + "]";
	},

	/**
	 * 
	 * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
	 * 지정한 head값만 읽어들인다.
	 * 값이 "" 인것들도 예외없이 처리한다. 값이 없는경우 배열객체에서 object가 제외되는것을 방지.
	 * @param object
	 * @return object의 jsonData
	 */
	jsonToListWithNull : function(object)
	{
		var k = 0;
		var results = "";
		for(var i = 0; i < object.length; i++){
			if(k == 0){
				results += "{";
			}
			else{
				results += ",{";
			}
			
			var n = 0;
			for(var key in object[i]){
				var value = object[i][key];
				
				if(n == 0){
					results += '"' + key.toString() + '" : "' + replaceStr(value) + '"';
				}
				else{
					results += ',"' + key.toString() + '" : "' + replaceStr(value) + '"';
				}
				
				n++;
			}
			results += "}";
			
			k++;
		}
		
		return "[" + results + "]";
	},
	
	/**
	 * 
	 * {a='1'} 형태의 Json 데이터를 파싱하여 Object 로 리턴한다.
	 * @param object
	 * @return object의 jsonData
	 */
	jsonToStringWithFormArr : function(object)
	{
		var results = [];
		for (var property in object){
			var value = object[property].value.toString();
			if(value){
				results.push('"'+object[property].name.toString() + '" : "' + replaceStr(value) + '"');
			}
		}
		
		return '{' + results.join(', ') + '}';
	},
	
	/**
	 * model name setting
	 * @param obj
	 * @returns
	 */
	setColumnKey : function(obj){
		var rtn = [];
		var l_grid = jQuery(obj);
		var l_cm = l_grid.getGridParam("colModel");
		for(var i = 0; i < l_cm.length; i++){
			if(l_cm[i].name != "rn" && l_cm[i].name != "cb"){
				if(l_cm[i].hidden == false){
					rtn.push(l_cm[i].name);
				}
			}
		}
			
		return util.jsonToArray(rtn);
	},
	
	/**
	 * model format setting
	 * @param obj
	 * @returns
	 */
	setFormatter : function(obj){
		var rtn = [];
		var l_grid = jQuery(obj);
		var l_cm = l_grid.getGridParam("colModel");
		for(var i = 0; i < l_cm.length; i++){
			if(l_cm[i].name != "rn" && l_cm[i].name != "cb"){
				if(l_cm[i].hidden == false){
					rtn.push(l_cm[i].formatter);
				}
			}
		}
		
		return util.jsonToArray(rtn);
	},
	
	/**
	 * model name setting
	 * @param obj
	 * @returns
	 */
	setColumnKeyWithoutHide : function(obj){
		var rtn = [];
		var l_grid = jQuery(obj);
		var l_cm = l_grid.getGridParam("colModel");
		for(var i = 0; i < l_cm.length; i++){
			if(l_cm[i].name != "rn" && l_cm[i].name != "cb"){
					rtn.push(l_cm[i].name);
			}
		}
			
		return util.jsonToArray(rtn);
	},
	
	/**
	 * model format setting
	 * @param obj
	 * @returns
	 */
	setFormatterWithoutHide : function(obj){
		var rtn = [];
		var l_grid = jQuery(obj);
		var l_cm = l_grid.getGridParam("colModel");
		for(var i = 0; i < l_cm.length; i++){
			if(l_cm[i].name != "rn" && l_cm[i].name != "cb"){
					rtn.push(l_cm[i].formatter);
			}
		}
		
		return util.jsonToArray(rtn);
	}
	
	
};
