package uo.ri.business.serviceLayer.impl;

import java.util.List;

import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.TrainingForMechanicRow;
import uo.ri.business.dto.TrainingHoursRow;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.business.serviceLayer.CourseReportService;
import uo.ri.business.transactionScripts.administrator.FindCertificatedByVehicleType;
import uo.ri.business.transactionScripts.administrator.FindVehicleTypes;
import uo.ri.common.BusinessException;

public class CourseReportServiceImpl implements CourseReportService {

	@Override
	public List<TrainingForMechanicRow> findTrainigByMechanicId(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrainingHoursRow> findTrainingByVehicleTypeAndMechanic() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CertificateDto> findCertificatedByVehicleType(String vehicleType) throws BusinessException {
		FindCertificatedByVehicleType fcbvt=new FindCertificatedByVehicleType(vehicleType);
		return fcbvt.execute();
	}

	@Override
	public List<VehicleTypeDto> findVehicleTypes() throws BusinessException {
		FindVehicleTypes fvt = new FindVehicleTypes();
		return fvt.execute();
	}

}
