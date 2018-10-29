package org.uengine.ui.monitor.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MonitorFilter {
    String tag;
        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

    String status;
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    String definition;
        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }
    
    String definitionName;
        public String getDefinitionName() {
            return definitionName;
        }
    
        public void setDefinitionName(String definitionName) {
            this.definitionName = definitionName;
        }

    String partCode;
        public String getPartCode() {
            return partCode;
        }
    
        public void setPartCode(String partCode) {
            this.partCode = partCode;
        }

    String roleCode;
        public String getRoleCode() {
            return roleCode;
        }
    
        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

    String filterUId;
        public String getFilterUId() {
            return filterUId;
        }
    
    String filterName;
        public String getFilterName() {
            return filterName;
        }
    
    /**
     * <pre>
     *  construt
     * </pre>
     *
     * @param filterName
     * 2011. 3. 9.  Sung-yeol Jung
     */
    public MonitorFilter(String filterName){
        this.filterName = filterName;
            
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA);
        this.filterUId=sdf.format(new Date());
    }
}
