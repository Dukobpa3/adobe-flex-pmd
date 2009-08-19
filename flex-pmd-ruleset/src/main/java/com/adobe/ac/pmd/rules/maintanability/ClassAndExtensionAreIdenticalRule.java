/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.maintanability;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ClassAndExtensionAreIdenticalRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      final String extensionName = classNode.getExtensionName();

      if ( extensionName != null )
      {
         final String extension = extractExtensionName( extensionName );

         if ( extension.equals( classNode.getName() ) )
         {
            addViolation( classNode );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   private String extractExtensionName( final String extensionName )
   {
      return extensionName.indexOf( '.' ) == -1 ? extensionName
                                               : StringUtils.substringAfterLast( extensionName,
                                                                                 "." );
   }
}
