package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.PostgresFunctionRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractFunctionTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresFunctionTest extends AbstractFunctionTest {

    @Autowired
    public PostgresFunctionTest(PostgresFunctionRepository postgresFunctionRepository) {
        super(postgresFunctionRepository);
    }
}
