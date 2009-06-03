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
package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import net.sourceforge.pmd.PMDException;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class ClassNodeTest extends FlexPmdTestBase
{
   private IClass modelLocator;
   private IClass nonBindableModelLocator;
   private IClass radonDataGrid;

   @Before
   public void setup() throws IOException,
                      TokenException,
                      PMDException
   {
      IParserNode ast = FileSetUtils.buildAst( testFiles.get( "RadonDataGrid.as" ) );
      radonDataGrid = NodeFactory.createPackage( ast ).getClassNode();
      ast = FileSetUtils.buildAst( testFiles.get( "cairngorm.BindableModelLocator.as" ) );
      modelLocator = NodeFactory.createPackage( ast ).getClassNode();
      ast = FileSetUtils.buildAst( testFiles.get( "cairngorm.NonBindableModelLocator.as" ) );
      nonBindableModelLocator = NodeFactory.createPackage( ast ).getClassNode();
   }

   @Test
   public void testGetAttributes()
   {
      assertEquals( 0,
                    radonDataGrid.getAttributes().size() );
   }

   @Test
   public void testGetConstants()
   {
      assertEquals( 0,
                    radonDataGrid.getConstants().size() );
   }

   @Test
   public void testGetConstructor()
   {
      assertNotNull( radonDataGrid.getConstructor() );
   }

   @Test
   public void testGetExtensionName()
   {
      assertEquals( "DataGrid",
                    radonDataGrid.getExtensionName() );
   }

   @Test
   public void testGetImplementations()
   {
      assertEquals( 0,
                    radonDataGrid.getImplementations().size() );
      assertEquals( 1,
                    modelLocator.getImplementations().size() );
   }

   @Test
   public void testGetMetaDataList()
   {
      assertEquals( 0,
                    radonDataGrid.getMetaDataList().size() );
      assertEquals( 1,
                    modelLocator.getMetaDataList().size() );
      assertEquals( "Bindable",
                    modelLocator.getMetaDataList().get( 0 ).getName() );
   }

   @Test
   public void testGetName()
   {
      assertEquals( "RadonDataGrid",
                    radonDataGrid.getName() );
   }

   @Test
   public void testIsFinal()
   {
      assertFalse( radonDataGrid.isFinal() );
   }

   @Test
   public void testVisibility()
   {
      assertTrue( radonDataGrid.isPublic() );
      assertTrue( modelLocator.isProtected() );
      assertTrue( nonBindableModelLocator.isPrivate() );
      assertFalse( nonBindableModelLocator.isProtected() );
      assertFalse( nonBindableModelLocator.isPublic() );
      assertFalse( radonDataGrid.isProtected() );
      assertFalse( radonDataGrid.isPrivate() );
      assertFalse( modelLocator.isPublic() );
      assertFalse( modelLocator.isPrivate() );
   }
}
