package se.lumera.example.payments.Model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import se.lumera.example.payments.DTO.Payments;
import se.lumera.example.payments.DTO.Payments.Payment;
import se.lumera.example.payments.Enum.TextFile;

public class FileReader {

	private FileParser fileParser;
	
	public FileReader() {
		fileParser = new FileParser();
	}
	
	public ArrayList<Payments> filesToParse(){
		try {
			File directory = new File("TextFilesToParse");
			File[] files = directory.listFiles();
			ArrayList<Payments> listOfPayments = new ArrayList<>();
			
			for(File file : files) {
				listOfPayments.add(readFile(file));
			}
			return listOfPayments;
		}catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}

	
	public Payments readFile(File file) {
		try{
			ArrayList<String> fileLines = new ArrayList<>();
			Payments payments = null;
			if(file.getName().endsWith(TextFile.DISBURSE.fileName)){
				
	            Files.lines(file.toPath(), StandardCharsets.ISO_8859_1)
	                    .forEach(fileLines::add);
	            
	            for(String line : fileLines)
	                 payments = fileParser.parseValues(line,TextFile.DISBURSE, payments);
	            
			}	
			else if(file.getName().endsWith(TextFile.PAYMENT.fileName)) {
				Files.lines(file.toPath(), StandardCharsets.ISO_8859_1)
                .forEach(fileLines::add);
        
		        for(String line : fileLines)
		             payments = fileParser.parseValues(line,TextFile.PAYMENT, payments);
        
			}
            
            if(!checkPaymentAmount(payments) && payments != null)
            	throw new IllegalArgumentException("Payments are wrong amount");
            return payments;
        } catch (NullPointerException | IOException e) {
            throw new NullPointerException();
        }
	}
	
	

	private boolean checkPaymentAmount(Payments payments) {
		int amountOfPayments = payments.getAmountOfPayments();
		ArrayList<Payment> paymentsToCheck = payments.getAllPayments();
		BigDecimal totalSum = payments.getTotalSum();
		BigDecimal paymentAdd = BigDecimal.ZERO;
		
		for(int index = 0; index < amountOfPayments; index++) {
			paymentAdd = paymentAdd.add(paymentsToCheck.get(index).getAmount());
		}

		return totalSum.compareTo(paymentAdd) == 0;
	}
	
	/*private TextFile checkFileTextType() {
		
	}*/

	
}
