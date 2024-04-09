import java.util.ArrayList;

public class OneLaneBridge extends Bridge{
    private Object enter = new Object();
    private Object exit = new Object();

    private int bridgeLimit;

    public OneLaneBridge(int limit){
        this.bridgeLimit = limit;
    }

    public void arrive(Car car) throws InterruptedException{
        synchronized(enter){
            //wait to cross
            while(!checkCross(car)){
                enter.wait();
            }
            
            //start crossing
            car.setEntryTime(currentTime);

            synchronized(this){
                //add car to the bridge and increment time
                bridge.add(car);
                currentTime++;

                System.out.print("Bridge (dir=" + direction + "): ");
                System.out.println(bridge);
            }
        }
    }

    public void exit(Car car) throws InterruptedException{
        synchronized(exit){
            //check to see if THIS car is ready to leave
            while(!car.equals(bridge.get(0))){
                exit.wait();
            }

            synchronized(this){
                bridge.remove(0); //remove the car from the front of the bridge

                System.out.print("Bridge (dir=" + direction + "): ");
                System.out.println(bridge);
            }

            exit.notifyAll(); //There might be more waiting in exit

            synchronized(enter){
                enter.notifyAll(); //There might be more waiting in enter
            }
        }
    }

    public boolean checkCross(Car car){
        //The bridge is empty, can just go
        if(bridge.size() == 0){
            direction = car.getDirection(); //set new direction for the bridge
            return true;
        }

        //The bridge is going the right way and there is room
        if(bridge.size() < bridgeLimit && direction == car.getDirection()){
            return true;
        }

        //The bridge is going the wrong way
        return false;
    }
}