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

package com.kdgregory.sandbox.log4j2.helpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

import com.kdgregory.sandbox.log4j2.MyAppender;


@Plugin(name = "TestableAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class TestableAppender extends MyAppender
{
    @PluginBuilderFactory
    public static TestableAppenderBuilder newBuilder() {
        return new TestableAppenderBuilder();
    }


    public static class TestableAppenderBuilder
    extends MyAppender.MyAppenderBuilder
    {
        @Override
        public TestableAppender build()
        {
            return new TestableAppender(this, new ByteArrayOutputStream());
        }
    }

    private ByteArrayOutputStream bos;

    private TestableAppender(MyAppenderConfig config, ByteArrayOutputStream bos)
    {
        super(config, new PrintStream(bos, true));
        this.bos = bos;
    }

    public String getOutput()
    {
        byte[] bytes = bos.toByteArray();
        return new String(bytes);
    }
}
