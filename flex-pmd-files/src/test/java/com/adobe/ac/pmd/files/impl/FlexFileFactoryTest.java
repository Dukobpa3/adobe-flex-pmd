package com.adobe.ac.pmd.files.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IAs3File;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.IMxmlFile;

public class FlexFileFactoryTest extends FlexPmdTestBase
{
   @Test
   public void testCreate()
   {
      assertTrue( "",
                  create( new File( getTestFiles().get( "AbstractRowData.as" ).getFilePath() ),
                          new File( "" ) ) instanceof IAs3File );
      assertTrue( "",
                  create( new File( getTestFiles().get( "Main.mxml" ).getFilePath() ),
                          new File( "" ) ) instanceof IMxmlFile );
      assertTrue( "",
                  create( new File( getTestFiles().get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" )
                                                  .getFilePath() ),
                          new File( "" ) ) instanceof IMxmlFile );
   }

   private IFlexFile create( final File sourceFile,
                             final File sourceDirectory )
   {
      IFlexFile file;

      if ( sourceFile.getName().endsWith( ".as" ) )
      {
         file = new As3File( sourceFile, sourceDirectory );
      }
      else
      {
         file = new MxmlFile( sourceFile, sourceDirectory );
      }

      return file;
   }
}
