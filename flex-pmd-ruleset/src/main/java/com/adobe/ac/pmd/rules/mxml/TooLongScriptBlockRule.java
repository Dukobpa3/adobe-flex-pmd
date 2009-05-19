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
package com.adobe.ac.pmd.rules.mxml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.PropertyDescriptor;

import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.PackageNode;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.IThresholdedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class TooLongScriptBlockRule extends AbstractFlexRule implements IThresholdedRule
{
   private int linesInScriptBlock;

   public int getActualValue()
   {
      return linesInScriptBlock;
   }

   public int getDefaultThreshold()
   {
      return 50;
   }

   public int getThreshold()
   {
      return getIntProperty( propertyDescriptorFor( getThresholdName() ) );
   }

   public String getThresholdName()
   {
      return MAXIMUM;
   }

   public boolean isConcernedByTheGivenFile( final AbstractFlexFile file )
   {
      return file.isMxml();
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected List< Violation > processFileBody( final PackageNode rootNode,
                                                final AbstractFlexFile file,
                                                final Map< String, AbstractFlexFile > files )
   {
      final int lineInitialValue = -1;
      final List< Violation > violations = new ArrayList< Violation >();
      linesInScriptBlock = lineInitialValue;

      for ( int i = 0; i < file.getLines().size(); i++ )
      {
         final String line = file.getLines().get( i );

         if ( linesInScriptBlock == lineInitialValue
               && line.contains( "Script>" ) )
         {
            linesInScriptBlock = 0;
         }
         else
         {
            if ( linesInScriptBlock != lineInitialValue )
            {
               if ( line.contains( "Script>" ) )
               {
                  if ( linesInScriptBlock > getThreshold() )
                  {
                     final int beginningScriptLine = i
                           - linesInScriptBlock;

                     addViolation( violations,
                                   file,
                                   new ViolationPosition( beginningScriptLine, i ) );
                  }
                  linesInScriptBlock = lineInitialValue;
               }
               else
               {
                  linesInScriptBlock++;
               }
            }
         }
      }
      return violations;
   }

   @Override
   protected Map< String, PropertyDescriptor > propertiesByName()
   {
      return getRuleProperties( this );
   }
}
