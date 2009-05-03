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
package com.adobe.ac.pmd.rules.performance;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.performance.DynamicFiltersUsedInPopup;

public class DynamicFiltersUsedInPopupTest
      extends AbstractRegExpBasedRuleTest
{
   @Override
   @Test
   public void testProcessConcernedButNonViolatingFiles()
         throws FileNotFoundException, URISyntaxException
   {
      assertEmptyViolations( "com.adobe.ac.ncss.BigImporterModel.as" );
   }

   @Override
   @Test
   public void testProcessNonConcernedFiles() throws FileNotFoundException,
         URISyntaxException
   {
      assertEmptyViolations( "com.adobe.ac.ncss.mxml.NestedComponent.mxml" );
   }

   @Override
   @Test
   public void testProcessViolatingFiles() throws FileNotFoundException,
         URISyntaxException
   {
      assertViolations(
            "filters.MyPopup.as", new ViolationPosition[]
            { new ViolationPosition( 37, 37 ) } );

      assertViolations(
            "filters.MyPopup.mxml",
            new ViolationPosition[]
            { new ViolationPosition( 41, 41 ), new ViolationPosition( 44, 44 ) } );
   }

   @Override
   protected String[] getMatchableLines()
   {
      final String[] lines =
      { "new DropShadowFilter", "new GlowFilter", "mx:DropShadowFilter" };
      return lines;
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new DynamicFiltersUsedInPopup();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      final String[] lines =
      { ".filterFunction", "DropShadowfilter(" };
      return lines;
   }
}