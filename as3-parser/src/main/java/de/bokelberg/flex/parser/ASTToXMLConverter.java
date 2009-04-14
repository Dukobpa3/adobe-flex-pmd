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
package de.bokelberg.flex.parser;

public class ASTToXMLConverter implements ASTConverter
{
   /*
    * (non-Javadoc)
    * @see
    * de.bokelberg.flex.parser.AstConverter#convert(de.bokelberg.flex.parser
    * .Node)
    */
   public String convert(
         final Node ast )
   {
      final StringBuffer result = new StringBuffer();
      visitNodes(
            ast, result, 0 );
      return result.toString();
   }

   protected String escapeEntities(
         final String s )
   {
      if ( s == null )
      {
         return null;
      }

      String result = "";
      for ( int i = 0; i < s.length(); i++ )
      {
         final char c = s.charAt( i );
         if ( c == '<' )
         {
            result += "&lt;";
         }
         else if ( c == '>' )
         {
            result += "&gt;";
         }
         else
         {
            result += c;
         }
      }
      return result;
   }

   protected void visitNodes(
         final Node ast, final StringBuffer result, final int level )
   {
      result.append( "<"
            + ast.id + " line=\"" + ast.line + "\" column=\"" + ast.column
            + "\">" );

      final int numChildren = ast.numChildren();
      if ( numChildren > 0 )
      {
         for ( int i = 0; i < numChildren; i++ )
         {
            visitNodes(
                  ast.getChild( i ), result, level + 1 );
         }
      }
      else if ( ast.stringValue != null )
      {
         result.append( escapeEntities( ast.stringValue ) );
      }
      result.append( "</"
            + ast.id + ">" );
   }
}