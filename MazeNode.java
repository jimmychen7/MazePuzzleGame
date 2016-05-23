public class MazeNode implements Comparable<MazeNode>{

	public MazeNode(boolean isPath, int x, int y){
		this.isPath = isPath;
		this.x = x;
		this.y = y;
		isSolution = false;
		this.visited = false;
		this.hasPowerUp = false;
	}
	public void setIsSolution(boolean isSolution){
		this.isSolution = isSolution;
	}
	
	public void randomizePowerUps(){
		this.hasPowerUp = rand(0.05);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hasPowerUp == null) ? 0 : hasPowerUp.hashCode());
		result = prime * result + ((isPath == null) ? 0 : isPath.hashCode());
		result = prime * result + ((isSolution == null) ? 0 : isSolution.hashCode());
		result = prime * result + ((visited == null) ? 0 : visited.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean getIsPath(){
		return isPath;
	}
	public boolean getIsSolution(){
		return isSolution;
	}
	public boolean isVisited(){
		return this.visited;
	}
	public boolean getHasPowerUp(){
		return this.hasPowerUp;
	}

	public String toString(){
		return "(" + x + "," + y + ")" + " path: " + isPath + " sol: "+ isSolution;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MazeNode other = (MazeNode) obj;
		if (hasPowerUp == null) {
			if (other.hasPowerUp != null)
				return false;
		} else if (!hasPowerUp.equals(other.hasPowerUp))
			return false;
		if (isPath == null) {
			if (other.isPath != null)
				return false;
		} else if (!isPath.equals(other.isPath))
			return false;
		if (isSolution == null) {
			if (other.isSolution != null)
				return false;
		} else if (!isSolution.equals(other.isSolution))
			return false;
		if (visited == null) {
			if (other.visited != null)
				return false;
		} else if (!visited.equals(other.visited))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	@Override
	public int compareTo(MazeNode other) {
		if(this.y >other.y)
			return 1;
		else if(this.y <other.y)
			return -1;
		else if(this.y == other.y && this.x > other.x)
			return 1;
		else if(this.y == other.y && this.x < other.x)
			return -1;
		else
			return 0;

	}
	public void setIsPath(boolean b) {
		this.isPath = b;
	}
	public void setVisited(boolean v){
		this.visited = v;
	}
	public void setPowerUp(boolean a){
		this.hasPowerUp = a;
	}
	
	private boolean rand(double probabilityTrue)
	{
	    return Math.random() >= 1.0 - probabilityTrue;
	}
	
	private Boolean isPath;
	private Boolean isSolution;
	private Boolean hasPowerUp;
	private Boolean visited;
	private int x;
	private int y;

}