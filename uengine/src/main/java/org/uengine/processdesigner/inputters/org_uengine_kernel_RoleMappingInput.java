package org.uengine.processdesigner.inputters;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.InputterAdapter;
import org.metaworks.inputter.ObjectInput;
import org.metaworks.inputter.TextInput;
import org.metaworks.web.HTML;

import org.uengine.kernel.CommandVariableValue;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.UEngineUtil;

public class org_uengine_kernel_RoleMappingInput extends ObjectInput{
	
	Role role;
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		
	public org_uengine_kernel_RoleMappingInput(){
		super(RoleMapping.class);
	}	
	
	public Object createValueFromHTTPRequest(Map req, String section, String name, Object oldValue) {
		RoleMapping nullValue = //a command that will do nothing
		new RoleMapping(){
			public boolean doCommand(ProcessInstance instance, String variableKey) throws Exception {
				return true;
			}
			public String getEndpoint() {
				return null;
			}
			public String toString(){
				return "<empty>";
			}
		};
		
		if(req==null || !req.containsKey(createNameAttribute(section, name))) return nullValue; 
		
		String value = TextInput.createStringFromHTTPRequest(req, section, name);
		if(!UEngineUtil.isNotEmpty(value)) return nullValue;
		String [] values = value.split(";");		

		String userName = TextInput.createStringFromHTTPRequest(req, section, name + "_display");
		if(!UEngineUtil.isNotEmpty(userName)) return nullValue;
		String [] userNames = userName.split(";");
		
		String gender = TextInput.createStringFromHTTPRequest(req, section, name + "_gender");
		if(!UEngineUtil.isNotEmpty(gender)) return nullValue;
		String [] genders = gender.split(";");
		
		String birthday = TextInput.createStringFromHTTPRequest(req, section, name + "_birthday");
		String [] birthdays = new String[]{};
		if(UEngineUtil.isNotEmpty(birthday))
			birthdays = birthday.split(";");

		if(values.length > 0){
			RoleMapping roleMapping = RoleMapping.create();
			for(int i=0; i<values.length; i++){
				roleMapping.setEndpoint(values[i]);
				roleMapping.setResourceName(userNames[i]);
				roleMapping.setMale("true".equals(genders[i]));
				
				try{
					Date birthdayInDate = new Date(Long.parseLong(birthdays[i]));				
					roleMapping.setBirthday(birthdayInDate);
				}catch(Exception e){
				}
				
				if(i < (values.length-1))
					roleMapping.moveToAdd();
			}
			roleMapping.beforeFirst();
			
			return roleMapping;
		}else{
			return nullValue;
		}
	}

	public String getInputterHTML(String section, FieldDescriptor fd, Object defaultValue, Map options) {
		String inputName = "_" + section + "_" + fd.getName();
		String displayInputName = inputName + "_display";
		String genderInputName = inputName + "_gender";
		String birthdayInputName = inputName + "_birthday";
		
		RoleMapping defaultRM = (RoleMapping)defaultValue;
		String defaultRMAsString = "", sep = "";
		String defaultUserNamesAsString = "";
		String defaultGendersAsString = "";
		String defaultBirthdayAsString = "";
		
		if(defaultValue!=null)
		do{
			if(defaultRM.getEndpoint() == null) continue;
			defaultRMAsString += (sep + defaultRM.getEndpoint());
			defaultUserNamesAsString += (sep + defaultRM.getResourceName());
			defaultGendersAsString += (sep + defaultRM.isMale());

			if(defaultRM.getBirthday() != null)
				defaultBirthdayAsString += (sep + defaultRM.getBirthday().getTime());

			sep = ";";
		}while(defaultRM.next());

		StringBuffer html = new StringBuffer();
		html
			.append("<input readonly name='" + displayInputName + "'"+ HTML.getAttrHTML("value", defaultUserNamesAsString) +">")
			.append("<input type=hidden name='" + genderInputName + "'"+ HTML.getAttrHTML("value", defaultGendersAsString) +">")
			.append("<input type=hidden name='" + birthdayInputName + "'"+ HTML.getAttrHTML("value", defaultBirthdayAsString) +">")
			.append("<input type=hidden editable=false name='" + inputName + "'"+ HTML.getAttrHTML("value", defaultRMAsString) +">")
			.append("<input type=button onclick=\"searchPeople('"+ inputName +"')\" value='...'>")
		;
    	
		return html.toString();
	}
	
	public void addScriptTo(Properties scripts, String section, FieldDescriptor fd, Object defaultValue, Map options) {
		StringBuffer searchPeople = new StringBuffer();
		searchPeople
			.append("<script>\n")
			.append("function searchPeople(inputName_){\n")
			.append("	var orgPicker = window.open('"+ GlobalContext.WEB_CONTEXT_ROOT +"/common/organizationChartPicker.jsp','_new','width=380,height=450,resizable=true,scrollbars=auto');\n")
			.append("	inputName = inputName_;\n")
			.append("	orgPicker.onOk = onUserSelected;\n")
			.append("	orgPicker.inputName = inputName;\n")
			.append("}\n")
			.append("</script>\n")
		;	
		
		scripts.put("searchPeople", searchPeople);

		StringBuffer onUserSelected = new StringBuffer();
		onUserSelected
			.append("<script>\n")
			.append("var inputName='';\n")
			.append("function onUserSelected(selectedPeople, inputName){\n")
			.append("	var userEndpoints = '';\n")
			.append("	var userNames = '';\n")
			.append("	var genders = '';\n")
			.append("	var birthdays = '';\n")
			.append("	var sep = '';\n")
			.append("\n")
			.append("	for(i=0; i<selectedPeople.length; i++){\n")
			.append("		userEndpoints += (sep + selectedPeople[i].id);")
			.append("		userNames += (sep + selectedPeople[i].name);")
			.append("		genders += (sep + selectedPeople[i].isMale);")
			.append("		birthdays += (sep + selectedPeople[i].birthday);")
			.append("		sep = ';';\n")
			.append("	}\n")
			.append("\n")
			.append("	document.forms[0].elements[inputName+'_display'].value = userNames;\n")
			.append("	document.forms[0].elements[inputName+'_gender'].value = genders;\n")
			.append("	document.forms[0].elements[inputName+'_birthday'].value = birthdays;\n")
			.append("	document.forms[0].elements[inputName].value = userEndpoints;\n")
			.append("}\n")
			.append("</script>\n")
		;	
		
		scripts.put("onUserSelected", onUserSelected);
	}


}
