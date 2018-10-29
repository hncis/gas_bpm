package org.uengine.kernel.descriptor;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.SelectInput;
import org.metaworks.validator.NotNullValid;
import org.metaworks.validator.Validator;

/**
 * 서브의 프로세스 변수 선언이 메인에 존재하지 않는 경우 이들을 메인에 자동 추가 여부 묻는, Prefix 를 추가해서 넣을 것인지.. , 서브의 변수리스트가 있다 할지라도 이를 중복적으로 prefix를 붙여서 추가할지의 여부..
 * @author Jinyoung
 *
 */
public class HowToBinding {

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;

		type.setName("Create and add variables corresponding child process definition?");
		
		fd = type.getFieldDescriptor("VariableCreationOption");
		fd.setInputter(new SelectInput(
				new Object[]{
						"Don't create variables",
						"Create if not exists",
						"Create with prefix"
				},
				new Object[]{
						new Integer(0),
						new Integer(1),
						new Integer(2),
				}
		));

	}
	
	int variableCreationOption; // 0: 추가 하지 않음   1: 없는경우 추가   2: prefix를 달고 추가
	String prefix;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public int getVariableCreationOption() {
		return variableCreationOption;
	}
	public void setVariableCreationOption(int variableCreationOption) {
		this.variableCreationOption = variableCreationOption;
	}
	
	
}
