package uo.ri.cws.application.service.training;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Manager
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface CertificateService {
	
	/**
	 * Generates certificates according to the rules:
	 * 	- Each vehicle type specifies the number of attended-and-passed 
	 * 		training hours needed to get the certificate for that vehicle type
	 * 
	 * 	- The mechanic has to accumulate at least that number of hours in 
	 * 		one or several courses
	 * 
	 *  - A course specifies the % of training hours devoted to some vehicle types
	 *  
	 * @return the number of new certificates generated
	 * DOES NOT @throws BusinessException
	 */
	int generateCertificates() throws BusinessException;

}
