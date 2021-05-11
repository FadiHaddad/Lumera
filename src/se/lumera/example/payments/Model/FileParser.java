package se.lumera.example.payments.Model;

import se.lumera.example.payments.DTO.Payments;
import se.lumera.example.payments.Enum.PostType;
import se.lumera.example.payments.Enum.TextFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileParser {
	
	public Payments parseValues(String line, TextFile textFile, Payments payments) {
		try {
			switch(textFile) {
			case DISBURSE:
				return parseDisburse(line,textFile,payments);
			case PAYMENT:
				return parsePayment(line,textFile,payments);
			}
		}catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		}
		
		return null;
		
	}

	

	private Payments parseDisburse(String line, TextFile textFile, Payments payments) {
		if(line.substring(0,1).equals(PostType.DISBURSEOPENING.postValue)) {
			try {
				String testAccountValues = line.substring(1,16).trim();
				String[] clearingAndAccount = checkAccount(testAccountValues);
				String clearingNumber = clearingAndAccount[0], accountNumber = clearingAndAccount[1];
				BigDecimal totalSum = new BigDecimal(line.substring(16, 30).trim().replace(',', '.'));
				int amountOfPayments = Integer.parseInt(line.substring(30,40).trim());
				String dateOfPaymentString = line.substring(40,48).trim();
				Date dateOfPayment = generateDateFromString(dateOfPaymentString);
				String currency = line.substring(48,51).trim();
				return new Payments(textFile, clearingNumber, accountNumber, totalSum, amountOfPayments, dateOfPayment, currency);
			}
			catch(ParseException | NumberFormatException e) {
				System.err.println(e.getMessage());
			}
		}
		else if(line.substring(0,1).equals(PostType.DISBURSEPOST.postValue)) {
			try {
				if(payments == null) {
					throw new NullPointerException();
				}
				BigDecimal amount = new BigDecimal(line.substring(1, 15).trim().replace(',', '.'));
	            String referens = line.substring(15, 50).trim();
	            payments.addPayment(amount,referens);
	            return payments;
			} catch (IllegalArgumentException |NullPointerException e) {
				System.err.println(e.getMessage());
			}
		}
		return payments;
	}


	private Payments parsePayment(String line, TextFile textFile, Payments payments) {
		if(line.substring(0,2).equals(PostType.PAYMENTSTART.postValue)) {
			try{
				String clearingAndAccount = line.substring(10,14) + " " + line.substring(14, 24);
				String[] clearingAndAccountChecked = checkAccount(clearingAndAccount);
				return new Payments(textFile,clearingAndAccountChecked[0], clearingAndAccountChecked[1]);
			}catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			}
			
		}
		else if(line.substring(0,2).equals(PostType.PAYMENTPOST.postValue)) {
			try{
				if(payments == null) {
					throw new NullPointerException("Start post not created");
				}
				BigDecimal paymentSum = getSum(line.substring(2,22));
				String reference = line.substring(40,65).trim();
				payments.addPayment(paymentSum, reference);
				return payments;
			}catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			}
		}
		else if(line.substring(0,2).equals(PostType.PAYMENTEND.postValue)) {
			try {
				if(payments == null) {
					throw new NullPointerException();
				}
				BigDecimal totalSum = getSum(line.substring(2,22));
				int amountofPayments = Integer.parseInt(line.substring(30,38));
				payments.setTotalSum(totalSum);
				payments.setAmountOfPayments(amountofPayments);
				return payments;
			}catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			}
		}
		return payments;
	}
	
	private BigDecimal getSum(String decimalString) throws NumberFormatException {
		String decimal = decimalString.substring(decimalString.length()-2, decimalString.length());
        decimalString = decimalString.substring(0,decimalString.length()-2) + "." + decimal;
        return new BigDecimal(decimalString);
	}



	private Date generateDateFromString(String dateOfPaymentString) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd").parse(dateOfPaymentString);
	}

	private String[] checkAccount(String testAccountValues) throws NumberFormatException {
		String[] splitAccount = testAccountValues.split(" ");

        BigInteger clearingNumberCheck = new BigInteger(splitAccount[0]);
        BigInteger accountNumberCheck = new BigInteger(splitAccount[1]);

        return splitAccount;
	}
}
