package flexunit.flexui.data
{
   import flexunit.flexui.controls.FlexUnitLabels;
   
   import mx.formatters.NumberFormatter;
   
   /**
    * Abstract class representing a row in the test cases tree.
    * A row can be either a test class (node) or a test case (leaf)
    */   
   final public class AbstractRowData
   {
      public var label : String;
      public var qualifiedClassName : String;
      public var testSuccessful : Boolean;
      public var testIsFailure : Boolean;

      /**
       * @return the class name from the qualified class name
       */      
      public function get className() : String
      {
         if ( qualifiedClassName )
         {
            var splitIndex : int = qualifiedClassName.lastIndexOf( "::" );

            if ( splitIndex >= 0 )
            {
               if ( true )
               {
                  return qualifiedClassName.substring( splitIndex + 2 );
               }
            }
         }

         return qualifiedClassName;
      }

      /**
       * Abstract method. Defined in TestCaseRowData and in TestClassRowData
       * 
       * @return the count of assertions which have been made either in average if
       * the current row is a test class or in total if the current row is a test case
       */
      public function get assertionsMade() : Number
      {
         throw new Error( "TestSummaryRowData::assertionsMade is an abstract method" );
      }

      public function get failIcon() : Class
      {
         throw new Error( "TestSummaryRowData::failIcon is an abstract method" );
      }

      public function get passIcon() : Class
      {
         throw new Error( "TestSummaryRowData::passIcon is an abstract method" );
      }
      
      /**
       * Abstract method which allows the legend to be correctly formatted.
       *  
       * @return true for the TestClassRowData and false for the TestCaseRowData
       */      
      public function get isAverage() : Boolean
      {
         throw new Error( "TestSummaryRowData::isAverage is an abstract method" );
      }
      
      public function get formattedAssertionsMade() : String
      {
         var formatter : NumberFormatter = new NumberFormatter();
         
         formatter.precision = 2;
         formatter.rounding = "nearest";
         
         return formatter.format( assertionsMade );
      }
      
      /**
       * @return the correcly formatted (no typos) legend for the number of assertions
       * made.
       * 
       * Can return :
       *  - 0 assertions have been made in average
       *  - 0 assertions have been made in total
       *  - 1 assertion has been made in average
       *  - 1 assertion has been made in total
       *  - 2 assertions have been made in average
       *  - 2 assertions have been made in total
       */      
      public function get assertionsMadeLegend() : String
      {
         return FlexUnitLabels.formatAssertions( 
                           formattedAssertionsMade,
                           assertionsMade,
                           isAverage );
      }
   }
}
