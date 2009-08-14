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
package com.adobe.ac.pmd.rules.flexunit;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.MetaData;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class EmptyUnitTest extends AbstractAstFlexRule
{
   private static final String[] ASSERTIONS = new String[]
                                            { "assertEquals",
               "assertObjectEquals",
               "assertMatch",
               "assertNoMatch",
               "assertContained",
               "assertNotContained",
               "assertStrictlyEquals",
               "assertTrue",
               "assertFalse",
               "assertNull",
               "assertNotNull",
               "assertUndefined",
               "assertNotUndefined",
               "assertThat",
               "fail"                      };

   private boolean               isExtendingTestCase;

   @Override
   protected void findViolations( final IClass classNode )
   {
      isExtendingTestCase = classNode.getExtensionName() != null
            && classNode.getExtensionName().endsWith( "TestCase" );

      super.findViolations( classNode );
   }

   @Override
   protected void findViolations( final IFunction function )
   {
      super.findViolations( function );

      if ( isExtendingTestCase
            && function.getName().startsWith( "test" )
            && function.findPrimaryStatementInBody( ASSERTIONS ).isEmpty() )
      {
         addViolation( function );
      }
      if ( !function.getMetaData( MetaData.TEST ).isEmpty()
            && function.findPrimaryStatementInBody( ASSERTIONS ).isEmpty() )
      {
         addViolation( function );
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}