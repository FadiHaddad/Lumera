package se.lumera.example.payments.Enum;

public enum TextFile {
	DISBURSE("_betalningsservice.txt"),
    PAYMENT("_inbetalningstjansten.txt");

    public final String fileName;

    TextFile(String fileName){
        this.fileName = fileName;
    }
}
