package org.holodeckb2b.bdxr.smp.impl;

import java.util.HashMap;
import java.util.Map;

import org.holodeckb2b.bdxr.sml.ISMPLocator;
import org.holodeckb2b.bdxr.smp.api.ICertificateFinder;
import org.holodeckb2b.bdxr.smp.api.IRequestExecutor;
import org.holodeckb2b.bdxr.smp.api.ISMPClient;
import org.holodeckb2b.bdxr.smp.api.ISMPResultProcessor;
import org.holodeckb2b.bdxr.smp.api.ITrustValidator;

/**
 * Contains the configuration for a {@link ISMPClient} instance.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SMPClientConfig {
	/**
	 * The {@link ISMPLocator} implementation the <code>SMPClient</code> should use to find the location of the SMP 
	 * serving a specific participant.
	 */
	private ISMPLocator smpLocator;
	/**
	 * The {@link IRequestExecutor} implementation the <code>SMPClient</code> should use to execute the SMP queries.
	 */
	private IRequestExecutor requestExecutor;
	/**
	 * The {@link ICertificateFinder} implementation the <code>SMPClient</code> should use to get the certificate used 
	 * by the SMP for signing the results.
	 */
	private ICertificateFinder certFinder;
	/**
	 * The {@link ITrustValidator} implementation the <code>SMPClient</code> should use to validate that certificate 
	 * used by the SMP is trusted and the results can be used.
	 */
	private ITrustValidator trustValidator;
    /**
     * Map of namespace URI to the {@link ISMPResultProcessor} implementation that can convert XML documents with that
     * namespace to object representation
     */
	private Map<String, ISMPResultProcessor> processorMap;

	/**
	 * Create a new SMP Client configuration with the default request executor, processor map and certificate finder
	 */
	public SMPClientConfig() {    	
		requestExecutor = new DefaultRequestExecutor();
        processorMap = new HashMap<>(2);
        processorMap.put(PEPPOLResultProcessor.NAMESPACE_URI, new PEPPOLResultProcessor());
        certFinder = new DefaultCertFinder();
    }

	/**
	 * Sets the {@link ISMPLocator} implementation the <code>SMPClient</code> should use to find the location of the 
	 * SMP serving a specific participant.
	 * 
	 * @param locator	The SMP locator implementation
	 */
	public void setSMPLocator(final ISMPLocator locator) {
		this.smpLocator = locator;
	}

	/**
	 * Gets the {@link ISMPLocator} implementation the <code>SMPClient</code> should use to find the location of the 
	 * SMP serving a specific participant.
	 * 
	 * return The SMP locator implementation
	 */
	public ISMPLocator getSMPLocator() {
		return this.smpLocator;
	}	
	
	/**
	 * Sets the {@link IRequestExecutor} implementation that the new <code>SMPClient</code> should use to execute
	 * the SMP queries.
	 * 
	 * @param executor	The request executor implementation
	 * @return this builder
	 */
	public void setRequestExecutor(IRequestExecutor executor) {
		this.requestExecutor = executor;
	}
	
	/**
	 * Gets the {@link IRequestExecutor} implementation that the new <code>SMPClient</code> should use to execute
	 * the SMP queries.
	 * 
	 * @return The request executor implementation
	 */
	public IRequestExecutor getRequestExecutor() {
		return this.requestExecutor;
	}
	
	/**
	 * Sets the {@link ICertificateFinder} implementation  that the new <code>SMPClient</code> should use to get the
	 * certificate used by the SMP for signing the results. The certificate finder only needs to be set when a custom
	 * finder is needed, i.e. if the certificate is not embedded within the <code>ds:Signature</code> element.
	 * 
	 * @param finder	The certificate finder implementation
	 */
	public void setCertificateFinder(ICertificateFinder finder) {
		this.certFinder = finder;
	}
	
	/**
	 * Gets the {@link ICertificateFinder} implementation  that the new <code>SMPClient</code> should use to get the
	 * certificate used by the SMP for signing the results. 
	 * 
	 * @return The certificate finder implementation
	 */
	public ICertificateFinder getCertificateFinder() {
		return this.certFinder;
	}
	
	/**
	 * Sets the {@link ITrustValidator} implementation  that the new <code>SMPClient</code> should use to validate 
	 * that certificate used by the SMP is trusted and the results can be used. Using trust validating is <b>optional
	 * </b>. If no validator is specified the SMP Client will only verify the correctness of the hashes and not 
	 * check the validity of the certificate. 
	 * 
	 * @param validator	The trust validator implementation
	 */
	public void setTrustValidator(ITrustValidator validator) {
		this.trustValidator = validator;
	}
	
	/**
	 * Gets the {@link ITrustValidator} implementation  that the new <code>SMPClient</code> should use to validate 
	 * that certificate used by the SMP is trusted and the results can be used. 
	 * 
	 * @return	The trust validator implementation
	 */
	public ITrustValidator getTrustValidator() {
		return this.trustValidator;
	}
	
	/**
	 * Sets the mapping of SMP result name space URIs to the {@link ISMPResultProcessor} that will handle the result
	 * and transform the XML documents into the object model representation.
	 * 
	 * @param processorMap	The mapping of result name space URIs to {@link ISMPResultProcessor}s. Must not be empty.
	 */
	public void setProcessors(Map<String, ISMPResultProcessor> processorMap) {
		if (processorMap == null || processorMap.isEmpty())
			throw new IllegalArgumentException("The processor map must contain at least one mapping");
		this.processorMap = processorMap;
	}
	
	/**
	 * Sets the {@link ISMPResultProcessor} implementation that should be used for the given result namespace URI. 
	 * When there already exists a mapping for the given name space URI it will be replaced.
	 * 
	 * @param namespaceURI	String containing the name space URI 
	 * @param processor		The {@link ISMPResultProcessor} to use for result with the given namespace
	 */
	public void setProcessor(final String namespaceURI, ISMPResultProcessor processor) {
		if (this.processorMap == null) 
			this.processorMap = new HashMap<>();
		this.processorMap.put(namespaceURI, processor);
	}
	
	/**
	 * Gets the mapping of SMP result name space URIs to the {@link ISMPResultProcessor} that will handle the result
	 * and transform the XML documents into the object model representation.
	 * 
	 * @return The mapping of result name space URIs to {@link ISMPResultProcessor}s.
	 */
	public Map<String, ISMPResultProcessor> getProcessors() {
		return this.processorMap;		
	}	
}
