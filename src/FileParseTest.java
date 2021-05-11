import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import se.lumera.example.payments.DTO.Payments;
import se.lumera.example.payments.Enum.TextFile;
import se.lumera.example.payments.Model.FileParser;
import se.lumera.example.payments.Model.FileReader;

class FileParseTest {

	@Test
	void parseSuccessfully() {
		File FILE_TO_TEST = new File("TextFilesToParse/Exempelfil_inbetalningstjansten.txt");
		
		FileReader fileReader = new FileReader();
		
		Payments returnValue = fileReader.readFile(FILE_TO_TEST);
		
		Payments expected = new Payments(TextFile.PAYMENT,"1234","1234567897");
		expected.setTotalSum(new BigDecimal("15300.00"));
		
		
		assertEquals(expected.getAccountNumber(), returnValue.getAccountNumber());
		assertEquals(expected.getTotalSum(), returnValue.getTotalSum());
		
	}
	
	@Test
	void parseThrowIllegalArgumentException() {
		File FILE_TO_TEST = new File("TextFilesToParse/Exempelfil_betalningsservice.txt");
		
		FileReader fileReader = new FileReader();
		
		IllegalArgumentException expectedIllegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () ->
				fileReader.readFile(FILE_TO_TEST));
		
		assertEquals(expectedIllegalArgumentException.getMessage(),"Payments are wrong amount");
	}
	
	@Test
	void parseThrowNullPointerExceptionBecauseItDoesntHaveAnOpeningSection() {
		String paymentLine = "30000000000000004000000000000000000000009876543210 ";
		FileParser fileParser = new FileParser();
		
		NullPointerException expectedNullPointerException = Assertions.assertThrows(NullPointerException.class, () ->
				fileParser.parseValues(paymentLine, TextFile.PAYMENT, null));
		
		assertEquals(expectedNullPointerException.getMessage(), "Start post not created");
	}

}
