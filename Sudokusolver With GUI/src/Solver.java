import java.io.File;

public class Solver {

	protected Field field;

	public Solver() {
		
		field = new Field();
		field.fromFile(" ");
	}
	
	public void sudokusolve() {
	try {
		solve(field, 0, 0);
	} catch (SolvedException e) {
		e.printStackTrace();
	}
	
}
public void ModelForFile(File inputFile) { // get field to GUI
		field = new Field();
		field.fromFile(inputFile.getAbsolutePath());
	}

public static void solve(Field f, int i, int j) throws SolvedException {
   if (i>=Field.SIZE){
	 
	   throw new SolvedException();  
   }
   
   else {
	   if (f.isEmpty(i, j)){
		   for (int val =1; val<=9; val++){
			   if (f.tryValue(val,i,j)){
				   if (j>=Field.SIZE-1){
					   solve (f,i+1,0);
					 
				   }
			   		else {
			   			solve(f,i,j+1); 
			   	}
		   	}
		  
		   }
		   f.clear(i, j);
	   }
   else {
	   if (j>=Field.SIZE-1){
		   solve(f,i+1,0);
	   }
	   else 
		   {
		   solve(f,i,j+1);
		   }
	   
   }
   }
}

}