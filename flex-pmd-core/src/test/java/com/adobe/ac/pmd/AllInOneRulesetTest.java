/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.engines.AbstractFlexPmdEngine;
import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;

import junit.framework.TestCase;

public class AllInOneRulesetTest extends TestCase
{
   static protected final String OUTPUT_DIRECTORY_URL = "target/report/";

   public void testLoadRuleSet() throws URISyntaxException,
                                FileNotFoundException,
                                PMDException
   {
      final AbstractFlexPmdEngine engine = new FlexPmdXmlEngine();
      final File sourceDirectory = new File( getClass().getResource( "/test" ).toURI().getPath() );
      final URL ruleSetUrl = getClass().getResource( "/allInOneRuleset.xml" );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl.toURI() );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl.toURI().getPath() );

      final File outputDirectory = new File( OUTPUT_DIRECTORY_URL );
      final File ruleSetFile = new File( ruleSetUrl.toURI().getPath() );

      engine.executeReport( sourceDirectory,
                            outputDirectory,
                            ruleSetFile,
                            new FlexPmdViolations() );

      assertEquals( "Number of rules found is not correct",
                    47,
                    engine.getRuleSet().size() );
   }
}
