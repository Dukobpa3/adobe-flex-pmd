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
package com.adobe.ac.pmd.rules.as3.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.nodes.VariableNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;

import de.bokelberg.flex.parser.Node;

public abstract class AbstractUnusedVariableRule< E extends VariableNode > extends AbstractAstFlexRule
{
   protected Map< E, Boolean > variablesUsed;

   protected void markVariableAsUsed( final Node ast )
   {
      if ( ast.numChildren() == 0 )
      {
         for ( final E variable : variablesUsed.keySet() )
         {
            final Boolean visited = variablesUsed.get( variable );

            if ( !visited
                  && variable.getName() != null && ast.stringValue != null
                  && variable.getName().compareTo( ast.stringValue ) == 0 )
            {
               variablesUsed.put( variable,
                                  true );
            }
         }
      }
      else
      {
         for ( final Node child : ast.children )
         {
            markVariableAsUsed( child );
         }
      }
   }

   @Override
   protected void visitFunction( final Node ast,
                                 final String type )
   {
      variablesUsed = new HashMap< E, Boolean >();

      super.visitFunction( ast,
                           type );
      for ( final E parameter : variablesUsed.keySet() )
      {
         final Boolean visited = variablesUsed.get( parameter );

         if ( !visited )
         {
            addViolation( parameter.getInternalNode(),
                          parameter.getInternalNode(),
                          parameter.getName() );
         }
      }
   }

   @Override
   protected void visitStatement( final Node ast )
   {
      super.visitStatement( ast );

      if ( variablesUsed != null
            && !variablesUsed.isEmpty() && ast != null )
      {
         markVariableAsUsed( ast );
      }
   }
}
