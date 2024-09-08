package Main;

import java.util.*;
import java.io.*;
import java.lang.*;

public class Booking {
	
	public static List<Taxi> createTaxis(int n)
    {
        List<Taxi> taxis = new ArrayList<Taxi>();
        // create taxis
        for(int i=1 ;i <=n;i++)
        {
            Taxi t = new Taxi();
            taxis.add(t);
            //****************************************************
            // now I alloted data not added data
            // while taking output it shows like this 
            //[Main.Taxi@2133c8f8, Main.Taxi@43a25848, Main.Taxi@3ac3fd8b, Main.Taxi@5594a1b5]
        }
        return taxis;
    }
	
	public static List<Taxi> getFreeTaxis(List<Taxi> taxis,int pickupTime,char pickupPoint)
    {
        List<Taxi> freeTaxis = new ArrayList<Taxi>();
        for(Taxi t : taxis)
        {   
            //1) taxi  free or not
            //2) Math.abs((t.currentSpot - '0') - (pickupPoint - '0')) <= pickupTime - t.freeTime) =>For characters, you should use a different approach, such as converting them to their positional values (e.g., 'A' -> 1, 'B' -> 2).
            // checking whether they can reach that time
            if(t.freeTime <= pickupTime && (Math.abs((t.currentSpot - '0') - (pickupPoint - '0')) <= pickupTime - t.freeTime))
            {
            	freeTaxis.add(t); 
            }
            	

        }
        return freeTaxis;
    }
	
	public static void bookTaxi(int customerID,char pickupPoint,char dropPoint,int pickupTime,List<Taxi> freeTaxis)
    {
        int min = 999;
        int distanceBetweenpickUpandDrop = 0;
        int earning = 0;
        int nextfreeTime = 0;
        char nextSpot = 'Z';
        Taxi bookedTaxi = null;
        String tripDetail = "";
        
        for(Taxi t : freeTaxis)
        {
            int distanceBetweenCustomerAndTaxi = Math.abs((t.currentSpot - '0') - (pickupPoint - '0')) * 15;
            if(distanceBetweenCustomerAndTaxi < min)
            {
                bookedTaxi = t;
                distanceBetweenpickUpandDrop = Math.abs((dropPoint - '0') - (pickupPoint - '0')) * 15;
                earning = (distanceBetweenpickUpandDrop-5) * 10 + 100;
                int dropTime  = pickupTime + distanceBetweenpickUpandDrop/15;
                nextfreeTime = dropTime;
                nextSpot = dropPoint;

                tripDetail = customerID + "               " + customerID + "          " + pickupPoint +  "      " + dropPoint + "       " + pickupTime + "          " +dropTime + "           " + earning;
                min = distanceBetweenCustomerAndTaxi;
            }
            
        }
      //setting corresponding details to allotted taxi
        bookedTaxi.setDetails(true,nextSpot,nextfreeTime,bookedTaxi.totalEarnings + earning,tripDetail);
        //BOOKED SUCCESSFULLY
        System.out.println("Taxi " + bookedTaxi.id + " booked");

    }

	public static void main(String[] args) {
		//create 4 taxis now but it should work for any number of taxi and so we 
		// creating function with n arguments
		//*******************************************************************
        List<Taxi> taxis = createTaxis(2);
        //*******************************************************************
        //here the data type we mentioned is taxi that is nothing but class that
        // have constructor
        // System.out.println("Mahi Krish");
    	// System.out.println(taxis);
        Scanner s = new Scanner(System.in);
        int id = 1; // id must for all booking
        
        boolean loop = true;
        
        while(loop)
        {
        	System.out.println("1 - > Book Taxi");
            System.out.println("2 - > Print Taxi details");
            System.out.println("3 - > EXIT");
            int choice = s.nextInt();
            switch(choice)
            {
	            case 1:
	            {
		            //get details from customers
		            
		            int customerID = id;
		            System.out.println("Enter Pickup point from A,B,C,D,E,F");
		            char pickupPoint = s.next().charAt(0);
		            System.out.println("Enter Drop point");
		            char dropPoint = s.next().charAt(0);
		            System.out.println("Enter Pickup time");
		            int pickupTime = s.nextInt();
		
		            //check if pickup and drop points are valid
		            if(pickupPoint < 'A' || dropPoint > 'F' || pickupPoint > 'F' || dropPoint < 'A')
		            {
		                System.out.println("Valid pickup and drop are A, B, C, D, E, F. Exitting");
		               // return;
		                break;
		            }
		            // get all free taxis that can reach customer on or before pickup time
		            //**************************************************
		            List<Taxi> freeTaxis = getFreeTaxis(taxis,pickupTime,pickupPoint);
		
		            //no free taxi means we cannot allot, exit!
		            if(freeTaxis.size() == 0)
		            {
		                System.out.println("No Taxi can be alloted. Exitting");
		               // return;
		                break;
		            }    
		
		             // Traditional Comparator
                    Collections.sort(freeTaxis, new Comparator<Taxi>() {
                        @Override
                        public int compare(Taxi t1, Taxi t2) {
                            return Integer.compare(t1.totalEarnings, t2.totalEarnings);
                        }
                    });
                    
		
		            //get free Taxi nearest to us
		            bookTaxi(id,pickupPoint,dropPoint,pickupTime,freeTaxis);
		            id++;
		            
	            }
	            break;
	            case 2:
	            {
	                //two functions to print details
	                 for(Taxi t : taxis)
	                    t.printTaxiDetails();
	                 for(Taxi t : taxis)
	                    t.printDetails();
	                
	            }
	            break;
//	            case 3:
//	            {
//	                Taxi t = new Taxi();
//	                
////	                for(int i=1 ;i <=n;i++)
////	                {
//	                    
//	                    System.out.println(t.trips);
//	                    
////	                }
//	                
//	            }
//	            break;
	            case 3:
                {
                    loop = false;
                }
                break;
                default:
                break;
	            

           

          
            }
        }

	}

}
