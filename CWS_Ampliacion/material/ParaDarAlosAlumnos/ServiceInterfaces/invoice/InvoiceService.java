package uo.ri.cws.application.service.invoice;

import java.util.List;
import java.util.Map;

import uo.ri.cws.application.dto.InvoiceDto;
import uo.ri.cws.application.dto.PaymentMeanDto;
import uo.ri.cws.application.dto.WorkOrderDto;
import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Cashier
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface InvoiceService {

	InvoiceDto createInvoiceFor(List<Long> workOrderIds) throws BusinessException;
	InvoiceDto findInvoice(Long numeroFactura) throws BusinessException;
	List<PaymentMeanDto> findPayMethodsForInvoice(Long invoiceId) throws BusinessException;
	void settleInvoice(Long invoiceId, Map<Long, Double> cargos) throws BusinessException;

	List<WorkOrderDto> findWorkOrdersByClientDni(String dni) throws BusinessException;
	
}
