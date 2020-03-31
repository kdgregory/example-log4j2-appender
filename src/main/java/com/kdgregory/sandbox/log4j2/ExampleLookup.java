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

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;


/**
 *  A lookup that provides hostname and process ID, retrieved from the
 *  RuntimeMXBean.
 */
@Plugin(category = "Lookup", name = "example")
public class ExampleLookup
implements StrLookup
{
    private String processId;
    private String hostname;

    public ExampleLookup()
    {
        RuntimeMXBean runtimeMx = ManagementFactory.getRuntimeMXBean();
        String vmName = runtimeMx.getName();
        int sepIdx = vmName.indexOf('@');

        if (sepIdx > 0)
        {
            processId = vmName.substring(0, sepIdx);
            hostname = vmName.substring(sepIdx + 1, vmName.length());
        }
    }

    @Override
    public String lookup(String key)
    {
        if ("HOSTNAME".equalsIgnoreCase(key)) return hostname;
        if ("PID".equalsIgnoreCase(key)) return processId;
        return null;
    }

    @Override
    public String lookup(LogEvent event, String key)
    {
        return lookup(key);
    }
}
