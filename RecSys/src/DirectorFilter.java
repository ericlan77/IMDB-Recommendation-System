public class DirectorFilter implements Filter {
	private String[] directors;
	
	public DirectorFilter(String directors) {
		this.directors = directors.split(",");
	}
	
	@Override
	public boolean satisfies(String id) {
		for(String d: directors) {
			if(MovieDatabase.getDirector(id).indexOf(d.trim())!=-1)
				return true;
		}
		return false;
	}	
}
