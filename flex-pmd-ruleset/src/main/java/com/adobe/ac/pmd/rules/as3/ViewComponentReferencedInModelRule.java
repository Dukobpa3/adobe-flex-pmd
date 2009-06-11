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
package com.adobe.ac.pmd.rules.as3;

import java.util.Locale;
import java.util.regex.Matcher;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ViewComponentReferencedInModelRule extends AbstractRegexpBasedRule
{
   private static final String ALERT_CLASS_NAME           = "Alert";
   private static final String FLEX_CONTROLS_PACKAGE_NAME = "mx.controls";
   private static final String MODEL_CLASS_SUFFIX         = "model";
   private static final String MODEL_PACKAGE_NAME         = "model";
   private static final String VIEW_PACKAGE_NAME          = "view";

   @Override
   public boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.isMxml()
            && file.getFullyQualifiedName().toLowerCase( Locale.ENGLISH ).contains( MODEL_CLASS_SUFFIX );
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected String getRegexp()
   {
      return ".*import (.*);?.*";
   }

   @Override
   protected boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                            final IFlexFile file )
   {
      final Matcher matcher = getMatcher( line );

      matcher.matches();
      final String importedClass = matcher.group( 1 );

      return importedClass.contains( FLEX_CONTROLS_PACKAGE_NAME )
            && !importedClass.contains( ALERT_CLASS_NAME ) || importedClass.contains( VIEW_PACKAGE_NAME )
            && !importedClass.contains( MODEL_PACKAGE_NAME );
   }
}
