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
package com.adobe.ac.pmd.rules.maintanability.forbiddentypes;

import java.util.List;
import java.util.Map.Entry;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;

public abstract class AbstractUseForbiddenTypeRule extends AbstractAstFlexRule
{
   @Override
   protected void findViolations( final IClass classNode )
   {
      super.findViolations( classNode );

      findViolationInVariableLists( classNode.getAttributes() );
      findViolationInVariableLists( classNode.getConstants() );
   }

   @Override
   protected void findViolations( final IFunction function )
   {
      findViolationsInReturnType( function );
      findViolationsInParametersList( function );
      findViolationsInLocalVariables( function );
   }

   protected void findViolationsInParametersList( final IFunction function )
   {
      findViolationInVariableLists( function.getParameters() );
   }

   abstract protected String getForbiddenType();

   private < E extends IVariable > void findViolationInVariableLists( final List< E > variables )
   {
      for ( final IVariable variable : variables )
      {
         if ( variable.getType() != null )
         {
            tryToAddViolation( variable.getInternalNode(),
                               variable.getType().toString() );
         }
      }
   }

   private void findViolationsInLocalVariables( final IFunction function )
   {
      for ( final Entry< String, IParserNode > localVariableEntry : function.getLocalVariables().entrySet() )
      {
         final IParserNode type = getTypeFromFieldDeclaration( localVariableEntry.getValue() );

         tryToAddViolation( type,
                            type.getStringValue() );
      }
   }

   private void findViolationsInReturnType( final IFunction function )
   {
      tryToAddViolation( function.getReturnType().getInternalNode(),
                         function.getReturnType().toString() );
   }

   private void tryToAddViolation( final IParserNode node,
                                   final String typeName )
   {
      if ( typeName.equals( getForbiddenType() ) )
      {
         addViolation( node );
      }
   }
}
