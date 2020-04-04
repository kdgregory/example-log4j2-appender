// Copyright (c) Keith D Gregory
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kdgregory.sandbox.log4j2.tests;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import static org.junit.Assert.*;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.kdgregory.sandbox.log4j2.helpers.TestableAppender;


public class TestMyAppender
{
    private Logger logger;
    private TestableAppender appender;

    protected void initialize(String testName)
    throws Exception
    {
        URI config = ClassLoader.getSystemResource(testName + ".xml").toURI();
        assertNotNull("was able to retrieve config", config);

        LoggerContext context = LoggerContext.getContext();
        context.setConfigLocation(config);

        logger = context.getLogger("TEST");

        appender = (TestableAppender)logger.getAppenders().get("TEST");
        assertNotNull("was able to retrieve appender", appender);
    }


    @Test
    public void testBasicOperation() throws Exception
    {
        initialize("testBasicOperation");

        logger.debug("line 1");
        logger.info("line 2");

        String[] output = appender.getOutput().split("\n");
        assertEquals(Arrays.asList(
                        "DEBUG - line 1",
                        "DEBUG - line 1",
                        "INFO - line 2",
                        "INFO - line 2"),
                     Arrays.asList(output));
    }


    @Test
    public void testDefaultConfiguration() throws Exception
    {
        initialize("testDefaultConfiguration");

        logger.debug("line 1");
        logger.info("line 2");

        String[] output = appender.getOutput().split("\n");
        assertEquals(Arrays.asList(
                        "DEBUG - line 1",
                        "INFO - line 2"),
                     Arrays.asList(output));
    }


    @Test
    public void testHeaderAndFooter() throws Exception
    {
        initialize("testHeaderAndFooter");

        logger.debug("line 1");
        logger.info("line 2");

        // have to shut down the framework to write footer
        LoggerContext.getContext().stop(100, TimeUnit.MILLISECONDS);

        String[] output = appender.getOutput().split("\n");
        assertEquals(Arrays.asList(
                        "header",
                        "DEBUG - line 1",
                        "INFO - line 2",
                        "footer"),
                     Arrays.asList(output));
    }
}
