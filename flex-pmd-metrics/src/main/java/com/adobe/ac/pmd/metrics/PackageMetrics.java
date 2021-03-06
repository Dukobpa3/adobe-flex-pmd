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
package com.adobe.ac.pmd.metrics;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;

public final class PackageMetrics extends AbstractPackagedMetrics
{
   public static PackageMetrics create( final Collection< File > classesInPackage,
                                        final String packageFullName,
                                        final int functionsInPackage,
                                        final int ncssInPackage,
                                        final int asDocsInPackage,
                                        final int multipleLineCommentInPackage,
                                        final int imports )
   {
      return new PackageMetrics( ncssInPackage,// NOPMD
                                 functionsInPackage,
                                 classesInPackage.size(),
                                 packageFullName,
                                 asDocsInPackage,
                                 multipleLineCommentInPackage,
                                 imports );
   }

   private final int classes;
   private final int functions;
   private final int imports;

   private PackageMetrics( final int nonCommentStatements,
                           final int functionsToBeSet,
                           final int classesToBeSet,
                           final String packageName,
                           final int asDocs,
                           final int multiLineComments,
                           final int importsToBeSet )
   {
      super( nonCommentStatements, packageName, 0, asDocs, multiLineComments );
      functions = functionsToBeSet;
      classes = classesToBeSet;
      imports = importsToBeSet;
   }

   public int getClasses()
   {
      return classes;
   }

   public String getContreteXml()
   {
      return new StringBuffer().append( MessageFormat.format( "<functions>{0}</functions>"
                                                                    + "<classes>{1}</classes>",
                                                              String.valueOf( functions ),
                                                              String.valueOf( classes ) ) ).toString();
   }

   @Override
   public String getFullName()
   {
      return getPackageName();
   }

   public int getFunctions()
   {
      return functions;
   }

   @Override
   public String getMetricsName()
   {
      return "package";
   }

   @Override
   protected int getNcss()
   {
      return imports + 1;
   }
}