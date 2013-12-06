/*
 * Copyright 2013 <a href="mailto:onacit@gmail.com">Jin Kwon</a>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.sql.databasemetadata;


import com.github.jinahya.sql.databasemetadata.SuppressionKey;
import com.github.jinahya.sql.databasemetadata.Suppression;
import com.github.jinahya.sql.databasemetadata.Suppressions;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 *
 * @author <a href="mailto:onacit@gmail.com">Jin Kwon</a>
 */
public class H2Test {


    private static final Logger LOGGER
        = LoggerFactory.getLogger(H2Test.class);


    private static final String DRIVER_NAME = "org.h2.Driver";


    private static final String CONNECTION_URL
        = "jdbc:h2:mem:test"; //;DB_CLOSE_DELAY=-1";


    @BeforeClass
    private static void beforeClass() throws SQLException {

        LOGGER.trace("beforeClass()");
    }


    @AfterClass
    private static void afterClass() throws SQLException {

        LOGGER.trace("afterClass()");
    }


    @Test
    public void retrieve() throws SQLException, JAXBException, IOException {

        try (Connection connection
            = DriverManager.getConnection(CONNECTION_URL)) {

            final DatabaseMetaData databaseMetaData = connection.getMetaData();
            final SuppressionKey suppressionKey
                = SuppressionKey.newInstance(databaseMetaData);
            LOGGER.trace("suppressionKey: {}", suppressionKey);

            final Suppressions suppressions = Suppressions.loadInstance();
            final Suppression suppression
                = suppressions.getSuppression(suppressionKey);

            final Metadata metadata
                = Metadata.newInstance(databaseMetaData, suppression);
            
            MetadataTest.print(metadata);

            MetadataTest.test(metadata);
        }
    }


}

