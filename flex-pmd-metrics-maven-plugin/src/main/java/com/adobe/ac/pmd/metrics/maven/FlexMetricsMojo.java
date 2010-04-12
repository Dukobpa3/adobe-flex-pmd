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
package com.adobe.ac.pmd.metrics.maven;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author xagnetti
 * @goal check
 * @execute goal="metrics"
 */
public final class FlexMetricsMojo extends AbstractMojo
{
   /**
    * CCN Limit, any code with a ccn greater than this number will generate a
    * violation
    * 
    * @parameter default-value="10"
    * @required
    */
   private int     ccnLimit;

   /**
    * Whether to fail the build if the validation check fails.
    * 
    * @parameter default-value="false"
    * @required
    */
   private boolean failOnViolation;

   /**
    * ncss Limit, any code with a ncss greater than this number will generate a
    * violation
    * 
    * @parameter default-value="100"
    * @required
    */
   private int     ncssLimit;

   /**
    * Specifies the location of the source files to be used.
    * 
    * @parameter expression="${project.build.sourceDirectory}"
    * @required
    * @readonly
    */
   private File    sourceDirectory;

   /**
    * Name of the file holding the xml file generated by JavaNCSS
    * 
    * @parameter default-value="javancss-raw-report.xml"
    * @required
    */
   private String  tempFileName;

   /**
    * Specifies the directory where the XML report will be generated.
    * 
    * @parameter default-value="${project.build.directory}"
    * @required
    */
   private File    xmlOutputDirectory;

   public FlexMetricsMojo()
   {
      super();
   }

   public FlexMetricsMojo( final File outputDirectoryToBeSet,
                           final File sourceDirectoryToBeSet )
   {
      xmlOutputDirectory = outputDirectoryToBeSet;
      sourceDirectory = sourceDirectoryToBeSet;
   }

   @SuppressWarnings("unchecked")
   public void execute() throws MojoExecutionException,
                        MojoFailureException
   {
      if ( sourceDirectory != null
            && sourceDirectory.exists() )
      {
         final Set< String > ccnViolation = new HashSet< String >();
         final Set< String > ncssViolation = new HashSet< String >();
         final List< Node > methodList = loadDocument().selectNodes( "//javancss/functions/function" );

         for ( final Node node : methodList )
         {
            if ( Integer.valueOf( node.valueOf( "ccn" ) ) > ccnLimit )
            {
               ccnViolation.add( node.valueOf( "name" ) );
            }
            if ( Integer.valueOf( node.valueOf( "ncss" ) ) > ncssLimit )
            {
               ncssViolation.add( node.valueOf( "name" ) );
            }
         }
         reportViolation( "ccn",
                          ccnViolation,
                          ccnLimit );
         reportViolation( "ncss",
                          ncssViolation,
                          ncssLimit );
      }
   }

   public void setCcnLimit( final int ccnLimitToBeSet )
   {
      ccnLimit = ccnLimitToBeSet;
   }

   public void setFailOnViolation( final boolean failOnViolationToBeSet )
   {
      failOnViolation = failOnViolationToBeSet;
   }

   public void setNcssLimit( final int ncssLimitToBeSet )
   {
      ncssLimit = ncssLimitToBeSet;
   }

   public void setTempFileName( final String tempFileNameToBeSet )
   {
      tempFileName = tempFileNameToBeSet;
   }

   public void setXmlOutputDirectory( final File xmlOutputDirectoryToBeSet )
   {
      xmlOutputDirectory = xmlOutputDirectoryToBeSet;
   }

   private Document loadDocument() throws MojoFailureException
   {
      final File ncssXmlFile = new File( xmlOutputDirectory
            + File.separator + tempFileName );
      try
      {
         return new SAXReader().read( ncssXmlFile );
      }
      catch ( final DocumentException de )
      {
         throw new MojoFailureException( "Can't read javancss xml output file : "
               + ncssXmlFile, de );
      }
   }

   private void reportViolation( final String statName,
                                 final Set< String > violationSet,
                                 final int limit ) throws MojoFailureException
   {
      getLog().debug( statName
            + " Violation = " + violationSet.size() );
      if ( !violationSet.isEmpty() )
      {
         final String violationString = "Your code has "
               + violationSet.size() + " method(s) with a " + statName + " greater than " + limit;
         getLog().warn( violationString );
         final Iterator< String > iterator = violationSet.iterator();
         while ( iterator.hasNext() )
         {
            getLog().warn( "    "
                  + iterator.next() );
         }
         if ( failOnViolation )
         {
            throw new MojoFailureException( violationString );
         }
      }
   }
}
