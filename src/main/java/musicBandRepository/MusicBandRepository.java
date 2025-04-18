package musicBandRepository;

import entities.MusicBand;
import exceptions.MusicBandNotFound;
import exceptions.MusicBandWithIdAlreadyExists;

import java.util.List;
import java.util.LinkedList;

public class MusicBandRepository {
    private final List<MusicBand> bandList = new LinkedList<>();

    public List<MusicBand> getAll() {
        return this.bandList;
    }

    public MusicBand getById(long id) {
        return this.bandList.stream().filter(el -> el.getId() == id).findAny().orElse(null);
    }

    public void updateById(long id, MusicBand updatedFields) throws MusicBandNotFound {
        MusicBand currentMusicBand = getById(id);
        if (currentMusicBand == null) {
            throw new MusicBandNotFound();
        }
        currentMusicBand.update(updatedFields);
    }

    public void create(MusicBand entity) throws MusicBandWithIdAlreadyExists {
        MusicBand musicBandWithId = this.getById(entity.getId());
        if (musicBandWithId != null) {
            throw new MusicBandWithIdAlreadyExists();
        }
        bandList.add(entity);
    }
}
