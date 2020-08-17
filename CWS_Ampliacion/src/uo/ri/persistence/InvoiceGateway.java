package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.common.BusinessException;

public interface InvoiceGateway {

	InvoiceDto WorkOrderBilling(List<Long> workOrderIds) throws BusinessException;

	void setConnection(Connection c);
}
