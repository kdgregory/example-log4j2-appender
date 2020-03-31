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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;


/**
 *  A layout plugin that redacts certain words in the logged message.
 */
@Plugin(name = "RedactingLayout", category = Core.CATEGORY_NAME, elementType = Layout.ELEMENT_TYPE)
public class RedactingLayout
extends AbstractStringLayout
{
    @PluginFactory
    public static RedactingLayout factory(
        @PluginConfiguration Configuration config,
        @PluginAttribute("redactionPatterns") String redactionPatterns)
    {
        return new RedactingLayout(config, redactionPatterns);
    }


    private StrSubstitutor subs;
    private List<Pattern> redactionPatterns = new ArrayList<>();


    private RedactingLayout(Configuration config, String redactionPatterns)
    {
        super(StandardCharsets.UTF_8);
        subs = config.getStrSubstitutor();
        for (String pattern : redactionPatterns.split(","))
        {
            this.redactionPatterns.add(Pattern.compile(pattern));
        }
    }


    @Override
    public String toSerializable(LogEvent event)
    {
        String raw_message = event.getMessage().getFormattedMessage();
        String message = subs.replace("${date:yyyy-MM-dd HH:mm:ss.SSS} - " + raw_message);
        for (Pattern pattern : redactionPatterns)
        {
            Matcher matcher = pattern.matcher(message);
            message = matcher.replaceAll("REDACTED");
        }
        return message;
    }
}
