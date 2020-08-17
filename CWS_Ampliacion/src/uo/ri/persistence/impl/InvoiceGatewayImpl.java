package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.Conf;
import uo.ri.persistence.InvoiceGateway;

public class InvoiceGatewayImpl implements InvoiceGateway {

	Connection connection;

	InvoiceDto factura;
	
	@Override
	public InvoiceDto WorkOrderBilling(List<Long> workOrderIds) throws BusinessException {		
		try {
			//connection = Jdbc.getConnection();
			//connection.setAutoCommit(false);

			testRepairs(workOrderIds);

			long numberInvoice = generateInvoiceNumber();
			Date dateInvoice = Dates.today();
			double amount = calculateTotalInvoice(workOrderIds); // not vat included
			double vat = vatPercentage(amount, dateInvoice);
			double total = amount * (1 + vat/100); // vat included
			total = Round.twoCents(total);

			long idInvoice = createInvoice(numberInvoice, dateInvoice, vat, total);
			linkWorkorderInvoice(idInvoice, workOrderIds);
			updateWorkOrderStatus(workOrderIds, "INVOICED");

			factura.number=numberInvoice;
			factura.date=dateInvoice;
			factura.amount=amount;
			factura.vat=vat;
			factura.total=total;
			factura.id=idInvoice;

			//connection.commit();

			return factura;
		}
		catch (SQLException e) {
			try { connection.rollback(); } catch (SQLException ex) {};
			throw new RuntimeException(e);
		}
		catch (BusinessException e) {
			try { connection.rollback(); } catch (SQLException ex) {};
			throw e;
		}
		finally {
			//Jdbc.close(connection);
		}
	}

	@Override
	public void setConnection(Connection c) {
		this.connection=c;
	}



	private void testRepairs(List<Long> workOrderIDS) throws SQLException, BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_CHECK_WORKORDER_STATUS"));

			for (Long workOrderID : workOrderIDS) {
				pst.setLong(1, workOrderID);

				rs = pst.executeQuery();
				if (rs.next() == false) {
					throw new BusinessException("Workorder " + workOrderID + " doesn't exist");
				}

				String status = rs.getString(1); 
				if (! "FINISHED".equalsIgnoreCase(status) ) {
					throw new BusinessException("Workorder " + workOrderID + " is not finished yet");
				}

			}
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	private void updateWorkOrderStatus(List<Long> breakdownIds, String status) throws SQLException {

		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_UPDATE_WORKORDER_STATUS"));

			for (Long breakdownId : breakdownIds) {
				pst.setString(1, status);
				pst.setLong(2, breakdownId);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}
	}

	private void linkWorkorderInvoice(long invoiceId, List<Long> workOrderIDS) throws SQLException {

		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_WORKORDER_INVOICE_ASSOC"));

			for (Long breakdownId : workOrderIDS) {
				pst.setLong(1, invoiceId);
				pst.setLong(2, breakdownId);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}
	}

	private long createInvoice(long numberInvoice, Date dateInvoice,
			double vat, double total) throws SQLException {

		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_INSERT_INVOICE"));
			pst.setLong(1, numberInvoice);
			pst.setDate(2, new java.sql.Date(dateInvoice.getTime()));
			pst.setDouble(3, vat);
			pst.setDouble(4, total);
			pst.setString(5, "NOT_YET_PAID");

			pst.executeUpdate();

			return getGeneratedKey(numberInvoice); // New invoice id

		} finally {
			Jdbc.close(pst);
		}
	}

	private long getGeneratedKey(long numberInvoice) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_RETRIEVE_GENERATED_KEY"));
			pst.setLong(1, numberInvoice);
			rs = pst.executeQuery();
			rs.next();

			return rs.getLong(1);

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	private Long generateInvoiceNumber() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_LAST_INVOICE_NUMBER"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else {  // there is none yet
				return 1L;
			}
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	private double vatPercentage(double totalInvoice, Date dateInvoice) {
		return Dates.fromString("1/7/2012").before(dateInvoice) ? 21.0 : 18.0;
	}

	protected double calculateTotalInvoice(List<Long> workOrderIDS)
			throws BusinessException, SQLException {

		double totalInvoice = 0.0;
		for(Long workOrderID : workOrderIDS) {
			double laborTotal = checkTotalLabor(workOrderID);
			double sparesTotal = checkTotalParts(workOrderID);
			double workTotal = laborTotal + sparesTotal;

			updateWorkorderTotal(workOrderID, workTotal);

			totalInvoice += workTotal; 
		}
		return totalInvoice;
	}

	private void updateWorkorderTotal(Long workOrderID, double total) throws SQLException {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_UPDATE_WORKORDER_AMOUNT"));
			pst.setDouble(1, total);
			pst.setLong(2, workOrderID);
			pst.executeUpdate();
		}	
		finally {
			Jdbc.close(pst);
		}
	}

	private double checkTotalParts(Long workOrderID) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_PARTS_TOTAL"));
			pst.setLong(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				return 0.0; // There is no part replaced in this breakdown 
			}

			return rs.getDouble(1);

		}
		finally {
			Jdbc.close(rs, pst);
		}
	}

	private double checkTotalLabor(Long workOrderID) throws BusinessException, SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_LABOR_TOTAL"));
			pst.setLong(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				throw new BusinessException("Workorder does not exist or it can not be charged");
			}

			return rs.getDouble(1);

		} catch (BusinessException e) {
			throw e;
		}
		finally {
			Jdbc.close(rs, pst);
		}

	}



}
