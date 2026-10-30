package com.krushikaMitra;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:krushikaMitraTest",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"jwt.secret=test-only-signing-key-with-at-least-32-bytes"
})
class KrushikaMitraApplicationTests {

	@Test
	void contextLoads() {
	}

}
