public class EMailService extends org.uengine.webservice.AbstractServiceProvider{

	public Object getStub(String endpoint) throws Exception{
		return (new org.uengine.webservices.emailserver.EMailServerServiceLocator()).getEMailServer(new java.net.URL(endpoint));
	}
	
}
