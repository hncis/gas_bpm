<?xml version="1.0" encoding="UTF-8"?>
<%
response.setContentType("text/xml; charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%><%@page 
	pageEncoding="KSC5601" 
	import="org.uengine.util.XMLUtil,org.uengine.util.UEngineUtil,javax.naming.Context,javax.naming.InitialContext,javax.naming.NamingException,javax.rmi.PortableRemoteObject,org.uengine.kernel.*,java.util.*,java.io.*,org.uengine.processmanager.*"
%><%!

	void printTree(Hashtable childs, Hashtable versions, String parent, PrintWriter pw, boolean omitVersion, boolean folderSelectable, boolean giveResourceURLAsValue, String objectType){
		try{	
			if(childs.containsKey(parent)){
				Vector childprocesses = (Vector)childs.get(parent);
				for(Iterator iter = childprocesses.iterator(); iter.hasNext(); ){
				
					String definitionId = (String)iter.next();	//first is definitionId
					ProcessDefinitionRemote process = (ProcessDefinitionRemote)iter.next(); //second is the object
					
					//filter with object types.
					if(!process.isFolder() && UEngineUtil.isNotEmpty(objectType) && !objectType.equals(process.getObjType())) continue;
					
					if(process.isFolder()){
						pw.print("<folder name='" + XMLUtil.encodeXMLReservedWords(process.getName().getText()) + "'");
						if(folderSelectable)
							pw.print(" value='folder=" + definitionId + "'");							
						pw.print(">");
						
						printTree(childs, versions, definitionId, pw, omitVersion, folderSelectable, giveResourceURLAsValue, objectType);
						pw.print("</folder>");
					}else{
						
						Vector versions_ = (Vector)versions.get(definitionId);
						
						pw.print("<definition name='" + XMLUtil.encodeXMLReservedWords(process.getName().getText()) + "'");
						if(omitVersion){
							pw.print(" value='");
														
							if(folderSelectable)
								pw.print("processdefinition=");
							
							if(process.getAlias()!=null) pw.print("[" + process.getAlias() + "]");
							else pw.print(definitionId);
							
							pw.print("'");
						}
						pw.print(">");
						
						if(!omitVersion)
						for(Iterator iter2 = versions_.iterator(); iter2.hasNext(); ){
							ProcessDefinitionRemote version = (ProcessDefinitionRemote)iter2.next();
							String pd = version.getId();
							int versionValue = version.getVersion();
							
							pw.print("<version name='"+ XMLUtil.encodeXMLReservedWords(process.getName().getText()) +" v");
							pw.print(versionValue);
							if(version.isProduction())
								pw.print("(production)");
							
							if(giveResourceURLAsValue)
								pw.print("' value='/html/uengine-web/processmanager/showDefinitionInLanguage.jsp?language=Bean&amp;isProcMgr=true&amp;versionId="+ pd +"'/>");
							else{
								if(process.getAlias()!=null) 
									pw.print("' value='["+ process.getAlias() + "]@" + pd +"'/>");
								else
									pw.print("' value='"+ definitionId + "@" + pd +"'/>");
							}
								
						}
						
						pw.print("</definition>");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
%><jsp:useBean id="processManagerFactory" scope="application" class="org.uengine.processmanager.ProcessManagerFactoryBean" /><%

	ProcessManagerRemote pm = processManagerFactory.getProcessManagerForReadOnly();
	ProcessDefinitionRemote[] pds = null;
	try{
		pds = pm.listProcessDefinitionRemotesLight();
	}finally{
		pm.remove();
	}
	
	Hashtable childs = new Hashtable();	
	Hashtable versions = new Hashtable();
	
	for(int i=0; i<pds.length; i++){
		ProcessDefinitionRemote definitionRemote = pds[i];
		String definitionId = definitionRemote.getId();
		String parent = definitionRemote.getParentFolder();
		
		//indexing the names for searching versions
		String definitionGroupId = definitionRemote.getBelongingDefinitionId();
		if(definitionGroupId==null)
			definitionGroupId = definitionRemote.getId();
		
		if(!versions.containsKey(definitionGroupId)){
			versions.put(definitionGroupId, new Vector());
		}
		
		Vector v = (Vector)(versions.get(definitionGroupId));
		v.add(definitionRemote);
		//

		//indexing the names for making tree
		if(!childs.containsKey(parent)){
			childs.put(parent, new Vector());
		}
		
		v = (Vector)(childs.get(parent));
		if(!v.contains(definitionGroupId)){
			v.add(definitionGroupId);
			v.add(definitionRemote);
		}
		//

	}

	StringWriter sw = new StringWriter();
	PrintWriter spw = new PrintWriter(sw);
	spw.print("<folder name='Process Definitions'>");
	
	String omitVersionString = request.getParameter("omitVersion");
	String objectType = request.getParameter("objectType");
	boolean omitVersion = (omitVersionString!=null && omitVersionString.equals("true"));
	String folderSelectableString = request.getParameter("folderSelectable");
	boolean folderSelectable = (folderSelectableString!=null && folderSelectableString.equals("true"));
	boolean giveResourceURLAsValue = ("true".equals(request.getParameter("giveResourceURLAsValue")));
	
	printTree(childs, versions, "-1", spw, omitVersion, folderSelectable, giveResourceURLAsValue, objectType);
	spw.print("</folder>");
%><%=sw.toString()%>