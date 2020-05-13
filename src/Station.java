
public class Station {
	String nomStation;
	int x;
	int y;
	public Station() {
		this.nomStation="BLANK";
		this.x=0;
		this.y=0;
	}
	public Station(String n,int x,int y) {
		this.nomStation=n;
		this.x=x;
		this.y=y;
	}
	public double Distance(Station arr) {
		return Math.sqrt(Math.pow(arr.x-x, 2)+Math.pow(arr.y-y, 2));
	}
	@Override
	public String toString() {
		return nomStation;
	}
	
}
