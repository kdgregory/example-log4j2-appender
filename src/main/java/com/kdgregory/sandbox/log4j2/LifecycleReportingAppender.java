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

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;


/**
 *  This is an appender that doesn't actually append anything. Instead, it writes
 *  messages to StdError whenever methods methods are called. Each message gives
 *  the appenders identity hashcode as a usually-unique id.
 */
@Plugin(name = "LifecycleReportingAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class LifecycleReportingAppender extends AbstractAppender
{
    @PluginFactory
    public static LifecycleReportingAppender createAppender(
        @PluginAttribute("name") String name,
        @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
        @PluginElement("Layout") Layout<String> layout,
        @PluginElement("Filters") Filter filter)
    {
        return new LifecycleReportingAppender(name, filter, layout);
    }


    @SuppressWarnings("deprecation")
    private LifecycleReportingAppender(String name, Filter filter, Layout<String> layout)
    {
        super(name, filter, layout);
        System.err.println("LifecycleReportingAppender constructed; id = " + System.identityHashCode(this));
    }


    @Override
    public void initialize()
    {
        // note: this does not appear to be called
        System.err.println("initialize() called on " + System.identityHashCode(this));
        super.initialize();
    }


    @Override
    public void start()
    {
        System.err.println("start() called on " + System.identityHashCode(this));
        super.start();
    }


    @Override
    public void stop()
    {
        // this will not be called for Log4J version 2.7 or later
        System.err.println("stop() called on " + System.identityHashCode(this));
        super.stop();
    }


    @Override
    public boolean stop(long timeout, TimeUnit timeUnit)
    {
        System.err.println("stop(" + timeout + "," + timeUnit + ") called on " + System.identityHashCode(this));
        return super.stop(timeout, timeUnit);
    }


    @Override
    public void append(LogEvent event)
    {
        System.err.println("append() called on " + System.identityHashCode(this));
    }
}
