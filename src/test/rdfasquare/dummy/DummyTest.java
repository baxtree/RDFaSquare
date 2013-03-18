package test.rdfasquare.dummy;

import junit.framework.*;

public class DummyTest extends TestCase { 
   public DummyTest(String name) { 
      super(name);
   }

   public void testSimpleTest() {
      int answer = 2;
      assertEquals((1+1), answer); 
   }
}
