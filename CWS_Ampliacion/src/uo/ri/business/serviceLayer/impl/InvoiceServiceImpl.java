package uo.ri.business.serviceLayer.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.serviceLayer.InvoiceService;
import uo.ri.business.transactionScripts.cashier.WorkOrderBilling;
import uo.ri.common.BusinessException;

public class InvoiceServiceImpl implements InvoiceService {

	@Override
	public InvoiceDto createInvoiceFor(List<Long> workOrderIds) throws BusinessException {		
		WorkOrderBilling wob=new WorkOrderBilling(workOrderIds);
		InvoiceDto factura=wob.execute();
		return factura;
	}

	@Override
	public InvoiceDto findInvoice(Long numeroFactura) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentMeanDto> findPayMethodsForInvoice(Long idFactura) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settleInvoice(Long idFactura, Map<Long, Double> cargos) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BreakdownDto> findRepairsByClient(String dni) throws BusinessException {
		// TODO
		return null;
	}

}
