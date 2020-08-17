package uo.ri.business.transactionScripts.cashier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.InvoiceGateway;

public class WorkOrderBilling {

	List<Long> workOrderIds;

	public WorkOrderBilling(List<Long> workOrderIds) {
		this.workOrderIds=workOrderIds;
	}

	public InvoiceDto execute() throws BusinessException {
		//Me lo he llevao todo a InvoiceGatewayImpl al metodo add

		try (Connection c=Jdbc.getConnection()){

			//InvoiceGateway ig=new InvoiceGatewayImpl();
			InvoiceGateway ig=PersistenceFactory.getInvoiceGateway();
			c.setAutoCommit(false);//Para hacer el commit/roollback según interese, mas adelante al comprobar. En este caso no tiene mucho sentido pero esa es la esencia

			ig.setConnection(c);

			InvoiceDto factura=ig.WorkOrderBilling(workOrderIds);
			c.commit();
			return factura;

		}
		catch(SQLException e) {
			throw new RuntimeException("Error de conexión");
		}

	}


}
