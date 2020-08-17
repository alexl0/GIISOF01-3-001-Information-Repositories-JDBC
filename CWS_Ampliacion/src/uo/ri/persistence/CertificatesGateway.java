package uo.ri.persistence;

import java.sql.Connection;

public interface CertificatesGateway {

	void setConnection(Connection c);

	int generateCertificates();

}
