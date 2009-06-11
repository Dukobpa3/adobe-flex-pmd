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

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedFlexRule;

public class TooLongScriptBlockRule extends AbstractMaximizedFlexRule
{
   private int linesInScriptBlock;

   public int getActualValueForTheCurrentViolation()
   {
      return linesInScriptBlock;
   }

   public int getDefaultThreshold()
   {
      return 50;
   }

   @Override
   public boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.isMxml();
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected List< IFlexViolation > processFileBody( final IPackage rootNode,
                                                     final IFlexFile file,
                                                     final Map< String, IFlexFile > files )
   {
      final int lineInitialValue = -1;
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();
      linesInScriptBlock = lineInitialValue;

      for ( int lineIndex = 0; lineIndex < file.getLines().size(); lineIndex++ )
      {
         final String line = file.getLines().get( lineIndex );

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
                     final int beginningScriptLine = lineIndex
                           - linesInScriptBlock;

                     addViolation( violations,
                                   file,
                                   new ViolationPosition( beginningScriptLine, lineIndex ) );
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
}
