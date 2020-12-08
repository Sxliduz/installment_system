package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class PaymentService {
	
	private OnlinePayment onlinePayment;

	public PaymentService(OnlinePayment onlinePayment) {
		this.onlinePayment = onlinePayment;
	}

	public void processContract (Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for (int i=1; i<=months; i++) {
			Date date = addMonths(contract.getDate(), i);
			double updatedQuota = basicQuota + onlinePayment.interest(basicQuota, i);
			double fullQuota = updatedQuota + onlinePayment.paymentFee(updatedQuota);
			contract.addInstallment(new Installment(date, fullQuota));
		}
	}
	
	private Date addMonths(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}
}