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
package com.adobe.ac.pmd.files.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IAs3File;
import com.adobe.ac.pmd.files.IMxmlFile;

public class AbstractFlexFileTest extends FlexPmdTestBase
{
   private IAs3File  as3;
   private IMxmlFile mainMxml;
   private IMxmlFile mxml;

   @Before
   public void init() throws FileNotFoundException,
                     URISyntaxException
   {
      as3 = ( IAs3File ) getTestFiles().get( "AbstractRowData.as" );
      mainMxml = ( IMxmlFile ) getTestFiles().get( "Main.mxml" );
      mxml = ( IMxmlFile ) getTestFiles().get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" );
   }

   @Test
   public void testContains()
   {
      assertTrue( as3.contains( "logger",
                                buildSetContaining( 0 ) ) );
      assertFalse( as3.contains( "loggerr",
                                 buildSetContaining( 0 ) ) );
      assertFalse( as3.contains( "addEventListener",
                                 buildSetContaining( 109,
                                                     114 ) ) );
   }

   @Test
   public void testEquals()
   {
      Assert.assertTrue( as3.equals( as3 ) );
      Assert.assertFalse( mxml.equals( as3 ) );
      Assert.assertFalse( as3.equals( mxml ) );
   }

   @Test
   public void testFlexPMD152()
   {
      Assert.assertEquals( "com.something",
                           AbstractFlexFile.computePackageName( "C:/somePath/ProjectName/com/something/Test.mxml",
                                                                "C:/somePath/ProjectName",
                                                                "Test.mxml",
                                                                "/" ) );

      Assert.assertEquals( "com.something",
                           AbstractFlexFile.computePackageName( "C:/somePath/ProjectName/com/something/Test.mxml",
                                                                "C:/somePath/ProjectName/",
                                                                "Test.mxml",
                                                                "/" ) );
   }

   @Test
   public void testGetClassName()
   {
      assertEquals( "AbstractRowData.as",
                    as3.getClassName() );
      assertEquals( "IterationsList.mxml",
                    mxml.getClassName() );
   }

   @Test
   public void testGetFileName()
   {
      Assert.assertEquals( "AbstractRowData.as",
                           as3.getFilename() );
   }

   @Test
   public void testGetFilePath()
   {
      assertNotNull( as3.getFilePath() );
      assertNotNull( mxml.getFilePath() );
      assertNotNull( mainMxml.getFilePath() );
   }

   @Test
   public void testGetPackageName()
   {
      assertEquals( "",
                    as3.getPackageName() );
      assertEquals( "com.adobe.ac.ncss.mxml",
                    mxml.getPackageName() );
   }

   @Test
   public void testGetPath()
   {
      assertEquals( "AbstractRowData.as",
                    as3.getFullyQualifiedName() );
      assertEquals( "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                    mxml.getFullyQualifiedName() );
   }

   @Test
   public void testIsMainApplication()
   {
      assertFalse( as3.isMainApplication() );
      assertFalse( mxml.isMainApplication() );
      assertTrue( mainMxml.isMainApplication() );
   }

   @Test
   public void testIsMxml()
   {
      assertFalse( as3.isMxml() );
      assertTrue( mxml.isMxml() );
   }

   private Set< Integer > buildSetContaining( final int... lines )
   {

      final HashSet< Integer > hashSet = new HashSet< Integer >();

      for ( final int line : lines )
      {
         hashSet.add( line );
      }
      return hashSet;
   }
}
