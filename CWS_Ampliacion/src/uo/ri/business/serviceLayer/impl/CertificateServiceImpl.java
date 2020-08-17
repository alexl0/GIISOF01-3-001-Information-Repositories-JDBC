package uo.ri.business.serviceLayer.impl;

import uo.ri.business.serviceLayer.CertificateService;
import uo.ri.business.transactionScripts.administrator.GenerateCertificates;
import uo.ri.common.BusinessException;

public class CertificateServiceImpl implements CertificateService {

	@Override
	public int generateCertificates() throws BusinessException {
		GenerateCertificates gc=new GenerateCertificates();
		return gc.execute();
	}

}
