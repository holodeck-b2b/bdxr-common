package org.holodeckb2b.bdxr.smp.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.holodeckb2b.bdxr.smp.api.SMPClientBuilder;
import org.holodeckb2b.bdxr.smp.api.SMPQueryException;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessIdentifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceInformation;
import org.holodeckb2b.bdxr.utils.Utils;
import org.holodeckb2b.brdx.smp.testhelpers.MockResultProcessor;
import org.holodeckb2b.brdx.smp.testhelpers.TestDocExecutor;
import org.junit.jupiter.api.Test;

class SMPClientTest {

	@Test
	void testGetEndpointsForProcess() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier selectedProcId = new ProcessIdentifier("PROCID_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(selectedProcId);			
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);

		ProcessIdentifier procId = new ProcessIdentifier("PROCID_2");
		procInfo = new ProcessInfo();
		procInfo.addProcessId(procId);		
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
													.setSMPLocator(new StaticLocator("http://localhost"))
													.setRequestExecutor(new TestDocExecutor())
													.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
													.build()
													.getEndpoints(participantId, serviceId, selectedProcId);
						
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
															&& ep.getTransportProfile().equals(epInf.getTransportProfile())
													 	) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
													 			&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}

	@Test
	void testGetEndpointsForProcessAndRole() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier procId = new ProcessIdentifier("PROCID_1");
		Identifier roleId = new Identifier("ROLE_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(procId);
		procInfo.addRole(roleId);
		procInfo.addRole(new Identifier("ROLE_11"));
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);
		
		procInfo = new ProcessInfo();
		procInfo.addProcessId(new ProcessIdentifier("PROCID_2"));
		procInfo.addRole(new Identifier("ROLE_2"));
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
					.build()
					.getEndpoints(participantId, roleId, serviceId, procId);
			
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
					&& ep.getTransportProfile().equals(epInf.getTransportProfile())
					) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
							&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}
	
	@Test
	void testInvalidNullArgs() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 		
		ProcessIdentifier procId = new ProcessIdentifier("PROCID_2");

		try {
			new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI))
					.build()
					.getEndpoints(null, serviceId, procId);
			fail();
		} catch (IllegalArgumentException e) {			
		} catch (SMPQueryException e) {
			fail();
		}
		try {
			new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI))
					.build()
					.getEndpoints(participantId, null, procId);
			fail();
		} catch (IllegalArgumentException e) {			
		} catch (SMPQueryException e) {
			fail();
		}
		try {
			new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI))
					.build()
					.getEndpoint(participantId, null, procId, null);
			fail();
		} catch (IllegalArgumentException e) {			
		} catch (SMPQueryException e) {
			fail();
		}
	}	
	
	@Test
	void testGetEndpoint() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier selectedProcId = new ProcessIdentifier("PROCID_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(selectedProcId);			
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);

		ProcessIdentifier procId = new ProcessIdentifier("PROCID_2");
		procInfo = new ProcessInfo();
		procInfo.addProcessId(procId);		
		svcInfo.addProcessInformation(procInfo);
		
		try {
			EndpointInfo endpoint = new SMPClientBuilder()
									.setSMPLocator(new StaticLocator("http://localhost"))
									.setRequestExecutor(new TestDocExecutor())
									.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
									.build()
									.getEndpoint(participantId, serviceId, selectedProcId, ep2Inf.getTransportProfile());
						
			assertNotNull(endpoint);
			assertEquals(ep2Inf.getEndpointURL(), endpoint.getEndpointURL());
			assertEquals(ep2Inf.getTransportProfile(), endpoint.getTransportProfile());			
		} catch (SMPQueryException e) {
			fail();
		}
	}	
	
	@Test
	void testGetEndpointNotFound() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier selectedProcId = new ProcessIdentifier("PROCID_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(selectedProcId);			
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);

		ProcessIdentifier procId = new ProcessIdentifier("PROCID_2");
		procInfo = new ProcessInfo();
		procInfo.addProcessId(procId);		
		svcInfo.addProcessInformation(procInfo);
		
		try {
			EndpointInfo endpoint = new SMPClientBuilder()
									.setSMPLocator(new StaticLocator("http://localhost"))
									.setRequestExecutor(new TestDocExecutor())
									.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
									.build()
									.getEndpoint(participantId, serviceId, selectedProcId, "test-3");
						
			assertNull(endpoint);
		} catch (SMPQueryException e) {
			fail();
		}
	}	
	
	@Test
	void testGetEndpointsForNoProcess() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier selectedProcId = new ProcessIdentifier();
		selectedProcId.setIsNoProcess(true);
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(selectedProcId);			
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);

		ProcessIdentifier procId = new ProcessIdentifier("PROCID_2");
		procInfo = new ProcessInfo();
		procInfo.addProcessId(procId);		
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
													.setSMPLocator(new StaticLocator("http://localhost"))
													.setRequestExecutor(new TestDocExecutor())
													.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
													.build()
													.getEndpoints(participantId, serviceId, null);
						
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
															&& ep.getTransportProfile().equals(epInf.getTransportProfile())
													 	) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
													 			&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}	

	@Test
	void testGetEndpointsIgnoreEmptyProcess() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier selectedProcId = new ProcessIdentifier("PROCID_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(selectedProcId);			
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);

		procInfo = new ProcessInfo();
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
													.setSMPLocator(new StaticLocator("http://localhost"))
													.setRequestExecutor(new TestDocExecutor())
													.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
													.build()
													.getEndpoints(participantId, serviceId, selectedProcId);
						
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
															&& ep.getTransportProfile().equals(epInf.getTransportProfile())
													 	) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
													 			&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}
	
	@Test
	void testGetEndpointsIgnoreEmptyRoles() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier procId = new ProcessIdentifier("PROCID_1");
		Identifier roleId = new Identifier("ROLE_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(procId);
		procInfo.addRole(roleId);
		procInfo.addRole(new Identifier("ROLE_11"));
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);
		
		procInfo = new ProcessInfo();
		procInfo.addProcessId(new ProcessIdentifier("PROCID_2"));
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
					.build()
					.getEndpoints(participantId, roleId, serviceId, procId);
			
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
					&& ep.getTransportProfile().equals(epInf.getTransportProfile())
					) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
							&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}	
	
	@Test
	void testGetEndpointsMatchToEmptyProcId() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessInfo procInfo = new ProcessInfo();		
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);
		
		procInfo = new ProcessInfo();
		procInfo.addProcessId(new ProcessIdentifier("PROCID_2"));
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
					.build()
					.getEndpoints(participantId, serviceId, new ProcessIdentifier("MATCH_EMPTY"));
			
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
					&& ep.getTransportProfile().equals(epInf.getTransportProfile())
					) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
							&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}
	
	@Test
	void testGetEndpointsMatchEmptyRoles() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier procId = new ProcessIdentifier("PROCID_1");
		ProcessInfo procInfo = new ProcessInfo();		
		procInfo.addProcessId(procId);
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);
		
		procInfo = new ProcessInfo();
		procInfo.addProcessId(new ProcessIdentifier("PROCID_2"));
		procInfo.addRole(new Identifier("ROLE_2"));
		svcInfo.addProcessInformation(procInfo);
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
					.build()
					.getEndpoints(participantId, new Identifier("MATCH_EMPTY"), serviceId, procId);
			
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
					&& ep.getTransportProfile().equals(epInf.getTransportProfile())
					) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
							&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}
	
	@Test
	void testGetEndpointsIgnoreCatchAllProcInfo() {
		
		Identifier participantId = new Identifier("PARTID_1", "test:scheme"); 
		Identifier serviceId = new Identifier("SVCID_1"); 
		ServiceInformation svcInfo = new ServiceInformation();
		svcInfo.setServiceId(serviceId);
		svcInfo.setParticipantId(participantId);
		
		ProcessIdentifier procId = new ProcessIdentifier("PROCID_1");
		Identifier roleId = new Identifier("ROLE_1"); 
		ProcessInfo procInfo = new ProcessInfo();			
		procInfo.addProcessId(procId);
		procInfo.addRole(roleId);
		EndpointInfo epInf = new EndpointInfo("test-1", "http://this.is.a.result");
		procInfo.addEndpoint(epInf);
		EndpointInfo ep2Inf = new EndpointInfo("test-2", "http://this.is.another.result");
		procInfo.addEndpoint(ep2Inf);
		
		svcInfo.addProcessInformation(procInfo);
		
		svcInfo.addProcessInformation(new ProcessInfo());
		
		try {
			List<EndpointInfo> endpoints = new SMPClientBuilder()
					.setSMPLocator(new StaticLocator("http://localhost"))
					.setRequestExecutor(new TestDocExecutor())
					.addProcessor(new MockResultProcessor(TestDocExecutor.TEST_NS_URI, svcInfo))
					.build()
					.getEndpoints(participantId, roleId, serviceId, procId);
			
			assertFalse(Utils.isNullOrEmpty(endpoints));
			assertEquals(2, endpoints.size());
			assertTrue(endpoints.stream().allMatch(ep -> ( ep.getEndpointURL().equals(epInf.getEndpointURL())
					&& ep.getTransportProfile().equals(epInf.getTransportProfile())
					) || (ep.getEndpointURL().equals(ep2Inf.getEndpointURL())
							&& ep.getTransportProfile().equals(ep2Inf.getTransportProfile()))));			
		} catch (SMPQueryException e) {
			fail();
		}
	}	
}
