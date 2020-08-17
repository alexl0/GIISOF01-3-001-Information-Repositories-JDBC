package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;

import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.VehicleTypeDto;

public interface CourseReportGateway {

	void setConnection(Connection c);

	List<VehicleTypeDto> findVehicleTypes();

	List<CertificateDto> findCertificatesByVehicleType(String vehicleType);

}
