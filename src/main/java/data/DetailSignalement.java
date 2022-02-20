package data;

import java.sql.Connection;
import java.sql.Statement;

public class DetailSignalement {
	int id;
	int idSignalement;
	String photo;
	
	public DetailSignalement() {}
	public DetailSignalement(int ID, int IDS, String PHOTO) {
		setId(ID);
		setIdSignalement(IDS);
		setPhoto(PHOTO);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSignalement() {
		return idSignalement;
	}
	public void setIdSignalement(int idSignalement) {
		this.idSignalement = idSignalement;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public int insert(Connection c) throws Exception {
		try{
	            Statement stm=c.createStatement();
	            System.out.println("INSERT INTO DetailsSignalement VALUES(NULL, '"+this.getIdSignalement()+"', '"+this.getPhoto()+"')");
	            stm.executeUpdate("INSERT INTO DetailsSignalement VALUES(NULL, '"+this.getIdSignalement()+"', '"+this.getPhoto()+"')");
	            stm.close();  
		}
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
		return 1;
	}
}
