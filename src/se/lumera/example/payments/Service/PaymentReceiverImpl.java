package se.lumera.example.payments.Service;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentReceiverImpl implements PaymentReceiver {

	@Override
	public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
		System.out.println(accountNumber + " " + paymentDate + " " + currency);
		
	}

	@Override
	public void payment(BigDecimal amount, String reference) {
		System.out.println(amount + " " + reference);
		
	}

	@Override
	public void endPaymentBundle() {
		System.out.println("File parsing finished");
		
	}

}
