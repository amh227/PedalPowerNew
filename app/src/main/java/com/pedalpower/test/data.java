package com.pedalpower.test;


        import static java.lang.Math.abs;
        import java.util.ArrayList;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.util.Scanner;

/**
 *Angela Hoeltje
 * data()
 *      void addData(double newPoint)
 *      boolean verify(double newData)
 *      void getData ()
 *      void lowPassFilter()
 *
 */
public class data {
    ArrayList myPoints=new ArrayList();                 //when myPOints==max -> compress -> zeroout
    ArrayList badData=new ArrayList();

    int totalPoints=0;//has good and bad
    int badCounter;
    int errorCount=0;
    //initialized to 1 to avoid divide by zero
    //high = 25.18 in W/kg from world class max https://www.trainingpeaks.com/blog/power-profiling/
    //25.18 W/kg * 1/2.20462 kg/lb = 11.42 W/lb => 12.00 W/lb :: Rounded up for potential future improvement
    double high = 12.00;    //W/lb
    double lastData=0;


    void addData(double newPoint){
        System.out.println("total points: "+totalPoints+"       totalPoints+1: "+(totalPoints+1));
        if (this.verify(newPoint)==true){
            this.myPoints.add(newPoint);
            if (badData.isEmpty()==false){
                badPoints bp=(badPoints)this.badData.get(0);
                if (this.totalPoints+1==bp.index +5){
                    this.lowPassFilter();
                }
            }
        }
        else{ System.out.println("Data in Error :"+ newPoint); }
    }
    boolean verify(double newData){ //always returns true:: used for bad data input
        if(newData<0){
            System.out.println("---Negative value acquired");
            System.out.println("Fill index"+this.totalPoints+1);
            badPoints bp=new badPoints();
            bp.index=totalPoints+1;
            bp.val=newData;
            this.badData.add(bp);
            errorCount++;
            if (this.badData.get(0)!=null){            //make sure there is bad data
                badPoints nextbp= (badPoints)this.badData.get(0);
                int badIndex=nextbp.index;
                System.out.println("Looking for: "+badIndex+ "   At:"+this.totalPoints+1);
                if (badIndex==this.totalPoints+1+5){ this.lowPassFilter(); }
                return true;
            }
        }
        if(newData>high){
            System.out.println("Number too high : "+newData+" > max: "+high);   //output for testing
            errorCount++;                                                       //increment error totalPoints+1
            badPoints bp=new badPoints();                                       //create new bad point
            bp.index=totalPoints+1;                                                     //save badpoints's index
            bp.val=newData;                                                     //save bad point value
            this.badData.add(bp);                                               //add to end of arraylist(check value of list at 0 for next)
            //first index to look for
            badPoints nextbp= (badPoints)this.badData.get(0);               //check first array location
            int badIndex=nextbp.index;
            System.out.println("Looking for: "+badIndex+ "   At:"+this.totalPoints+1);
            return true; //return true, because we save bad data for low pass filter test
        }
        return true;
    }
    void getData () throws FileNotFoundException{
        //initialize first bad data point
        //implement all bluetooth to get data UNLESS testing
        if(MainActivity.testing==true){
            //get value from text file to test
            try{
                File temp=new File("sample.txt");
                Scanner inFile = new Scanner(temp);
                while (inFile.hasNext()){
                    (this.totalPoints)++;//remove for better testing
                    this.addData(inFile.nextDouble());
                }
                inFile.close();
            }
            catch(FileNotFoundException exception){
                System.out.println("COuld not find file: ");
            }
        }
    }
    void lowPassFilter(){
        System.out.println ("In low pass filter");
        //get all relative data points and find avereage
        int i;
        double temp, sumx=0;
        int goodDataCount=11;       //5 points before + badpoint+ 5 points after
        for(i=0;i<11;i++){
            try{
                temp=(double)this.myPoints.get(this.totalPoints-i-5);
                sumx=sumx+temp;
                System.out.println("Sum: "+sumx+"="+temp+" + "+ (sumx - temp));
            }
            catch(ArrayIndexOutOfBoundsException exception){
                //there is another hole in data
                goodDataCount--;
            }
        }
        //get average
        temp=sumx/goodDataCount;
        this.myPoints.set(this.totalPoints-6, temp);//fill with new floating average
        this.badData.remove(0);
    }


    /*
    //number of raw data points/ new points in array
    //i.e. 8 points raw to one point displayed=8
    //must then average next 8 points to add point to array
    //when array fills compress every 2 points to 1, compression ratio=compressionratio*2
    //numDisplayPoints=numDisplayPOints/
    */
    int maxPoints = 128;        //SHould be statically defined when agreed upon
    int compressionRatio=1;     //ratio starts at 1
    int sizeCompressed=0;       //
    int sizeNew;
    int numDisplayPoints;
    ArrayList outputPoints=new ArrayList();
    ArrayList Compressedpoints=new ArrayList();
    void compress(){


    }
}











