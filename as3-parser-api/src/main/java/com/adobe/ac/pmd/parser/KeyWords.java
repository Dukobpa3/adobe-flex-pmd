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
package com.adobe.ac.pmd.parser;

public enum KeyWords
{
   AS("as"),
   CASE("case"),
   CATCH("catch"),
   CLASS("class"),
   CONST("const"),
   DEFAULT("default"),
   DELETE("delete"),
   DO("do"),
   DYNAMIC("dynamic"),
   EACH("each"),
   ELSE("else"),
   EOF("__END__"),
   EXTENDS("extends"),
   FALSE("false"),
   FINAL("final"),
   FINALLY("finally"),
   FOR("for"),
   FOREACH("foreach"),
   FORIN("forin"),
   FUNCTION("function"),
   GET("get"),
   IF("if"),
   IMPLEMENTS("implements"),
   IMPORT("import"),
   INSTANCE_OF("instanceOf"),
   INTERFACE("interface"),
   INTERNAL("internal"),
   IS("is"),
   NEW("new"),
   NULL("null"),
   OVERRIDE("override"),
   PACKAGE("package"),
   PRIVATE("private"),
   PROTECTED("protected"),
   PUBLIC("public"),
   RETURN("return"),
   SET("set"),
   STATIC("static"),
   SUPER("super"),
   SWITCH("switch"),
   TRUE("true"),
   TRY("try"),
   TYPEOF("typeof"),
   UNDEFINED("undefined"),
   USE("use"),
   VAR("var"),
   VOID("void"),
   WHILE("while");

   private String name;

   private KeyWords( final String nameToBeSet )
   {
      name = nameToBeSet;
   }

   @Override
   public String toString()
   {
      return name;
   }
}