package com.divergentsl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class DrugTest {

	Map<String, String> map;

	@Test
	void getDrugDataTest() {
		map = new HashMap<>();
		
		map.put("drug_id", "1");
		map.put("drug_name", "Amoxicillin");
		map.put("description",
				"Amoxicillin is used to treat many different types of infection caused by bacteria, such as tonsillitis, bronchitis, pneumonia, and infections of the ear, nose, throat, skin, or urinary tract.");
		
		assertEquals(map ,CRUDDrugs.getDrugData("1"));
	}
	
	@Test
	void deleteDrugTest() {
		
		assertEquals(true, CRUDDrugs.deleteDrugUtil("2"));
		
	}

}
