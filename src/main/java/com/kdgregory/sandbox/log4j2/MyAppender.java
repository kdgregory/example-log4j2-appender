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
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;


/**
 *  A not-so-great replacement for ConsoleAppender. Now with repeating output!
 */
@Plugin(name = "MyAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class MyAppender extends AbstractAppender
{
    @PluginBuilderFactory
    public static MyAppenderBuilder newBuilder() {
        return new MyAppenderBuilder();
    }


    public static class MyAppenderBuilder
    implements org.apache.logging.log4j.core.util.Builder<MyAppender>
    {
        @PluginBuilderAttribute("name")
        @Required(message = "MyAppender: no name provided")
        private String name;

        @PluginElement("Layout")
        private Layout<String> layout;

        @PluginElement("Filter")
        private Filter filter;

        @PluginBuilderAttribute("repeats")
        private int repeats = 1;

        public MyAppenderBuilder setName(String value)
        {
            this.name = value;
            return this;
        }

        public MyAppenderBuilder setLayout(Layout<String> value)
        {
            this.layout = value;
            return this;
        }

        public MyAppenderBuilder setFilter(Filter value)
        {
            this.filter = value;
            return this;
        }

        public MyAppenderBuilder setRepeats(int value)
        {
            this.repeats = value;
            return this;
        }

        @Override
        public MyAppender build()
        {
            return new MyAppender(name, filter, layout, repeats);
        }
    }

//----------------------------------------------------------------------------
//  Appender implementation
//----------------------------------------------------------------------------

    private int repeats;


    @SuppressWarnings("deprecation")
    private MyAppender(String name, Filter filter, Layout<String> layout, int repeats)
    {
        super(name, filter, layout);
        this.repeats = repeats;
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
