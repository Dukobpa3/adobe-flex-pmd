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
   FINAL("final"),
   FINALLY("finally"),
   FOR("for"),
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
   TRY("try"),
   TYPEOF("typeof"),
   USE("use"),
   VAR("var"),
   VOID("void"),
   WHILE("while");

   private final String name;

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
