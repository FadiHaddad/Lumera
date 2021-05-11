package se.lumera.example.payments.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import se.lumera.example.payments.Enum.TextFile;

public class Payments {
	
	public class Payment{
		private BigDecimal amount;
		private String reference;
		
		public Payment(BigDecimal amount, String reference) {
			this.amount = amount;
			this.reference = reference;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getReference() {
			return reference;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}
		
	}
	
	private TextFile textFile;
    private String accountNumber;
    private BigDecimal totalSum;
    private int amountOfPayments;
    private Date dateOfPayment;
    private String currency;
    private ArrayList<Payment> listOfPayments;

    public Payments(){

    }

    public Payments(TextFile textFil, String clearingNumber, String accountNumber) {
        this.textFile = textFil;
        this.accountNumber = clearingNumber + accountNumber;
        this.listOfPayments = new ArrayList<>();
        this.currency = "SEK";
    }

    public Payments(TextFile textFil, String clearingNumber, String accountNumber, BigDecimal totalSum, int amountOfPayments, Date dateOfPayment, String currency) {
        this.textFile = textFil;

        this.accountNumber = clearingNumber + accountNumber;
        this.totalSum = totalSum;
        this.amountOfPayments = amountOfPayments;
        this.dateOfPayment = dateOfPayment;
        this.currency = currency;
        this.listOfPayments = new ArrayList<Payment>(amountOfPayments);
    }

    public TextFile getTextFile() {
        return textFile;
    }

    public void setTextFile(TextFile textFile) {
        this.textFile = textFile;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum= totalSum;
    }

    public int getAmountOfPayments() {
        return amountOfPayments;
    }

    public void setAmountOfPayments(int amountOfPayments) {
        this.amountOfPayments = amountOfPayments;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }



    public void addPayment(BigDecimal amount, String reference){
        if(this.listOfPayments == null)
            throw new NullPointerException();
        else
            this.listOfPayments.add(new Payment(amount,reference));
    }

    public ArrayList<Payment> getAllPayments() {
        return listOfPayments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.listOfPayments = payments;
    }


}
