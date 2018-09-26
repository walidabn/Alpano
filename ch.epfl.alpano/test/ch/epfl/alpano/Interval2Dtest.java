package ch.epfl.alpano;

import static org.junit.Assert.*;

import org.junit.Test;

public class Interval2Dtest {

    @Test
    public void testTostringworks() {
        Interval1D interval= new Interval1D(-1,0);

        Interval1D interval2= new Interval1D(1,5);
        
        Interval2D interval2d= new Interval2D(interval,interval2);
        
        assertTrue(interval2d.toString().equals("[-1..0]×[1..5]"));
    }
    
    @Test 
    public void testEqualsworkscorrectly(){

        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,5);
        

        Interval1D interval3= new Interval1D(-1,1);

        Interval1D interval4= new Interval1D(1,5);
        
        Interval2D interval2d1= new Interval2D(interval,interval2);
        Interval2D interval2d2= new Interval2D(interval3,interval4);
        
        assertTrue(interval2d1.equals(interval2d2));
    }
    
    
    @Test 
    public void testgetteriX(){
        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,5);
        
        Interval1D interval3=new Interval1D(-1,1);
        

        Interval2D interval2d1= new Interval2D(interval,interval2);
      
       assertEquals(interval2d1.iX(), interval3);
    }
    
    @Test 
    public void testgetteriY(){
        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,5);
        
        Interval1D interval3=new Interval1D(1,5);
        

        Interval2D interval2d1= new Interval2D(interval,interval2);
      
       assertEquals(interval2d1.iY(), interval2);

    }
    
    @Test 
    public void containsacorrectpair(){
        

        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,5);
        
        Interval1D interval3=new Interval1D(1,5);
        

        Interval2D interval2d1= new Interval2D(interval,interval2);
        
        assertTrue(interval2d1.contains(-1, 5));
    }
    
    @Test 
    public void sizeof2Discorrect(){
        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,4);
        
        Interval1D interval3=new Interval1D(1,5);
        

        Interval2D interval2d1= new Interval2D(interval,interval2);
        
        assertTrue(interval2d1.size()==  12);
        
        
        
    }
    
    @Test 
    public void sizeofIntersectionof2Discorrect(){

        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,3);
        

        Interval1D interval3= new Interval1D(2,10);

        Interval1D interval4= new Interval1D(10,14);
        
        Interval2D interval2d1= new Interval2D(interval,interval2);
        Interval2D interval2d2= new Interval2D(interval3,interval4);
        
        assertEquals(interval2d1.sizeOfIntersectionWith(interval2d2), 0,0);
        
    }
    
    @Test
    public void testboundingUnion2discorrect(){

        Interval1D interval= new Interval1D(-1,1);

        Interval1D interval2= new Interval1D(1,3);
        

        Interval1D interval3= new Interval1D(2,10);

        Interval1D interval4= new Interval1D(10,14);
        
        Interval1D interval5= new Interval1D(-1,10);

        Interval1D interval6= new Interval1D(1,14);
        

        Interval2D interval2d1= new Interval2D(interval,interval2);
        Interval2D interval2d2= new Interval2D(interval3,interval4);
        Interval2D interval2d3= new Interval2D(interval5,interval6);
        
        assertEquals(interval2d2.boundingUnion(interval2d2), interval2d2);
        
    }
    
    @Test 
    public void isUnionablewithiscorrect(){
        

        Interval1D interval= new Interval1D(1,5);

        Interval1D interval2= new Interval1D(5,10);
        

        Interval1D interval3= new Interval1D(2,6);

        Interval1D interval4= new Interval1D(6,11);
        
        Interval2D interval2d1= new Interval2D(interval,interval2);
        Interval2D interval2d2= new Interval2D(interval3,interval4);
        System.out.println(interval2d1.boundingUnion(interval2d2).size());
        System.out.println((interval2d1.size() + interval2d2.size() - interval2d1.sizeOfIntersectionWith(interval2d2)));
        assertTrue(interval2d1.isUnionableWith(interval2d2));
        
    }
    
    @Test
    public void Unioniscorrect(){
        
        Interval1D interval= new Interval1D(1,5);

        Interval1D interval2= new Interval1D(5,10);
        

        Interval1D interval3= new Interval1D(2,6);

        Interval1D interval4= new Interval1D(6,11);
        
        Interval1D interval5= new Interval1D(1,5);

        Interval1D interval6= new Interval1D(5,11);
        
        Interval2D interval2d1= new Interval2D(interval,interval2);
        Interval2D interval2d2= new Interval2D(interval3,interval4);

        Interval2D interval2d3= new Interval2D(interval5,interval6);
        
        assertEquals(interval2d1.union(interval2d2),interval2d3);
        
    }

}
