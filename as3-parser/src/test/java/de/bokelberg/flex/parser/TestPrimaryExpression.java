/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestPrimaryExpression extends AbstractAs3ParserTest
{
   @Test
   public void testArrayLiteral() throws TokenException
   {
      assertPrimary( "[1,2,3]",
                     "<array line=\"1\"><primary line=\"1\">1"
                           + "</primary><primary line=\"1\">2</primary>"
                           + "<primary line=\"1\">3</primary></array>" );
   }

   @Test
   public void testBooleans() throws TokenException
   {
      assertPrimary( "true" );
      assertPrimary( "false" );
   }

   @Test
   public void testFunctionLiteral() throws TokenException
   {
      assertPrimary( "function ( a : Object ) : * { trace('test'); }",
                     "<lambda line=\"1\"><parameter-list line=\"1\">"
                           + "<parameter line=\"1\"><name-type-init line=\"1\">"
                           + "<name line=\"1\">a</name><type line=\"1\">"
                           + "Object</type></name-type-init></parameter></parameter-list>"
                           + "<type line=\"1\">*</type><block line=\"1\">"
                           + "<call line=\"1\"><primary line=\"1\">trace</primary>"
                           + "<arguments line=\"1\"><primary line=\"1\">'test'"
                           + "</primary></arguments></call></block></lambda>" );
   }

   @Test
   public void testNull() throws TokenException
   {
      assertPrimary( "null" );
   }

   @Test
   public void testNumbers() throws TokenException
   {
      assertPrimary( "1" );
      assertPrimary( "0xff" );
      assertPrimary( "0777" );
      assertPrimary( ".12E5" );
   }

   @Test
   public void testObjectLiteral() throws TokenException
   {
      assertPrimary( "{a:1,b:2}",
                     "<object line=\"1\"><prop line=\"1\">"
                           + "<name line=\"1\">a</name><value line=\"1\">"
                           + "<primary line=\"1\">1</primary></value></prop><prop line=\"1\">"
                           + "<name line=\"1\">b</name><value line=\"1\">"
                           + "<primary line=\"1\">2</primary></value></prop></object>" );
   }

   @Test
   public void testStrings() throws TokenException
   {
      assertPrimary( "\"string\"" );
      assertPrimary( "'string'" );
   }

   @Test
   public void testUndefined() throws TokenException
   {
      assertPrimary( "undefined" );
   }

   private void assertPrimary( final String input ) throws TokenException
   {
      assertPrimary( input,
                     input );
   }

   private void assertPrimary( final String input,
                               final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input,
                  "__END__" } );
      asp.nextToken();
      final String result = new ASTToXMLConverter().convert( asp.parsePrimaryExpression() );
      assertEquals( "unexpected",
                    "<primary line=\"1\">"
                          + expected + "</primary>",
                    result );
   }

}
