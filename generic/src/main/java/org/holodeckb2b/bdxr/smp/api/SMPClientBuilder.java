package org.holodeckb2b.bdxr.smp.api;

import java.util.Map;

import org.holodeckb2b.bdxr.sml.ISMPLocator;
import org.holodeckb2b.bdxr.smp.impl.SMPClient;
import org.holodeckb2b.bdxr.smp.impl.SMPClientConfig;

/**
 * Is a <i>builder</i> of {@link ISMPClient}s and should be used to configure and create new SMP clients. 
 * <p>At a minimum the <b>SMP locator</b> implementation to find the correct SMP server must be configured before
 * calling the <code>build</code> to create a new SMP client. See the method documentation for all configuration
 * options available.   
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SMPClientBuilder {
	/**
	 * The configuration for the new SMP client
	 */
	private SMPClientConfig	newClientConfig = new SMPClientConfig();
	
	/**
	 * Sets the {@link ISMPLocator} implementation that the new <code>SMPClient</code> should use to find the location
	 * of the SMP serving a specific participant.
	 * 
	 * @param locator	The SMP locator implementation
	 * @return	this builder
	 */
	public SMPClientBuilder setSMPLocator(ISMPLocator locator) {
		newClientConfig.setSMPLocator(locator);
		return this;
	}
	
	/**
	 * Sets the {@link IRequestExecutor} implementation that the new <code>SMPClient</code> should use to execute
	 * the SMP queries.
	 * 
	 * @param executor	The request executor implementation
	 * @return this builder
	 */
	public SMPClientBuilder setRequestExecutor(IRequestExecutor executor) {
		newClientConfig.setRequestExecutor(executor);
		return this;
	}
	
	/**
	 * Sets the {@link ICertificateFinder} implementation  that the new <code>SMPClient</code> should use to get the
	 * certificate used by the SMP for signing the results. The certificate finder only needs to be set when a custom
	 * finder is needed, i.e. if the certificate is not embedded within the <code>ds:Signature</code> element.
	 * 
	 * @param finder	The certificate finder implementation
	 * @return this builder
	 */
	public SMPClientBuilder setCertificateFinder(ICertificateFinder finder) {
		newClientConfig.setCertificateFinder(finder);
		return this;
	}
	
	/**
	 * Sets the {@link ITrustValidator} implementation  that the new <code>SMPClient</code> should use to validate 
	 * that certificate used by the SMP is trusted and the results can be used. Using trust validating is <b>optional
	 * </b>. If no validator is specified the SMP Client will only verify the correctness of the hashes and not 
	 * check the validity of the certificate. 
	 * 
	 * @param validator	The trust validator implementation
	 * @return this builder
	 */
	public SMPClientBuilder setTrustValidator(ITrustValidator validator) {
		newClientConfig.setTrustValidator(validator);
		return this;				
	}
	
	/**
	 * Sets the mapping of SMP result name space URIs to the {@link ISMPResultProcessor} that will handle the result
	 * and transform the XML documents into the object model representation.
	 * 
	 * @param processorMap	The mapping of result name space URIs to {@link ISMPResultProcessor}s. Must not be empty.
	 */	
	public SMPClientBuilder setProcessors(Map<String, ISMPResultProcessor> processorMap) {
		newClientConfig.setProcessors(processorMap);
		return this;
	}
	
	/**
	 * Sets the {@link ISMPResultProcessor} implementation that should be used for the given result namespace URI. 
	 * When there already exists a mapping for the given name space URI it will be replaced.
	 * 
	 * @param namespaceURI	String containing the name space URI 
	 * @param processor		The {@link ISMPResultProcessor} to use for result with the given namespace
	 */
	public SMPClientBuilder setProcessor(final String namespaceURI, ISMPResultProcessor processor) {
		newClientConfig.setProcessor(namespaceURI, processor);
		return this;
	}
	
	/**
	 * Gets a new {@link ISMPClient} implementation configured according to the settings provided to the builder.
	 * 
	 * @return 	The new SMP client 
	 */
	public ISMPClient build() {
		if (newClientConfig.getSMPLocator() == null)
			throw new IllegalStateException("No SMP locator specified, unable to build client");
		
		return new SMPClient(newClientConfig);
	}
}
