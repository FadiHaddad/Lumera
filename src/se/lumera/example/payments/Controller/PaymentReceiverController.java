
package se.lumera.example.payments.Controller;

import java.io.File;
import java.util.ArrayList;

import se.lumera.example.payments.DTO.Payments;
import se.lumera.example.payments.Enum.TextFile;
import se.lumera.example.payments.Model.FileReader;
import se.lumera.example.payments.Service.PaymentReceiver;

public class PaymentReceiverController {

	private final PaymentReceiver paymentReceiver;
	
	public PaymentReceiverController(PaymentReceiver paymentReceiver) {
		this.paymentReceiver = paymentReceiver;
	}
	
	public void startParsingFiles() {
		FileReader fileReader = new FileReader();
		
		try {
			ArrayList<Payments> payments = fileReader.filesToParse();
			
			payments.forEach(element -> {
				if(element != null) {
					callInterface(element);
				}
			});
		}catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void parseOneFile(String fileName, TextFile textFile) {
		FileReader fileReader = new FileReader();
		
		try{
			Payments payments = fileReader.readFile(new File("TextFilesToParse/" + fileName));
			if(payments != null)
				callInterface(payments);
		}catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void callInterface(Payments payments) {
		paymentReceiver.startPaymentBundle(payments.getAccountNumber(),payments.getDateOfPayment(), payments.getCurrency());
		payments.getAllPayments().forEach(payment -> paymentReceiver.payment(payment.getAmount(), payment.getReference()));
		paymentReceiver.endPaymentBundle();
	}
	
}
