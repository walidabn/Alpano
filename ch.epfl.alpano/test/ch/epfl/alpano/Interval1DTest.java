package ch.epfl.alpano;

import static org.junit.Assert.*;

import org.junit.Test;

public class Interval1DTest {

    @Test
    public void IncludedFromreturns2() {
        Interval1D interval= new Interval1D(-3,4);
       assertEquals(interval.includedFrom(),-3,0 );
    }
    
    @Test
    public void IncludedToreturns4(){

        Interval1D interval= new Interval1D(2,4);

        assertEquals(interval.includedTo(),4,0 );
    }
    
    

    @Test
    public void contains24has3(){

        Interval1D interval= new Interval1D(2,4);
        assertTrue(interval.contains(3));
        
    }
    
    @Test
    public void contains22has2(){

        Interval1D interval= new Interval1D(2,2);
        assertTrue(interval.contains(2));
        
    }
    
    @Test
    public void contains24hasnot5(){

        Interval1D interval= new Interval1D(2,4);
        assertFalse(interval.contains(5));
        
    } 
    
    @Test
    public void size24returns3(){

        Interval1D interval= new Interval1D(2,4);

        assertEquals(interval.size(),3,0 );
        
        
    }
    
    @Test
    public void sizeofintersectionis3(){
        

        Interval1D interval= new Interval1D(3,4);

        Interval1D interval2= new Interval1D(0,2);
        
        assertEquals(interval.sizeOfIntersectionWith(interval2),0,0);
        
        
        
    }
    
    @Test 
    public void testboundingUnion(){
        
        Interval1D interval= new Interval1D(2,5);

        Interval1D interval2= new Interval1D(7,9);
        

        Interval1D interval3= new Interval1D(2,9);
        
        assertEquals(interval2.boundingUnion(interval),interval3);
    }
    
    
    
    
    @Test
    public void equals2intervals(){
        
        Interval1D interval= new Interval1D(2,4);

        Interval1D interval2= null;
        
        assertFalse(interval.equals(interval2));
    }
    
    @Test
    
    public void toStringiscorrect(){
        

        Interval1D interval= new Interval1D(2,4);
        assertTrue(interval.toString().equals("[2..4]")  );
    }
    
    @Test 
    
    public void isUnionablewithiscorrect(){
        

        Interval1D interval= new Interval1D(-1,0);

        Interval1D interval2= new Interval1D(1,5);
        
        assertTrue(interval.isUnionableWith(interval2));
        
    }
    
    @Test
    
    public void Unionworks(){
        

        Interval1D interval= new Interval1D(-1,0);

        Interval1D interval2= new Interval1D(1,5);
        
        Interval1D interval3= new Interval1D (-1,5);
        
        assertEquals(interval.union(interval2), interval3 );
        
    }
    
}
