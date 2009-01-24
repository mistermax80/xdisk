package xdisk.persistence.database;


import java.util.Collection;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Extension;

public class ExtensionController {

private ExtensionController() {
super();
}

public static void load(Extension object) throws PersistenceException{ExtensionDAO.load(object);}

public static void insert(Extension object) throws PersistenceException{ExtensionDAO.insert(object);}

public static void delete(Extension object) throws PersistenceException{ExtensionDAO.delete(object);}

public static void update(Extension object) throws PersistenceException{ExtensionDAO.update(object);}

public static int removeAll() throws PersistenceException{
return ExtensionDCS.removeAll();
}

public static Collection<Extension> getAll() throws PersistenceException{
return ExtensionDCS.getAll();
}

}
