package de.as.javabot.comunio;

public class ComunioPlayer implements Player {

	private String name;
	private String club;
	private String position;
	private String marketValue;
	private String points;
	private String status;
	private String photoWebLink;
	
	public String toString(){
		return name + " ist " + status;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPhotoWebLink(String photoWebLink) {
		this.photoWebLink = photoWebLink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}

	private String weblink;
	public static final String unkown = "unknown information";
	
	@Override
	public String getName() {
		if(name!=null){
			return name;
		}
		return unkown;
	}

	@Override
	public String getClub() {
		if(club!=null){
			return club;
		}
		return unkown;
	}

	@Override
	public String getPosition() {
		if(position!=null){
			return position;
		}
		return unkown;
	}

	@Override
	public String getMarketValue() {
		if(marketValue!=null){
			return marketValue;
		}
		return unkown;
	}

	@Override
	public String getPoints() {
		if(points!=null){
			return points;
		}
		return unkown;
	}

	@Override
	public String getStatus() {
		if(status!=null){
			return status;
		}
		return unkown;
	}

	@Override
	public String getPhotoWebLink() {
		if(photoWebLink!=null){
			return photoWebLink;
		}
		return unkown;
	}

	@Override
	public String getComunioPlayerPage() {
		if(weblink!=null){
			return weblink;
		}
		return unkown;
	}

}
