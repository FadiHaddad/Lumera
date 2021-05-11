package se.lumera.example.payments.Enum;

public enum PostType {
	
	DISBURSEOPENING("O"),
    DISBURSEPOST("B"),
    PAYMENTSTART("00"),
    PAYMENTPOST("30"),
    PAYMENTEND("99");

    public final String postValue;

    PostType(String postValue){
        this.postValue = postValue;
    }

}
