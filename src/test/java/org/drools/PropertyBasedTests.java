/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package org.drools;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.jqwik.api.*;
import org.assertj.core.api.*;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

@PropertyDefaults(tries = 10, shrinking = ShrinkingMode.FULL)
class PropertyBasedTests {
    
    @Property
    void testMvel1(
            @ForAll Double d1
    ) {
        String rule = "70 + 30 * x1";
        ParserContext parserContext = new ParserContext();
        Serializable compileExpression = MVEL.compileExpression(rule, parserContext);
        Map<String, Object> expressionVars = new HashMap<>();
        expressionVars.put("x1", d1);
        Object result = MVEL.executeExpression(compileExpression, parserContext, expressionVars);

        ExampleJShell exampleJShell = new ExampleJShell();

        String javaEval = exampleJShell.eval("70 + 30 * " + d1);

//        Assertions.assertThat(((Number)result).doubleValue()).isEqualTo(70 + 30 * d1);
        Assertions.assertThat(result.toString()).isEqualTo(javaEval);
    }
}