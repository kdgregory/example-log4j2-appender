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
 *  A not-so-great replacement for ConsoleAppender. Now with repeating output!
 */
@Plugin(name = "MyAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class MyAppender extends AbstractAppender
{
    @PluginFactory
    public static MyAppender createAppender(
        @PluginAttribute("name") String name,
        @PluginElement("Layout") Layout<String> layout,
        @PluginElement("Filters") Filter filter),
        @PluginAttribute("repeats") int repeats
    {
        return new MyAppender(name, filter, layout, repeats);
    }


    private int repeats;


    @SuppressWarnings("deprecation")
    private MyAppender(String name, Filter filter, Layout<String> layout, int repeats)
    {
        super(name, filter, layout);
        this.repeats = (repeats != 0) ? repeats : 1;
    }


    @Override
    public void append(LogEvent event)
    {
        for (int ii = 0 ; ii < repeats ; ii++)
        {
            System.out.println(getLayout().toSerializable(event));
        }
    }
}
