package clinicReservationSystem;

import java.io.Serializable;
import java.util.LinkedList;

public class Hospital implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private final int id;
    private String name;
    private LinkedList<Section> sections;

    public Hospital(int id, String name) {
        this.id = id;
        this.name = name;
        this.sections = new LinkedList<Section>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Section> listSections() {
        return sections;
    }
    
    public Section getSection(int id) {
        for(Section section : sections){
            if(section.getId() == id)
                return section;
        }
        return null;
    }
        
    @SuppressWarnings("unused")
	private Section getSection(String name) {
        for(Section section : sections){
             if (section.getName().equals(name)){
                 return section;
             }
        }
        return null;
    }

	public void addSection(Section section) throws DuplicateInfoException {
        for (Section sec : sections) {
            if (sec.getId() == section.getId()) {
                throw new DuplicateInfoException("Bu id'ye sahip bir bölüm zaten mevcut.");
            }
        }
        this.sections.add(section);
    }

    @Override
    public String toString() {
        return "Hospital name: " + name + ", id: " + id;
    }
}