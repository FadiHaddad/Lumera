package se.lumera.example.payments;

import se.lumera.example.payments.Controller.PaymentReceiverController;
import se.lumera.example.payments.Enum.TextFile;
import se.lumera.example.payments.Model.FileParser;
import se.lumera.example.payments.Service.PaymentReceiverImpl;

public class Main {

	public static void main(String[] args) {
		PaymentReceiverController paymentReceiverController = new PaymentReceiverController(new PaymentReceiverImpl());

        //paymentReceiverController.startParsingFiles();
        
        
        paymentReceiverController.parseOneFile("Exempelfil_inbetalningstjansten.txt", TextFile.PAYMENT);
       
	}

}
