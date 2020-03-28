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

package com.kdgregory.sandbox.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;


public class Main
{
    public static void main(String[] argv)
    throws Exception
    {
        System.err.println("main started, getting logger");
        Logger logger = LogManager.getLogger(Main.class);

        logger.info("Hello, World");

        // this simulates changes to the configuration file during runtime
        System.err.println("reconfiguring logging context");
        final LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.reconfigure();

        logger.info("Did you hear me? Was it on a separate line?");

        System.err.println("end of main()");
    }
}
