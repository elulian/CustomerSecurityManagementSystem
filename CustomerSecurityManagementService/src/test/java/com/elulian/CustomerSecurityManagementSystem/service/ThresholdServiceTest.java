/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elulian.CustomerSecurityManagementSystem.exception.NotExistsException;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

/**
 * @author elulian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-resources.xml",
// "classpath:applicationContext-dao.xml",
// "classpath:applicationContext-service.xml",
	"classpath:**/security.xml",
"classpath:**/applicationContext*.xml" })
public class ThresholdServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ThresholdServiceTest.class);
	
	@Autowired
	private IThresholdService thresholdService;
	
	private String ruleVersionString = "0.1-SNAPSHOT";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logger.info("init threshold service before test");
		thresholdService.initThresholdService(IThresholdService.defaultGroupId, IThresholdService.defaultArtifactId, ruleVersionString);
   //	System.out.println("Default Charset=    ****我是*********" + Charset.defaultCharset());
   // 	System.out.println("Default Charset in Use=" + getDefaultCharSet());
    	
	}
/*	private static String getDefaultCharSet() {
    	OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
    	String enc = writer.getEncoding();
    	return enc;
    }
*/
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#refreshThresholdService()}.
	 * @throws NotExistsException 
	 */
	@Test (expected = com.elulian.CustomerSecurityManagementSystem.exception.NotExistsException.class)
	public void testRefreshThresholdService() throws NotExistsException {
		//fail("Not yet implemented");
		logger.info("======testRefreshThresholdService========");
		thresholdService.refreshThresholdService(IThresholdService.defaultGroupId, IThresholdService.defaultArtifactId, "wrong-version");
		
	}
	
	

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 * @throws NotExistsException 
	 */
	@Test
	public void testSetCustomerThresholdsInfoForAllRules() throws NotExistsException {
		logger.info("======testSetCustomerThresholdsInfoForAllRulesMatched========");
		thresholdService.refreshThresholdService(IThresholdService.defaultGroupId, IThresholdService.defaultArtifactId, ruleVersionString);
		CustomerInfo info = new CustomerInfo();
		/* Integer can not be auto converted to int in drool for null object */
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(280 == info.getRiskValue());
		StringBuffer sb = new StringBuffer("Invalid professionCode value: ");
		sb.append(info.getProfessionCode()).append(" Invalid Certification End date value: ")
			.append(info.getCertificateEndDate()).append(" Customer Name is null")
			.append(" Invalid Certification ID: ").append(info.getCertificateId())
			.append(" 境外标志: ").append(info.getForeignFlag());
		assertEquals(sb.toString(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("老师");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		info.setCustomerName("customerName");
		info.setForeignFlag(false);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
	}
	
}
