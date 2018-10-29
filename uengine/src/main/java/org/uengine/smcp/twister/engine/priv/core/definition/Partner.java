package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * A partner is a peer the engine communicates with.
 */

public interface Partner {

	/**
	 * 
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * 
	 * @uml.property name="name"
	 */
	public void setName(String name);

	/**
	 * 
	 * @uml.property name="role"
	 */
	public String getRole();

	/**
	 * 
	 * @uml.property name="role"
	 */
	public void setRole(String name);

	/**
	 * 
	 * @uml.property name="partnerRole"
	 */
	public String getPartnerRole();

	/**
	 * 
	 * @uml.property name="partnerRole"
	 */
	public void setPartnerRole(String partnerRole);

	/**
	 * 
	 * @uml.property name="service"
	 * @uml.associationEnd 
	 * @uml.property name="service" multiplicity="(0 1)"
	 */
	public Service getService();

	/**
	 * 
	 * @uml.property name="service"
	 */
	public void setService(Service service);

}
