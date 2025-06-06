package br.dev.projects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "IDP_URL=https://example.com")
class MyfinancesApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
