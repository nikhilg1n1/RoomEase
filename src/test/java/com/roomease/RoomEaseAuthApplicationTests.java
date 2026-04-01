package com.roomease;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-ci.properties")
class RoomEaseAuthApplicationTests {

    @Test
    void contextLoads() {
    }

}
